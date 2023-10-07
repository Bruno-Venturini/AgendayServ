package com.agenday.agendayserv.services;

import com.agenday.agendayserv.model.Empresa;
import com.agenday.agendayserv.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService implements CrudService<Empresa, Long> {
    @Autowired
    private EmpresaRepository repository;

    public List<Empresa> obterTodos() {
        return repository.findAll();
    }

    public Empresa adicionar(Empresa empresa) {
        return repository.save(empresa);
    }

    public Empresa atualizar(Empresa empresa) {
        return repository.save(empresa);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
