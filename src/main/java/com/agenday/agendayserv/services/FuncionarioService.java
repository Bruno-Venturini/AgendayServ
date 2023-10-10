package com.agenday.agendayserv.services;

import com.agenday.agendayserv.model.Funcionario;
import com.agenday.agendayserv.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService extends BaseService<Funcionario, Long> {
    @Autowired
    private FuncionarioRepository repository;

    @Override
    public FuncionarioRepository getRepository() {
        return repository;
    }
}
