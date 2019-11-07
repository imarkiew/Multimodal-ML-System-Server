FROM openjdk:8
WORKDIR /server
ARG jarName=Multimodal-ML-System-Server-assembly-1.0.jar
ARG assetsJarName=multimodal-ml-system-server_2.11-1.0-web-assets.jar
ENV jarNameEnvValue=$jarName
COPY target/scala-2.11/${jarName} ${jarName}
COPY target/scala-2.11/${assetsJarName} ${assetsJarName}
CMD ["sh", "-c", "java -jar -Dhttp.port=disabled -Dconfig.file=./application.prod.conf ${jarNameEnvValue}"]
