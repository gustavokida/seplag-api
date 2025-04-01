package com.servidor.api.modulos.servidorefetivo;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "pessoa.nome", params = "nomeParcial", spec = Like.class)
public interface ServidorEfetivoSpec extends Specification<ServidorEfetivo> {
}
