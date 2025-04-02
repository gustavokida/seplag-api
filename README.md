# seplag-api
Api de servidores - projeto prático seplag

## Dados de inscrição
- Processo Seletivo: PSS 02/2025/SEPLAG (Analista de TI - Perfil Junior, Pleno e Sênior) - PLENO
- Inscrição: 9013
- Nome: GUSTAVO EIJI KIDA

## Iniciando o projeto

- Instale as seguintes versões do Java e Gradle:
  - Java 21
  - Gradle 8.13

- Gere o .jar da aplicação utilizando o comando abaixo
```
gradle clean bootjar  
```

- faça a build do container da aplicação
```
docker build -t seplag-api:latest .
```

- Inicie o docker compose
```
 docker-compose up
```

## Testando o projeto

### Login
- Foi utilizado o Swagger para realizar os testes da api -> http://localhost:8080/swagger-ui/index.html#

- Primeiramente, faça o login, troque o request body para o json abaixo
```
{
  "username": "admin",
  "password": "admin123"
}
```
![image](https://github.com/user-attachments/assets/cb8e42ad-e84c-439a-a1f8-df48d0d1a11a)

- Copie o Access Token
![image](https://github.com/user-attachments/assets/02d02936-4c1a-47f5-bda4-f81afe705e74)

- Vá no topo da página, clique no cadeado no canto direito superior do endpoint e insira o token. (Não é necessário escrever Bearer)
![image](https://github.com/user-attachments/assets/42ddbab9-5813-46e2-aa8a-8f20d8027eb0)


### Criando dados

- Para testar, crie os dados nessa ordem, utilizando os respectivos endpoints POST e alterando o RequestBody:
  - Cidade POST
    ```
    {
      "nome": "cuiaba",
      "uf": "mt"
    }
    ```
  - Endereco POST
    ```
    {
      "tipoLogradouro": "tipo log",
      "logradouro": "logradouro teste",
      "numero": 123,
      "bairro": "bairro teste",
      "cidade": 1
    }
    ```
  - Unidade POST
    ```
    {
      "nome": "unidade teste",
      "sigla": "ut",
      "unidadeEnderecos":[
       {"endereco":
         {"id":1}
       }
     ]
    }
    ```
  - Pessoa POST
    ```
    {
      "nome": "nome teste",
      "dataNascimento": "2025-04-01",
      "sexo": "masculino",
      "mae": "nome mae",
      "pai": "nome pai",
      "enderecos": [
        1
      ],
      "servidoresEfetivos": [
        {
          "matricula": "1234"
        }
      ],
      "servidoresTemporarios": [
        {
          "dataAdmissao": "2025-04-01",
          "dataDemissao": "2025-04-03"
        }
      ],
      "lotacoes": [
        {
          "unidadeId": 1,
          "portaria": "portaria teste",
          "dataLocacao": "2025-04-01",
          "dataRemocao": "2025-04-03"
        }
      ]
    }  
    ```
  - Foto Pessoa ->  /pessoa/{id} POST
    - Insira o Id da pessoa criada
    - Insira quantas fotos quiser 

### Consulta de servidores efetivos lotados em determinada unidade
  
  - No swagger, Vá em "unidade-controller" e faça uma requisição no endpoint /api/unidade/{id}/servidores-efetivos-lotados
  - insira o id da unidade que deseja


### Consulta de endereço funcional de servidor efetivo
  - no "servidor-efetivo-controller" endpoint "/api/servidor-efetivo/buscar-endereco-funcional", insira uma parte do nome e faça a requisição.
  


# Consulta foto pessoa por URL Min.io
  - Coloque o id da entidade fotoPessoa que armazena o hash da foto no "foto-pessoa-controller" endpoint "/api/foto-pessoa/{id}/url", ele irá retornar a URL da foto no Min.io.
