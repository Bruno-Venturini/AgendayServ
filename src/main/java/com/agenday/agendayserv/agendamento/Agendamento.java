package com.agenday.agendayserv.agendamento;

import com.agenday.agendayserv.cliente.Cliente;
import com.agenday.agendayserv.empresa.funcionario.Funcionario;
import com.agenday.agendayserv.agendamento.pagamento.Pagamento;
import com.agenday.agendayserv.empresa.servico.Servico;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "agendamento")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_agendamento")
    @SequenceGenerator(name = "seq_agendamento", sequenceName = "seq_agendamento")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusAgendamentoEnum status;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_servico", nullable = false)
    private Servico servico;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_pagamento")
    private Pagamento pagamento;

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
