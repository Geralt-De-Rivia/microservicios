#Argumento para reutilizarlo en dockerfile de manera GLOBAL
ARG MSVC_NAME=msvc_cursos

#Imagen de Java de DockerHub
FROM openjdk:18-jdk-alpine AS builder

#Argumento reutilizado
ARG MSVC_NAME

#Directorio donde voy a copiar mi proyecto
WORKDIR /app/$MSVC_NAME

#Donde se encuentra el parent ya que es la carpeta padre y el directorio donde deseo que quede
COPY ./pom.xml /app

#Copiar las dependencias de maven para no tener que compilarlas siempre
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .
#Donde se encuentra nuestro .jar
#COPY ./target/msvc_cursos-0.0.1-SNAPSHOT.jar .

#Ejecutamos el compilador pero que ignore los test y elimine la carpeta target
RUN sed -i 's/\r$//' mvnw
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

#El microservicio que voy a copiar y el directorio donde deseo que quede que es el WORKDIR
COPY ./msvc_cursos/src ./src

#Ejecutar el compilador
RUN ./mvnw clean package -DskipTests

#Nuevo ambiente de trabajo
FROM openjdk:18-jdk-alpine

#Argumento reutilizado
ARG MSVC_NAME
ARG TARGET=/app/$MSVC_NAME/target
ARG SNAPSHOT=msvc_cursos-0.0.1-SNAPSHOT.jar

WORKDIR /app

#Creacion de carpeta de logs
RUN mkdir ./logs

COPY --from=builder $TARGET/$SNAPSHOT .

#Puerto donde esta expuesto el proyecto, el que colocamos en .properties
ARG PORT_ARG=8002
ENV PORT=$PORT_ARG
EXPOSE $PORT

#Punto de entrada, comando separado para levantar el proyecto con el jar en el WORKDIR
#ENTRYPOINT ["java", "-jar", "./target/msvc_cursos-0.0.1-SNAPSHOT.jar"]
#Punto de entrada, comando separado para levantar el proyecto con el jar externo
CMD ["java", "-jar", "msvc_cursos-0.0.1-SNAPSHOT.jar"]