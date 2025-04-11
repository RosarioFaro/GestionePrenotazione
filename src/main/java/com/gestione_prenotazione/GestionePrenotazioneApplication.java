package com.gestione_prenotazione;

import com.gestione_prenotazione.classi.postazione.Postazione;
import com.gestione_prenotazione.classi.postazione.TipoPostazione;
import com.gestione_prenotazione.exception.PrenotazioneException;
import com.gestione_prenotazione.service.PrenotazioneService;
import com.gestione_prenotazione.service.UtenteService;
import com.gestione_prenotazione.classi.utente.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class GestionePrenotazioneApplication implements CommandLineRunner {
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Autowired
	private UtenteService utenteService;
	
	private Utente utenteLoggato = null;
	
	public static void main(String[] args) {
		SpringApplication.run(GestionePrenotazioneApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			if (utenteLoggato == null) {
				System.out.println("Seleziona un'opzione:");
				System.out.println("1. Login");
				System.out.println("2. Crea un nuovo utente");
				System.out.println("3. Uscire");
				
				int scelta = scanner.nextInt();
				scanner.nextLine();
				
				switch (scelta) {
					case 1:
						System.out.println("Inserisci il tuo username:");
						String usernameLogin = scanner.nextLine();
						try {
							utenteLoggato = utenteService.login(usernameLogin);
							System.out.println("Login effettuato con successo.");
						} catch (PrenotazioneException e) {
							System.out.println(e.getMessage());
						}
						break;
					
					case 2:
						System.out.println("Inserisci il tuo username per la registrazione:");
						String username = scanner.nextLine();
						System.out.println("Inserisci il tuo nome completo:");
						String nomeCompleto = scanner.nextLine();
						try {
							String risultato = utenteService.creaUtente(username, nomeCompleto);
							System.out.println(risultato);
						} catch (PrenotazioneException e) {
							System.out.println(e.getMessage());
						}
						break;
					
					case 3:
						System.out.println("Uscendo...");
						scanner.close();
						return;
					
					default:
						System.out.println("Scelta non valida. Riprova.");
				}
			} else {
				System.out.println("Gestione Prenotazioni - Seleziona un'opzione:");
				System.out.println("1. Prenotare una postazione");
				System.out.println("2. Cercare postazioni");
				System.out.println("3. Logout");
				System.out.println("4. Uscire");
				
				int scelta = scanner.nextInt();
				scanner.nextLine();
				
				switch (scelta) {
					case 1:
						System.out.println("Inserisci la data della prenotazione (yyyy-MM-dd):");
						String dataInput = scanner.nextLine();
						LocalDate data = LocalDate.parse(dataInput);
						
						System.out.println("Inserisci il tipo di postazione ('PRIVATO', 'OPENSPACE' o 'SALA_RIUNIONI'):");
						TipoPostazione tipo = TipoPostazione.valueOf(scanner.nextLine().toUpperCase());
						
						System.out.println("Inserisci la città dell'edificio:");
						String citta = scanner.nextLine();
						
						String risultato = prenotazioneService.prenotaPostazione(utenteLoggato.getUsername(), data, tipo, citta);
						System.out.println(risultato);
						break;
					
					case 2:
						System.out.println("Inserisci la città dell'edificio:");
						String citta1 = scanner.nextLine();
						
						List<String> edifici = prenotazioneService.getNomiEdificiDisponibili(citta1);
						if (edifici.isEmpty()) {
							System.out.println("Nessun edificio trovato in questa città.");
							break;
						}
						
						System.out.println("Edifici trovati:");
						for (int i = 0; i < edifici.size(); i++) {
							System.out.println((i + 1) + ". " + edifici.get(i));
						}
						
						System.out.println("Seleziona un edificio inserendo il numero:");
						int sceltaEdificio = scanner.nextInt();
						scanner.nextLine();
						
						if (sceltaEdificio < 1 || sceltaEdificio > edifici.size()) {
							System.out.println("Scelta non valida.");
							break;
						}
						
						String edificioSelezionato = edifici.get(sceltaEdificio - 1);
						
						List<Postazione> postazioniDisponibili = prenotazioneService.getPostazioniByEdificio(edificioSelezionato);
						
						if (postazioniDisponibili.isEmpty()) {
							System.out.println("Nessuna postazione trovata in questo edificio.");
						} else {
							System.out.println("Postazioni disponibili nell'edificio " + edificioSelezionato + ":");
							for (Postazione postazione : postazioniDisponibili) {
								System.out.println("- " + postazione.getDescrizione() + " | Tipo: " + postazione.getTipo() + " | Capacità: " + postazione.getCapacita());
							}
						}
						break;
					
					case 3:
						System.out.println("Logout effettuato.");
						utenteLoggato = null; // Logout
						break;
					
					case 4:
						System.out.println("Uscendo...");
						scanner.close();
						return;
					
					default:
						System.out.println("Scelta non valida. Riprova.");
				}
			}
		}
	}
}
