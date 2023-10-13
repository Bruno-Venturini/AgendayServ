package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.models.Servico;
import com.agenday.agendayserv.services.BaseService;
import com.agenday.agendayserv.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> obterPorEmpresa(@PathVariable Long id, @RequestParam Boolean ativo) {
        return ResponseEntity.ok(service.obterAtivoPorEmpresa(id, ativo));
    }
}