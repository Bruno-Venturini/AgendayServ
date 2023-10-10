package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.Agendamento;
import com.agenday.agendayserv.services.BaseService;
import com.agenday.agendayserv.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController extends BaseController<Agendamento, Long> {
    @Autowired
    private AgendamentoService service;

    @Override
    protected BaseService<Agendamento, Long> getService() {
        return service;
    }
}