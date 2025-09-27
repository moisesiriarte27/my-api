# 1. Usa una imagen base de Java 17. Asegúrate de que la versión de Java coincida con la de tu proyecto.
FROM eclipse-temurin:17-jdk-jammy

# 2. Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# 3. Copia el archivo .jar compilado desde la carpeta target al contenedor
#    ¡OJO! Asegúrate de que el nombre del .jar ('my_API-0.0.1-SNAPSHOT.jar') coincida EXACTAMENTE con el tuyo.
COPY target/my_API-0.0.1-SNAPSHOT.jar app.jar

# 4. Expone el puerto en el que correrá la aplicación. Render asignará uno dinámicamente.
EXPOSE 8080

# 5. El comando para iniciar la aplicación cuando el contenedor se ejecute
ENTRYPOINT ["java", "-jar", "app.jar"]