package com.servidor.api.modulos.fotopessoa;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.servidor.api.modulos.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "foto_pessoa")
public class FotoPessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "fp_id")
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pes_id", foreignKey = @ForeignKey(name = "fk_foto_pessoa_pessoa"))
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Pessoa pessoa;

  @Temporal(TemporalType.DATE)
  @Column(name = "fp_data")
  private LocalDate data;

  @Column(name = "fp_bucket", length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
  private String bucket;

  @Column(name = "fp_hash", length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
  private String hash;
}
