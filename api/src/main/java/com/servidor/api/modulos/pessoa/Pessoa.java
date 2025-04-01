package com.servidor.api.modulos.pessoa;

import com.servidor.api.modulos.fotopessoa.FotoPessoa;
import com.servidor.api.modulos.lotacao.Lotacao;
import com.servidor.api.modulos.pessoaendereco.PessoaEndereco;
import com.servidor.api.modulos.servidorefetivo.ServidorEfetivo;
import com.servidor.api.modulos.servidortemporario.ServidorTemporario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "pessoa")
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "pes_id")
  private Integer id;

  @Column(name = "pes_nome", length = 200, nullable = false, columnDefinition = "VARCHAR(200)")
  private String nome;

  @Temporal(TemporalType.DATE)
  @Column(name = "pes_data_nascimento", nullable = false)
  private LocalDate dataNascimento;

  @Column(name = "pes_sexo", length = 9, nullable = false, columnDefinition = "VARCHAR(9)")
  private String sexo;

  @Column(name = "pes_mae", length = 200, nullable = false, columnDefinition = "VARCHAR(200)")
  private String mae;

  @Column(name = "pes_pai", length = 200, nullable = false, columnDefinition = "VARCHAR(200)")
  private String pai;

  @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Lotacao> lotacoes;

  @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = false)
  private List<PessoaEndereco> pessoaEnderecos;

  @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ServidorEfetivo> servidoresEfetivos;

  @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ServidorTemporario> servidoresTemporarios;

  @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FotoPessoa> fotos;

}
