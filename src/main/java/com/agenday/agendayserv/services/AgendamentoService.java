package com.agenday.agendayserv.services;

import com.agenday.agendayserv.model.Agendamento;
import com.agenday.agendayserv.model.Cliente;
import com.agenday.agendayserv.model.Funcionario;
import com.agenday.agendayserv.model.Servico;
import com.agenday.agendayserv.repositories.AgendamentoRepository;
import com.agenday.agendayserv.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgendamentoService extends AbstractService<Agendamento, Long> {
    @Autowired
    private AgendamentoRepository repository;

    @Override
    public AgendamentoRepository getRepository() {
        return repository;
    }

    @Override
    public Agendamento adicionar(Agendamento agendamento) throws Exception {
        validar(agendamento);

        return super.adicionar(agendamento);
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

        if (StringUtils.isNullOrEmpty(cliente.getEmail()) ||
            StringUtils.isNullOrEmpty(cliente.getTelefone())) {
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
