package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.Servico;
import com.agenday.agendayserv.models.ServicoMidia;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(View.Public.class)
    private Long id;

    @JsonView(View.Public.class)
    private List<ServicoMidiaDto> midias;

    @JsonView(View.Internal.class)
    private String nome;

    @JsonView(View.Internal.class)
    private String descricao;

    @JsonView(View.Internal.class)
    private BigDecimal preco = BigDecimal.ZERO;

    @JsonView(View.Internal.class)
    private Integer duracao;

    @JsonView(View.Internal.class)
    private Boolean ativo = Boolean.TRUE;

    public static List<ServicoDto> fromEntity(List<Servico> servicoMidias) {
        return servicoMidias.stream().map(ServicoDto::fromEntity).toList();
    }

    public static ServicoDto fromEntity(Servico servico) {
        return new ServicoDto(servico.getId(), ServicoMidiaDto.fromEntity(servico.getMidias()), servico.getNome(), servico.getDescricao(), servico.getPreco(), servico.getDuracao(), servico.getAtivo());
    }
}
