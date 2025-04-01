package com.servidor.api.modulos.servidorefetivo;

import com.servidor.api.modulos.endereco.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/servidor-efetivo")
public class ServidorEfetivoController {

  @Autowired
  private ServidorEfetivoRepository servidorEfetivoRepository;


  @GetMapping("buscar-endereco-funcional")
  public ResponseEntity<?> buscarEnderecoFuncional(ServidorEfetivoSpec spec, Pageable pageable) {
    try {
      Optional<ServidorEfetivo> servidorEfetivo = servidorEfetivoRepository.findAll(spec, pageable)
              .stream()
              .findFirst();
      if (servidorEfetivo.isPresent()) {
        Endereco endereco = servidorEfetivo.get().getPessoa().getPessoaEnderecos().getFirst().getEndereco();
        return new ResponseEntity<>(endereco, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  public ResponseEntity<Page<ServidorEfetivo>> getAll(Pageable pageable) {
    try {
      Page<ServidorEfetivo> servidoresEfetivos = servidorEfetivoRepository.findAll(pageable);
      if (servidoresEfetivos.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(servidoresEfetivos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<ServidorEfetivo> getById(@PathVariable Long id) {
    Optional<ServidorEfetivo> servidorEfetivoData = servidorEfetivoRepository.findById(id);
    return servidorEfetivoData.map(servidorEfetivo -> new ResponseEntity<>(servidorEfetivo, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<ServidorEfetivo> create(@RequestBody ServidorEfetivo servidorEfetivo) {
    try {
      ServidorEfetivo _servidorEfetivo = servidorEfetivoRepository.save(servidorEfetivo);
      return new ResponseEntity<>(_servidorEfetivo, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<ServidorEfetivo> update(@PathVariable Long id, @RequestBody ServidorEfetivo servidorEfetivo) {
    Optional<ServidorEfetivo> servidorEfetivoData = servidorEfetivoRepository.findById(id);

    if (servidorEfetivoData.isPresent()) {
      ServidorEfetivo _servidorEfetivo = servidorEfetivoData.get();
      _servidorEfetivo.setMatricula(servidorEfetivo.getMatricula());
      // Atualize outros campos conforme necessário
      return new ResponseEntity<>(servidorEfetivoRepository.save(_servidorEfetivo), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
    try {
      servidorEfetivoRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}