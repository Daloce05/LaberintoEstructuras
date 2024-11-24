# Implementación del Algoritmo A* en Java para Resolver Laberintos

Este repositorio presenta la implementación del algoritmo A* (A-star), un algoritmo heurístico ampliamente utilizado en problemas de búsqueda de caminos. Nos permite identificar y trazar la ruta más corta desde un punto inicial (A) hasta un punto final (B) dentro de un laberinto. Este proyecto tiene como objetivo resolver el problema específico de encontrar el camino más eficiente en un laberinto y demostrar cómo este enfoque puede adaptarse a otros contextos que requieran optimización en la toma de decisiones.

## Tecnologías Utilizadas
- **Lenguaje de Programación:** Java
- **Entorno de Desarrollo:** IntelliJ IDEA Community Edition 2024.2.1
- **JDK Requerido:** 22.0.2

## Requisitos Previos
Para ejecutar correctamente el proyecto, asegúrate de tener instalado:
1. IntelliJ IDEA Community Edition 2024.2.1
2. JDK 22.0.2 y un SDK configurado por defecto.

## Archivo de Laberinto
El laberinto utilizado por el programa debe proporcionarse en un archivo de texto plano (`.txt`), el cual está incluido dentro del repositorio. 

- La ruta absoluta al archivo del laberinto debe especificarse directamente en el código fuente.
- Las anotaciones en el código indican dónde modificar la ruta para que coincida con la ubicación del archivo en tu sistema local.

Por ejemplo, en sistemas Windows:
```plaintext
"C:\\Users\\TuUsuario\\Ruta\\Proyecto\\laberinto.txt"
```
Nota: Asegúrate de usar doble barra inversa (`\\`) en las rutas para evitar errores.

## Pasos para Ejecutar y Probar el Código

### 1. Descargar el Repositorio
- Clona el repositorio o descarga el archivo comprimido (`.zip`) desde GitHub.
- Extrae los archivos en una ubicación accesible en tu sistema.

### 2. Configurar el Entorno de Desarrollo
- Abre IntelliJ IDEA y carga el proyecto.
- Configura el proyecto para utilizar el JDK 22.0.2 y un SDK compatible.

### 3. Proveer el Archivo del Laberinto
- Dentro del repositorio encontrarás un archivo de texto (`laberinto.txt`) con un laberinto de prueba.
- Modifica las líneas de código indicadas para especificar la ruta absoluta del archivo en tu sistema.

### 4. Ejecutar el Programa
- Ejecuta el proyecto desde IntelliJ IDEA.
- El programa procesará el archivo del laberinto y calculará la ruta más corta desde el punto A hasta el punto B utilizando el algoritmo A*.

## Personalización del Archivo de Laberinto
Puedes editar el archivo del laberinto según tus necesidades:
- Modifica el diseño, crea nuevos caminos o ajusta las dimensiones.
- El algoritmo buscará la solución más eficiente para conectar los puntos A y B independientemente de las modificaciones.

### Formato del Laberinto
- Cada celda del laberinto debe estar representada por un carácter:
  - `X` para paredes
  - Espacios en blanco para caminos

## Características Técnicas
1. **Flexibilidad:** El algoritmo A* combina búsqueda por coste y heurística, adaptándose a distintos diseños y configuraciones de laberintos.
2. **Escalabilidad:** Diseñado para manejar laberintos de diferentes tamaños y niveles de complejidad.

## Conclusión
Este proyecto es una herramienta práctica para aprender y experimentar con el algoritmo A*. Proporciona una implementación funcional y personalizable en Java, demostrando cómo resolver problemas complejos en inteligencia artificial, optimización y planificación de rutas. Con una configuración adecuada, cualquier usuario podrá ejecutar y personalizar este proyecto para comprender mejor la lógica y las aplicaciones del algoritmo A*.
