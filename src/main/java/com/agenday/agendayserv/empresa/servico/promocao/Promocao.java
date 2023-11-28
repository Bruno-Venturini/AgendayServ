package com.agenday.agendayserv.empresa.servico.promocao;

import com.agenday.agendayserv.empresa.servico.Servico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "promocao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Promocao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_promocao")
    @SequenceGenerator(name = "seq_promocao", sequenceName = "seq_promocao")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_servico", nullable = false)
    private Servico servico;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "desconto", precision = 5, scale = 2, nullable = false)
    private BigDecimal desconto;

    @Column(name = "ativo")
    private Boolean ativo = Boolean.TRUE;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFinal;
}
