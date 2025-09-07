# ===== Build stage =====
FROM maven:3.9-eclipse-temurin-17 AS build
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -e -DskipTests package

# ===== Run stage =====
FROM eclipse-temurin:17-jre
WORKDIR /app
# corregge problemi di encoding
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
# copia il file JAR generato
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Xms256m -Xmx512m"
# avvio di default con profilo 'dev'
ENV SPRING_PROFILES_ACTIVE=dev
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
