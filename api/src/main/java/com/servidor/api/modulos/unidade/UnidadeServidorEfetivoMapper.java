package com.servidor.api.modulos.unidade;

import com.servidor.api.modulos.fotopessoa.FotoPessoa;
import com.servidor.api.modulos.fotopessoa.FotoPessoaRepository;
import com.servidor.api.modulos.lotacao.Lotacao;
import com.servidor.api.modulos.minio.MinioService;
import com.servidor.api.modulos.servidorefetivo.ServidorEfetivo;
import com.servidor.api.modulos.servidorefetivo.UnidadeServidorEfetivoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Component
public class UnidadeServidorEfetivoMapper {

  @Autowired
  private UnidadeRepository unidadeRepository;

  @Autowired
  private FotoPessoaRepository fotoPessoaRepository;

  @Autowired
  private MinioService minioService;

  public List<UnidadeServidorEfetivoDTO> toDTO(List<ServidorEfetivo> servidorEfetivos, Long unidadeId) {
    Unidade unidade = unidadeRepository.findById(unidadeId).orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
    List<UnidadeServidorEfetivoDTO> dtos = new ArrayList<>();
    for(ServidorEfetivo servidorEfetivo : servidorEfetivos) {
      UnidadeServidorEfetivoDTO dto = new UnidadeServidorEfetivoDTO();
      dto.setNomePessoa(servidorEfetivo.getPessoa().getNome());
      dto.setNomeUnidade(unidade.getNome());

      LocalDate currentDate = LocalDate.now();
      int idade = Period.between(servidorEfetivo.getPessoa().getDataNascimento(), currentDate).getYears();
      dto.setIdade(idade);

      List<FotoPessoa> fotoPessoas = fotoPessoaRepository.findAllByPessoaId(servidorEfetivo.getPessoa().getId().longValue());
      List<String> fotoPessoasUrl = fotoPessoas.stream().map(fotoPessoa -> {
        String url = minioService.getFileUrl(fotoPessoa.getHash());
        return url;
      }).toList();
      dto.setUrl(fotoPessoasUrl);
    }
    return dtos;
  }
}
