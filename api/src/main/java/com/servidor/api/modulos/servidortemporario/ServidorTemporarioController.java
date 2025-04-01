package com.servidor.api.modulos.servidortemporario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/servidor-temporario")
public class ServidorTemporarioController {

  @Autowired
  private ServidorTemporarioRepository servidorTemporarioRepository;

  @GetMapping
  public ResponseEntity<Page<ServidorTemporario>> getAll(Pageable pageable) {
    try {
      Page<ServidorTemporario> servidoresTemporarios = servidorTemporarioRepository.findAll(pageable);
      if (servidoresTemporarios.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(servidoresTemporarios, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<ServidorTemporario> getById(@PathVariable Long id) {
    Optional<ServidorTemporario> servidorTemporarioData = servidorTemporarioRepository.findById(id);
    return servidorTemporarioData.map(servidorTemporario -> new ResponseEntity<>(servidorTemporario, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<ServidorTemporario> create(@RequestBody ServidorTemporario servidorTemporario) {
    try {
      ServidorTemporario _servidorTemporario = servidorTemporarioRepository.save(servidorTemporario);
      return new ResponseEntity<>(_servidorTemporario, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<ServidorTemporario> update(@PathVariable Long id, @RequestBody ServidorTemporario servidorTemporario) {
    Optional<ServidorTemporario> servidorTemporarioData = servidorTemporarioRepository.findById(id);

    if (servidorTemporarioData.isPresent()) {
      ServidorTemporario _servidorTemporario = servidorTemporarioData.get();
      _servidorTemporario.setDataAdmissao(servidorTemporario.getDataAdmissao());
      _servidorTemporario.setDataDemissao(servidorTemporario.getDataDemissao());
      return new ResponseEntity<>(servidorTemporarioRepository.save(_servidorTemporario), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
    try {
      servidorTemporarioRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}