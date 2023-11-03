package com.agenday.agendayserv.empresa.servico;

import com.agenday.agendayserv.empresa.Empresa;
import com.agenday.agendayserv.empresa.servico.midia.ServicoMidia;
import com.agenday.agendayserv.models.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "servico")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Servico implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_servico")
    @SequenceGenerator(name = "seq_servico", sequenceName = "seq_servico")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco = BigDecimal.ZERO;

    @Column(name = "duracao")
    private Integer duracao;

    @Column(name = "ativo")
    private Boolean ativo = Boolean.TRUE;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "servico")
    private List<ServicoMidia> midias;
}
