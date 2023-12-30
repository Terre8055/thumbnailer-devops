FROM eclipse-temurin:11


WORKDIR /thumbnailer

COPY . /thumbnailer

COPY entrypoint.sh /thumbnailer

# Add entry-point.sh
COPY /thumbnailer/entrypoint.sh /thumbnailer/target/entrypoint.sh
RUN chmod +x /thumbnailer/target/entrypoint.sh

ENTRYPOINT ["/thumbnailer/target/entrypoint.sh"]
