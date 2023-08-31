package com.agenday.agendayserv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "funcionario_servico")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FuncionarioServico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_funcionario_servico")
    @SequenceGenerator(name = "seq_funcionario_servico", sequenceName = "seq_funcionario_servico")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_servico")
    private Servico servico;
}
