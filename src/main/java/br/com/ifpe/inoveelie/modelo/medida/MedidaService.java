package br.com.ifpe.inoveelie.modelo.medida;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service

public class MedidaService {
    
    @Autowired
    private MedidaRepository repository;

    @Transactional
    public Medida save(Medida medida) {

        medida.setHabilitado(Boolean.TRUE);
        medida.setVersao(1L);
        medida.setDataCriacao(LocalDate.now());
        return repository.save(medida);
    }

    @Transactional
    public void update(Long id, Medida medidaAlterada) {
 
       Medida medida = repository.findById(id).get();
       medida.setBusto(medidaAlterada.getBusto());
       medida.setCintura(medidaAlterada.getCintura());
       medida.setQuadril(medidaAlterada.getQuadril());
       medida.setAlturaManga(medidaAlterada.getAlturaManga());
       medida.setAlturaCava(medidaAlterada.getAlturaCava());
       medida.setLargura(medidaAlterada.getLargura());
       medida.setComprimentoSaia(medidaAlterada.getComprimentoSaia());
         
       medida.setVersao(medida.getVersao() + 1);
       repository.save(medida);
   }
 

    public List<Medida> listarTodos() {

        return repository.findAll();
    }

    public Medida obterPorID(Long id) {

        return repository.findById(id).get();
    }
    
    @Transactional
   public void delete(Long id) {

       Medida medida = repository.findById(id).get();
       medida.setHabilitado(Boolean.FALSE);
       medida.setVersao(medida.getVersao() + 1);

       repository.save(medida);
   }
}
