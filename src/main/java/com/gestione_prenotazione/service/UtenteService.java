package com.gestione_prenotazione.service;

import com.gestione_prenotazione.classi.utente.Utente;
import com.gestione_prenotazione.classi.utente.UtenteRepository;
import com.gestione_prenotazione.exception.PrenotazioneException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtenteService {
    
    private final UtenteRepository utenteRepository;
    
    public Utente login(String username) {
        Utente utente = utenteRepository.findByUsername(username);
        if (utente == null) {
            throw new PrenotazioneException("Username non trovato.");
        }
        return utente;
    }
    
    public String creaUtente(String username, String nomeCompleto) {
        if (utenteRepository.findByUsername(username) != null) {
            throw new PrenotazioneException("Username già esistente.");
        }
        
        Utente utente = new Utente();
        utente.setUsername(username);
        utente.setNomeCompleto(nomeCompleto);
        utente.setEmail(username + "@example.com"); // Imposta la mail automaticamente
        
        utenteRepository.save(utente);
        return "Utente creato con successo.";
    }
}
