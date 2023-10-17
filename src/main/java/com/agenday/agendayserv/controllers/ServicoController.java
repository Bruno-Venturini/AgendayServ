package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.controllers.dtos.ServicoDto;
import com.agenday.agendayserv.models.Servico;
import com.agenday.agendayserv.services.BaseService;
import com.agenday.agendayserv.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController extends BaseController<Servico, ServicoDto, Long> {
    @Autowired
    private ServicoService service;

    @Override
    protected BaseService<Servico, Long> getService() {
        return service;
    }

    @GetMapping("/empresas/{id}")
    public ResponseEntity<List<ServicoDto>> obterPorEmpresa(
            @PathVariable Long id,
            @RequestParam(defaultValue = "true") Boolean ativo) {
        return ResponseEntity.ok(ServicoDto.fromEntity(service.obterAtivoPorEmpresa(id, ativo)));
    }
}