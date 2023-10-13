package com.agenday.agendayserv.services;

import com.agenday.agendayserv.models.QServico;
import com.agenday.agendayserv.models.Servico;
import com.agenday.agendayserv.repositories.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService extends BaseService<Servico, Long> {
    @Autowired
    private ServicoRepository repository;

    @Override
    public ServicoRepository getRepository() {
        return repository;
    }

    public List<Servico> obterAtivoPorEmpresa(Long id, Boolean ativo) {
        return repository.findAll(QServico.servico.empresa.id.eq(id).and(QServico.servico.ativo.eq(ativo)));
    }
}
