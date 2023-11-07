package com.agenday.agendayserv.agendamento.horariolivre;

import com.agenday.agendayserv.empresa.funcionario.Funcionario;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HorarioLivreDTO {
    private Funcionario funcionario;
    private List<AgendamentoLivreDTO> agendamentosLivre = new ArrayList<>();

    public void addAgendamento(AgendamentoLivreDTO agendamentoLivre) {
        this.agendamentosLivre.add(agendamentoLivre);
    }

    public void removeAgendamento(AgendamentoLivreDTO agendamentoLivre) {
        this.agendamentosLivre.remove(agendamentoLivre);
    }
}
