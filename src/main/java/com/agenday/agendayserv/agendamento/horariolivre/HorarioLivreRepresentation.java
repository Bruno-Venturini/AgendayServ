package com.agenday.agendayserv.agendamento.horariolivre;

import com.agenday.agendayserv.empresa.funcionario.FuncionarioRepresentation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

public interface HorarioLivreRepresentation {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class HorarioLivreResponse {
        private FuncionarioRepresentation.FuncionarioResponse funcionario;
        private List<AgendamentoLivreResponse> agendamentosLivres;

        public static HorarioLivreResponse from(HorarioLivreDTO agendamento) {
            return HorarioLivreResponse.builder()
                    .funcionario(FuncionarioRepresentation.FuncionarioResponse.from(agendamento.getFuncionario()))
                    .agendamentosLivres(agendamento.getAgendamentosLivre().stream().map(AgendamentoLivreResponse::from).toList())
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class AgendamentoLivreResponse {
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime inicioHorarioLivre;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime fimHorarioLivre;

        public static AgendamentoLivreResponse from(AgendamentoLivreDTO agendamento) {
            return AgendamentoLivreResponse.builder()
                    .inicioHorarioLivre(agendamento.getInicioHorarioLivre())
                    .fimHorarioLivre(agendamento.getFimHorarioLivre())
                    .build();
        }
    }
}
