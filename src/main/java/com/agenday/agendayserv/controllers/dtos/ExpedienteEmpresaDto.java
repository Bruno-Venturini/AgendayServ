package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.ExpedienteEmpresa;
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
    private Long id;
    private DayOfWeek diaSemana;
    private LocalTime aberturaMatutino;
    private LocalTime fechamentoMatutino;
    private LocalTime aberturaVespertino;
    private LocalTime fechamentoVespertino;

    public static List<ExpedienteEmpresaDto> fromEntity(List<ExpedienteEmpresa> expedientesEmpresa) {
        return expedientesEmpresa.stream().map(ExpedienteEmpresaDto::fromEntity).toList();
    }

    public static ExpedienteEmpresaDto fromEntity(ExpedienteEmpresa expedienteEmpresa) {
        return new ExpedienteEmpresaDto(expedienteEmpresa.getId(), expedienteEmpresa.getDiaSemana(), expedienteEmpresa.getAberturaMatutino(), expedienteEmpresa.getAberturaVespertino(), expedienteEmpresa.getFechamentoMatutino(), expedienteEmpresa.getFechamentoVespertino());
    }
}
