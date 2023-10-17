package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.enums.MetodoPagamentoEnum;
import com.agenday.agendayserv.enums.StatusPagamentoEnum;
import com.agenday.agendayserv.models.Pagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagamentoDto {
    private Long id;
    private BigDecimal valor = BigDecimal.ZERO;
    private MetodoPagamentoEnum metodoPagamento;
    private StatusPagamentoEnum statusPagamento;

    public static List<PagamentoDto> fromEntity(List<Pagamento> pagamentos) {
        return pagamentos.stream().map(PagamentoDto::fromEntity).toList();
    }

    public static PagamentoDto fromEntity(Pagamento pagamento) {
        return new PagamentoDto(pagamento.getId(), pagamento.getValor(), pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
    }
}
