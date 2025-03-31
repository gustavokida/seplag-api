package com.servidor.api.modulos.endereco;

import com.servidor.api.modulos.cidade.Cidade;
import com.servidor.api.modulos.pessoaendereco.PessoaEndereco;
import com.servidor.api.modulos.unidadeendereco.UnidadeEndereco;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "endereco")
public class Endereco {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "end_id")
  private Integer id;

  @Column(name = "end_tipo_logradouro", length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
  private String tipoLogradouro;

  @Column(name = "end_logradouro", length = 200, nullable = false, columnDefinition = "VARCHAR(200)")
  private String logradouro;

  @Column(name = "end_numero", nullable = false)
  private Integer numero;

  @Column(name = "end_bairro", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
  private String bairro;

  @ManyToOne
  @JoinColumn(name = "cid_id", foreignKey = @ForeignKey(name = "fk_cidade_endereco"))
  private Cidade cidade;

  @OneToMany(mappedBy = "endereco")
  private List<UnidadeEndereco> unidadeEnderecos;

  @OneToMany(mappedBy = "endereco")
  private List<PessoaEndereco> pessoaEnderecos;



}
