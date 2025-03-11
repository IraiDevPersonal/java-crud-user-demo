# Pasos para probar API

## Importante

Es necesario tener instalado java 17+ y maven para gestión de dependencias.
Para la base de datos H2 al ser un banco de datos en memoria, al levantar la aplicación en desarrollo la base de datos se levantara automaticamente, pero al detener la aplicacion los datos se perderan, esta base de dates es idual para pruebas en desarrollo.

## Estructura proyecto

Se genero una estructura basica para separa las responsabilidades de la aplicación.

> main/java/com/crud_user_demo

a este nivel se encontraran las carpetas: controllers, repositories, entities, exceptions y utils en donde esta el codigo de las funcionalidades de la aplicación.
Para mas detalle dirigirse a cada archivo.

## Levantar API

Estando dentro del directorio raiz del proyecto, ejecutar el siguiente comando, para levantar el proyeto en desarrollo (utlizando maven).

```
    mvn spring-boot:run
```

## Endpoints

Se recomienda usar un cliente, como Postman o Bruno para probar los endpoint (POST no se podra ejecutar desde el navegar, GET si).

```
    URL base http://localhost:8081/api
```

```
    GET /users
    RESPONSE []
```

retorna un arreglo de usurios.

```
    POST /users
    BODY JSON
    {
        "name": "test-1",
        "email": "test-1@gmail.com",
        "password": "MiContrasenaBienSegura123",
        "phones": [
            {
               "number": "1234567",
                "citycode": "1",
                "countrycode": "57"
            }
        ]
    }
```

Retorna lo siguiente:

```
{
    "createdAt": "2025-03-11T12:39:01.856894",
    "lastLogin": "2025-03-11T12:39:01.856894",
    "password": "MiContrasenaBienSegura123",
    "modifiedAt": "2025-03-11T12:39:01.8584",
    "name": "test-1",
    "phones": [
        {
            "id": 1,
            "number": "1234567",
            "citycode": "1",
            "countrycode": "57"
        }
    ],
    "active": true,
    "id": 1,
    "email": "test-1@gmail.com",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LTFAZ21haWwuY29tIiwianRpIjoiYjUyZGFkOGQtMWFiNy00ZTE3LTkzYjktNjRmZjE2NWU4MTM4IiwiaWF0IjoxNzQxNzA3NTQxLCJleHAiOjE3NDE3MTQ3NDF9.2wI54JPJHqqRNg9OJWODH4k9z2jVryfM3xhY4aMPv8c"
}
```

En caso de errores se obtenedra en el siguiente formato (El mensaje de error dependera del error).

```
{
    "message": "Error correo ya registrado"
}
```
