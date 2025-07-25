FROM maven:3.9.9-amazoncorretto-23 AS build

# Cria diretório de trabalho
WORKDIR /app

# Copia o projeto inteiro
COPY . .

# Faz o build e gera o .jar
RUN mvn clean package -DskipTests

# Segunda fase: apenas o runtime da aplicação
FROM amazoncorretto:21-alpine3.18

WORKDIR /app

# Copia o .jar gerado na fase anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Comando para rodar o .jar
CMD ["java", "-jar", "app.jar"]
