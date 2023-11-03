package com.agenday.agendayserv.empresa.servico;

import com.agenday.agendayserv.empresa.EmpresaRepository;
import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("empresas/{idEmpresa}/servicos")
@AllArgsConstructor
@NoArgsConstructor
public class ServicoController {
    private ServicoService service;
    private EmpresaRepository empresaRepository;

    @GetMapping
    public ResponseEntity<List<ServicoRepresentation.ServicoResponse>> getAll(
            @PathVariable Long idEmpresa, @RequestParam(defaultValue = "true") Boolean ativo) {
        return ResponseEntity.ok(service.obterAtivoPorEmpresa(idEmpresa, ativo).stream().map(ServicoRepresentation.ServicoResponse::from).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<ServicoRepresentation.ServicoResponse> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ServicoRepresentation.ServicoResponse.from(service.getById(id)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<ServicoRepresentation.ServicoResponse> add(
            @RequestBody ServicoRepresentation.ServicoCreate entity) {
        var save = service.add(entity, empresaRepository);

        return ResponseEntity
                .created(URI.create("/" + save.getId()))
                .body(ServicoRepresentation.ServicoResponse.from(save));
    }

    @PutMapping("{id}")
    public ResponseEntity<ServicoRepresentation.ServicoResponse> update(
            @PathVariable Long id, @RequestBody ServicoRepresentation.ServicoUpdate entity) {
        try {
            return ResponseEntity.ok().body(ServicoRepresentation.ServicoResponse.from(service.update(id, entity)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ServicoRepresentation.ServicoResponse> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}