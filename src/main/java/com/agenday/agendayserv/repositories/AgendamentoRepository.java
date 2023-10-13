package com.agenday.agendayserv.repositories;

import com.agenday.agendayserv.models.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>, CustomQuerydslPredicateExecutor<Agendamento> {
}
