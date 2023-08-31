package com.agenday.agendayserv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "horarios_disponiveis")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HorariosDisponiveis {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_horarios_disponiveis")
    @SequenceGenerator(name = "seq_horarios_disponiveis", sequenceName = "seq_horarios_disponiveis")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @JoinColumn(name = "id_funcionario", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Funcionario funcionario;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;
}
