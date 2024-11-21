# Etapa 1: Compilar o projeto
FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar o arquivo pom.xml e dependências do Maven primeiro para cache eficiente
COPY pom.xml ./

# Baixar dependências para otimizar builds futuros
RUN mvn dependency:go-offline -B

# Copiar o código-fonte do projeto
COPY src ./src

# Compilar o projeto e empacotar o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Configuração da imagem final
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copiar o arquivo .jar gerado na etapa de compilação
COPY --from=build /app/target/SIMEC-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta que o Spring Boot usa
EXPOSE 8080

# Definir o comando de entrada para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
