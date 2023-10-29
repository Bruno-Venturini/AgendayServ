package com.agenday.agendayserv.models;

import com.agenday.agendayserv.enums.StatusAgendamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "agendamento")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Agendamento implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_agendamento")
    @SequenceGenerator(name = "seq_agendamento", sequenceName = "seq_agendamento")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servico", nullable = false)
    private Servico servico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pagamento")
    private Pagamento pagamento;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusAgendamentoEnum status;

    @Column(name = "descricao")
    private String descricao;

    public boolean horarioIsBetween(LocalTime inicio, LocalTime fim) {
        return this.dataHora.toLocalTime().isAfter(inicio) && this.dataHora.toLocalTime().isBefore(fim);
    }

    public LocalTime getHoraInicioAgendamento() {
        return this.dataHora.toLocalTime();
    }

    public LocalTime getHoraFimAgendamento() {
        return this.dataHora.plusMinutes(this.servico.getDuracao()).toLocalTime();
    }
}
