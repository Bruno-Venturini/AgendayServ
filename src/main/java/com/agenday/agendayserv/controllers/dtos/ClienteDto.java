package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.Cliente;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(View.Public.class)
    private Long id;

    @JsonView(View.Internal.class)
    private String nome;

    @JsonView(View.Internal.class)
    private String email;

    @JsonView(View.Internal.class)
    private String senha;

    @JsonView(View.Internal.class)
    private String telefone;

    public static List<ClienteDto> fromEntity(List<Cliente> clientes) {
        return clientes.stream().map(ClienteDto::fromEntity).toList();
    }

    public static ClienteDto fromEntity(Cliente cliente) {
        return new ClienteDto(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getSenha(), cliente.getTelefone());
    }
}
