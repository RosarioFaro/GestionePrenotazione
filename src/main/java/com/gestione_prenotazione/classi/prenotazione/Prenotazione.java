package com.gestione_prenotazione.classi.prenotazione;

import com.gestione_prenotazione.classi.postazione.Postazione;
import com.gestione_prenotazione.classi.utente.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prenotazioni")

public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    private Utente utente;
    
    @ManyToOne
    private Postazione postazione;
    
    private LocalDate data;
    
}