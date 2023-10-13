package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.models.Cliente;
import com.agenday.agendayserv.services.BaseService;
import com.agenday.agendayserv.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController extends BaseController<Cliente, Long> {
    @Autowired
    private ClienteService service;

    @Override
    protected BaseService<Cliente, Long> getService() {
        return service;
    }
}