# --- ETAPA 1: Construcción (usando una imagen de Maven) ---
# Usamos una imagen que tiene Java 17 y Maven. La nombramos 'build'.
FROM maven:3.8-eclipse-temurin-17 AS build

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos primero el pom.xml para aprovechar la caché de Docker
COPY pom.xml .
# Descargamos las dependencias
RUN mvn dependency:go-offline

# Copiamos el resto del código fuente
COPY src ./src

# Compilamos la aplicación y creamos el .jar, omitiendo los tests
RUN mvn clean package -DskipTests


# --- ETAPA 2: Ejecución (usando una imagen limpia de solo Java) ---
# Usamos una imagen mucho más ligera que solo tiene el entorno de ejecución de Java 17
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copiamos el .jar que se creó en la etapa 'build' a nuestra imagen final
# OJO: Asegúrate de que el nombre del .jar coincida con el tuyo
COPY --from=build /app/target/my-API-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que correrá la aplicación
EXPOSE 8080

# El comando para iniciar la aplicación cuando el contenedor se ejecute
ENTRYPOINT ["java", "-jar", "app.jar"]