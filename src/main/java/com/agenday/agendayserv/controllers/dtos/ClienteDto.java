package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteDto {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;

    public static List<ClienteDto> fromEntity(List<Cliente> clientes) {
        return clientes.stream().map(ClienteDto::fromEntity).toList();
    }

    public static ClienteDto fromEntity(Cliente cliente) {
        return new ClienteDto(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getSenha(), cliente.getTelefone());
    }
}
