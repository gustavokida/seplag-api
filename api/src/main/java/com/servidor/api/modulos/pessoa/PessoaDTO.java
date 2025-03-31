package com.servidor.api.modulos.pessoa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.servidor.api.modulos.lotacao.LotacaoDTO;
import com.servidor.api.modulos.servidorefetivo.ServidorEfetivoDTO;
import com.servidor.api.modulos.servidortemporario.ServidorTemporarioDTO;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PessoaDTO {

  @Id
  private Long id;

  private String nome;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate dataNascimento;

  private String sexo;

  private String mae;

  private String pai;

  private List<LotacaoDTO> Lotacoes;

  private List<Long> enderecos;

  private List<ServidorEfetivoDTO> servidoresEfetivos;

  private List<ServidorTemporarioDTO> servidoresTemporarios;

}