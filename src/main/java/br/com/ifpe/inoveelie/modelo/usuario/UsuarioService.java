package br.com.ifpe.inoveelie.modelo.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.inoveelie.modelo.acesso.UserService;
import br.com.ifpe.inoveelie.modelo.mensagens.EmailService;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private EmailService emailService;

    
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public Usuario save(Usuario usuario) {

        userService.save(usuario.getUser());

        usuario.setHabilitado(Boolean.TRUE);
        usuario.setVersao(1L);
        usuario.setDataCriacao(LocalDate.now());
        Usuario usuarioSalvo = repository.save(usuario);

        emailService.enviarEmailConfirmacaoCadastroUsuario(usuario);

        return usuarioSalvo;
    }

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
        usuario.setFoneCelular(usuarioAlterado.getFoneCelular());
            
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
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Transactional
   public Empresa adicionarEmpresa(Long usuarioId, Empresa cnpj) {

       Usuario usuario = this.obterPorID(usuarioId);
      
       //Primeiro salva o EnderecoCliente:

       cnpj.setUsuario(usuario);
       cnpj.setHabilitado(Boolean.TRUE);
       empresaRepository.save(cnpj);
      
       //Depois acrescenta o endereço criado ao cliente e atualiza o cliente:

       List<Empresa> listaEmpresa = usuario.getCnpj();
      
       if (listaEmpresa == null) {
        listaEmpresa = new ArrayList<Empresa>();
       }
      
       listaEmpresa.add(cnpj);
       usuario.setCnpj(listaEmpresa);
       usuario.setVersao(usuario.getVersao() + 1);
       repository.save(usuario);
      
       return cnpj;
   }

   @Transactional
   public Empresa atualizarEmpresa(Long id, Empresa cnpjAlterado) {

    Empresa cnpj = empresaRepository.findById(id).get();
    cnpj.setNomeEmpresa(cnpjAlterado.getNomeEmpresa());
    cnpj.setCepEmpresa(cnpjAlterado.getCepEmpresa());

       return empresaRepository.save(cnpj);
   }

   @Transactional
    public void removerEmpresa(Long id) {

        Empresa cnpj = empresaRepository.findById(id).get();
        cnpj.setHabilitado(Boolean.FALSE);
    empresaRepository.save(cnpj);

    Usuario usuario = this.obterPorID(cnpj.getUsuario().getId());
    usuario.getCnpj().remove(cnpj);
    usuario.setVersao(usuario.getVersao() + 1);
    repository.save(usuario);
}




}
