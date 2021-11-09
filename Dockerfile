FROM node:17-alpine

ADD . /app
RUN npm install -g sass
RUN sass --no-source-map app/src/main/resources/sass:/app/src/main/resources/public/css

FROM heroku/heroku:20

RUN apt-get update \
  && apt-get install -y maven \
  && apt-get clean \
  && rm -Rf /var/lib/apt/lists/*
RUN mkdir /app

WORKDIR /app

ADD pom.xml /app
# RUN mvn dependency:resolve

ADD . /app
COPY --from=0 /app/src/main/resources/public/css /app/src/main/resources/public/css

RUN scripts/docker/mvn_if_production.sh

CMD mvn spring-boot:run

