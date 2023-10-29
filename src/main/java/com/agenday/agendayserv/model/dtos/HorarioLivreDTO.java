package com.agenday.agendayserv.model.dtos;

import com.agenday.agendayserv.models.Funcionario;
import lombok.Getter;
import lombok.Setter;

import java.time.Period;
import java.util.List;


@Getter
@Setter
public class HorarioLivreDTO {

    private Funcionario funcionario;

    private List<Period> periodosLivres;
}
