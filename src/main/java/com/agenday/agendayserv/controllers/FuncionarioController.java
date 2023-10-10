package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.Funcionario;
import com.agenday.agendayserv.services.BaseService;
import com.agenday.agendayserv.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController extends BaseController<Funcionario, Long> {
    @Autowired
    private FuncionarioService service;

    @Override
    protected BaseService<Funcionario, Long> getService() {
        return service;
    }
}