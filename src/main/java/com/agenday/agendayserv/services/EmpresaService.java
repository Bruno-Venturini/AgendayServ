package com.agenday.agendayserv.services;

import com.agenday.agendayserv.model.Agendamento;
import com.agenday.agendayserv.model.Empresa;
import com.agenday.agendayserv.repositories.AgendamentoRepository;
import com.agenday.agendayserv.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository repository;

    public List<Empresa> buscarTodos() {
        return repository.findAll();
    }

    public Empresa adicionarEmpresa(Empresa empresa) {
        return repository.save(empresa);
    }

    public Empresa atualizarEmpresa(Empresa empresa) {
        return repository.save(empresa);
    }

    public void deletarEmpresa(Long id) {
        repository.deleteById(id);
    }
}
