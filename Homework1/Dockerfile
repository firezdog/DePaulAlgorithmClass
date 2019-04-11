FROM openjdk:8
COPY ./out/production/Homework1/ /tmp
EXPOSE 5005
WORKDIR /tmp
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005", "Homework1/HW1"]