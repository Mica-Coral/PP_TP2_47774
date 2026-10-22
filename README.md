# TP1 - Paradigmas de Programacion

Proyecto Java que modela un sistema de eventos universitarios, desarrollado en 4 ejercicios incrementales:

- Ejercicio 1: clase EventoUniversitario aislada, con constructor de copia y contador estatico.
- Ejercicio 2: se incorporan relaciones (Sala, Actividad, Estudiante, Inscripcion).
- Ejercicio 3: Actividad se vuelve abstracta; Charla y Taller como subclases concretas (herencia y polimorfismo).
- Ejercicio 4: mapa de memoria de ejecucion (ver mapa_memoria.png).

## Como ejecutar

1. Clonar este repositorio.
2. Abrir la carpeta en cualquier IDE con soporte de Java.
3. Compilar y ejecutar la clase App (contiene el metodo main).

## Archivos

- Clases del modelo: EventoUniversitario, Actividad (abstracta), Charla, Taller, Sala, Estudiante, Inscripcion.
- App.java: clase de arranque con el metodo main.
- mapa_memoria.png: mapa de memoria de ejecucion (Ejercicio 4).
- salida_consola.png: captura de una ejecucion del programa.