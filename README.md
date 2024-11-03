Proyecto Vinilos

Este proyecto es una aplicación de Android desarrollada en Android Studio. La aplicación está configurada para ejecutarse en dispositivos con Android Nougat 7.0 o superior, cubriendo el 97.4% de los dispositivos.
Requisitos

    Android Studio (preferiblemente la última versión)
    JDK 11 o superior
    Versión mínima de Android: Nougat 7.0 (API nivel 24)

Configuración del Entorno

    Clona el repositorio en tu máquina local:

    bash

    git clone <URL-del-repositorio>
    cd <nombre-del-repositorio>

    Abre el proyecto en Android Studio:
        Desde Android Studio, selecciona File > Open y navega hasta la carpeta del proyecto.

    Descarga las dependencias:
        Android Studio descargará automáticamente las dependencias especificadas en los archivos build.gradle.

    Configura un Emulador (si prefieres no usar un dispositivo físico):
        Configura un dispositivo virtual con Android 7.0 (Nougat) o superior en el AVD Manager de Android Studio.

Ejecución de la Aplicación

Para ejecutar la aplicación en el emulador o en un dispositivo físico:

    Conecta un dispositivo físico o inicia un emulador.
    Selecciona el dispositivo o emulador desde la barra de opciones de ejecución en Android Studio.
    Haz clic en Run > Run 'app' o usa el atajo Shift + F10.

Pruebas con Espresso

Para ejecutar las pruebas de UI con Espresso:

    Desactiva las animaciones en el emulador o en el dispositivo físico. Esto es importante ya que las animaciones pueden interferir con la ejecución de las pruebas. Sigue estos pasos:
        En el emulador o dispositivo, ve a Configuración > Opciones de desarrollador.
        Configura las siguientes opciones en Animación:
            Escala de animación ventana: Animación desactivada
            Escala de transición: Animación desactivada
            Escala duración de animador: Animación desactivada

    Ejecuta las pruebas:
        Ve a la carpeta de pruebas (src/androidTest/java/com.miso.vinillos).
        Haz clic derecho en el archivo de prueba o en el paquete y selecciona Run 'Tests in...'.(VinilosProductInstrumentalTest/VinilosUserInstrumentalTest)
        También puedes ejecutar las pruebas desde la línea de comandos con:

        bash

        ./gradlew connectedAndroidTest

Las pruebas se ejecutarán en el emulador o en el dispositivo físico seleccionado y los resultados aparecerán en la ventana de "Run".
