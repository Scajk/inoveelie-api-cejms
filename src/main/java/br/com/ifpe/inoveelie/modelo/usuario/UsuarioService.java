package br.com.ifpe.inoveelie.modelo.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    
    /* @Autowired
    private UsuarioRepository usuarioRepository; */

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

    //<<<<<<<<<<<<<<< EXCEPTIONS >>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<< EXCEPTIONS >>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<< EXCEPTIONS >>>>>>>>>>>>>>>>

    //FINALIZAR 3

    

    /* 
    3
     * package br.com.ifpe.inoveelie.service;

import br.com.ifpe.inoveelie.exception.SenhaInvalidaException;
import br.com.ifpe.inoveelie.validator.SenhaValidator;

public class UsuarioService {

    private final SenhaValidator senhaValidator;

    public UsuarioService(SenhaValidator senhaValidator) {
        this.senhaValidator = senhaValidator;
    }

    public void criarUsuario(String senha) {
        try {
            senhaValidator.isValid(senha, null);
            // Continuar com a criação do usuário
        } catch (SenhaInvalidaException e) {
            // Lidar com a exceção e informar ao usuário
            System.out.println(e.getMessage());
        }
    }
}
    */

    public class SenhasNaoConferemException extends RuntimeException {
        public SenhasNaoConferemException(String message) {
            super(message);
        }
    }

    public class EmailJaExistenteException extends RuntimeException {
        public EmailJaExistenteException(String message) {
            super(message);
        }
    }

    public class SenhaInvalidaException extends RuntimeException {
        public SenhaInvalidaException(String message) {
            super(message);
        }
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        // 1. Validação de Senhas
        if (!usuario.getPassword().equals(usuario.getConfirmaPassword())) {
            throw new SenhasNaoConferemException("A senha e a confirmação de senha não são idênticas.");
        }

        if (repository.existsByEmail(usuario.getEmail())) {
            throw new EmailJaExistenteException("Já existe um usuário cadastrado com este e-mail.");
        }

        // 2. Codificação da Senha
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // 3. Preparação para Ativação
        String activationCode = UUID.randomUUID().toString();
        usuario.setCodigoAtivacao(activationCode);
        usuario.setAtivo(false); // Definindo a conta como inativa até a ativação

        // 4. Configurações Padrão
        usuario.setHabilitado(Boolean.TRUE);
        usuario.setVersao(1L);
        usuario.setDataCriacao(LocalDate.now());

        // 5. Salvamento do Usuário
        Usuario usuarioSalvo = repository.save(usuario);

        // 6. Envio de E-mail de Ativação
        emailService.enviarEmailConfirmacaoCadastroUsuario(usuarioSalvo);

        return usuarioSalvo;
    }

    /*
     * @Transactional
     * public Usuario save(Usuario usuario) {
     * if (!usuario.getPassword().equals(usuario.getConfirmaPassword())) {
     * throw new
     * SenhasNaoConferemException("A senha e a confirmação de senha não são idênticas."
     * );
     * }
     * 
     * usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
     * usuario.setHabilitado(Boolean.TRUE);
     * usuario.setVersao(1L);
     * usuario.setDataCriacao(LocalDate.now());
     * Usuario usuarioSalvo = repository.save(usuario);
     * 
     * emailService.enviarEmailConfirmacaoCadastroUsuario(usuario);
     * 
     * return usuarioSalvo;
     * }
     */

    /*
     * @Transactional
     * public Usuario save(Usuario usuario) {
     * // Geração do código de ativação
     * String activationCode = UUID.randomUUID().toString();
     * usuario.setCodigoAtivacao(activationCode);
     * 
     * // Salvar o usuário com o código de ativação
     * Usuario savedUser = repository.save(usuario);
     * 
     * // Enviar email de ativação
     * emailService.enviarEmailConfirmacaoCadastroUsuario(savedUser);
     * 
     * return savedUser;
     * }
     */

    @Transactional
    public boolean activateUser(String email, String activationCode) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Verifica se o código de ativação coincide
            if (usuario.getCodigoAtivacao().equals(activationCode)) {
                usuario.setAtivo(true);
                usuario.setCodigoAtivacao(null); // Remove o código de ativação após uso
                repository.save(usuario);
                return true;
            }
        }

        return false;
    }

    // Método para iniciar o processo de recuperação de senha
    @Transactional
    public void iniciarRecuperacaoSenha(String email) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String resetToken = UUID.randomUUID().toString();
            usuario.setResetToken(resetToken);
            repository.save(usuario);

            // Envia email com o token de redefinição
            emailService.enviarEmailRecuperacaoSenha(usuario);
        }
    }

    // Método para redefinir a senha usando o token
    @Transactional
    public boolean redefinirSenha(String email, String token, String novaSenha) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Verifica se o token coincide
            if (usuario.getResetToken() != null && usuario.getResetToken().equals(token)) {
                usuario.setPassword(novaSenha);
                usuario.setResetToken(null); // Remove o token após uso
                repository.save(usuario);
                return true;
            }
        }

        return false;
    }

    /*
     * //<<<<<<<<<<<<<<<<<<<<<<<<< SOLICTAR RECUPERAÇÃO DE SENHA
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
     * //<<<<<<<<<<<<<<<<<<<<<<<<< SOLICTAR RECUPERAÇÃO DE SENHA
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
     * //<<<<<<<<<<<<<<<<<<<<<<<<< SOLICTAR RECUPERAÇÃO DE SENHA
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
     * 
     * public void solicitarRecuperacaoSenha(String email) {
     * Usuario usuario = usuarioRepository.findByUsername(email)
     * .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
     * 
     * String resetCode = generateResetCode(); // Método que gera o código de
     * recuperação
     * usuario.setPasswordResetCode(resetCode);
     * usuarioRepository.save(usuario);
     * 
     * emailService.enviarEmailRecuperacaoSenha(usuario, resetCode); // Implementar
     * este método no EmailService
     * }
     * 
     * private String generateResetCode() {
     * Random random = new Random();
     * int code = 100000 + random.nextInt(900000); // Gera um código de 6 dígitos
     * return String.valueOf(code);
     * }
     * 
     * //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< REDEFINIR SENHA
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
     * //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< REDEFINIR SENHA
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
     * //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< REDEFINIR SENHA
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
     * 
     * @Transactional
     * public void redefinirSenha(String email, String resetCode, String novaSenha)
     * {
     * Usuario usuario = usuarioRepository.findByUsername(email)
     * .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
     * 
     * if (usuario.getPasswordResetCode().equals(resetCode)) {
     * usuario.setPassword(passwordEncoder.encode(novaSenha));
     * usuario.setPasswordResetCode(null); // Limpa o código de recuperação
     * usuarioRepository.save(usuario);
     * } else {
     * throw new RuntimeException("Código de recuperação inválido");
     * }
     * }
     */

    /* public Usuario save(Usuario usuario, boolean ativo) {
        // Gera um código de ativação
        String activationCode = generateActivationCode();
        usuario.setActivationCode(activationCode);
        usuario.setActive(false); // Conta não ativada inicialmente
        Usuario savedUsuario = repository.save(usuario);

        // Envia o e-mail com o código de ativação
        emailService.enviarEmailCodigoAtivacao(usuario, activationCode);

        return savedUsuario;
    } */

    private String generateActivationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Gera um código de 6 dígitos
        return String.valueOf(code);
    }

    
    /* public boolean activateUser(String email, String activationCode) {
     Usuario usuario = usuarioRepository.findByUsername(email)
     .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
     
     if (usuario.getActivationCode().equals(activationCode)) {
     usuario.setActive(true);
     usuario.setActivationCode(null); // Limpa o código de ativação
     usuarioRepository.save(usuario);
     return true;
     }
     return false;
    } */
    

    public List<Usuario> listarTodos() {

        return repository.findAll();
    }

    public Usuario obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Usuario usuarioAlterado) {

        Usuario usuario = repository.findById(id).get();
        usuario.setNome(usuarioAlterado.getNome());
        usuario.setSobrenome(usuarioAlterado.getSobrenome());

        usuario.setVersao(usuario.getVersao() + 1);
        repository.save(usuario);
    }

    @Transactional
    public void delete(Long id) {

        Usuario usuario = repository.findById(id).get();
        usuario.setHabilitado(Boolean.FALSE);
        usuario.setVersao(usuario.getVersao() + 1);

        repository.delete(usuario);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // <<<<<<<<<<<<<<<<<<<<< EMPRESA
    //////////////////////////////////////////////////////////////////////////////////////////////////////// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<< EMPRESA
    //////////////////////////////////////////////////////////////////////////////////////////////////////// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<< EMPRESA
    //////////////////////////////////////////////////////////////////////////////////////////////////////// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<< EMPRESA
    //////////////////////////////////////////////////////////////////////////////////////////////////////// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

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
