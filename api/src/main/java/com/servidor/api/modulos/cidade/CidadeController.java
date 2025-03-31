package com.servidor.api.modulos.cidade;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cidade")
public class CidadeController {

  @Autowired
  private CidadeRepository cidadeRepository;

  @GetMapping
  public ResponseEntity<Page<Cidade>> getAllCidades(Pageable pageable) {
    try {
      Page<Cidade> cidades = cidadeRepository.findAll(pageable);
      if (cidades.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(cidades, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cidade> getCidadeById(@PathVariable("id") Long id) {
    Optional<Cidade> cidadeData = cidadeRepository.findById(id);

    return cidadeData.map(cidade -> new ResponseEntity<>(cidade, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @Transactional
  @PostMapping
  public ResponseEntity<Cidade> createCidade(@RequestBody Cidade cidade) {
    try {
      Cidade _cidade = cidadeRepository.save(cidade);
      return new ResponseEntity<>(_cidade, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @PutMapping("/{id}")
  public ResponseEntity<Cidade> updateCidade(@PathVariable("id") Long id, @RequestBody Cidade cidade) {
    Optional<Cidade> cidadeData = cidadeRepository.findById(id);

    if (cidadeData.isPresent()) {
      Cidade _cidade = cidadeData.get();
      _cidade.setNome(cidade.getNome());
      _cidade.setUf(cidade.getUf());
      return new ResponseEntity<>(cidadeRepository.save(_cidade), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteCidade(@PathVariable("id") Long id) {
    try {
      cidadeRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}