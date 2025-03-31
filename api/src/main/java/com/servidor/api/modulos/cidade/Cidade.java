package com.servidor.api.modulos.cidade;

import com.servidor.api.modulos.endereco.Endereco;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "cidade")
public class Cidade {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "cid_id")
  private Integer id;

  @Column(name = "cid_nome", length = 200, nullable = false, columnDefinition = "VARCHAR(200)")
  private String nome;

  @Column(name = "cid_uf", length = 2, nullable = false, columnDefinition = "VARCHAR(2)")
  private String uf;

  @OneToMany(mappedBy = "cidade")
  private List<Endereco> enderecos;
}
