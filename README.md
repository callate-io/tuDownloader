# tuDownloader
Herramienta para descargar fotos y mensajes privados de la red social Tuenti.

### Actualización (16 Mar 2016)
Tuenti desactivó el acceso a los álbumes de fotos de los usuarios vía web recientemente. tuDownloader, que se basaba en simular una conexión web para obtener y descargar las fotos, ha dejado de ser funcional. Para poder descargar las fotos, la única alternativa es usar la herramienta que Tuenti ha puesto en disposición de los usuarios. Los mensajes privados pueden seguir bajándose usando tuDownloader.

## Requisitos
- Java 7 o superior
- Maven

## Uso
Para iniciar el programa:

    mvn exec:java
    
Para crear un .jar ejecutable con todas las dependencias:

    mvn assembly:single


## Creadores
**Jose Toro**
- https://twitter.com/joseotoro
- https://github.com/joseotoro

**Nur Álvarez**
- https://twitter.com/subnurmality
- https://github.com/nur-ag


## Copyright y licencia
Copyright 2016 [callate.io](http://callate.io). Código bajo [licencia MIT](https://github.com/callate-io/tuDownloader/blob/master/LICENSE).
