package br.com.ifpe.inoveelie.api.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.inoveelie.modelo.usuario.Usuario;
import br.com.ifpe.inoveelie.modelo.usuario.UsuarioService;
import br.com.ifpe.inoveelie.modelo.usuario.Empresa;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Operation(
        summary = "Serviço responsável por salvar um usuario no sistema."
    )
    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody @Valid UsuarioRequest request) {

        Usuario usuario = usuarioService.save(request.build());
        return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Serviço responsável por listar todos os usuario do sistema."
    )
    @GetMapping
    public List<Usuario> listarTodos() {

        return usuarioService.listarTodos();
    }

    @Operation(
        summary = "Serviço responsável por retornar o usuario correspondente ao ID passado na URL."
    )
    @GetMapping("/{id}")
    public Usuario obterPorID(@PathVariable Long id) {

        return usuarioService.obterPorID(id);
    }

    @GetMapping("getUsuario/{username}")
    public Usuario obterPorUsername(@PathVariable String username) {

        return usuarioService.obterPorUsername(username);
    }

    @Operation(
        summary = "Serviço responsável por alterar um cliente no sistema."
    )
    @PostMapping("/update/{id}")
   public Usuario update(
           @PathVariable("id") Long id,
           @RequestParam(value = "nome", required = false) String nome,
           @RequestParam(value = "sobrenome", required = false) String sobrenome) {

       return usuarioService.update( id, nome, sobrenome);
   }



   /* @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable("id") Long id, 
            @RequestBody @Valid UsuarioRequest request) {

        usuarioService.update(id, request.build());
        return ResponseEntity.ok().build();
    } */

    @Operation(
        summary = "Serviço responsável por gerar código de exclusão de usuario no sistema.",
        description = "Gera código de 6 dígitos para exclusão e envia por email."
    )
    @PostMapping("/iniciar-exclusao-conta")
    public ResponseEntity<Usuario> iniciarExclusaoConta(@RequestParam String email) {
        Usuario usuario = usuarioService.iniciarExclusaoConta(email);
        
        if (usuario != null) {
            return ResponseEntity.ok(usuario);  
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  
        }
    }

    @Operation(
        summary = "Serviço responsável por verificar código de exclusaõ e excluir conta do usuário no sistema.",
        description = "Excluir conta apenas em caso de confirmação do código."
    )
    @DeleteMapping("/confirmar-exclusao-conta")
    public ResponseEntity<Void> confirmarExclusaoConta(@RequestParam String email, @RequestParam String token) {
        boolean contaExcluida = usuarioService.confirmarExclusaoConta(email, token);
        return contaExcluida ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
        summary = "Serviço responsável por ativar um usuario no sistema.",
        description = "Serviço vital para o usuário acessar o sistema."
    )
    @PostMapping("/ativar")
    public ResponseEntity<Void> ativarConta(@RequestParam String email, @RequestParam String codigoAtivacao) {
        boolean ativado = usuarioService.activateUser(email, codigoAtivacao);
        return ativado ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
        summary = "Serviço responsável por recuperar senha do usuario no sistema.",
        description = "Gerar token de recuperação e enviar por email."
    )
    @PostMapping("/recuperar-senha")
    public ResponseEntity<Usuario> iniciarRecuperacaoSenha(@RequestParam String email) {
    Usuario usuario = usuarioService.iniciarRecuperacaoSenha(email);
    
    if (usuario != null) {
        return ResponseEntity.ok(usuario);  
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  
    }
    }

    @Operation(
        summary = "Serviço responsável por alterar a senha de um usuario no sistema.",
        description = "executado mediante a confirmação de token enviado por email."
    )
    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(@RequestParam String email, @RequestParam String token, @RequestParam String novaSenha) {
        boolean senhaRedefinida = usuarioService.redefinirSenha(email, token, novaSenha);
        return senhaRedefinida ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // <<<<<<<<<<<<    EMPRESA    >>>>>>>>>>>>>>>

    @Operation(
        summary = "Serviço responsável por salvar empresa de usuario no sistema."
    )
    @PostMapping("/empresa/{usuarioId}")
   public ResponseEntity<Empresa> adicionarEmpresa(@PathVariable("usuarioId") Long empresaId, @RequestBody @Valid EmpresaRequest request) {

    Empresa usuario = usuarioService.adicionarEmpresa(empresaId, request.build());
       return new ResponseEntity<Empresa>(usuario, HttpStatus.CREATED);
   }

   @Operation(
        summary = "Serviço responsável por alterar dados da empresa de um usuario no sistema."
    )
   @PutMapping("/empresa/{usuarioId}")
   public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable("usuarioId") Long usuarioId, @RequestBody EmpresaRequest request) {

    Empresa usuario = usuarioService.atualizarEmpresa(usuarioId, request.build());
       return new ResponseEntity<Empresa>(usuario, HttpStatus.OK);
   }

   @Operation(
        summary = "Serviço responsável por apagar a empresa de um usuario no sistema."
    )
   @DeleteMapping("/empresa/{usuarioId}")
   public ResponseEntity<Void> removerEnderecoCliente(@PathVariable("usuarioId") Long usuarioId) {

    usuarioService.removerEmpresa(usuarioId);
       return ResponseEntity.noContent().build();
   }

}
