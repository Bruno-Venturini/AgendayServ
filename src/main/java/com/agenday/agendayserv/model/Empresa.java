package com.agenday.agendayserv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "empresa" , uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }, name = "cku_email_empresa")})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Empresa {

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
}
