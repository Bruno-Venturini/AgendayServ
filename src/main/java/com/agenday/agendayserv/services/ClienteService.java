package com.agenday.agendayserv.services;

import com.agenday.agendayserv.models.Cliente;
import com.agenday.agendayserv.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends BaseService<Cliente, Long> {
    @Autowired
    private ClienteRepository repository;

    @Override
    public ClienteRepository getRepository() {
        return repository;
    }
}
