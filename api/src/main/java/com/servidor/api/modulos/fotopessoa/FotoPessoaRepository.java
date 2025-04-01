package com.servidor.api.modulos.fotopessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoPessoaRepository extends JpaRepository<FotoPessoa, Long> {

  List<FotoPessoa> findAllByPessoaId(Long pessoaId);
}
