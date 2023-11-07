package com.agenday.agendayserv.empresa.expediente;

import com.agenday.agendayserv.empresa.EmpresaRepository;
import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("empresas/{idEmpresa}/expedientes")
@AllArgsConstructor
public class ExpedienteEmpresaController {
    private ExpedienteEmpresaService service;
    private EmpresaRepository empresaRepository;

    @GetMapping
    public ResponseEntity<List<ExpedienteEmpresaRepresentation.ExpedienteEmpresaResponse>> getAll(@PathVariable Long idEmpresa) {
        return ResponseEntity.ok(service.findByEmpresaId(idEmpresa).stream().map(ExpedienteEmpresaRepresentation.ExpedienteEmpresaResponse::from).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<ExpedienteEmpresaRepresentation.ExpedienteEmpresaResponse> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ExpedienteEmpresaRepresentation.ExpedienteEmpresaResponse.from(service.getById(id)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<ExpedienteEmpresaRepresentation.ExpedienteEmpresaResponse> add(
            @RequestBody ExpedienteEmpresaRepresentation.ExpedienteEmpresaCreate entity) {
        var save = service.add(entity, empresaRepository);

        return ResponseEntity
                .created(URI.create("/" + save.getId()))
                .body(ExpedienteEmpresaRepresentation.ExpedienteEmpresaResponse.from(save));
    }

    @PutMapping("{id}")
    public ResponseEntity<ExpedienteEmpresaRepresentation.ExpedienteEmpresaResponse> update(
            @PathVariable Long id, @RequestBody ExpedienteEmpresaRepresentation.ExpedienteEmpresaUpdate entity) {
        try {
            return ResponseEntity.ok().body(ExpedienteEmpresaRepresentation.ExpedienteEmpresaResponse.from(service.update(id, entity)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ExpedienteEmpresaRepresentation.ExpedienteEmpresaResponse> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
