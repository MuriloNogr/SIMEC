![image](https://github.com/user-attachments/assets/294945d7-fd13-4987-b8a8-6d5af2209c7b)



# SIMEC - Sistema Integrado de Monitoramento de Energia para Condomínios 🌟

**SIMEC** é uma aplicação web desenvolvida em **Java** com **Spring Boot**, focada no monitoramento e gerenciamento de consumo energético em condomínios. O sistema visa promover práticas de consumo sustentável e engajamento por meio de relatórios, gamificação e dicas personalizadas. O projeto utiliza tecnologias modernas como **Spring Security**, **RabbitMQ** e **OpenAI** para oferecer uma experiência robusta e escalável.

---

## Links

[Link para a aplicação no Fly.io](https://simec.fly.dev/)

[Link para o vídeo de apresentação](https://www.youtube.com/watch?v=-4ng3O57-hw)

---

## 🚀 Recursos Principais

- **📊 Monitoramento de Consumo de Energia**: Permite que os usuários acompanhem o consumo mensal de energia em tempo real.
- **📝 Gerenciamento de Condomínios e Usuários**: CRUD completo para entidades como condomínios e usuários.
- **🔐 Autenticação e Autorização**: Utiliza Spring Security para proteger a aplicação e gerenciar acessos.
- **💡 Relatórios e Dicas Personalizadas**: Relatórios detalhados sobre consumo energético, com integração ao OpenAI para dicas personalizadas.
- **📬 Envio de Mensagens e E-mails**: Sistema de envio de notificações via e-mail e integração com RabbitMQ.

---

## 📋 Estrutura do Projeto

A aplicação segue a arquitetura **MVC (Model-View-Controller)** com as seguintes camadas:

- **Controller**: Gerencia as requisições HTTP e define os endpoints.
- **Service**: Contém a lógica de negócio e validações.
- **Repository**: Manipula a persistência e recuperação de dados do banco.
- **Model**: Representa as entidades persistidas no banco.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Security**
- **Thymeleaf**
- **RabbitMQ**
- **Docker**
- **Fly.io**
- **OpenAI API**
- **Oracle Database**
- **Bootstrap**

---

## 📂 Estrutura de Pastas

```
.
├── config
│   ├── ModelMapperConfig.java
│   ├── SecurityConfig.java
│   ├── SwaggerConfig.java
│   ├── WebConfig.java
├── controller
│   ├── AuthController.java
│   ├── CondominioController.java
│   ├── CondominioThymeleafController.java
│   ├── ContactController.java
│   ├── HomeController.java
│   ├── OpenAIController.java
├── dto
│   ├── CondominioDto.java
│   ├── UsuarioDto.java
├── model
│   ├── Condominio.java
│   ├── Usuario.java
├── repository
│   ├── CondominioRepository.java
│   ├── UsuarioRepository.java
├── service
│   ├── CondominioService.java
│   ├── UsuarioService.java
│   ├── EmailService.java
│   ├── MessagingService.java
│   ├── OpenAIService.java
│   ├── QueueConsumerService.java
├── templates
│   ├── index.html
│   ├── contact.html
│   ├── condominios
│       ├── listar.html
│       ├── formulario.html
│       ├── analise-consumo.html
├── application.properties
```

---

## 🌐 Endpoints da API

| Método | Endpoint                  | Descrição                                 |
|--------|----------------------------|-------------------------------------------|
| GET    | /api/condominios          | Lista todos os condomínios                |
| POST   | /api/condominios          | Cria um novo condomínio                   |
| GET    | /api/condominios/{id}     | Retorna informações de um condomínio      |
| DELETE | /api/condominios/{id}     | Remove um condomínio                      |
| GET    | /api/openai/analisar-consumo/{id} | Gera dicas personalizadas via OpenAI  |

---

## 📜 Configurações Importantes

### `application.properties`
Configurações de banco de dados, RabbitMQ e OpenAI:
```properties
spring.datasource.url=jdbc:oracle:thin:@//oracle.fiap.com.br:1521/orcl
spring.datasource.username=rm89162
spring.datasource.password=280400
spring.ai.openai.api-key=<CHAVE_API_OPENAI>
spring.rabbitmq.host=gull.rmq.cloudamqp.com
spring.rabbitmq.username=<USUARIO_RABBIT>
spring.rabbitmq.password=<SENHA_RABBIT>
```

---

## 🐳 Deploy com Docker

### `Dockerfile`
```dockerfile
# Etapa 1: Compilar o projeto
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Executar a aplicação
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/SIMEC-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 📐 Diagrama UML

![SIMECwebUML](https://github.com/user-attachments/assets/35b347ad-3ec4-46aa-ad35-a22895ec3aa8)

---

## 🧑‍💻 Contribuidores

- **Luis Fernando Menezes Zampar** - RM 550531
- **Diogo Fagioli Bombonatti** - RM 551694
- **Murilo Nogueira** - RM 89162
- **Gabriel Galdino da Silva** - RM 550711
