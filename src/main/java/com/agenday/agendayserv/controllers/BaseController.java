package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.models.BaseEntity;
import com.agenday.agendayserv.services.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;

public abstract class BaseController<T extends BaseEntity, ID> {
    protected abstract BaseService<T, ID> getService();

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable ID id) {
        return ResponseEntity.ok(getService().getById(id));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody T entity) {
        var save = getService().add(entity);

        return ResponseEntity.created(URI.create("/" + save.getId())).body(save);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody T entity) {
        var update = getService().update(id, entity);

        if (update == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(update);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@PathParam("id") ID id) {
        getService().delete(id);

        return ResponseEntity.noContent().build();
    }
}
