package com.agenday.agendayserv.cliente;

import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {
    private ModelMapper modelMapper;
    private ClienteRepository repository;

    public List<Cliente> getAll() {
        return repository.findAll();
    }

    public Cliente getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Cliente"));
    }

    public Cliente add(ClienteRepresentation.ClienteCreateUpdate create) {
        return repository.save(Cliente.builder()
                .nome(create.getNome())
                .email(create.getEmail())
                .senha(create.getSenha())
                .telefone(create.getTelefone())
                .build());
    }

    public Cliente update(Long id, ClienteRepresentation.ClienteCreateUpdate entity) {
        var dbEntity = repository.findById(id).orElseThrow(() -> new NotFoundException("Cliente"));

        modelMapper.map(entity, dbEntity);

        return repository.save(dbEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
