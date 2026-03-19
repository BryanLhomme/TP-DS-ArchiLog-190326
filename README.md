# Plateforme Coworking — Microservices

Projet de réservation de salles de coworking en architecture microservices avec Spring Boot, Spring Cloud et Apache Kafka.

## Les services

- config-server (port 8888) : configuration centralisée
- discovery-server (port 8761) : Eureka, registre des services
- api-gateway (port 8080) : point d'entrée unique qui route vers les services
- room-service (port 8081) : gestion des salles (CRUD + disponibilité)
- member-service (port 8082) : gestion des membres et abonnements (BASIC/PRO/ENTERPRISE)
- reservation-service (port 8083) : gestion des réservations avec validation cross-service

## Comment lancer

Il faut Java 17+, Maven et Docker.

1. Lancer Kafka avec Docker :
docker-compose up -d

2. Lancer les services dans cet ordre (chacun dans un terminal) :
cd discovery-server && mvn spring-boot:run
cd config-server && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
cd room-service && mvn spring-boot:run
cd member-service && mvn spring-boot:run
cd reservation-service && mvn spring-boot:run

Il faut attendre environ 30 secondes que tout se lance avant de tester.

## Swagger

Chaque service métier a sa doc Swagger accessible quand il tourne :
- room-service : http://localhost:8081/swagger-ui.html
- member-service : http://localhost:8082/swagger-ui.html
- reservation-service : http://localhost:8083/swagger-ui.html