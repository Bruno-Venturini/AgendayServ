package com.agenday.agendayserv.agendamento.pagamento;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pagamento")
@Builder
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

    @Column(name = "valor")
    private BigDecimal valor = BigDecimal.ZERO;

    @Column(name = "metodo_pagamento")
    @Enumerated(EnumType.STRING)
    private MetodoPagamentoEnum metodoPagamento;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum statusPagamento;
}
