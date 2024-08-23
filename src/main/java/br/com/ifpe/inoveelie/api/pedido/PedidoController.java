package br.com.ifpe.inoveelie.api.pedido;

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

import br.com.ifpe.inoveelie.api.cliente.ClienteRequest;
import br.com.ifpe.inoveelie.modelo.cliente.Cliente;
import br.com.ifpe.inoveelie.modelo.material.MaterialService;
import br.com.ifpe.inoveelie.modelo.pedido.Pedido;
import br.com.ifpe.inoveelie.modelo.pedido.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin

public class PedidoController {
    
   @Autowired
   private PedidoService pedidoService;

   @Autowired
   private MaterialService materialService;

   @Operation(
       summary = "Serviço responsável por salvar um pedido no sistema.",
       description = "Endpoint responsável por inserir um pedido no sistema."
   )
   @PostMapping
    public ResponseEntity<Pedido> save(@RequestBody @Valid PedidoRequest request) {

        Pedido pedido = pedidoService.save(request.build());
        return new ResponseEntity<Pedido>(pedido, HttpStatus.CREATED);

    }

   @Operation(
       summary = "Serviço responsável por listar um produto no sistema.",
       description = "Exemplo de descrição de um endpoint responsável por listar um produto no sistema."
   )
   @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
   }

   @Operation(
       summary = "Serviço responsável por obter um pedido no sistema.",
       description = "Endpoint responsável por obter um pedido no sistema."
   )
   @GetMapping("/{id}")
    public Pedido obterPorID(@PathVariable Long id) {
        return pedidoService.obterPorID(id);
   }

   @Operation(
       summary = "Serviço responsável por alterar um pedido no sistema.",
       description = "Endpoint responsável por alterar um pedido no sistema."
   )
   @PutMapping("/{id}")
    public ResponseEntity<Pedido> update(@PathVariable("id") Long id, @RequestBody @Valid PedidoRequest request) {

        pedidoService.update(id, request.build());
        return ResponseEntity.ok().build();
   }

   @Operation(
       summary = "Serviço responsável por apagar um pedido no sistema.",
       description = "Endpoint responsável por apagar um pedido no sistema."
   )
     @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {

       pedidoService.delete(id);
       return ResponseEntity.ok().build();
   }
}
