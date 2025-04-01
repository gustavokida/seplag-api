package com.servidor.api.modulos.servidorefetivo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Long>{

  List<ServidorEfetivo> findByPessoa_Lotacoes_Unidade_Id(Long unidadeId);

  ServidorEfetivo findByPessoa_NomeLike(String nome);

}
