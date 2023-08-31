package com.agenday.agendayserv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "servico_midia")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicoMidia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_servico_midia")
    @SequenceGenerator(name = "seq_servico_midia", sequenceName = "seq_servico_midia")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servico", nullable = false)
    private Servico servico;

    @Lob
    @Column(name = "foto")
    private byte[] foto;
}
