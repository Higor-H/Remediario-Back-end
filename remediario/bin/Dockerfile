# Etapa 1: Build do JAR com Maven
FROM maven:3.8-openjdk-17-slim AS build

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o pom.xml e baixar dependências (para otimizar a construção)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar o código-fonte
COPY src ./src

# Construir o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Imagem de execução com o JAR gerado
FROM openjdk:17-jdk-slim

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o JAR gerado para o container
COPY --from=build /app/target/app.jar .

# Expor a porta que o Spring Boot vai rodar (geralmente 8080)
EXPOSE 8080

# Definir o comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
