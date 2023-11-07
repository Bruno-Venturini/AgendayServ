package com.agenday.agendayserv.empresa.expediente;

import com.fasterxml.jackson.annotation.JsonFormat;
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

        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime aberturaMatutino;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime fechamentoMatutino;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime aberturaVespertino;
        @JsonFormat(pattern = "HH:mm:ss")
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

        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime aberturaMatutino;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime fechamentoMatutino;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime aberturaVespertino;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime fechamentoVespertino;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class ExpedienteEmpresaResponse {
        private Long id;
        private DayOfWeek diaSemana;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime aberturaMatutino;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime fechamentoMatutino;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime aberturaVespertino;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime fechamentoVespertino;
        private Long empresa;

        public static ExpedienteEmpresaResponse from(ExpedienteEmpresa expedienteEmpresa) {
            return ExpedienteEmpresaResponse.builder()
                    .id(expedienteEmpresa.getId())
                    .diaSemana(expedienteEmpresa.getDiaSemana())
                    .aberturaMatutino(expedienteEmpresa.getAberturaMatutino())
                    .fechamentoMatutino(expedienteEmpresa.getFechamentoMatutino())
                    .aberturaVespertino(expedienteEmpresa.getAberturaVespertino())
                    .fechamentoVespertino(expedienteEmpresa.getFechamentoVespertino())
                    .empresa(expedienteEmpresa.getEmpresa().getId())
                    .build();
        }
    }
}
