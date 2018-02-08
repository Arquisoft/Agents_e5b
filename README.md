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

Para arrancarlo todo y que funcione, se debe ejecutar el siguiente comando, estando situado en la carpeta Agents_e5b:
``mvn spring-boot:run``

## Servicio REST

### Para obtener los datos de un agente
  El punto de entrada para obtener la información de un agente se encuentra en http://localhost:8080/user.
   
  Acepta peticiones POST en formato JSON con el contenido:
  ```json
  {
    "login": usuario, 
    "password": password, 
    "kind": tipo de agente
  }
  ```
  Los tres campos anteriores son Strings.
   
  Puede devolver la informacion del agente tanto en formato JSON como en XML, 
  según se indique en la cabecera de la petición.
   
  Si los datos no son correctos se devuelve un error HTTP 404.
   
  #### Formato de retorn JSON
   ```json
   {
     "name": Nombre,
     "location": Coordenadas (opcional),
     "email": Email,
     "id": identificador,
     "kind": tipo de usuario,
     "kindCode": código numérico del tipo de usuario
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
     
  ### Para cambiar los datos de un agente
  El punto de entrada para cambiar la información de un agente se encuentra en http://localhost:8080/changeInfo.
   
  Acepta peticiones POST en formato JSON con el contenido:
  ```json
  {
    "login": usuario, 
    "password": password, 
    "kind": tipo de agente, 
    "infoToChange": tipo de agente, 
    "newInfo": tipo de agente
  }
  ```
    
  Los campos anteriores son todos Strings.
  El campo infoToChange puede tomar uno de los siguientes valores:
	 `password, email, name, kind o location`
	El campo newInfo contiene el nuevo valor que va a tener el campo a cambiar
      
  Si se ha encontrado el agente y se ha podido cambiar la informacion especificada, se devuelve un OK HTTP 200.
  En caso contrario, se devuelve un error HTTP 400.
   
## Interfaz HTML
  1. Escribir en el navegador: http://localhost:8080/
  2. Proporcionar los datos de login para los 3 agentes disponibles (uno de cada tipo):
     * Login: 31668313G  Password: 1234  Kind: Person
     * Login: A58818501  Password: 1234  Kind: Entity
     * Login: 525695S    Password: 1234  Kind: Sensor
  3. Aparecerá la pantalla que muestra los datos del agente
     * Se puede ir a la pantalla de cambio de datos
     * Se puede ir a la pantalla de inicio
  4. Cambio de datos:
     * Se puede cambiar la contraseña
       * Escribir la contraseña antigua
       * Escribir la contraseña nueva
     * Se puede cambiar el email del agente, siempre que sea una dirección de email válida 
     * Se puede cambiar el nombre del agente, siempre que no esté en blanco
     * Se puede cambiar la localización del agente u omitirla
     * Se puede cambiar el tipo del agente, siempre que sea uno de los 3 tipos válidos
     * Se puede ir a la pantalla de vista de datos del agente
     * Se puede ir a la pantalla de inicio


