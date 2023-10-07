package com.agenday.agendayserv.model;

import com.agenday.agendayserv.enums.StatusAgendamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamento")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Agendamento {
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

    @Column(name = "horario")
    private LocalDateTime horario;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusAgendamentoEnum status;

    @Column(name = "descricao")
    private String descricao;
}
