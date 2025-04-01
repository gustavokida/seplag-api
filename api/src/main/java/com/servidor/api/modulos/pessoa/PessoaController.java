package com.servidor.api.modulos.pessoa;

import com.servidor.api.modulos.fotopessoa.FotoPessoa;
import com.servidor.api.modulos.minio.MinioService;
import io.minio.ObjectWriteResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private MinioService minioService;

  @Autowired
  private PessoaMapper pessoaMapper;

  @Transactional
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<?> createPessoa(@ModelAttribute PessoaDTO pessoaDTO) {
    try {
      Pessoa pessoa = pessoaMapper.toEntity(pessoaDTO);
      List<FotoPessoa> fotos = new ArrayList<>();
      pessoa.setFotos(new ArrayList<>());
      if(pessoaDTO.getFotos() != null){
        for (MultipartFile foto : pessoaDTO.getFotos()) {
          if (!foto.isEmpty()) {
            String hash = UUID.randomUUID().toString();
            ObjectWriteResponse minioResponse = minioService.uploadFile(foto, hash);
            FotoPessoa fotoPessoa = new FotoPessoa();
            fotoPessoa.setPessoa(pessoa);
            fotoPessoa.setData(LocalDate.now());
            fotoPessoa.setBucket(minioResponse.bucket());
            fotoPessoa.setHash(hash);
            fotos.add(fotoPessoa);
          }
        }
        pessoa.setFotos(fotos);
      }
      Pessoa savedPessoa = pessoaRepository.save(pessoa);
      return new ResponseEntity<>(savedPessoa, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  public ResponseEntity<Page<Pessoa>> getAllPessoas(Pageable pageable) {
    try {
      Page<Pessoa> pagePessoas = pessoaRepository.findAll(pageable);

      if (pagePessoas.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(pagePessoas, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pessoa> getPessoaById(@PathVariable("id") Long id) {
    Optional<Pessoa> pessoaData = pessoaRepository.findById(id);

    return pessoaData.map(pessoa -> new ResponseEntity<>(pessoa, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @Transactional
  @PutMapping("/{id}")
  public ResponseEntity<Pessoa> updatePessoa(@PathVariable("id") Long id, @RequestBody PessoaDTO pessoa) {
    Optional<Pessoa> pessoaData = pessoaRepository.findById(id);

    if (pessoaData.isPresent()) {
      Pessoa existingPessoa = pessoaData.get();
      pessoaMapper.toEntity(existingPessoa, pessoa);

      return new ResponseEntity<>(pessoaRepository.save(existingPessoa), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deletePessoa(@PathVariable("id") Long id) {
    try {
      pessoaRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}