package br.com.ifpe.inoveelie.modelo.tipoPedido;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class TipoPedidoService {

    @Autowired
    private TipoPedidoRepository repository;

    @PostConstruct
    public void init() {
        createDefaultTipoIfNotExists("Conserto");
        createDefaultTipoIfNotExists("Confecção");
    }

    private void createDefaultTipoIfNotExists(String tipoDescricao) {
        Optional<TipoPedido> existingTipo = repository.findByDescricao(tipoDescricao);
        if (existingTipo.isEmpty()) {
            TipoPedido tipoPedido = TipoPedido.builder()
                .descricao(tipoDescricao)
                .habilitado(Boolean.TRUE)
                .versao(1L)
                .dataCriacao(LocalDate.now())
                .build();
            repository.save(tipoPedido);
        }
    }

    @Transactional
    public TipoPedido save(TipoPedido tipoPedido) {
        tipoPedido.setHabilitado(Boolean.TRUE);
        tipoPedido.setVersao(1L);
        tipoPedido.setDataCriacao(LocalDate.now());
        return repository.save(tipoPedido);
    }

    @Transactional
    public void update(Long id, TipoPedido tipoPedidoAlterado) {
        TipoPedido tipoPedido = repository.findById(id).get();
        tipoPedido.setDescricao(tipoPedidoAlterado.getDescricao());
        tipoPedido.setVersao(tipoPedido.getVersao() + 1);
        repository.save(tipoPedido);
    }

    public List<TipoPedido> listarTodos() {
        return repository.findAll();
    }

    public TipoPedido obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void delete(Long id) {
        TipoPedido tipoPedido = repository.findById(id).get();
        tipoPedido.setHabilitado(Boolean.FALSE);
        tipoPedido.setVersao(tipoPedido.getVersao() + 1);
        repository.save(tipoPedido);
    }
}
