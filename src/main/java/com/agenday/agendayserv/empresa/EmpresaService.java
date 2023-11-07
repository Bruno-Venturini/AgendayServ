package com.agenday.agendayserv.empresa;

import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmpresaService {
    private ModelMapper modelMapper;
    private EmpresaRepository repository;

    public List<Empresa> getAll() {
        return repository.findAll();
    }

    public Empresa getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Empresa"));
    }

    public Empresa add(EmpresaRepresentation.EmpresaCreate create) {
        return repository.save(Empresa.builder()
                .nome(create.getNome())
                .email(create.getEmail())
                .senha(create.getSenha())
                .telefone(create.getTelefone())
                .build());
    }

    public Empresa update(Long id, EmpresaRepresentation.EmpresaUpdate entity) {
        var dbEntity = repository.findById(id).orElseThrow(() -> new NotFoundException("Empresa"));

        modelMapper.map(entity, dbEntity);

        return repository.save(dbEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
