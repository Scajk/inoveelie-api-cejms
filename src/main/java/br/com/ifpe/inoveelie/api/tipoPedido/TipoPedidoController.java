package br.com.ifpe.inoveelie.api.tipoPedido;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.inoveelie.modelo.tipoPedido.TipoPedido;
import br.com.ifpe.inoveelie.modelo.tipoPedido.TipoPedidoService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/tipoPedido")
@CrossOrigin

public class TipoPedidoController {
    
    @Autowired
    private TipoPedidoService tipoPedidoService;
 
    @Operation(
        summary = "Serviço responsável por salvar uma categoria de produto no sistema.",
        description = "Exemplo de descrição de um endpoint responsável por inserir uma categoria de produto no sistema."
    )
    @PostMapping
    public ResponseEntity<TipoPedido> save(@RequestBody TipoPedidoRequest request) {
 
        TipoPedido tipoPedidoNovo = request.build();
        TipoPedido tipoPedido = tipoPedidoService.save(tipoPedidoNovo);
        return new ResponseEntity<TipoPedido>(tipoPedido, HttpStatus.CREATED);
        
    }
 
    @GetMapping
     public List<TipoPedido> listarTodos() {
         return tipoPedidoService.listarTodos();
     }
 
     @GetMapping("/{id}")
     public TipoPedido obterPorID(@PathVariable Long id) {
         return tipoPedidoService.obterPorID(id);
     }
 
     @PutMapping("/{id}")
     public ResponseEntity<TipoPedido> update(@PathVariable("id") Long id, @RequestBody TipoPedidoRequest request) {
 
         tipoPedidoService.update(id, request.build());
         return ResponseEntity.ok().build();
     }
 
     @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
 
        tipoPedidoService.delete(id);
        return ResponseEntity.ok().build();
    }

}
