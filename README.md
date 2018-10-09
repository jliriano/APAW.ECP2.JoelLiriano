# APAW. ECP2. Arquitecturas y Patrones Web
> Este proyecto es un ejercicio de arquitectura de un API-REST simulado para comprender las capas que intervienen y la organización de los diferentes tipos de test, con integración continua y control de la calidad del código.
> ##### [Máster en Ingeniería Web por la Universidad Politécnica de Madrid](http://miw.etsisi.upm.es)
> ##### Asignatura: *Arquitectura y Patrones para Aplicaciones Web*

## Tecnologías necesarias
* Java
* Maven
* GitHub
* SonarCloud

## Diseño de entidades
![joel-liriano-entities-class-diagram](./docs/joel-liriano-entities-class-diagram.png)

## API

### POST /publishers
### GET /publishers/{id}
### POST /publishers/{id}/games
### PATCH /publishers/{id}/games/{id}
### POST /publishers/{id}/reviews
### PUT /publishers/{id}/reviews/{id}
### DELETE /publishers/{id}/reviews/{id}
### GET /publishers/search?q=gameRating:TEEN