package com.servidor.api.modulos.pessoa;

import com.servidor.api.modulos.endereco.Endereco;
import com.servidor.api.modulos.endereco.EnderecoRepository;
import com.servidor.api.modulos.lotacao.Lotacao;

import com.servidor.api.modulos.pessoaendereco.PessoaEndereco;
import com.servidor.api.modulos.pessoaendereco.PessoaEnderecoKey;
import com.servidor.api.modulos.servidorefetivo.ServidorEfetivo;
import com.servidor.api.modulos.servidorefetivo.ServidorEfetivoDTO;
import com.servidor.api.modulos.servidortemporario.ServidorTemporario;
import com.servidor.api.modulos.servidortemporario.ServidorTemporarioDTO;
import com.servidor.api.modulos.unidade.Unidade;
import com.servidor.api.modulos.unidade.UnidadeRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PessoaMapper {


  @Autowired
  UnidadeRepository unidadeRepository;

  @Autowired
  EnderecoRepository enderecoRepository;

  @Autowired
  PessoaRepository pessoaRepository;

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "fotos", ignore = true)
  @Mapping(target = "lotacoes", ignore = true)
  @Mapping(target = "servidoresEfetivos", ignore = true)
  @Mapping(target = "servidoresTemporarios", ignore = true)
  @Mapping(target = "pessoaEnderecos", ignore = true)
  public abstract Pessoa toEntity(PessoaDTO pessoaDTO);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "nome", source = "nome")
  @Mapping(target = "dataNascimento", source = "dataNascimento")
  @Mapping(target = "sexo", source = "sexo")
  @Mapping(target = "mae", source = "mae")
  @Mapping(target = "pai", source = "pai")
  @Mapping(target = "fotos", ignore = true)
  @Mapping(target = "lotacoes", ignore = true)
  @Mapping(target = "servidoresEfetivos", ignore = true)
  @Mapping(target = "servidoresTemporarios", ignore = true)
  @Mapping(target = "pessoaEnderecos", ignore = true)
  public abstract void toEntity(@MappingTarget Pessoa pessoa, PessoaDTO pessoaDTO);

  @AfterMapping
  public void mapLotacao(PessoaDTO pessoaDTO, @MappingTarget Pessoa pessoa) {
    if(pessoaDTO.getLotacoes() == null) {
      return;
    }
    List<PessoaDTO.Lotacoes> lotacoes = pessoaDTO.getLotacoes();
    List<Lotacao> lotacaoList = new ArrayList<>();
    for (PessoaDTO.Lotacoes lotacaoDTO : lotacoes) {
      Unidade unidade = unidadeRepository.findById(lotacaoDTO.getUnidadeId()).orElse(null);
      Lotacao lotacao = new Lotacao();
      lotacao.setPessoa(pessoa);
      lotacao.setDataLocacao(lotacaoDTO.getDataLocacao());
      lotacao.setDataRemocao(lotacaoDTO.getDataRemocao());
      lotacao.setPortaria(lotacaoDTO.getPortaria());
      lotacao.setUnidade(unidade);
      lotacaoList.add(lotacao);
    }
    pessoa.setLotacoes(lotacaoList);
  }

  @AfterMapping
  public void mapEndereco(PessoaDTO pessoaDTO, @MappingTarget Pessoa pessoa) {
    if(pessoaDTO.getEnderecos() == null) {
      return;
    }
    pessoa = pessoaRepository.save(pessoa);
    List<Long> enderecos = pessoaDTO.getEnderecos();
    List<PessoaEndereco> enderecosList = new ArrayList<>();
    for (Long id : enderecos) {
      if (id != null) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        PessoaEnderecoKey pessoaEnderecoKey = new PessoaEnderecoKey();
        pessoaEnderecoKey.setEnderecoId(endereco.get().getId());
        pessoaEnderecoKey.setPessoaId(pessoa.getId());
        PessoaEndereco pessoaEndereco = new PessoaEndereco();
        pessoaEndereco.setPessoa(pessoa);
        pessoaEndereco.setEndereco(endereco.get());
        pessoaEndereco.setId(pessoaEnderecoKey);
        enderecosList.add(pessoaEndereco);
      }
    }
    pessoa.setPessoaEnderecos(enderecosList);
  }

  @AfterMapping
  public void mapServidorEfetivo(PessoaDTO pessoaDTO, @MappingTarget Pessoa pessoa) {
    if(pessoaDTO.getServidoresEfetivos() == null) {
      return;
    }
    pessoa = pessoaRepository.save(pessoa);
    List<ServidorEfetivoDTO> servidoresEfetivos = pessoaDTO.getServidoresEfetivos();
    List<ServidorEfetivo> servidoresEfetivosList = new ArrayList<>();
    for (ServidorEfetivoDTO servidor : servidoresEfetivos) {
      ServidorEfetivo servidorEfetivo = new ServidorEfetivo();
      servidorEfetivo.setPessoa(pessoa);
      servidorEfetivo.setMatricula(servidor.getMatricula());
      servidorEfetivo.setPessoaId(pessoa.getId());
      servidoresEfetivosList.add(servidorEfetivo);
    }
    pessoa.setServidoresEfetivos(servidoresEfetivosList);
  }

  @AfterMapping
  public void mapServidorTemporario(PessoaDTO pessoaDTO, @MappingTarget Pessoa pessoa) {
    if(pessoaDTO.getServidoresTemporarios() == null) {
      return;
    }
    pessoa = pessoaRepository.save(pessoa);
    List<ServidorTemporarioDTO> servidoresTemporarios = pessoaDTO.getServidoresTemporarios();
    List<ServidorTemporario> servidoresTemporariosList = new ArrayList<>();
    for (ServidorTemporarioDTO servidor : servidoresTemporarios) {
      ServidorTemporario servidorTemporario = new ServidorTemporario();
      servidorTemporario.setPessoa(pessoa);
      servidorTemporario.setDataAdmissao(servidor.getDataAdmissao());
      servidorTemporario.setDataDemissao(servidor.getDataDemissao());
      servidorTemporario.setPessoaId(pessoa.getId());
      servidoresTemporariosList.add(servidorTemporario);
    }
    pessoa.setServidoresTemporarios(servidoresTemporariosList);
  }
}
