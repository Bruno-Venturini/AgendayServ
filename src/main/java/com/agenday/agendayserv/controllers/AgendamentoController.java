package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.Agendamento;
import com.agenday.agendayserv.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AgendamentoController {
    @Autowired
    private AgendamentoService service;

    @GetMapping("/agendamento")
    public List<Agendamento> get() {
        return service.getAgendamentos();
    }

    @PostMapping("/agendamento")
    public void post(Agendamento agendamento) {
        service.save(agendamento);
    }

    @DeleteMapping("/agendamento")
    public void post(Long id) {

    }
}
