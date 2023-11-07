package com.agenday.agendayserv.empresa;

import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("empresas")
@AllArgsConstructor
public class EmpresaController {
    private EmpresaService service;

    @GetMapping
    public ResponseEntity<List<EmpresaRepresentation.EmpresaResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(EmpresaRepresentation.EmpresaResponse::from).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<EmpresaRepresentation.EmpresaResponse> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(EmpresaRepresentation.EmpresaResponse.from(service.getById(id)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmpresaRepresentation.EmpresaResponse> add(
            @RequestBody EmpresaRepresentation.EmpresaCreate entity) {
        var save = service.add(entity);

        return ResponseEntity
                .created(URI.create("/" + save.getId()))
                .body(EmpresaRepresentation.EmpresaResponse.from(save));
    }

    @PutMapping("{id}")
    public ResponseEntity<EmpresaRepresentation.EmpresaResponse> update(
            @PathVariable Long id, @RequestBody EmpresaRepresentation.EmpresaUpdate entity) {
        try {
            return ResponseEntity.ok().body(EmpresaRepresentation.EmpresaResponse.from(service.update(id, entity)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<EmpresaRepresentation.EmpresaResponse> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
