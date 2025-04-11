package com.gestione_prenotazione.common;

import com.gestione_prenotazione.classi.edificio.Edificio;
import com.gestione_prenotazione.classi.edificio.EdificioRepository;
import com.gestione_prenotazione.classi.postazione.Postazione;
import com.gestione_prenotazione.classi.postazione.PostazioneRepository;
import com.gestione_prenotazione.classi.postazione.TipoPostazione;
import com.gestione_prenotazione.classi.prenotazione.Prenotazione;
import com.gestione_prenotazione.classi.prenotazione.PrenotazioneRepository;
import com.gestione_prenotazione.classi.utente.Utente;
import com.gestione_prenotazione.classi.utente.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(1)
public class DatabaseRunner implements CommandLineRunner {
    
    private final PostazioneRepository postazioneRepository;
    private final UtenteRepository utenteRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final EdificioRepository edificioRepository;
    
    @Override
    public void run(String... args) throws Exception {
        Edificio edificio1 = new Edificio();
        edificio1.setNome("Uffici Ottavio");
        edificio1.setIndirizzo("Via Roma 1");
        edificio1.setCitta("Milano");
        
        Edificio edificio2 = new Edificio();
        edificio2.setNome("Casa Bianca");
        edificio2.setIndirizzo("Via Milano 2");
        edificio2.setCitta("Roma");
        
        Edificio edificio3 = new Edificio();
        edificio3.setNome("Casa Bianca");
        edificio3.setIndirizzo("Via Vittorio Emanuele 3");
        edificio3.setCitta("Milano");
        
        List<Edificio> edifici = Arrays.asList(edificio1, edificio2, edificio3);
        edificioRepository.saveAll(edifici);
        
        Postazione postazione1 = new Postazione();
        postazione1.setDescrizione("Postazione privata al 1° piano");
        postazione1.setTipo(TipoPostazione.PRIVATO);
        postazione1.setCapacita(1);
        postazione1.setEdificio(edificio1);
        
        Postazione postazione2 = new Postazione();
        postazione2.setDescrizione("Postazione open space al 2° piano");
        postazione2.setTipo(TipoPostazione.OPENSPACE);
        postazione2.setCapacita(5);
        postazione2.setEdificio(edificio2);
        
        Postazione postazione3 = new Postazione();
        postazione3.setDescrizione("Postazione sala riunioni al 5° piano");
        postazione3.setTipo(TipoPostazione.SALA_RIUNIONI);
        postazione3.setCapacita(10);
        postazione3.setEdificio(edificio2);
        
        Postazione postazione4 = new Postazione();
        postazione4.setDescrizione("Postazione sala riunioni al 4° piano");
        postazione4.setTipo(TipoPostazione.SALA_RIUNIONI);
        postazione4.setCapacita(18);
        postazione4.setEdificio(edificio1);
        
        Postazione postazione5 = new Postazione();
        postazione5.setDescrizione("Postazione sala riunioni al 5° piano");
        postazione5.setTipo(TipoPostazione.SALA_RIUNIONI);
        postazione5.setCapacita(18);
        postazione5.setEdificio(edificio3);
        
        List<Postazione> postazioni = Arrays.asList(postazione1, postazione2, postazione3, postazione4, postazione5);
        postazioneRepository.saveAll(postazioni);
        
        Utente utente1 = new Utente();
        utente1.setUsername("Sandokan");
        utente1.setNomeCompleto("Sandokan Rossi");
        utente1.setEmail("pippobaudo777@example.com");
        
        Utente utente2 = new Utente();
        utente2.setUsername("NinoDangerous");
        utente2.setNomeCompleto("Nino Dangerous");
        utente2.setEmail("ndangerous58@example.com");
        
        List<Utente> utenti = Arrays.asList(utente1, utente2);
        utenteRepository.saveAll(utenti);
        
        Prenotazione prenotazione1 = new Prenotazione();
        prenotazione1.setUtente(utente1);
        prenotazione1.setPostazione(postazione1);
        prenotazione1.setData(LocalDate.of(2025, 4, 15));
        
        Prenotazione prenotazione2 = new Prenotazione();
        prenotazione2.setUtente(utente2);
        prenotazione2.setPostazione(postazione2);
        prenotazione2.setData(LocalDate.of(2025, 4, 16));
        
        List<Prenotazione> prenotazioni = Arrays.asList(prenotazione1, prenotazione2);
        prenotazioneRepository.saveAll(prenotazioni);
        
        System.out.println("Dati di esempio inseriti nel database.");
    }
}
