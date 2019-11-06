FROM openjdk:8
WORKDIR /server
CMD ["sh", "-c", "java -jar -Dhttp.port=disabled -Dconfig.file=./application.prod.conf Multimodal-ML-System-Server.jar"]
