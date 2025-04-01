package com.servidor.api.modulos.servidorefetivo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

import java.util.List;

@Repository
public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Long>, JpaSpecificationExecutorWithProjection<ServidorEfetivo>, JpaSpecificationExecutor<ServidorEfetivo> {

  List<ServidorEfetivo> findByUnidadeId(Long unidadeId);

}
