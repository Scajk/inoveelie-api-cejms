package br.com.ifpe.inoveelie.modelo.material;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service

public class MaterialService {
    
    @Autowired
    private MaterialRepository repository;

    @Transactional
    public Material save(Material material) {

        material.setHabilitado(Boolean.TRUE);
        material.setVersao(1L);
        material.setDataCriacao(LocalDate.now());
        return repository.save(material);
    }

    @Transactional
    public void update(Long id, Material materialAlterado) {
 
       Material material = repository.findById(id).get();
       material.setIdLogin(materialAlterado.getIdLogin());
       material.setNome(materialAlterado.getNome());
       material.setDescricao(materialAlterado.getDescricao());
       material.setQuantidade(materialAlterado.getQuantidade());
         
       material.setVersao(material.getVersao() + 1);
       repository.save(material);
   }
 

    public List<Material> listarTodos() {

        return repository.findAll();
    }

    public Material obterPorID(Long id) {

        return repository.findById(id).get();
    }
    
    @Transactional
   public void delete(Long id) {

       Material material = repository.findById(id).get();
       material.setHabilitado(Boolean.FALSE);
       material.setVersao(material.getVersao() + 1);

       repository.save(material);
   }
}
