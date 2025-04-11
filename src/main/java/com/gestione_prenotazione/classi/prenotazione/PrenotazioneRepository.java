package com.gestione_prenotazione.classi.prenotazione;


import com.gestione_prenotazione.classi.postazione.Postazione;
import com.gestione_prenotazione.classi.utente.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByUtenteAndData(Utente utente, LocalDate data);
    boolean existsByPostazioneAndData(Postazione postazione, LocalDate data);
}