package com.servidor.api.modulos.unidade;

import com.servidor.api.modulos.lotacao.Lotacao;
import com.servidor.api.modulos.unidadeendereco.UnidadeEndereco;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "unidade")
public class Unidade {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "uni_id")
  private Integer id;

  @Column(name = "uni_nome", length = 200, nullable = false, columnDefinition = "VARCHAR(200)")
  private String nome;

  @Column(name = "uni_sigla", length = 20, nullable = false, columnDefinition = "VARCHAR(20)")
  private String sigla;

  @OneToMany(mappedBy = "unidade")
  private List<UnidadeEndereco> unidadeEnderecos;

  @OneToMany(mappedBy = "unidade")
  private List<Lotacao> lotacoes;
}
