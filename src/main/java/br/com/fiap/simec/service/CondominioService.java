package br.com.fiap.simec.service;

import br.com.fiap.simec.model.Condominio;
import br.com.fiap.simec.repository.CondominioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CondominioService {

    @Autowired
    private CondominioRepository condominioRepository;

    public List<Condominio> findAll() {
        return condominioRepository.findAll();
    }

    public Optional<Condominio> findById(Long id) {
        return Optional.ofNullable(condominioRepository.findById(id).orElseThrow(() -> new RuntimeException("Condomínio não encontrado: " + id)));
    }

    public List<Condominio> findByNome(String nome) {
        return condominioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Condominio save(Condominio condominio) {
        return condominioRepository.save(condominio);
    }

    public void deleteById(Long id) {
        condominioRepository.deleteById(id);
    }

    public Double findAverageConsumptionByCep(double cep) {
        Double average = condominioRepository.findAverageConsumptionByCep(cep);
        return average != null ? average : 0.0;
    }


}



