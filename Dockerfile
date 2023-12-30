FROM eclipse-temurin:11


WORKDIR /thumbnailer

COPY . /thumbnailer

# Add entry-point.sh
COPY /thumbnailer/target/entrypoint.sh /thumbnailer/target/entrypoint.sh
RUN chmod +x /thumbnailer/target/entrypoint.sh

ENTRYPOINT ["/thumbnailer/target/entrypoint.sh"]
