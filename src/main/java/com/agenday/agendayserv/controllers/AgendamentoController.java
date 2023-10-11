package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.model.Agendamento;
import com.agenday.agendayserv.services.AgendamentoService;
import com.agenday.agendayserv.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController extends BaseController<Agendamento, Long> {
    @Autowired
    private AgendamentoService service;

    @Override
    protected BaseService<Agendamento, Long> getService() {
        return service;
    }

    @GetMapping("/servicos/{idServico}")
    public List<LocalDate> obterDiasDisponiveis(
            @PathVariable Long idServico,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate inicio,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fim) {
        return service.obterDiasDisponiveis(idServico, inicio, fim);
    }
}