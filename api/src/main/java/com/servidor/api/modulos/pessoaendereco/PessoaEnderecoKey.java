package com.servidor.api.modulos.pessoaendereco;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PessoaEnderecoKey implements Serializable {

  @Column(name = "pes_id")
  private Integer pessoaId;

  @Column(name = "end_id")
  private Integer enderecoId;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PessoaEnderecoKey that = (PessoaEnderecoKey) o;

    if (!pessoaId.equals(that.pessoaId)) return false;
    return enderecoId.equals(that.enderecoId);
  }

  @Override
  public int hashCode() {
    int result = pessoaId.hashCode();
    result = 31 * result + enderecoId.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "PessoaEnderecoKey{" +
            "pessoaId=" + pessoaId +
            ", enderecoId=" + enderecoId +
            '}';
  }

}
