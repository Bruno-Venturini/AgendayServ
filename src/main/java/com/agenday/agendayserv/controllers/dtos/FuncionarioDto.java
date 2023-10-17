package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.Funcionario;
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
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private EmpresaDto empresa;

    public static List<FuncionarioDto> fromEntity(List<Funcionario> funcionarios) {
        return funcionarios.stream().map(FuncionarioDto::fromEntity).toList();
    }

    public static FuncionarioDto fromEntity(Funcionario funcionario) {
        return new FuncionarioDto(funcionario.getId(), funcionario.getNome(), funcionario.getEmail(), funcionario.getSenha(), funcionario.getTelefone(), EmpresaDto.fromEntity(funcionario.getEmpresa()));
    }
}
