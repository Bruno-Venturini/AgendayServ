package com.agenday.agendayserv.controllers;

import com.agenday.agendayserv.controllers.dtos.View;
import com.agenday.agendayserv.exceptions.NotFoundException;
import com.agenday.agendayserv.models.BaseEntity;
import com.agenday.agendayserv.services.BaseService;
import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

public abstract class BaseController<T extends BaseEntity, D, ID> {
    final Class<T> entityType;
    final Class<D> dtoType;

    @Autowired
    private ModelMapper modelMapper;

    protected abstract BaseService<T, ID> getService();

    public BaseController() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        entityType = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        dtoType = (Class<D>) parameterizedType.getActualTypeArguments()[1];
    }

    @GetMapping
    @JsonView(View.Public.class)
    public ResponseEntity<List<D>> getAll() {
        return ResponseEntity.ok(getService().getAll().stream().map(item -> modelMapper.map(item, dtoType)).toList());
    }

    @GetMapping("/{id}")
    @JsonView(View.Internal.class)
    public ResponseEntity<D> getById(@PathVariable ID id) {
        try {
            return ResponseEntity.ok(modelMapper.map(getService().getById(id), dtoType));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @JsonView(View.Internal.class)
    public ResponseEntity<D> add(@JsonView(View.Internal.class) @RequestBody D entity) {
        var save = getService().add(modelMapper.map(entity, entityType));

        return ResponseEntity.created(URI.create("/" + save.getId())).body(modelMapper.map(save, dtoType));
    }

    @PutMapping("/{id}")
    @JsonView(View.Internal.class)
    public ResponseEntity<D> update(@PathVariable ID id, @JsonView(View.Internal.class) @RequestBody D entity) {
        try {
            var map = modelMapper.map(entity, entityType);

            return ResponseEntity.ok().body(modelMapper.map(getService().update(id, map), dtoType));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<D> delete(@PathVariable ID id) {
        getService().delete(id);

        return ResponseEntity.noContent().build();
    }
}
