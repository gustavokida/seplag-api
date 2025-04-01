package com.servidor.api.modulos.servidorefetivo;

import lombok.Data;

import java.util.List;

@Data
public class UnidadeServidorEfetivoDTO {

  private String nomePessoa;

  private Integer idade;

  private String nomeUnidade;

  private List<String> url;
}
