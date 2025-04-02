package com.servidor.api.modulos.servidortemporario;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.servidor.api.modulos.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "servidor_temporario")
public class ServidorTemporario {

  @Id
  @Column(name = "pes_id")
  private Integer pessoaId;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("pessoaId")
  @JoinColumn(name = "pes_id", foreignKey = @ForeignKey(name = "fk_servidor_temporario_pessoa"))
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Pessoa pessoa;

  @Temporal(TemporalType.DATE)
  @Column(name = "st_data_admissao")
  private LocalDate dataAdmissao;

  @Temporal(TemporalType.DATE)
  @Column(name = "st_data_demissao")
  private LocalDate dataDemissao;
}
