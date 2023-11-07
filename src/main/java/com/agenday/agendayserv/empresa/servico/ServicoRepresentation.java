package com.agenday.agendayserv.empresa.servico;

import com.agenday.agendayserv.empresa.servico.midia.ServicoMidia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public interface ServicoRepresentation {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class ServicoCreate {
        @NotNull(message = "O nome não pode ser nulo")
        @NotEmpty(message = "O nome não pode ser vazio")
        private String nome;

        @NotNull(message = "A descrição não pode ser nula")
        @NotEmpty(message = "A descrição não pode ser vazia")
        private String descricao;

        @NotNull(message = "O preço não pode ser nulo")
        private BigDecimal preco;

        @NotNull(message = "A duração não pode ser nula")
        private Integer duracao;

        private Boolean ativo;

        @NotNull(message = "A empresa não pode ser nula")
        private Long empresa;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class ServicoUpdate {
        @NotNull(message = "O nome não pode ser nulo")
        @NotEmpty(message = "O nome não pode ser vazio")
        private String nome;

        @NotNull(message = "A descrição não pode ser nula")
        @NotEmpty(message = "A descrição não pode ser vazia")
        private String descricao;

        @NotNull(message = "O preço não pode ser nulo")
        private BigDecimal preco;

        @NotNull(message = "A duração não pode ser nula")
        private Integer duracao;

        private Boolean ativo;

        private List<Long> midias;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class ServicoResponse {
        private Long id;
        private String nome;
        private String descricao;
        private BigDecimal preco;
        private Integer duracao;
        private Boolean ativo;
        private Long empresa;
        private List<Long> midias;

        public static ServicoResponse from(Servico servico) {
            return ServicoResponse.builder()
                    .id(servico.getId())
                    .nome(servico.getNome())
                    .descricao(servico.getDescricao())
                    .preco(servico.getPreco())
                    .duracao(servico.getDuracao())
                    .ativo(servico.getAtivo())
                    .empresa(servico.getEmpresa().getId())
                    .midias(servico.getMidias().stream().map(ServicoMidia::getId).toList())
                    .build();
        }
    }
}
