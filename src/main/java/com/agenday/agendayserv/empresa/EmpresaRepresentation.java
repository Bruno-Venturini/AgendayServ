package com.agenday.agendayserv.empresa;

import com.agenday.agendayserv.empresa.expediente.ExpedienteEmpresa;
import com.agenday.agendayserv.empresa.funcionario.Funcionario;
import com.agenday.agendayserv.empresa.servico.Servico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface EmpresaRepresentation {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class EmpresaCreate {
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
    class EmpresaUpdate {
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

        private List<Long> expedientes;
        private List<Long> servicos;
        private List<Long> funcionarios;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class EmpresaResponse {
        private Long id;
        private String nome;
        private String email;
        private String telefone;
        private List<Long> expedientes;
        private List<Long> servicos;
        private List<Long> funcionarios;

        public static EmpresaResponse from(Empresa empresa) {
            return EmpresaResponse.builder()
                    .id(empresa.getId())
                    .nome(empresa.getNome())
                    .email(empresa.getEmail())
                    .telefone(empresa.getTelefone())
                    .expedientes(empresa.getExpedientes().stream().map(ExpedienteEmpresa::getId).toList())
                    .servicos(empresa.getServicos().stream().map(Servico::getId).toList())
                    .funcionarios(empresa.getFuncionarios().stream().map(Funcionario::getId).toList())
                    .build();
        }
    }
}
