package com.agenday.agendayserv.repositories;

import com.agenday.agendayserv.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, CustomQuerydslPredicateExecutor<Empresa> {
}
