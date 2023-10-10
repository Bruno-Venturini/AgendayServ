package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.Empresa;
import com.agenday.agendayserv.services.BaseService;
import com.agenday.agendayserv.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresa")
public class EmpresaController extends BaseController<Empresa, Long> {
    @Autowired
    private EmpresaService service;

    @Override
    protected BaseService<Empresa, Long> getService() {
        return service;
    }
}
