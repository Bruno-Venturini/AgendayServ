package com.agenday.agendayserv.repositories;

import com.agenday.agendayserv.models.ExpedienteEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedienteEmpresaRepository extends JpaRepository<ExpedienteEmpresa, Long>, CustomQuerydslPredicateExecutor<ExpedienteEmpresa> {
}
