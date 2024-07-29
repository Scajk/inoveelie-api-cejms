package br.com.ifpe.inoveelie.api.medida;

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
import br.com.ifpe.inoveelie.modelo.medida.Medida;
import br.com.ifpe.inoveelie.modelo.medida.MedidaService;

@RestController
@RequestMapping("/api/medida")
@CrossOrigin

public class MedidaController {

    @Autowired
    private MedidaService medidaService;

    @PostMapping
    public ResponseEntity<Medida> save(@RequestBody MedidaRequest request) {

        Medida medida = medidaService.save(request.build());
        return new ResponseEntity<Medida>(medida, HttpStatus.CREATED);

    }

    @GetMapping
    public List<Medida> listarTodos() {
        return medidaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Medida obterPorID(@PathVariable Long id) {
        return medidaService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medida> update(@PathVariable("id") Long id, @RequestBody MedidaRequest request) {

        medidaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        medidaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
