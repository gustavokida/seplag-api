package com.servidor.api.modulos.unidadeendereco;

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
  private Unidade unidade;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "end_id", foreignKey = @ForeignKey(name = "fk_unidade_endereco_endereco"))
  private Endereco endereco;

}
