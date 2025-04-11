package com.gestione_prenotazione.service;

import com.gestione_prenotazione.classi.postazione.Postazione;
import com.gestione_prenotazione.classi.postazione.PostazioneRepository;
import com.gestione_prenotazione.classi.postazione.TipoPostazione;
import com.gestione_prenotazione.classi.prenotazione.Prenotazione;
import com.gestione_prenotazione.classi.prenotazione.PrenotazioneRepository;
import com.gestione_prenotazione.classi.utente.Utente;
import com.gestione_prenotazione.classi.utente.UtenteRepository;
import com.gestione_prenotazione.exception.PrenotazioneException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {
    
    private final PrenotazioneRepository prenotazioneRepository;
    private final PostazioneRepository postazioneRepository;
    private final UtenteRepository utenteRepository;
    
    public String prenotaPostazione(String username, LocalDate dataPrenotazione, TipoPostazione tipoPostazione, String citta) {
        try {
            Utente utente = utenteRepository.findByUsername(username);
            if (utente == null) {
                throw new PrenotazioneException("Utente non trovato");
            }
            
            List<Postazione> postazioniDisponibili = postazioneRepository.findByTipoAndEdificio_Citta(tipoPostazione, citta);
            if (postazioniDisponibili.isEmpty()) {
                throw new PrenotazioneException("Nessuna postazione disponibile per il tipo e la città richiesti.");
            }
            
            boolean esistePrenotazione = prenotazioneRepository.existsByUtenteAndData(utente, dataPrenotazione);
            if (esistePrenotazione) {
                throw new PrenotazioneException("L'utente ha già una prenotazione per questa data.");
            }
            
            for (Postazione postazione : postazioniDisponibili) {
                boolean postazioneOccupata = prenotazioneRepository.existsByPostazioneAndData(postazione, dataPrenotazione);
                if (postazioneOccupata) {
                    return "La postazione " + postazione + " è già prenotata per questa data.";
                }
            }
            
            Postazione postazioneDisponibile = postazioniDisponibili.get(0);
            
            Prenotazione prenotazione = new Prenotazione();
            prenotazione.setUtente(utente);
            prenotazione.setPostazione(postazioneDisponibile);
            prenotazione.setData(dataPrenotazione);
            
            prenotazioneRepository.save(prenotazione);
            
            return "Prenotazione effettuata con successo per la " + postazioneDisponibile.getDescrizione() + " " + "il giorno " + dataPrenotazione;
        } catch (PrenotazioneException e) {
            return "Errore durante la prenotazione: " + e.getMessage();
        }
    }
    
    public List<String> getNomiEdificiDisponibili(String citta) {
        return postazioneRepository.findDistinctEdificiByCitta(citta);
    }
    
    public List<Postazione> getPostazioniByEdificio(String nomeEdificio) {
        return postazioneRepository.findByEdificio_Nome(nomeEdificio);
    }
}