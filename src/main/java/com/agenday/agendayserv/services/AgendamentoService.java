package com.agenday.agendayserv.services;

import com.agenday.agendayserv.model.Agendamento;
import com.agenday.agendayserv.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository repository;

    public List<Agendamento> getAgendamentos() {
        return repository.findAll();
    }

    public void addAgendamento(Agendamento agendamento) {

    }
}
