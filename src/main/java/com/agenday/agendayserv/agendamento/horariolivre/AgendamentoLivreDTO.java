package com.agenday.agendayserv.agendamento.horariolivre;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoLivreDTO {
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime inicioHorarioLivre;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime fimHorarioLivre;
}
