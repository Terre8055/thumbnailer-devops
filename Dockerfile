FROM eclipse-temurin:11


WORKDIR /thumbnailer

COPY . /thumbnailer

COPY entrypoint.sh /thumbnailer/target

RUN chmod +x /thumbnailer/target/entrypoint.sh

ENTRYPOINT ["/thumbnailer/target/entrypoint.sh"]
