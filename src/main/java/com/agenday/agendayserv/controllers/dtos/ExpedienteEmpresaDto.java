package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.ExpedienteEmpresa;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpedienteEmpresaDto {
    @JsonView(View.Public.class)
    private Long id;

    @JsonView(View.Internal.class)
    private DayOfWeek diaSemana;

    @JsonView(View.Internal.class)
    private LocalTime aberturaMatutino;

    @JsonView(View.Internal.class)
    private LocalTime fechamentoMatutino;

    @JsonView(View.Internal.class)
    private LocalTime aberturaVespertino;

    @JsonView(View.Internal.class)
    private LocalTime fechamentoVespertino;

    public static List<ExpedienteEmpresaDto> fromEntity(List<ExpedienteEmpresa> expedientesEmpresa) {
        return expedientesEmpresa.stream().map(ExpedienteEmpresaDto::fromEntity).toList();
    }

    public static ExpedienteEmpresaDto fromEntity(ExpedienteEmpresa expedienteEmpresa) {
        return new ExpedienteEmpresaDto(expedienteEmpresa.getId(), expedienteEmpresa.getDiaSemana(), expedienteEmpresa.getAberturaMatutino(), expedienteEmpresa.getAberturaVespertino(), expedienteEmpresa.getFechamentoMatutino(), expedienteEmpresa.getFechamentoVespertino());
    }
}
