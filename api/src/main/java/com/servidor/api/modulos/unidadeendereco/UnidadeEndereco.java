package com.servidor.api.modulos.unidadeendereco;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.servidor.api.modulos.endereco.Endereco;
import com.servidor.api.modulos.unidade.Unidade;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "unidade_endereco")
public class UnidadeEndereco {

  @Id
  @Column(name = "uni_id")
  private Integer unidadeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("unidadeId")
  @JoinColumn(name = "uni_id", foreignKey = @ForeignKey(name = "fk_unidade_endereco_unidade"))
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Unidade unidade;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "end_id", foreignKey = @ForeignKey(name = "fk_unidade_endereco_endereco"))
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Endereco endereco;

}
