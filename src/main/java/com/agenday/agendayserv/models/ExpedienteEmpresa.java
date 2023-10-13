package com.agenday.agendayserv.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "expediente_empresa")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpedienteEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_expediente_empresa")
    @SequenceGenerator(name = "seq_expediente_empresa", sequenceName = "seq_expediente_empresa")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empresa", updatable = true, nullable = false)
    private Empresa empresa;

    @Column(name = "dia_semana")
    @Enumerated(EnumType.STRING)
    private DayOfWeek diaSemana;

    @Column(name = "abertura_matutino")
    private LocalTime aberturaMatutino;

    @Column(name = "fechamento_matutino")
    private LocalTime fechamentoMatutino;

    @Column(name = "abertura_vespertino")
    private LocalTime aberturaVespertino;

    @Column(name = "fechamento_vespertino")
    private LocalTime fechamentoVespertino;
}
