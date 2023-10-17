package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.enums.StatusAgendamentoEnum;
import com.agenday.agendayserv.models.Agendamento;
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
    private Long id;
    private ClienteDto cliente;
    private ServicoDto servico;
    private FuncionarioDto funcionario;
    private PagamentoDto pagamento;
    private LocalDateTime horario;
    private StatusAgendamentoEnum status;
    private String descricao;

    public static List<AgendamentoDto> fromEntity(List<Agendamento> agendamentos) {
        return agendamentos.stream().map(AgendamentoDto::fromEntity).toList();
    }

    public static AgendamentoDto fromEntity(Agendamento agendamento) {
        return new AgendamentoDto(agendamento.getId(), ClienteDto.fromEntity(agendamento.getCliente()), ServicoDto.fromEntity(agendamento.getServico()), FuncionarioDto.fromEntity(agendamento.getFuncionario()), PagamentoDto.fromEntity(agendamento.getPagamento()), agendamento.getHorario(), agendamento.getStatus(), agendamento.getDescricao());
    }
}
