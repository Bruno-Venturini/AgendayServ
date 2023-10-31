package com.agenday.agendayserv.models.horariolivre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoLivreDTO {
    private LocalTime inicioHorarioLivre;

    private LocalTime fimHorarioLivre;

    public boolean horariosLivreIsBetween(LocalDateTime inicio, LocalDateTime fim) {
        return this.inicioHorarioLivre.isAfter(inicio.toLocalTime()) && this.fimHorarioLivre.isBefore(fim.toLocalTime());
    }
}
