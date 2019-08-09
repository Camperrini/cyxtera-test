# cyxtera-test

Esta sección esta más orientada a como desplegar la aplicación y también a mejoras que podrian hacerse para hacer mas robusto el producto

## Proceso de despliegue y decisiones de diseño

Elegí redis como tecnologia de persistencia y separar la fuente de datos de la aplicación debido a que esto garantiza mayor robustrez
y flexibilidad a la hora de manejar grandes cantidades de datos.
La decisión de redis se tomo debido a su naturaleza orientada a performance y su proposito de ser una base de datos llave valor por lo cual
se ajusta a las necesidades de la aplicación ya que no se necesita almacenar datos estructurados ni complejos.

Para garantizar el correcto funcionamiento de la aplicación se debe primero empaquetar la aplicacion usando maven luego construir las imagenes
de los contenedores que se van a usar y por ultimo ejecutar los servicios

```
mvn package
docker-compose build
docker-compose up
```

## Mejoras

Por ultimo como propuestas de mejoras se podria primero separar la aplicacion en dos microservicios:
El primero que se encargue del almacenamiento (la sesión), desacoplando esta funcionalidad para permitir a nuevos microservicios interactuar con los datos
almacenados en la plataforma.
El segundo se encargaria de las operaciones matematicas y se comunicaria con el primer microservicio para obtener los datos.

Otra propuesta de mejora seria usar un almacenamiento para los logs que permita mejorar la busqueda y llevar la traza de todas las operaciones 
que sucedieron en una sesion. Para almacenar los logs se podria usar bigquery o elasticsearch con logstash.

Una propuesta adicional consistiría en hacer uso de los profiles de spring para manejar los diferentes ambientes y configuraciones de desarrollo, producción, test, qa, etc.

Y por ultimo otra mejora aplicaria para las pruebas validando casos más complejos y flujos de la aplicación que no se tuvieron en cuenta
para garantizar que el codigo funciona correctamente en otros flujos. Además se podria incluir pruebas de integración para garantizar el 
funcionamiento de la comunicación con el datasource.
