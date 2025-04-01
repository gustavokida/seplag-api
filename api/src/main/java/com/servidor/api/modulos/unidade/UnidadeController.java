package com.servidor.api.modulos.unidade;

import com.servidor.api.modulos.servidorefetivo.ServidorEfetivo;
import com.servidor.api.modulos.servidorefetivo.ServidorEfetivoRepository;
import com.servidor.api.modulos.servidorefetivo.UnidadeServidorEfetivoDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/unidade")
public class UnidadeController {

  @Autowired
  private UnidadeRepository unidadeRepository;

  @Autowired
  private ServidorEfetivoRepository servidorEfetivoRepository;

  @Autowired
  private UnidadeServidorEfetivoMapper unidadeServidorEfetivoMapper;

  @GetMapping("{id}/servidores-efetivos-lotados")
  public ResponseEntity<?> getServidoresEfetivosLotados(@PathVariable("id") Long id) {
    try {
      List<ServidorEfetivo> servidorEfetivo = servidorEfetivoRepository.findByPessoa_Lotacoes_Unidade_Id(id);
      if (servidorEfetivo.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      List<UnidadeServidorEfetivoDTO> servidoresEfetivos = unidadeServidorEfetivoMapper.toDTO(servidorEfetivo, id);
      return new ResponseEntity<>(servidoresEfetivos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  public ResponseEntity<Page<Unidade>> getAllUnidades(Pageable pageable) {
    try {
      Page<Unidade> unidades = unidadeRepository.findAll(pageable);
      if (unidades.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(unidades, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Unidade> getUnidadeById(@PathVariable("id") Long id) {
    Optional<Unidade> unidadeData = unidadeRepository.findById(id);

    return unidadeData.map(unidade -> new ResponseEntity<>(unidade, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @Transactional
  @PostMapping
  public ResponseEntity<Unidade> createUnidade(@RequestBody Unidade unidade) {
    try {
      Unidade _unidade = unidadeRepository.save(unidade);
      return new ResponseEntity<>(_unidade, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @PutMapping("/{id}")
  public ResponseEntity<Unidade> updateUnidade(@PathVariable("id") Long id, @RequestBody Unidade unidade) {
    Optional<Unidade> unidadeData = unidadeRepository.findById(id);

    if (unidadeData.isPresent()) {
      Unidade _unidade = unidadeData.get();
      _unidade.setNome(unidade.getNome());
      _unidade.setSigla(unidade.getSigla());
      return new ResponseEntity<>(unidadeRepository.save(_unidade), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteUnidade(@PathVariable("id") Long id) {
    try {
      unidadeRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}