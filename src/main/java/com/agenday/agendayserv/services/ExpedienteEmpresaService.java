package com.agenday.agendayserv.services;

import com.agenday.agendayserv.models.ExpedienteEmpresa;
import com.agenday.agendayserv.models.QExpedienteEmpresa;
import com.agenday.agendayserv.repositories.ExpedienteEmpresaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ExpedienteEmpresaService  extends BaseService<ExpedienteEmpresa, Long> {
    @Autowired
    private ExpedienteEmpresaRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ExpedienteEmpresaRepository getRepository() {
        return repository;
    }

    public ExpedienteEmpresa findByEmpresaId(Long empresaId) {
        ExpedienteEmpresa expedienteEmpresa = new JPAQueryFactory(entityManager).selectFrom(QExpedienteEmpresa.expedienteEmpresa)
                .where(QExpedienteEmpresa.expedienteEmpresa.empresa.id.eq(empresaId)).fetchOne();

        return expedienteEmpresa;
    }
}
