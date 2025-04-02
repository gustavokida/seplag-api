package com.servidor.api.modulos.pessoaendereco;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.servidor.api.modulos.endereco.Endereco;
import com.servidor.api.modulos.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "pessoa_endereco")
public class PessoaEndereco {

  @EmbeddedId
  private PessoaEnderecoKey id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("pessoaId")
  @JoinColumn(name = "pes_id", foreignKey = @ForeignKey(name = "fk_pessoa_endereco_pessoa"))
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Pessoa pessoa;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("enderecoId")
  @JoinColumn(name = "end_id", foreignKey = @ForeignKey(name = "fk_pessoa_endereco_endereco"))
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Endereco endereco;
}
