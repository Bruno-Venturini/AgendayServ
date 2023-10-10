package com.agenday.agendayserv.services;

import com.agenday.agendayserv.model.Empresa;
import com.agenday.agendayserv.model.DiaDisponivel;
import com.agenday.agendayserv.model.Servico;
import com.agenday.agendayserv.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpresaService extends BaseService<Empresa, Long> {
    @Autowired
    private EmpresaRepository repository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Override
    public EmpresaRepository getRepository() {
        return repository;
    }

    public List<DiaDisponivel> obterHorariosDisponiveis(Servico servico, LocalDateTime inicio, LocalDateTime fim) {
        var empresa = servico.getEmpresa();
        var expedientes = empresa.getExpedientes();
        var funcionarios = funcionarioService.obterPorEmpresa(empresa.getId());

        for (var i = inicio; i.isBefore(fim); i.plusDays(1)) {
            var expediente = expedientes.stream().filter(x -> x.getDiaSemana() == i.getDayOfWeek());


        }

        return null;
    }
}
