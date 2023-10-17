package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmpresaDto {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private List<ExpedienteEmpresaDto> expedientes;

    public static List<EmpresaDto> fromEntity(List<Empresa> empresas) {
        return empresas.stream().map(EmpresaDto::fromEntity).toList();
    }

    public static EmpresaDto fromEntity(Empresa empresa) {
        return new EmpresaDto(empresa.getId(), empresa.getNome(), empresa.getEmail(), empresa.getSenha(), empresa.getTelefone(), ExpedienteEmpresaDto.fromEntity(empresa.getExpedientes()));
    }
}
