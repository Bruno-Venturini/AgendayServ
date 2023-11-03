package com.agenday.agendayserv.empresa.funcionario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface FuncionarioRepresentation {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class FuncionarioCreate {
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

        @NotNull(message = "A empresa não pode ser nula")
        private Long empresa;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class FuncionarioUpdate {
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
    class FuncionarioResponse {
        private Long id;
        private String nome;
        private String email;
        private String telefone;
        private Long empresa;

        public static FuncionarioResponse from(Funcionario funcionario) {
            return FuncionarioResponse.builder()
                    .id(funcionario.getId())
                    .nome(funcionario.getNome())
                    .email(funcionario.getEmail())
                    .telefone(funcionario.getTelefone())
                    .empresa(funcionario.getEmpresa().getId())
                    .build();
        }
    }
}
