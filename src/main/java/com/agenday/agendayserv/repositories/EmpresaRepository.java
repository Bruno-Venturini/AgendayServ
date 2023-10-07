package com.agenday.agendayserv.repositories;

import com.agenday.agendayserv.model.Empresa;
import com.agenday.agendayserv.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, CustomQuerydslPredicateExecutor<Empresa> {
}
