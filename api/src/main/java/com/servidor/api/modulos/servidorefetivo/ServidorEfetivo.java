package com.servidor.api.modulos.servidorefetivo;

import com.servidor.api.modulos.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "servidor_efetivo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pes_id", "se_matricula"}, name = "uk_servidor_efetivo_pessoa_matricula")
})
public class ServidorEfetivo {

  @Id
  @Column(name = "pes_id")
  private Integer pessoaId;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("pessoaId")
  @JoinColumn(name = "pes_id", foreignKey = @ForeignKey(name = "fk_servidor_efetivo_pessoa"))
  private Pessoa pessoa;

  @Column(name = "se_matricula", length = 20, nullable = false, columnDefinition = "VARCHAR(20)")
  private String matricula;
}
