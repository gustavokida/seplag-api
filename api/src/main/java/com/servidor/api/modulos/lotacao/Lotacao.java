package com.servidor.api.modulos.lotacao;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.servidor.api.modulos.pessoa.Pessoa;
import com.servidor.api.modulos.unidade.Unidade;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "lotacao")
public class Lotacao {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "lot_id")
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pes_id", foreignKey = @ForeignKey(name = "fk_pessoa_lotacao"))
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Pessoa pessoa;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unid_id", foreignKey = @ForeignKey(name = "fk_unidade_lotacao"))
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Unidade unidade;

  @Temporal(TemporalType.DATE)
  @Column(name = "lot_data_lotacao", nullable = false)
  private LocalDate dataLocacao;

  @Temporal(TemporalType.DATE)
  @Column(name = "lot_data_remocao")
  private LocalDate dataRemocao;

  @Column(name = "lot_portaria", length = 100, columnDefinition = "VARCHAR(100)")
  private String portaria;

}
