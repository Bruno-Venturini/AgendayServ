package com.agenday.agendayserv.model;

import com.agenday.agendayserv.enums.MetodoPagamentoEnum;
import com.agenday.agendayserv.enums.StatusPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pagamento")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pagamento")
    @SequenceGenerator(name = "seq_pagamento", sequenceName = "seq_pagamento")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @JoinColumn(name = "id_agendamento")
    @ManyToOne
    private Agendamento agendamento;

    @Column(name = "valor")
    private BigDecimal valor = BigDecimal.ZERO;

    @Column(name = "metodo_pagamento")
    @Enumerated(EnumType.STRING)
    private MetodoPagamentoEnum metodoPagamento;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum statusPagamento;
}
