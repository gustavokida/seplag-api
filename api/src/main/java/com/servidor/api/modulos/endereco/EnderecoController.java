package com.servidor.api.modulos.endereco;

import com.servidor.api.modulos.cidade.Cidade;
import com.servidor.api.modulos.cidade.CidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {

  @Autowired
  private EnderecoRepository enderecoRepository;

  @Autowired
  private CidadeRepository cidadeRepository;

  @GetMapping
  public ResponseEntity<Page<Endereco>> getAllEnderecos(Pageable pageable) {
    try {
      Page<Endereco> enderecos = enderecoRepository.findAll(pageable);
      if (enderecos.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(enderecos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Endereco> getEnderecoById(@PathVariable("id") Long id) {
    Optional<Endereco> enderecoData = enderecoRepository.findById(id);

    return enderecoData.map(endereco -> new ResponseEntity<>(endereco, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @Transactional
  @PostMapping
  public ResponseEntity<Endereco> createEndereco(@RequestBody Endereco endereco) {
    try {
      Optional<Cidade> cidade = cidadeRepository.findById(Long.valueOf(endereco.getCidade().getId()));
      endereco.setCidade(cidade.get());
      Endereco _endereco = enderecoRepository.save(endereco);
      return new ResponseEntity<>(_endereco, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @PutMapping("/{id}")
  public ResponseEntity<Endereco> updateEndereco(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
    Optional<Endereco> enderecoData = enderecoRepository.findById(id);

    if (enderecoData.isPresent()) {
      Endereco _endereco = enderecoData.get();
      _endereco.setLogradouro(endereco.getLogradouro());
      _endereco.setNumero(endereco.getNumero());
      _endereco.setTipoLogradouro(endereco.getTipoLogradouro());
      _endereco.setBairro(endereco.getBairro());
      _endereco.setNumero(endereco.getNumero());
      _endereco.setCidade(endereco.getCidade());
      return new ResponseEntity<>(enderecoRepository.save(_endereco), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteEndereco(@PathVariable("id") Long id) {
    try {
      enderecoRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}