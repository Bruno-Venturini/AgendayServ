package com.agenday.agendayserv.empresa;

import com.agenday.agendayserv.empresa.Empresa;
import com.agenday.agendayserv.repositories.CustomQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, CustomQuerydslPredicateExecutor<Empresa> {
}
