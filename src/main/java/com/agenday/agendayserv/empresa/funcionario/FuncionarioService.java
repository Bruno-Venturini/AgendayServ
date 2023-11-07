package com.agenday.agendayserv.empresa.funcionario;

import com.agenday.agendayserv.empresa.EmpresaRepository;
import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FuncionarioService {
    private ModelMapper modelMapper;
    private FuncionarioRepository repository;

    public List<Funcionario> getAll() {
        return repository.findAll();
    }

    public Funcionario getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Funcionario"));
    }

    public Funcionario add(FuncionarioRepresentation.FuncionarioCreate create, EmpresaRepository empresa) {
        return repository.save(Funcionario.builder()
                .nome(create.getNome())
                .email(create.getEmail())
                .senha(create.getSenha())
                .telefone(create.getTelefone())
                .empresa(empresa.findById(create.getEmpresa()).orElseThrow(() -> new NotFoundException("Empresa")))
                .build());
    }

    public Funcionario update(Long id, FuncionarioRepresentation.FuncionarioUpdate entity) {
        var dbEntity = repository.findById(id).orElseThrow(() -> new NotFoundException("Funcionario"));

        modelMapper.map(entity, dbEntity);

        return repository.save(dbEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Funcionario> obterPorEmpresa(Long idEmpresa) {
        return repository.findAll(QFuncionario.funcionario.empresa.id.eq(idEmpresa));
    }

    public List<Funcionario> obterPorServico(Long idServico) {
        return repository.findAll(QFuncionario.funcionario.servicos.any().id.eq(idServico));
    }
}
