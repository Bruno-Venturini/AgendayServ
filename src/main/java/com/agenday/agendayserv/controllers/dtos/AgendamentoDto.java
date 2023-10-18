package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.enums.StatusAgendamentoEnum;
import com.agenday.agendayserv.models.Agendamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoDto {
    @JsonView(View.Public.class)
    private Long id;

    @JsonView(View.Public.class)
    @JsonIgnoreProperties({"nome", "email", "senha", "telefone"})
    private ClienteDto cliente;

    @JsonView(View.Public.class)
    @JsonIgnoreProperties({"midias", "nome", "descricao", "preco", "duracao", "ativo"})
    private ServicoDto servico;

    @JsonView(View.Public.class)
    @JsonIgnoreProperties({"nome", "email", "senha", "telefone"})
    private FuncionarioDto funcionario;

    @JsonView(View.Public.class)
    @JsonIgnoreProperties({"valor", "metodoPagamento", "statusPagamento"})
    private PagamentoDto pagamento;

    @JsonView(View.Internal.class)
    private LocalDateTime horario;

    @JsonView(View.Internal.class)
    private StatusAgendamentoEnum status;

    @JsonView(View.Internal.class)
    private String descricao;

    public static List<AgendamentoDto> fromEntity(List<Agendamento> agendamentos) {
        return agendamentos.stream().map(AgendamentoDto::fromEntity).toList();
    }

    public static AgendamentoDto fromEntity(Agendamento agendamento) {
        return new AgendamentoDto(agendamento.getId(), ClienteDto.fromEntity(agendamento.getCliente()), ServicoDto.fromEntity(agendamento.getServico()), FuncionarioDto.fromEntity(agendamento.getFuncionario()), PagamentoDto.fromEntity(agendamento.getPagamento()), agendamento.getHorario(), agendamento.getStatus(), agendamento.getDescricao());
    }
}
