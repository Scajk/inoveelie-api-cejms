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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<<          EMPRESA          >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/empresa/{usuarioId}")
   public ResponseEntity<Empresa> adicionarEmpresa(@PathVariable("usuarioId") Long empresaId, @RequestBody @Valid EmpresaRequest request) {

    Empresa cnpj = usuarioService.adicionarEmpresa(empresaId, request.build());
       return new ResponseEntity<Empresa>(cnpj, HttpStatus.CREATED);
   }

   @PutMapping("/empresa/{cnpjId}")
   public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable("cnpjId") Long cnpjId, @RequestBody EmpresaRequest request) {

    Empresa cnpj = usuarioService.atualizarEmpresa(cnpjId, request.build());
       return new ResponseEntity<Empresa>(cnpj, HttpStatus.OK);
   }
  
   @DeleteMapping("/empresa/{cnpjId}")
   public ResponseEntity<Void> removerEnderecoCliente(@PathVariable("cnpjId") Long cnpjId) {

    usuarioService.removerEmpresa(cnpjId);
       return ResponseEntity.noContent().build();
   }

}
