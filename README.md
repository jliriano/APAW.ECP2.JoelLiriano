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
#### Parámetros del cuerpo
- `name`: String (**requerido**)
- `website`: String

#### Respuesta
- 200 OK 
  - `id`: String
- 403 BAD_REQUEST

---

### GET /publishers/{id}
#### Parámetros del cuerpo
- `id`: String (**requerido**)

#### Respuesta
- 200 OK 
  - `{name:String,website:String}`: String
- 403 BAD_REQUEST
- 404 NOT_FOUND

---

### POST /publishers/{id}/games
### PATCH /publishers/{id}/games/{id}
### POST /publishers/{id}/reviews
### PUT /publishers/{id}/reviews/{id}
### DELETE /publishers/{id}/reviews/{id}
### GET /publishers/search?q=gameRating:TEEN