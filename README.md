# 📅 Turnos Médicos App

Una aplicación móvil desarrollada en Android para facilitar la reserva de turnos médicos con un listado predeterminado de doctores y horarios de atención.

## 🧩 Funcionalidades

- Registro de turnos médicos desde la app
- Visualización de todos los turnos reservados
- Lista de médicos cargada desde Firestore con:
    - Nombre
    - Especialidad
    - Horarios de atención predeterminados
- Interfaz simple e intuitiva
- Almacenamiento de datos en Firebase Firestore

## ⚙️ Tecnologías usadas

- Kotlin
- Android Jetpack
- Firebase Firestore (backend de base de datos)
- Gradle y Android Studio

## 🚀 Instalación y uso

### 🔹 Opción 1: Instalar directamente

Ya se encuentra incluido un archivo firmado (`.apk`) listo para usar en la carpeta `/release`. Puedes instalarlo directamente en un dispositivo Android sin necesidad de compilar el proyecto.

### 🔹 Opción 2: Compilar desde el código fuente

1. Clona el repositorio:
   ```bash
   https://github.com/daliss228/TurnosMedicosFinal.git
   
2. Abre el proyecto en Android Studio.

3. Ejecuta la aplicación en un emulador o dispositivo físico.

## 📦 Distribución

La aplicación se encuentra firmada y lista para instalarse directamente mediante el archivo .apk incluido.

Si deseas generar una nueva versión, puedes hacerlo desde Android Studio: Build > Generate Signed Bundle / APK

## 📝 Notas adicionales

Los datos de médicos están precargados en la colección medicos de Firestore.

No se valida disponibilidad de turnos (los horarios no se bloquean).

## 🎬 Video

https://drive.google.com/file/d/1IS6AxNyp5YwiemFThJo-p2upXP8qzrCW/view?usp=sharing
