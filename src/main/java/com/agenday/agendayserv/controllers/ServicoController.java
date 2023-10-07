package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.Servico;
import com.agenday.agendayserv.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {
    @Autowired
    private ServicoService service;

    @GetMapping
    public List<Servico> obterTodos() {
        return service.obterTodos();
    }

    @GetMapping("/{id}")
    public Servico obterPorId(@PathVariable Long id) {
        return service.obterPorId(id);
    }

    @GetMapping("/empresa/{id}")
    public List<Servico> obterPorEmpresa(@PathVariable Long id) {
        return service.obterPorEmpresa(id);
    }

    @PostMapping
    public Servico adicionar(@RequestBody Servico servico) throws Exception {
        return service.adicionar(servico);
    }

    @PutMapping("/{id}")
    public Servico atualizar(@PathVariable Long id, @RequestBody Servico servico) {
        return service.atualizar(id, servico);
    }

    @DeleteMapping
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}