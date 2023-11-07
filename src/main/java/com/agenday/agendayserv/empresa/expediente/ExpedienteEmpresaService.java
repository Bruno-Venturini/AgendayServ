package com.agenday.agendayserv.empresa.expediente;

import com.agenday.agendayserv.empresa.EmpresaRepository;
import com.agenday.agendayserv.exceptions.NotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.util.List;

@Service
@AllArgsConstructor
public class ExpedienteEmpresaService {
    private ModelMapper modelMapper;
    private ExpedienteEmpresaRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<ExpedienteEmpresa> getAll() {
        return repository.findAll();
    }

    public ExpedienteEmpresa getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("ExpedienteEmpresa"));
    }

    public ExpedienteEmpresa add(ExpedienteEmpresaRepresentation.ExpedienteEmpresaCreate create, EmpresaRepository empresa) {
        return repository.save(ExpedienteEmpresa.builder()
                .diaSemana(create.getDiaSemana())
                .aberturaMatutino(create.getAberturaMatutino())
                .fechamentoMatutino(create.getFechamentoMatutino())
                .aberturaVespertino(create.getAberturaVespertino())
                .fechamentoVespertino(create.getFechamentoVespertino())
                .empresa(empresa.findById(create.getEmpresa()).orElseThrow(() -> new NotFoundException("Empresa")))
                .build());
    }

    public ExpedienteEmpresa update(Long id, ExpedienteEmpresaRepresentation.ExpedienteEmpresaUpdate entity) {
        var dbEntity = repository.findById(id).orElseThrow(() -> new NotFoundException("ExpedienteEmpresa"));

        modelMapper.map(entity, dbEntity);

        return repository.save(dbEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<ExpedienteEmpresa> findByEmpresaId(Long empresaId) {
        return new JPAQueryFactory(entityManager).selectFrom(QExpedienteEmpresa.expedienteEmpresa)
                .where(QExpedienteEmpresa.expedienteEmpresa.empresa.id.eq(empresaId)).stream().toList();
    }

    public ExpedienteEmpresa findByDayOfWeek(Long empresaId, DayOfWeek dayOfWeek) {
        return new JPAQueryFactory(entityManager).selectFrom(QExpedienteEmpresa.expedienteEmpresa)
                .where(QExpedienteEmpresa.expedienteEmpresa.empresa.id.eq(empresaId).and(QExpedienteEmpresa.expedienteEmpresa.diaSemana.eq(dayOfWeek))).fetchOne();
    }
}
