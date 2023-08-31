package com.agenday.agendayserv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "avaliacao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_avaliacao")
    @SequenceGenerator(name = "seq_avaliacao", sequenceName = "seq_avaliacao")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @JoinColumn(name = "id_agendamento")
    @ManyToOne
    private Agendamento agendamento;

    @Column(name = "pontuacao", nullable = false)
    private Integer pontuacao;

    @Column(name = "comentario", nullable = false)
    private String comentario;
}
