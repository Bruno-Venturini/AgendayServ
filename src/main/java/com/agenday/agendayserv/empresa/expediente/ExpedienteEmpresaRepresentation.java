package com.agenday.agendayserv.empresa.expediente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

public interface ExpedienteEmpresaRepresentation {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class ExpedienteEmpresaCreate {
        @NotNull(message = "O dia da semana não pode ser nulo")
        private DayOfWeek diaSemana;
        
        private LocalTime aberturaMatutino;
        private LocalTime fechamentoMatutino;
        private LocalTime aberturaVespertino;
        private LocalTime fechamentoVespertino;

        @NotNull(message = "A empresa não pode ser nula")
        private Long empresa;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class ExpedienteEmpresaUpdate {
        @NotNull(message = "O dia da semana não pode ser nulo")
        private DayOfWeek diaSemana;

        private LocalTime aberturaMatutino;
        private LocalTime fechamentoMatutino;
        private LocalTime aberturaVespertino;
        private LocalTime fechamentoVespertino;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class ExpedienteEmpresaResponse {
        private Long id;
        private LocalTime aberturaMatutino;
        private LocalTime fechamentoMatutino;
        private LocalTime aberturaVespertino;
        private LocalTime fechamentoVespertino;
        private Long empresa;

        public static ExpedienteEmpresaResponse from(ExpedienteEmpresa expedienteEmpresa) {
            return ExpedienteEmpresaResponse.builder()
                    .id(expedienteEmpresa.getId())
                    .aberturaMatutino(expedienteEmpresa.getAberturaMatutino())
                    .fechamentoMatutino(expedienteEmpresa.getFechamentoMatutino())
                    .aberturaVespertino(expedienteEmpresa.getAberturaVespertino())
                    .fechamentoVespertino(expedienteEmpresa.getFechamentoVespertino())
                    .empresa(expedienteEmpresa.getEmpresa().getId())
                    .build();
        }
    }
}
