package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.Agendamento;
import com.agenday.agendayserv.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/agendamento")
public class AgendamentoController {
    @Autowired
    private AgendamentoService service;

    @GetMapping("/")
    public List<Agendamento> get() {
        return service.getAgendamentos();
    }
}
