[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2d1976960db9415892b85d741bb4a336)](https://www.codacy.com/app/jelabra/Agents_e5b?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/Agents_e5b&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/Arquisoft/Agents_e5b.svg?branch=master)](https://travis-ci.org/Arquisoft/Agents_e5b)
[![codecov](https://codecov.io/gh/Arquisoft/participants2a/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/Agents_e5b)


# Agents 5b

[![Join the chat at https://gitter.im/Arquisoft/participants2a](https://badges.gitter.im/Arquisoft/participants2a.svg)](https://gitter.im/Arquisoft/participants2a?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Skeleton of participants module

# Authors
## Nuevos Autores
- Carlos Sanabria Miranda (@CarlosSanabriaM)
- Manuel Fernández Antuña (@uo2999)
- Adrián Pérez Carou (@adrycarou896)
- Mª Rosa Valdés Pire (@RosaValdesPire)

## Antiguos Autores
- Herminio García González (@herminiogg)
- Jose Emilio Labra Gayo (@labra)
- Sergio Flórez Vallina (@zerolfer)
- Rubén García Ruiz (@RubenGarciaR)
- Sonia Gestal Huelga (@sonia94)
- Luis Irazusta Lorenzo (@Fuegon)


# Funcionamiento:
## Interfaz HTML
1. Escribir en el navegador: http://localhost:8080/
2. Proporcionar los datos de login para los 3 agentes disponibles (uno de cada tipo):
 * Login: 31668313G  Password: 1234  Kind: Person
 * Login: A58818501  Password: 1234  Kind: Entity
 * Login: 525695S    Password: 1234  Kind: Sensor
3. Aparecerá la pantalla que muestra los datos del agente
 * Se puede modificar el email directamente (comprobándose si el email es válido)
 * Se puede ir a la pantalla de cambio de contraseña
4. Cambio de contraseña:
 * Escribir la contreseña antigua
 * Escribir la contraseña nueva

## Servicio REST
   El punto de entrada se encuentra en http://localhost:8080/user.
   
   Acepta peticiones POST en formato JSON con el contenido:
   ``{"login": usuario, "password": password, "kind": tipo de agente}``
   
   Devuelve la informacion del agente si las credenciales en formato 
   JSON o XML segun se indique en la cabecera de la petición.
   
   Si los datos no son correctos se devuelve un error HTTP 404.
   Si los parametros no son correctos se devuelve un codigo de error HTTP 406.
   
### Formato de retorn JSON
   ```json
   {
     "name": Nombre,
     "location": Coordenadas (opcional),
     "email": Email,
     "id": identificador,
     "kind": tipo de usuario,
     "kindCode": código numérico del tipo de usuario,
   }
   ```
### Formato retorno XML
   ```xml
   <AgentMin>
       <name>Nombre</name>
       <location>Coordenadas (opcional)</location>
       <email>Email</email>
       <id>identificador</id>
       <kind>tipo de usuario</kind>
       <kindCode>código numérico del tipo de usuario</kindCode>
   </AgentMin>
   ```
   


