package br.com.ifpe.inoveelie.modelo.statusPedido;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class StatusPedidoService {

    @Autowired
    private StatusPedidoRepository repository;

    @PostConstruct
    public void init() {
        createDefaultStatusIfNotExists("Concluído");
        createDefaultStatusIfNotExists("Em andamento");
        createDefaultStatusIfNotExists("Em aberto");
        createDefaultStatusIfNotExists("Cancelado");
    }

    private void createDefaultStatusIfNotExists(String statusName) {
        Optional<StatusPedido> existingStatus = repository.findByNome(statusName);
        if (existingStatus.isEmpty()) {
            StatusPedido statusPedido = StatusPedido.builder()
                .nome(statusName)
                .habilitado(Boolean.TRUE)
                .versao(1L)
                .dataCriacao(LocalDate.now())
                .build();
            repository.save(statusPedido);
        }
    }
    

    @Transactional
    public StatusPedido save(StatusPedido statusPedido) {
        statusPedido.setHabilitado(Boolean.TRUE);
        statusPedido.setVersao(1L);
        statusPedido.setDataCriacao(LocalDate.now());
        return repository.save(statusPedido);
    }

    @Transactional
    public void update(Long id, StatusPedido statusPedidoAlterado) {
        StatusPedido statusPedido = repository.findById(id).get();
        statusPedido.setConcluido(statusPedidoAlterado.getConcluido());
        statusPedido.setEmAndamento(statusPedidoAlterado.getEmAndamento());
        statusPedido.setCancelado(statusPedidoAlterado.getCancelado());
        statusPedido.setFinalizado(statusPedidoAlterado.getFinalizado());
         
        statusPedido.setVersao(statusPedido.getVersao() + 1);
        repository.save(statusPedido);
    }

    public List<StatusPedido> listarTodos() {
        return repository.findAll();
    }

    public StatusPedido obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void delete(Long id) {
        StatusPedido statusPedido = repository.findById(id).get();
        statusPedido.setHabilitado(Boolean.FALSE);
        statusPedido.setVersao(statusPedido.getVersao() + 1);
        repository.save(statusPedido);
    }
}
