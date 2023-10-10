package com.agenday.agendayserv.services;

import com.agenday.agendayserv.model.*;
import com.agenday.agendayserv.repositories.AgendamentoRepository;
import com.agenday.agendayserv.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgendamentoService extends BaseService<Agendamento, Long> {
    @Autowired
    private AgendamentoRepository repository;

    @Autowired
    private ServicoService servicoService;

    @Override
    public AgendamentoRepository getRepository() {
        return repository;
    }

    @Override
    public Agendamento add(Agendamento entity) {
        // validar(entity);

        return super.add(entity);
    }

    public List<Agendamento> obterPorDias(LocalDate inicio, LocalDate fim) {
        return repository.findAll(QAgendamento.agendamento.horario.between(inicio.atStartOfDay(), fim.atTime(23, 59)));
    }

    public List<LocalDate> obterDiasDisponiveis(Long idServico, LocalDate inicio, LocalDate fim) {
        var retorno = new ArrayList<LocalDate>();
        var expedientes = servicoService.getById(idServico).getEmpresa().getExpedientes();
        var agendamentos = obterPorDias(inicio, fim);

        for (var i = inicio; i.isBefore(fim); i = i.plusDays(1)) {
            var dia = i;

            if (expedientes.stream().anyMatch(x -> x.getDiaSemana() == dia.getDayOfWeek()))
                continue;

            if (agendamentos.stream().anyMatch(x -> x.getHorario().toLocalDate() == dia))
                continue;

            retorno.add(i);
        }

        return retorno;
    }

    private void validar(Agendamento agendamento) throws Exception {
        var agora = LocalDateTime.now();

        if (agendamento.getHorario().isBefore(agora)) {
            throw new Exception("É necessário que o horário seja maior que " + agora);
        }

        validarCliente(agendamento.getCliente());
        validarServico(agendamento.getServico());
        validarFuncionario(agendamento.getFuncionario());
    }

    private void validarCliente(Cliente cliente) throws Exception {
        if (cliente == null) {
            throw new Exception("É necessário informar um cliente");
        }

        if (StringUtils.isNullOrEmpty(cliente.getNome())) {
            throw new Exception("É necessário preencher o campo 'nome'");
        }

        if (StringUtils.isNullOrEmpty(cliente.getEmail()) || StringUtils.isNullOrEmpty(cliente.getTelefone())) {
            throw new Exception("É necessário preencher os campos 'telefone' ou 'email'");
        }
    }

    private void validarServico(Servico servico) throws Exception {
        if (servico == null) {
            throw new Exception("É necessário informar um serviço");
        }
    }

    private void validarFuncionario(Funcionario funcionario) throws Exception {
        if (funcionario == null) {
            throw new Exception("É necessário informar um funcionário");
        }
    }
}
