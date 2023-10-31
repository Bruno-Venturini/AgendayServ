package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.Funcionario;
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
public class FuncionarioDto {
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

    @JsonView(View.Public.class)
    private EmpresaDto empresa;

    public static List<FuncionarioDto> fromEntity(List<Funcionario> funcionarios) {
        return funcionarios.stream().map(FuncionarioDto::fromEntity).toList();
    }

    public static FuncionarioDto fromEntity(Funcionario funcionario) {
        return new FuncionarioDto(funcionario.getId(), funcionario.getNome(), funcionario.getEmail(), funcionario.getSenha(), funcionario.getTelefone(), EmpresaDto.fromEntity(funcionario.getEmpresa()));
    }
}
