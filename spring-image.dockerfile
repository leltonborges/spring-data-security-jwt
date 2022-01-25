FROM leltondev/openjdk17-maven:latest

ENV PORT=${PORT:-8081}
WORKDIR /app
VOLUME /tmp
COPY . .
EXPOSE $PORT

RUN ["/opt/apache-maven-3.8.4/bin/mvn", "clean", "package", "-DskipTests"]

RUN cp target/*jar ./app.jar;

CMD [ "java","-jar","app.jar" ]