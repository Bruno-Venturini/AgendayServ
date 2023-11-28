package com.agenday.agendayserv.empresa.funcionario;

import com.agenday.agendayserv.empresa.Empresa;
import com.agenday.agendayserv.empresa.servico.Servico;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "funcionario", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }, name = "cku_email_funcionario")})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_funcionario")
    @SequenceGenerator(name = "seq_funcionario", sequenceName = "seq_funcionario")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_empresa", updatable = true, nullable = false)
    private Empresa empresa;

    @ManyToMany
    @JoinTable(
        name = "funcionario_servico",
        joinColumns = @JoinColumn(name = "id_funcionario"),
        inverseJoinColumns = @JoinColumn(name = "id_servico"))
    private Set<Servico> servicos;
}
