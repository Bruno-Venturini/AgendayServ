package com.agenday.agendayserv.empresa.expediente;

import com.agenday.agendayserv.repositories.CustomQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedienteEmpresaRepository extends JpaRepository<ExpedienteEmpresa, Long>, CustomQuerydslPredicateExecutor<ExpedienteEmpresa> {
}
