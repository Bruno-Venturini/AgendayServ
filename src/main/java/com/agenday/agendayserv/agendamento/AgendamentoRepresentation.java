package com.agenday.agendayserv.agendamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface AgendamentoRepresentation {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class AgendamentoCreateUpdate {
        @NotNull(message = "O horário não pode ser nulo")
        @NotEmpty(message = "O horário não pode ser vazio")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime dataHora;

        @NotNull(message = "O status não pode ser nulo")
        private StatusAgendamentoEnum status;

        @NotNull(message = "A descrição não pode ser nula")
        @NotEmpty(message = "A descrição não pode ser vazia")
        private String descricao;

        @NotNull(message = "O cliente não pode ser nulo")
        private Long cliente;

        @NotNull(message = "O serviço não pode ser nulo")
        private Long servico;

        @NotNull(message = "O funcionário não pode ser nulo")
        private Long funcionario;

        private Long pagamento;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class AgendamentoResponse {
        private Long id;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime dataHora;
        private StatusAgendamentoEnum status;
        private String descricao;
        private Long cliente;
        private Long servico;
        private Long funcionario;
        private Long pagamento;

        public static AgendamentoResponse from(Agendamento agendamento) {
            return AgendamentoResponse.builder()
                    .id(agendamento.getId())
                    .dataHora(agendamento.getDataHora())
                    .status(agendamento.getStatus())
                    .descricao(agendamento.getDescricao())
                    .cliente(agendamento.getCliente().getId())
                    .servico(agendamento.getServico().getId())
                    .funcionario(agendamento.getFuncionario().getId())
                    .pagamento(agendamento.getPagamento() == null ? 0 : agendamento.getPagamento().getId())
                    .build();
        }
    }
}
