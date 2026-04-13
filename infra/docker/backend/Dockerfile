FROM eclipse-temurin:25-jdk

WORKDIR /app

# O contexto do build no docker-compose é ../, então chegamos à raiz
# Nós copiamos o JAR compilado do Maven na host machine
COPY backend/apps/api/target/meubolso-api-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]
