package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.Agendamento;
import com.agenday.agendayserv.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {
    @Autowired
    private AgendamentoService service;

    @GetMapping
    public List<Agendamento> obterTodos() {
        return service.obterTodos();
    }

    @GetMapping("/{id}")
    public Agendamento obterPorId((@PathParam("id") Long id) {
        return service.obterPorId(id);
    }

    @PostMapping
    public Agendamento adicionar(Agendamento agendamento) throws Exception {
        return service.adicionar(agendamento);
    }

    @PutMapping("/{id}")
    public Agendamento atualizar(@PathParam("id") Long id, Agendamento agendamento) {
        return service.atualizar(agendamento);
    }

    @DeleteMapping
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}