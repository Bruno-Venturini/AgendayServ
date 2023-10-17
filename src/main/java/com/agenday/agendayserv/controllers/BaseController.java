package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.exceptions.NotFoundException;
import com.agenday.agendayserv.models.BaseEntity;
import com.agenday.agendayserv.services.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;

public abstract class BaseController<T extends BaseEntity, ID> {
    protected abstract BaseService<T, ID> getService();

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        try {
            return ResponseEntity.ok(getService().getById(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<T> add(@RequestBody T entity) {
        var save = getService().add(entity);

        return ResponseEntity.created(URI.create("/" + save.getId())).body(save);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        try {
            return ResponseEntity.ok().body(getService().update(id, entity));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<T> delete(@PathParam("id") ID id) {
        getService().delete(id);

        return ResponseEntity.noContent().build();
    }
}
