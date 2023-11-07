package com.agenday.agendayserv.empresa.servico;

import com.agenday.agendayserv.empresa.EmpresaRepository;
import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServicoService {
    private ModelMapper modelMapper;
    private ServicoRepository repository;

    public List<Servico> getAll() {
        return repository.findAll();
    }

    public Servico getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Servico"));
    }

    public Servico add(ServicoRepresentation.ServicoCreate create, EmpresaRepository empresa) {
        return repository.save(Servico.builder()
                .nome(create.getNome())
                .preco(create.getPreco())
                .duracao(create.getDuracao())
                .ativo(create.getAtivo())
                .empresa(empresa.findById(create.getEmpresa()).orElseThrow(() -> new NotFoundException("Empresa")))
                .build());
    }

    public Servico update(Long id, ServicoRepresentation.ServicoUpdate entity) {
        var dbEntity = repository.findById(id).orElseThrow(() -> new NotFoundException("Servico"));

        modelMapper.map(entity, dbEntity);

        return repository.save(dbEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Servico> obterAtivoPorEmpresa(Long id, Boolean ativo) {
        return repository.findAll(QServico.servico.empresa.id.eq(id).and(QServico.servico.ativo.eq(ativo)));
    }
}
