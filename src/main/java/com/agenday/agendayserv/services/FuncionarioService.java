package com.agenday.agendayserv.services;

import com.agenday.agendayserv.models.Funcionario;
import com.agenday.agendayserv.models.QFuncionario;
import com.agenday.agendayserv.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService extends BaseService<Funcionario, Long> {
    @Autowired
    private FuncionarioRepository repository;

    @Override
    public FuncionarioRepository getRepository() {
        return repository;
    }

    public List<Funcionario> obterPorEmpresa(Long idEmpresa) {
        return repository.findAll(QFuncionario.funcionario.empresa.id.eq(idEmpresa));
    }
}
