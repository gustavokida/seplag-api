package com.servidor.api.modulos.servidortemporario;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ServidorTemporarioDTO {

  private Long id;

  private LocalDate dataAdmissao;

  private LocalDate dataDemissao;
}
