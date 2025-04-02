package com.servidor.api.modulos.servidorefetivo;

import com.servidor.api.modulos.endereco.Endereco;
import com.servidor.api.modulos.lotacao.Lotacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/servidor-efetivo")
public class ServidorEfetivoController {

  @Autowired
  private ServidorEfetivoRepository servidorEfetivoRepository;

  @GetMapping("/buscar-endereco-funcional")
  public ResponseEntity<?> buscarEnderecoFuncional(@RequestParam(name = "nomeParcial") String nomeParcial) {
    try {
      List<ServidorEfetivo> servidorEfetivo = servidorEfetivoRepository.findPessoaNomesBySubstring(nomeParcial);
      if (servidorEfetivo != null) {
        List<Lotacao> lotacaoList = servidorEfetivo.getFirst().getPessoa().getLotacoes();
        Endereco endereco = lotacaoList.getFirst().getUnidade().getUnidadeEnderecos().getFirst().getEndereco();
        Map<String, String> response = new HashMap<String, String>();
        response.put("endereco", endereco.getTipoLogradouro() + " " +  endereco.getLogradouro() + ", " +
                endereco.getNumero() + ", " + endereco.getBairro() + ", " + endereco.getCidade().getNome());
        return new ResponseEntity<>(response, HttpStatus.OK);
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