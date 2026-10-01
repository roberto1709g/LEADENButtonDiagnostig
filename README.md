# LEADEN1 Button Diagnostic

Proyecto Android mínimo para investigar qué eventos de botones multimedia entrega Android cuando las gafas `glases` están conectadas.

## Qué prueba

- MediaSessionCompat
- MEDIA_PLAY_PAUSE
- MEDIA_PLAY
- MEDIA_PAUSE
- MEDIA_NEXT
- MEDIA_PREVIOUS
- MEDIA_STOP
- VOLUME_UP / VOLUME_DOWN cuando Android los entrega a la aplicación
- ASSIST y otros KeyEvent conocidos

La aplicación NO usa Bluetooth GATT y NO necesita permisos de ubicación, cámara, contactos o Internet.

## Compilación sin Android Studio

1. Sube este proyecto a un repositorio GitHub.
2. Ve a Actions.
3. Ejecuta `Build LEADEN1 Button Diagnostic APK`.
4. Descarga el artefacto `LEADEN1-Button-Diagnostic`.
5. Dentro estará `app-debug.apk`.
6. Instálalo en el teléfono Android.

## Nota

Android puede consumir los eventos de volumen a nivel del sistema. Por eso que `VOLUME_UP` o `VOLUME_DOWN` no aparezcan en el historial no significa que las gafas no los estén enviando. La prueba principal es `MEDIA_PLAY_PAUSE`.
