package com.agenday.agendayserv.cliente;

import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("clientes")
@AllArgsConstructor
public class ClienteController {
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteRepresentation.ClienteResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(ClienteRepresentation.ClienteResponse::from).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteRepresentation.ClienteResponse> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ClienteRepresentation.ClienteResponse.from(service.getById(id)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<ClienteRepresentation.ClienteResponse> add(
            @RequestBody ClienteRepresentation.ClienteCreateUpdate entity) {
        var save = service.add(entity);

        return ResponseEntity
                .created(URI.create("/" + save.getId()))
                .body(ClienteRepresentation.ClienteResponse.from(save));
    }

    @PutMapping("{id}")
    public ResponseEntity<ClienteRepresentation.ClienteResponse> update(
            @PathVariable Long id, @RequestBody ClienteRepresentation.ClienteCreateUpdate entity) {
        try {
            return ResponseEntity.ok().body(ClienteRepresentation.ClienteResponse.from(service.update(id, entity)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ClienteRepresentation.ClienteResponse> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}