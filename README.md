# seplag-api
Api de servidores - projeto prático seplag

## Dados de inscrição


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

- Vá no topo da página, clique em Authorize e insira o token. (Não é necessário escrever Bearer)
![image](https://github.com/user-attachments/assets/3516f2ac-0367-48be-8446-a0307563d18b)


