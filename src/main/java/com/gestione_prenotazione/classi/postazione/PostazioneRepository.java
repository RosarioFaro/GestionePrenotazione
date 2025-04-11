package com.gestione_prenotazione.classi.postazione;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostazioneRepository extends JpaRepository<Postazione, Long> {
    List<Postazione> findByTipoAndEdificio_Citta(TipoPostazione tipo, String citta);
    
    List<Postazione> findByEdificioNomeAndTipo(String edificioNome, TipoPostazione tipo);
    
    @Query("SELECT DISTINCT p.edificio.nome FROM Postazione p WHERE p.edificio.citta = :citta")
    List<String> findDistinctEdificiByCitta(@Param("citta") String citta);
    
    List<Postazione> findByEdificio_Nome(String nomeEdificio);
}