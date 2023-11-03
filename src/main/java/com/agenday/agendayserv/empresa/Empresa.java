package com.agenday.agendayserv.empresa;

import com.agenday.agendayserv.empresa.funcionario.Funcionario;
import com.agenday.agendayserv.empresa.servico.Servico;
import com.agenday.agendayserv.models.BaseEntity;
import com.agenday.agendayserv.empresa.expediente.ExpedienteEmpresa;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresa" , uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }, name = "cku_email_empresa") })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Empresa implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empresa")
    @SequenceGenerator(name = "seq_empresa", sequenceName = "seq_empresa")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "telefone")
    private String telefone;

    @OneToMany(mappedBy = "empresa")
    private List<ExpedienteEmpresa> expedientes = new ArrayList<>();

    @OneToMany(mappedBy = "empresa")
    private List<Servico> servicos = new ArrayList<>();

    @OneToMany(mappedBy = "empresa")
    private List<Funcionario> funcionarios = new ArrayList<>();
}
