package com.servidor.api.modulos.fotopessoa;

import com.servidor.api.modulos.minio.MinioService;
import com.servidor.api.modulos.pessoa.Pessoa;
import com.servidor.api.modulos.pessoa.PessoaRepository;
import io.minio.ObjectWriteResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/foto-pessoa")
public class FotoPessoaController {

  @Autowired
  private FotoPessoaRepository fotoPessoaRepository;

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private MinioService minioService;

  @GetMapping
  public ResponseEntity<Page<FotoPessoa>> getAllFotoPessoas(Pageable pageable) {
    try {
      Page<FotoPessoa> fotoPessoas = fotoPessoaRepository.findAll(pageable);
      if (fotoPessoas.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(fotoPessoas, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<FotoPessoa> getFotoPessoaById(@PathVariable("id") Long id) {
    Optional<FotoPessoa> fotoPessoaData = fotoPessoaRepository.findById(id);

    return fotoPessoaData.map(fotoPessoa -> new ResponseEntity<>(fotoPessoa, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/{id}/url")
  public ResponseEntity<String> getFotoPessoaUrl(@PathVariable("id") Long id) {
    Optional<FotoPessoa> fotoPessoaData = fotoPessoaRepository.findById(id);

    if (fotoPessoaData.isPresent()) {
      FotoPessoa fotoPessoa = fotoPessoaData.get();
      String url = minioService.getFileUrl(fotoPessoa.getHash());
      return new ResponseEntity<>(url, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/url")
  public ResponseEntity<Page<String>> getAllFotoPessoasUrl(Pageable pageable) {
    try {
      Page<FotoPessoa> fotoPessoas = fotoPessoaRepository.findAll(pageable);
      Page<String> fotoPessoasUrl = fotoPessoas.map(fotoPessoa -> {
        String url = minioService.getFileUrl(fotoPessoa.getHash());
        return url;
      });
      if (fotoPessoas.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(fotoPessoasUrl, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @PostMapping("pessoa/{id}")
  public ResponseEntity<List<FotoPessoa>> createFotoPessoa(@PathVariable("id") Long id, @RequestParam("fotos") MultipartFile[] fotos) {
    try {
      Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
      List<FotoPessoa> fotoPessoaList = new ArrayList<>();
      for (MultipartFile foto : fotos) {
        if (!foto.isEmpty()) {
          String hash = UUID.randomUUID().toString();
          byte[] bytes = foto.getBytes();
          ObjectWriteResponse minioResponse = minioService.uploadFile(bytes, hash);
          FotoPessoa fotoPessoa = new FotoPessoa();
          fotoPessoa.setPessoa(pessoa);
          fotoPessoa.setData(LocalDate.now());
          fotoPessoa.setBucket(minioResponse.bucket());
          fotoPessoa.setHash(hash);
          fotoPessoaList.add(fotoPessoa);
          fotoPessoaRepository.save(fotoPessoa);
        }
      }
      return new ResponseEntity<>(fotoPessoaList, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @PutMapping("/{id}")
  public ResponseEntity<FotoPessoa> updateFotoPessoa(@PathVariable("id") Long id, @RequestBody FotoPessoa fotoPessoa) {
    Optional<FotoPessoa> fotoPessoaData = fotoPessoaRepository.findById(id);

    if (fotoPessoaData.isPresent()) {
      FotoPessoa fotoPessoaAux = fotoPessoaData.get();
      fotoPessoaAux.setPessoa(fotoPessoa.getPessoa());
      fotoPessoaAux.setData(fotoPessoa.getData());
      fotoPessoaAux.setBucket(fotoPessoa.getBucket());
      fotoPessoaAux.setHash(fotoPessoa.getHash());
      return new ResponseEntity<>(fotoPessoaRepository.save(fotoPessoaAux), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteFotoPessoa(@PathVariable("id") Long id) {
    try {
      fotoPessoaRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}