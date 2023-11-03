package com.agenday.agendayserv.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface ClienteRepresentation {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class ClienteCreateUpdate {
        @NotNull(message = "O nome não pode ser nulo")
        @NotEmpty(message = "O nome não pode ser vazio")
        private String nome;

        @NotNull(message = "O e-mail não pode ser nulo")
        @NotEmpty(message = "O e-mail não pode ser vazio")
        private String email;

        @NotNull(message = "A senha não pode ser nula")
        @NotEmpty(message = "A senha não pode ser vazia")
        private String senha;

        @NotNull(message = "O telefone não pode ser nulo")
        @NotEmpty(message = "O telefone não pode ser vazio")
        private String telefone;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class ClienteResponse {
        private Long id;
        private String nome;
        private String email;
        private String telefone;

        public static ClienteResponse from(Cliente cliente) {
            return ClienteResponse.builder()
                    .id(cliente.getId())
                    .nome(cliente.getNome())
                    .email(cliente.getEmail())
                    .telefone(cliente.getTelefone())
                    .build();
        }
    }
}
