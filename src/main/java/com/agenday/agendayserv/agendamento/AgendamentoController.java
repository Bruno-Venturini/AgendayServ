package com.agenday.agendayserv.agendamento;

import com.agenday.agendayserv.agendamento.horariolivre.HorarioLivreDTO;
import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("agendamentos")
@AllArgsConstructor
@NoArgsConstructor
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
            @RequestBody AgendamentoRepresentation.AgendamentoCreateUpdate entity) {
        var save = service.add(entity);

        return ResponseEntity
                .created(URI.create("/" + save.getId()))
                .body(AgendamentoRepresentation.AgendamentoResponse.from(save));
    }

    @PutMapping("{id}")
    public ResponseEntity<AgendamentoRepresentation.AgendamentoResponse> update(
            @PathVariable Long id, @RequestBody AgendamentoRepresentation.AgendamentoCreateUpdate entity) {
        try {
            return ResponseEntity.ok().body(AgendamentoRepresentation.AgendamentoResponse.from(service.update(id, entity)));
        } catch (NotFoundException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AgendamentoRepresentation.AgendamentoResponse> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/servicos/{idServico}")
    public ResponseEntity<List<LocalDate>> obterDiasDisponiveis(
            @PathVariable Long idServico,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate inicio,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fim) {
        return ResponseEntity.ok(service.obterDiasDisponiveis(idServico, inicio, fim));
    }

    @GetMapping("/horarios-disponiveis/empresa/{idEmpresa}")
    public ResponseEntity<List<HorarioLivreDTO>> obterHorariosLivres (
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate data,
            @PathVariable Long idEmpresa) {
        return ResponseEntity.ok(service.obterHorariosLivres(data, idEmpresa));
    }
}