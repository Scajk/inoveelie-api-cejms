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
        summary = "Serviço responsável por salvar um tipo de pedido no sistema.",
        description = "Este endpoint insere um novo tipo de pedido no sistema."
    )
    @PostMapping
    public ResponseEntity<TipoPedido> save(@RequestBody TipoPedidoRequest request) {
        TipoPedido tipoPedidoNovo = request.build();
        TipoPedido tipoPedido = tipoPedidoService.save(tipoPedidoNovo);
        return new ResponseEntity<>(tipoPedido, HttpStatus.CREATED);
    }
 
    @Operation(
        summary = "Lista todos os tipos de pedido.",
        description = "Este endpoint retorna uma lista de todos os tipos de pedido existentes no sistema."
    )
    @GetMapping
    public ResponseEntity<List<TipoPedido>> listarTodos() {
        List<TipoPedido> tiposPedido = tipoPedidoService.listarTodos();
        return ResponseEntity.ok(tiposPedido);
    }
 
    @Operation(
        summary = "Obtém um tipo de pedido por ID.",
        description = "Este endpoint retorna um tipo de pedido específico com base no ID fornecido."
    )
    @GetMapping("/{id}")
    public ResponseEntity<TipoPedido> obterPorID(@PathVariable Long id) {
        TipoPedido tipoPedido = tipoPedidoService.obterPorID(id);
        return ResponseEntity.ok(tipoPedido);
    }
 
    @Operation(
        summary = "Atualiza um tipo de pedido existente.",
        description = "Este endpoint atualiza um tipo de pedido com base no ID fornecido e nos dados fornecidos no corpo da requisição."
    )
    @PutMapping("/{id}")
    public ResponseEntity<TipoPedido> update(@PathVariable("id") Long id, @RequestBody TipoPedidoRequest request) {
        TipoPedido tipoPedidoAtualizado = request.build();
        tipoPedidoService.update(id, tipoPedidoAtualizado);
        return ResponseEntity.ok(tipoPedidoAtualizado);
    }
 
    @Operation(
        summary = "Desativa um tipo de pedido existente.",
        description = "Este endpoint desativa um tipo de pedido com base no ID fornecido, em vez de excluí-lo fisicamente do banco de dados."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoPedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
