package com.agenday.agendayserv.services;

import com.agenday.agendayserv.model.QServico;
import com.agenday.agendayserv.model.Servico;
import com.agenday.agendayserv.repositories.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService extends AbstractService<Servico, Long> {
    @Autowired
    private ServicoRepository repository;

    @Override
    public ServicoRepository getRepository() {
        return repository;
    }

    public List<Servico> obterPorEmpresa(Long id) {
        return getRepository().findAll(QServico.servico.empresa.id.eq(id));
    }
}
