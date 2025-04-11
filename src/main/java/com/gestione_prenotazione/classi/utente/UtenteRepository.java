package com.gestione_prenotazione.classi.utente;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByUsername(String username);
}