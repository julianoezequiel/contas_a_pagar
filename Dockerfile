# Usar uma imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR gerado para dentro do container
COPY target/contasapagar-api.jar /app/contasapagar-api.jar

# Expor a porta na qual a aplicação vai rodar
EXPOSE 8080

# Definir o comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "contasapagar-api.jar"]
