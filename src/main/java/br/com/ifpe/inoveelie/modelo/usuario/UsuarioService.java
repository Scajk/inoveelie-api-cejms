package br.com.ifpe.inoveelie.modelo.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ifpe.inoveelie.modelo.mensagens.EmailService;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UsuarioService(UsuarioRepository usRepository, AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.repository = usRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public class SenhasNaoConferemException extends RuntimeException {
        public SenhasNaoConferemException(String message) {
            super(message);
        }
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        // 1. Validação de Senhas
        if (!usuario.getPassword().equals(usuario.getConfirmaPassword())) {
            throw new SenhasNaoConferemException("A senha e a confirmação de senha não são idênticas.");
        }

        // 2. Codificação da Senha
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setConfirmaPassword(passwordEncoder.encode(usuario.getConfirmaPassword()));

        // 3.1 Preparação para Ativação, com código numérico de 6 dígitos
        String activationCode = generateActivationCode();
        usuario.setCodigoAtivacao(activationCode);
        usuario.setAtivo(false);

        // 4. Configurações Padrão
        usuario.setHabilitado(Boolean.TRUE);// ctrl + clique em setHabilitado
        usuario.setVersao(1L);
        usuario.setDataCriacao(LocalDate.now());

        // 5. Salvamento do Usuário
        Usuario usuarioSalvo = repository.save(usuario);

        // 6. Envio de E-mail de Ativação
        //emailService.enviarEmailConfirmacaoCadastroUsuario(usuarioSalvo);

        return usuarioSalvo;
    }

    private String generateActivationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); 
        return String.valueOf(code);
    }

    @Transactional
    public boolean activateUser(String email, String activationCode) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getCodigoAtivacao() != null && usuario.getCodigoAtivacao().equals(activationCode)) {
                usuario.setAtivo(true);
                usuario.setCodigoAtivacao(null); 
                repository.save(usuario);
                return true;
            }
        }

        return false;
    }

    @Transactional
    public Usuario iniciarRecuperacaoSenha(String email) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String resetToken = generateRecupationCode();
            usuario.setResetToken(resetToken);
            repository.save(usuario);

            emailService.enviarEmailRecuperacaoSenha(usuario);

            return usuario;
        }

        return null;

    }

    private String generateRecupationCode() {
        Random random = new Random();
        int codeRec = 100000 + random.nextInt(900000);
        return String.valueOf(codeRec);
    }

    // Método para redefinir a senha usando o token
    @Transactional
    public boolean redefinirSenha(String email, String token, String novaSenha) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getResetToken() != null && usuario.getResetToken().equals(token)) {
                usuario.setPassword(novaSenha);
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuario.setResetToken(null); 
                repository.save(usuario);
                return true;
            }
        }

        return false;
    }

    public List<Usuario> listarTodos() {

        return repository.findAll();
    }

    public Usuario obterPorID(Long id) {

        return repository.findById(id).get();
    }

    public Usuario obterPorUsername(String username) {

        return repository.findByUsername(username).get();
    }

    @Transactional
    public Usuario update(Long id, String nome, String sobrenome) {

        Usuario usuario = repository.findById(id).get();
        usuario.setNome(nome);
        usuario.setSobrenome(sobrenome);


        usuario.setVersao(usuario.getVersao() + 1);
        return repository.save(usuario);
    }

    @Transactional
    public void update(Long id, Usuario usuarioAlterado) {

        Usuario usuario = repository.findById(id).get();
        usuario.setNome(usuarioAlterado.getNome());
        usuario.setSobrenome(usuarioAlterado.getSobrenome());
        usuario.setEmail(usuarioAlterado.getEmail());
        usuario.setPassword(usuarioAlterado.getPassword());
        usuario.setConfirmaPassword(usuarioAlterado.getConfirmaPassword());

        usuario.setVersao(usuario.getVersao() + 1);
        repository.save(usuario);
    }

    @Transactional
    public Usuario iniciarExclusaoConta(String email) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String deleteToken = generateDeletionCode();
            usuario.setDeleteToken(deleteToken);
            repository.save(usuario);

            emailService.enviarEmailExclusaoConta(usuario);

            return usuario;
        }

        return null;
    }

    private String generateDeletionCode() {
        Random random = new Random();
        int codeDel = 100000 + random.nextInt(900000);
        return String.valueOf(codeDel);
    }

    @Transactional
    public boolean confirmarExclusaoConta(String email, String token) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            
            if (usuario.getDeleteToken() != null && usuario.getDeleteToken().equals(token)) {
                repository.delete(usuario); 
                return true;
            }
        }

        return false;
    }

    public Usuario obterUsuarioAtual() {
    // Obtém o principal do contexto de segurança
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    // Busca o usuário pelo username
    return repository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // <<<<<<<<<<<<<<<<<<<<< EMPRESA >>>>>>>>>>>>>>>>>>>>>>>>

    @Transactional
    public Empresa adicionarEmpresa(Long usuarioId, Empresa empresas) {

        Usuario usuario = this.obterPorID(usuarioId);

        // Primeiro salva o EnderecoCliente:

        empresas.setUsuario(usuario);
        empresas.setHabilitado(Boolean.TRUE);
        empresaRepository.save(empresas);

        // Depois acrescenta o endereço criado ao cliente e atualiza o cliente:

        List<Empresa> listaEmpresa = usuario.getEmpresas();

        if (listaEmpresa == null) {
            listaEmpresa = new ArrayList<Empresa>();
        }

        listaEmpresa.add(empresas);
        usuario.setEmpresas(listaEmpresa);
        usuario.setVersao(usuario.getVersao() + 1);
        repository.save(usuario);

        return empresas;
    }

    @Transactional
    public Empresa atualizarEmpresa(Long id, Empresa empresasAlterado) {

        Empresa empresas = empresaRepository.findById(id).get();
        empresas.setNomeEmpresa(empresasAlterado.getNomeEmpresa());
        empresas.setCepEmpresa(empresasAlterado.getCepEmpresa());

        return empresaRepository.save(empresas);
    }

    @Transactional
    public void removerEmpresa(Long id) {

        Empresa empresas = empresaRepository.findById(id).get();
        empresas.setHabilitado(Boolean.FALSE);
        empresaRepository.save(empresas);

        Usuario usuario = this.obterPorID(empresas.getUsuario().getId());
        usuario.getEmpresas().remove(empresas);
        usuario.setVersao(usuario.getVersao() + 1);
        repository.save(usuario);
    }

    public Usuario authenticate(String username, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        return repository.findByUsername(username).orElseThrow();
    }

    @Transactional
    public Usuario findByUsername(String username) {

        return repository.findByUsername(username).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByUsername(username).get();
    }

}
