package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.Servico;
import com.agenday.agendayserv.models.ServicoMidia;
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
public class ServicoDto {
    private Long id;

    private List<ServicoMidiaDto> midias;

    private String nome;

    private String descricao;

    private BigDecimal preco = BigDecimal.ZERO;

    private Integer duracao;

    private Boolean ativo = Boolean.TRUE;

    public static List<ServicoDto> fromEntity(List<Servico> servicoMidias) {
        return servicoMidias.stream().map(ServicoDto::fromEntity).toList();
    }

    public static ServicoDto fromEntity(Servico servico) {
        return new ServicoDto(servico.getId(), ServicoMidiaDto.fromEntity(servico.getMidias()), servico.getNome(), servico.getDescricao(), servico.getPreco(), servico.getDuracao(), servico.getAtivo());
    }
}
