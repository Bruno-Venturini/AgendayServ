package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.Empresa;
import com.agenday.agendayserv.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
    @Autowired
    private EmpresaService service;

    @GetMapping
    public List<Empresa> buscarTodasEmpresas() {
        return service.obterTodos();
    }

    @PostMapping
    public Empresa adicionar(Empresa empresa) throws Exception {
        return service.adicionar(empresa);
    }

    @PutMapping("/{id}")
    public Empresa atualizar(@PathParam("id") Long id, Empresa empresa) {
        return service.atualizar(id, empresa);
    }

    @DeleteMapping
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
