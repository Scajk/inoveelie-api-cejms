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
        summary = "Serviço responsável por salvar um usuario no sistema.",
        description = "Exemplo de descrição de um endpoint responsável por inserir um usuario no sistema."
    )
    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody @Valid UsuarioRequest request) {

        Usuario usuario = usuarioService.save(request.build());
        return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Serviço responsável por listar todos os usuario do sistema.",
        description = "Exemplo de descrição de um endpoint responsável por inserir um usuario no sistema."
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

    @Operation(
        summary = "Serviço responsável por alterar um cliente no sistema."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable("id") Long id, 
            @RequestBody @Valid UsuarioRequest request) {

        usuarioService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ativar")
    public ResponseEntity<Void> ativarConta(@RequestParam String email, @RequestParam String codigo) {
        boolean ativado = usuarioService.activateUser(email, codigo);
        return ativado ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<Void> iniciarRecuperacaoSenha(@RequestParam String email) {
        usuarioService.iniciarRecuperacaoSenha(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(@RequestParam String email, @RequestParam String token, @RequestParam String novaSenha) {
        boolean senhaRedefinida = usuarioService.redefinirSenha(email, token, novaSenha);
        return senhaRedefinida ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/empresa/{usuarioId}")
   public ResponseEntity<Empresa> adicionarEmpresa(@PathVariable("usuarioId") Long empresaId, @RequestBody @Valid EmpresaRequest request) {

    Empresa usuario = usuarioService.adicionarEmpresa(empresaId, request.build());
       return new ResponseEntity<Empresa>(usuario, HttpStatus.CREATED);
   }

   @PutMapping("/empresa/{usuarioId}")
   public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable("usuarioId") Long usuarioId, @RequestBody EmpresaRequest request) {

    Empresa usuario = usuarioService.atualizarEmpresa(usuarioId, request.build());
       return new ResponseEntity<Empresa>(usuario, HttpStatus.OK);
   }
  
   @DeleteMapping("/empresa/{usuarioId}")
   public ResponseEntity<Void> removerEnderecoCliente(@PathVariable("usuarioId") Long usuarioId) {

    usuarioService.removerEmpresa(usuarioId);
       return ResponseEntity.noContent().build();
   }

}
