package com.agenday.agendayserv.services;

import com.agenday.agendayserv.model.Empresa;
import com.agenday.agendayserv.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService extends BaseService<Empresa, Long> {
    @Autowired
    private EmpresaRepository repository;

    @Override
    public EmpresaRepository getRepository() {
        return repository;
    }
}
