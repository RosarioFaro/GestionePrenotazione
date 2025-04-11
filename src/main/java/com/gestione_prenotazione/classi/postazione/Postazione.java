package com.gestione_prenotazione.classi.postazione;

import com.gestione_prenotazione.classi.edificio.Edificio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "postazioni")

public class Postazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private String descrizione;
    private Integer capacita;
    @Enumerated(EnumType.STRING)
    private TipoPostazione tipo;
    @ManyToOne
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;
    
    @Override
    public String toString() {
        return "Postazione{" +
                "descrizione='" + descrizione + '\'' +
                ", tipo=" + tipo +
                ", capacita=" + capacita +
                ", edificio=" + (edificio != null ? edificio.getNome() : "N/A") +
                '}';
    }
}