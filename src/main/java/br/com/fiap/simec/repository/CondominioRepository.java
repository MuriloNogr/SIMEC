package br.com.fiap.simec.repository;

import br.com.fiap.simec.model.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface  CondominioRepository extends JpaRepository<Condominio, Long> {
    List<Condominio> findByNomeContainingIgnoreCase(String nome);


    @Query("SELECT AVG(c.consumoMensal) FROM Condominio c WHERE c.cep = :cep")
    Double findAverageConsumptionByCep(@Param("cep") double cep);

}
