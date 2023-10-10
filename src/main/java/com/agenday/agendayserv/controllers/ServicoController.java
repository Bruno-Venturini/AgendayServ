package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.Servico;
import com.agenday.agendayserv.services.BaseService;
import com.agenday.agendayserv.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController extends BaseController<Servico, Long> {
    @Autowired
    private ServicoService service;

    @Override
    protected BaseService<Servico, Long> getService() {
        return service;
    }

    @GetMapping("/empresas/{id}")
    public List<Servico> obterPorEmpresa(@PathVariable Long id) {
        return service.obterPorEmpresa(id);
    }
}