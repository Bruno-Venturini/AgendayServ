package com.agenday.agendayserv.repositories;

import com.agenday.agendayserv.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, CustomQuerydslPredicateExecutor<Cliente> {
}
