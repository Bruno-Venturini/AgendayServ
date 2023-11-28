package com.agenday.agendayserv.agendamento;

import com.agenday.agendayserv.agendamento.horariolivre.HorarioLivreRepresentation;
import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("agendamentos")
@AllArgsConstructor
public class AgendamentoController {
    private AgendamentoService service;

    @GetMapping
    public ResponseEntity<List<AgendamentoRepresentation.AgendamentoResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(AgendamentoRepresentation.AgendamentoResponse::from).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<AgendamentoRepresentation.AgendamentoResponse> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(AgendamentoRepresentation.AgendamentoResponse.from(service.getById(id)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<AgendamentoRepresentation.AgendamentoResponse> add(
            @RequestBody AgendamentoRepresentation.AgendamentoCreate entity) {
        var save = service.add(entity);

        return ResponseEntity
                .created(URI.create("/" + save.getId()))
                .body(AgendamentoRepresentation.AgendamentoResponse.from(save));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AgendamentoRepresentation.AgendamentoResponse> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/confirmar")
    public ResponseEntity<AgendamentoRepresentation.AgendamentoResponse> confirmar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(AgendamentoRepresentation.AgendamentoResponse.from(service.confirmar(id)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("{id}/cancelar")
    public ResponseEntity<AgendamentoRepresentation.AgendamentoResponse> cancelar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(AgendamentoRepresentation.AgendamentoResponse.from(service.cancelar(id)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("{id}/concluir")
    public ResponseEntity<AgendamentoRepresentation.AgendamentoResponse> concluir(
            @PathVariable Long id,
            @RequestBody AgendamentoRepresentation.PagamentoCreate entity) {
        try {
            return ResponseEntity.ok().body(AgendamentoRepresentation.AgendamentoResponse.from(service.concluir(id, entity.getValor())));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("servicos/{idServico}/dias-disponiveis")
    public ResponseEntity<List<LocalDate>> obterDiasDisponiveis(
            @PathVariable Long idServico,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate inicio,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fim) {
        return ResponseEntity.ok(service.obterDiasDisponiveis(idServico, inicio, fim));
    }

    @GetMapping("servicos/{idServico}/horarios-disponiveis")
    public ResponseEntity<List<HorarioLivreRepresentation.HorarioLivreResponse>> obterHorariosLivres (
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate data,
            @PathVariable Long idServico) {
        return ResponseEntity.ok(service.obterHorariosLivres(data, idServico).stream().map(HorarioLivreRepresentation.HorarioLivreResponse::from).toList());
    }
}