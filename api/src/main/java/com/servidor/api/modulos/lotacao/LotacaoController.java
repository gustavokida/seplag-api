package com.servidor.api.modulos.lotacao;

import com.servidor.api.modulos.unidade.Unidade;
import com.servidor.api.modulos.unidade.UnidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/lotacao")
public class LotacaoController {

  @Autowired
  private LotacaoRepository lotacaoRepository;

  @Autowired
  UnidadeRepository unidadeRepository;

  @GetMapping
  public ResponseEntity<Page<Lotacao>> getAllLotacoes(Pageable pageable) {
    try {
      Page<Lotacao> lotacoes = lotacaoRepository.findAll(pageable);
      if (lotacoes.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(lotacoes, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Lotacao> getLotacaoById(@PathVariable("id") Long id) {
    Optional<Lotacao> lotacaoData = lotacaoRepository.findById(id);

    return lotacaoData.map(lotacao -> new ResponseEntity<>(lotacao, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @Transactional
  @PostMapping
  public ResponseEntity<Lotacao> createLotacao(@RequestBody Lotacao lotacao) {
    try {
      Lotacao _lotacao = lotacaoRepository.save(lotacao);
      return new ResponseEntity<>(_lotacao, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @PutMapping("/{id}")
  public ResponseEntity<Lotacao> updateLotacao(@PathVariable("id") Long id, @RequestBody LotacaoDTO dto) {
    Optional<Lotacao> lotacaoData = lotacaoRepository.findById(id);

    if (lotacaoData.isPresent()) {
      Optional<Unidade> unidade = unidadeRepository.findById(dto.getUnidadeId());
      Lotacao lotacao = lotacaoData.get();
      lotacao.setDataRemocao(dto.getDataRemocao());
      lotacao.setDataLocacao(dto.getDataLocacao());
      lotacao.setPortaria(dto.getPortaria());
      lotacao.setUnidade(unidade.orElse(null));
      return new ResponseEntity<>(lotacaoRepository.save(lotacao), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteLotacao(@PathVariable("id") Long id) {
    try {
      lotacaoRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}