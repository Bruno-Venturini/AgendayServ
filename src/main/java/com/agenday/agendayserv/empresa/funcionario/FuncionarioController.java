package com.agenday.agendayserv.empresa.funcionario;

import com.agenday.agendayserv.empresa.EmpresaRepository;
import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("empresas/{idEmpresa}/funcionarios")
@AllArgsConstructor
public class FuncionarioController {
    private FuncionarioService service;
    private EmpresaRepository empresaRepository;

    @GetMapping
    public ResponseEntity<List<FuncionarioRepresentation.FuncionarioResponse>> getAll(@PathVariable Long idEmpresa) {
        return ResponseEntity.ok(service.obterPorEmpresa(idEmpresa).stream().map(FuncionarioRepresentation.FuncionarioResponse::from).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<FuncionarioRepresentation.FuncionarioResponse> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(FuncionarioRepresentation.FuncionarioResponse.from(service.getById(id)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<FuncionarioRepresentation.FuncionarioResponse> add(
            @RequestBody FuncionarioRepresentation.FuncionarioCreate entity) {
        var save = service.add(entity, empresaRepository);

        return ResponseEntity
                .created(URI.create("/" + save.getId()))
                .body(FuncionarioRepresentation.FuncionarioResponse.from(save));
    }

    @PutMapping("{id}")
    public ResponseEntity<FuncionarioRepresentation.FuncionarioResponse> update(
            @PathVariable Long id, @RequestBody FuncionarioRepresentation.FuncionarioUpdate entity) {
        try {
            return ResponseEntity.ok().body(FuncionarioRepresentation.FuncionarioResponse.from(service.update(id, entity)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<FuncionarioRepresentation.FuncionarioResponse> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}