package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.Empresa;
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
public class EmpresaDto {
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
    private List<ExpedienteEmpresaDto> expedientes;

    public static List<EmpresaDto> fromEntity(List<Empresa> empresas) {
        return empresas.stream().map(EmpresaDto::fromEntity).toList();
    }

    public static EmpresaDto fromEntity(Empresa empresa) {
        return new EmpresaDto(empresa.getId(), empresa.getNome(), empresa.getEmail(), empresa.getSenha(), empresa.getTelefone(), ExpedienteEmpresaDto.fromEntity(empresa.getExpedientes()));
    }
}
