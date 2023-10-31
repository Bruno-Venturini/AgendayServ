package com.agenday.agendayserv.controllers.dtos;

import com.agenday.agendayserv.models.ServicoMidia;
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
public class ServicoMidiaDto {
    @JsonView(View.Public.class)
    private Long id;

    @JsonView(View.Internal.class)
    private byte[] foto;

    public static List<ServicoMidiaDto> fromEntity(List<ServicoMidia> servicoMidias) {
        return servicoMidias.stream().map(ServicoMidiaDto::fromEntity).toList();
    }

    public static ServicoMidiaDto fromEntity(ServicoMidia servicoMidia) {
        return new ServicoMidiaDto(servicoMidia.getId(), servicoMidia.getFoto());
    }
}
