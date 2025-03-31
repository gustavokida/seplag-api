package com.servidor.api.modulos.pessoaendereco;

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
  private Pessoa pessoa;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("enderecoId")
  @JoinColumn(name = "end_id", foreignKey = @ForeignKey(name = "fk_pessoa_endereco_endereco"))
  private Endereco endereco;
}
