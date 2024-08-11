package br.com.ifpe.inoveelie.api.itensPedido;

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
import br.com.ifpe.inoveelie.modelo.material.MaterialService;
import br.com.ifpe.inoveelie.modelo.itensPedido.ItensPedido;
import br.com.ifpe.inoveelie.modelo.itensPedido.ItensPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/itensPedido")
@CrossOrigin

public class ItensPedidoController {
    
   @Autowired
   private ItensPedidoService itensPedidoService;

   @Autowired
   private MaterialService materialService;

   @Operation(
       summary = "Serviço responsável por salvar itens de pedido no sistema.",
       description = "Endpoint responsável por inserir itens de pedido no sistema."
   )
   @PostMapping
   public ResponseEntity<ItensPedido> save(@RequestBody @Valid
   ItensPedidoRequest request) {

       ItensPedido itensPedidoNovo = request.build();
       itensPedidoNovo.setMateriais(materialService.obterPorID(request.getIdMaterial()));

       ItensPedido itensPedido = itensPedidoService.save(itensPedidoNovo);
       return new ResponseEntity<ItensPedido>(itensPedido, HttpStatus.CREATED);
   }

   @Operation(
       summary = "Serviço responsável por listar itens de pedido no sistema.",
       description = "Endpoint responsável por listar itens de pedido no sistema."
   )
   @GetMapping
    public List<ItensPedido> listarTodos() {
        return itensPedidoService.listarTodos();
    }

    @Operation(
       summary = "Serviço responsável por obter itens de pedido no sistema.",
       description = "Endpoint responsável por obter itens de pedido no sistema."
   )
    @GetMapping("/{id}")
    public ItensPedido obterPorID(@PathVariable Long id) {
        return itensPedidoService.obterPorID(id);
    }

    @Operation(
       summary = "Serviço responsável por alterar itens de pedido no sistema.",
       description = "Endpoint responsável por alterar itens de pedido no sistema."
   )
   @PutMapping("/{id}")
   public ResponseEntity<ItensPedido> update(@PathVariable("id") Long id, @RequestBody ItensPedidoRequest request) {

       ItensPedido itensPedido = request.build();
       itensPedido.setMateriais(materialService.obterPorID(request.getIdMaterial()));
       itensPedidoService.update(id, itensPedido);
      
       return ResponseEntity.ok().build();
   }

   @Operation(
       summary = "Serviço responsável por apagar itens de pedido no sistema.",
       description = "Endpoint responsável por apagar itens de pedido no sistema."
   )
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {

       itensPedidoService.delete(id);
       return ResponseEntity.ok().build();
   }
}
