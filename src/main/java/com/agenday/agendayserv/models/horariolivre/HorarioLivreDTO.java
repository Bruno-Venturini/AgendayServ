package com.agenday.agendayserv.models.horariolivre;

import com.agenday.agendayserv.models.Funcionario;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

@Getter
@Setter
public class HorarioLivreDTO {

    private Funcionario funcionario;

    private List<AgendamentoLivreDTO> agendamentosLivre;

    public void addAgendamento(AgendamentoLivreDTO agendamentoLivre) {
        this.agendamentosLivre.add(agendamentoLivre);
    }

    public void removeAgendamento(AgendamentoLivreDTO agendamentoLivre) {
        this.agendamentosLivre.remove(agendamentoLivre);
    }
}
