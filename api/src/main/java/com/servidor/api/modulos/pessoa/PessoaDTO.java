package com.servidor.api.modulos.pessoa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.servidor.api.modulos.lotacao.LotacaoDTO;
import com.servidor.api.modulos.servidorefetivo.ServidorEfetivoDTO;
import com.servidor.api.modulos.servidortemporario.ServidorTemporarioDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
public class PessoaDTO {

  private String nome;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dataNascimento;

  private String sexo;

  private String mae;

  private String pai;

  private List<Lotacoes> Lotacoes;

  private List<Long> enderecos;

  private List<ServidorEfetivoDTO> servidoresEfetivos;

  private List<ServidorTemporarioDTO> servidoresTemporarios;

  @Data
  @NoArgsConstructor
  public static class Lotacoes{

    private Long unidadeId;

    private String portaria;

    private LocalDate dataLocacao;

    private LocalDate dataRemocao;
  }

}