# Ping pong service

Example implementation of an application on GraalVM

The system consists of two microservices

Microservices communicate using gRPC

## Requirements

- Java 11
- Maven
- Docker
- [GraalVM](https://www.graalvm.org/downloads/) (optional)
- [Helm](https://helm.sh/ru/docs/intro/install/) (optional)
- [gRPCurl](https://github.com/fullstorydev/grpcurl) (optional)
- [Vegeta](https://github.com/tsenart/vegeta) (optional)

## Docker-compose

Start

```shell
docker-compose up --detach
```

Stop

```shell
docker-compose down
```

## Build native image

### If GraalVM is installed on your computer

```shell
./mvnw clean verify -Pnative
```

### If GraalVM is not on your computer

Create a Docker image

```shell
./mvnw clean verify -Pnative \
  -Dquarkus.container-image.build=true \
  -Dquarkus.native.container-build=true \
  -Dquarkus.container-image.name=NAME_MY_IMAGE
```

Example

```shell
./mvnw clean verify -Pnative \
  -Dquarkus.container-image.build=true \
  -Dquarkus.native.container-build=true \
  -Dquarkus.container-image.name=ping-pong-front:1
```

### If GraalVM is not on your computer and the computer does not have Internet access

Create a Docker image

We can download the image using the GraalVM compiler locally or point to a Docker registry mirror

```shell
./mvnw clean verify -Pnative \
  -Dquarkus.container-image.build=true \
  -Dquarkus.native.container-build=true \
  -Dquarkus.container-image.name=NAME_MY_IMAGE \
  -Dquarkus.container-image.image=IMAGE_WITH_GRAAL_VM
```

Example

```shell
./mvnw clean verify -Pnative \
  -Dquarkus.container-image.build=true \
  -Dquarkus.native.container-build=true \
  -Dquarkus.container-image.name=ping-pong-front:1 \
  -Dquarkus.container-image.image=registry.access.redhat.com/quarkus/mandrel-22-rhel8:22.3
```

## Build JDK version

You can test the system using the regular JDK version

```shell
./mvnw clean package
```

## Applications

### Front

The application responds to a REST request from the user

[Source](front/)

#### API

Example of REST requests in IDEA REST Client notation: [example](http/front.http)

##### cURL Example

Checking that the application is working

```shell
curl http://localhost:8080/api/v1/ping
```

Pass the request to the back service

```shell
curl http://localhost:8080/api/v1/ping
```

### Back

The application receives a gRPC request and responds with a delay

[Source](back/)

#### API

Example of REST requests in IDEA REST Client notation: [example](http/back.http)

##### cURL Example

Find out the current response time limits of the gRPC service

```shell
curl http://localhost:8080/bound
```

Setting response time limits

```shell
curl -d '{ "lower": 50, "upper": 200 }' http://localhost:8080/bound
```

##### gRPC

Description

```shell
grpcurl localhost:9000 list

grpcurl -plaintext localhost:9000 describe ru.sbrf.gwec.PingPong

grpcurl -plaintext localhost:9000 describe ru.sbrf.gwec.PingPong

grpcurl -plaintext localhost:9000 describe ru.sbrf.gwec.Msg
```

Request

```shell
grpcurl -plaintext -d '{"body": "PING"}' localhost:9000 ru.sbrf.gwec.PingPong/ping
```

## Helm-chart

### Install

```shell
helm install ping-pong helm/ping-pong
```

### Upgrade to JIT version

```shell
helm upgrade ping-pong helm/ping-pong --values helm/ping-pong/values/demo-jit.yaml
```

### Upgrade to GraalVM version

```shell
helm upgrade ping-pong helm/ping-pong --values helm/ping-pong/values/demo-native.yaml
```

### Uninstall

```shell
helm uninstall ping-pong
```

## Stress test

You can perform a stress test using [Vegeta](vegeta/lt.sh)