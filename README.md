## Motivación

Este proyecto fue realizado como un desafío personal para aplicar algunas de las tecnologías que he ido aprendiendo a lo largo del tiempo. También tiene la finalidad de ser parte de un portafolio de temas y tecnologías que he ido aprendiendo a lo largo de los años.

## Objetivo

Mostrar en un mapa (google maps u open street map) lugares de interés turístico que me gustaría visitar alguna vez. La idea es elegir el país, y me marcará el o los atractivos turísticos que hay ahí (solo los que me gustaría visitar).
La parte del cliente consiste en consumir los servicios Rest publicados por el servidor y generar una página web donde se mostrarán en un mapa las atracciones turísticas seleccionadas.

### Nota
Este ejemplo tiene muchas cosas que corregir, por ejemplo no se le ha agregado spring-security para generar accesos a la aplicación, y los logs generados no están implementados con Log4j2 o Slf4j.
Para correr la aplicación dentro de mi mismo PC se cambio el puerto del servidor web al 8081. El 8080 es usado por la versión Server del mismo proyecto.

Los temas que más me costó resolver corresponden a:
- Cómo consumir los servicios REST publicados. Principalmente con el uso de las clases EntityModel, CollectionModel y Traverson.
- Cómo manejar ciclos javascript pero por medio de una plantilla Thymeleaf.

## Tecnologías Usadas

- Eclipse IDE for Enterprise Java and Web Developers. Version: 2022-09 (4.25.0)
- Java 8
- SpringBoot 2.7.4
- HateOAS
- Apache Tomcat 9.0.65
- Thymeleaf 3.0.15.RELEASE
- HTML5 & CSS3
- jackson-datatype-jts 1.2.9
- jts-core 1.18.2
- OpenstreetMap

## Descripción del proyecto

1. Corresponde a la parte cliente del proyecto. Esta consiste de una página web en la que se selecciona, de una lista de países, el que se quiere visitar y aparecerán en el mapa los atractivos turísticos que me gustaría visitar. Inicialmente me basé en los siguientes proyectos y ejemplos ([\[1\]](#ref1), [\[2\]](#ref2) y [\[3\]](#ref3))  
2. El proceso comienza con la carga del listado de países que me gustaría visitar y quedarán dentro de un formulario. Estos provienen del endpoint del servidor: http://localhost:8080/placestovisit-1.0-FINAL/countries. Esa lista de países es cargada mediante thymeleaf.
3. Una vez seleccionado el país, se envía el formulario y se retornan el listado de atracciones turísticas y una serie de parámetros necesarios para el correcto despliegue del mapa [\[4\]](#ref4). (En un futuro podría usarse Ajax para el envío del formulario, sin tener que usar el botón "Submit").
4. Para poder deserializar JSON con datos geográficos se tuvo que agregar las siguientes dependencias:
    + jackson-core (v2.13.4)
    + jackson-datatype-jts (v1.2.9, groupId: org.n52.jackson)
    + jts-core (v1.18.2, groupId: org.locationtech.jts)

   <p>En particular, jts-core fue necesario para trabajar con las clases Geometry y para la creación de objetos de la clase Point.</p>
5. Para agregar los puntos al mapa usando Thymeleaf, fue en base a [\[5\]](#ref5) y a [\[6\]](#ref6).
6. Se crearon variables para el centroide de todos los puntos de interés y para definir el nivel necesario para el mapa[\[7\]](#ref7). 

## Referencias

<a id="ref1" href="https://medium.com/@hermanmaleiane/spring-boot-thymeleaf-leaflet-js-mapping-corona-virus-a8309c5a0b6d">Spring Boot+Thymeleaf+ Leaflet Js Mapping Corona Virus</a>
<a id="ref2" href="https://leafletjs.com/examples/quick-start/">Leaflet - A quickstart guide</a>
<a id="ref3" href="https://leafletjs.com/examples/extending/extending-2-layers.html">Extending Leaflet, new Layers</a>
<a id="ref4" href="https://stackoverflow.com/questions/25687816/setting-up-a-javascript-variable-from-spring-model-by-using-thymeleaf">Setting up a JavaScript variable from Spring model by using Thymeleaf</a>
<a id="ref5" href="https://stackoverflow.com/questions/45713934/jackson-deserialize-geojson-point-in-spring-boot">Jackson deserialize GeoJson Point in Spring Boot</a>
<a id="ref6" href="https://stackoverflow.com/questions/41352424/thymeleaf-foreach-loop-in-javascript">Thymeleaf forEach loop in JavaScript</a>
<a id="ref7" href="https://gis.stackexchange.com/questions/19632/how-to-calculate-the-optimal-zoom-level-to-display-two-or-more-points-on-a-map">How to calculate the optimal zoom-level to display two or more points on a map</a>