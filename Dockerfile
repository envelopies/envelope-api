FROM gradle:8.10.2-jdk21-alpine AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle build --quiet --exclude test

FROM bellsoft/liberica-openjre-alpine-musl:21.0.4-9
RUN addgroup -S spring && \
    adduser -S spring -G spring && \
    apk update && \
    apk add libwebp libwebp-tools && \
    rm -rf /var/cache/* && \
    mkdir -p /app/images
USER spring
EXPOSE 8080
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/envelope-api.jar /app/envelope-api.jar
ENTRYPOINT [ "java", "-jar", "envelope-api.jar" ]
