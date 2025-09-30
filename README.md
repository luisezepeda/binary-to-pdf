# TxtToPdfConverter

Un proyecto Java 20 simple para convertir strings binarios contenidos en archivos TXT a documentos PDF.

## Descripción

Este proyecto permite leer un archivo de texto que contiene una cadena binaria (secuencia de 0s y 1s) y convertirla en un archivo PDF con el contenido decodificado.

## Características

- ✅ Lectura de archivos TXT con contenido binario
- ✅ Validación de cadenas binarias
- ✅ Conversión de binario a texto legible
- ✅ Generación de PDF usando iText 7
- ✅ Manejo de caracteres no imprimibles
- ✅ Tests unitarios con JUnit 5

## Requisitos

- Java 20 o superior
- Maven 3.6+

## Instalación

1. Clona el repositorio:
```bash
git clone <repository-url>
cd cli
```

2. Compila el proyecto:
```bash
mvn clean compile
```

3. Ejecuta los tests:
```bash
mvn test
```

4. Genera el JAR ejecutable:
```bash
mvn package
```

## Uso

### Desde línea de comandos

```bash
# Usando el JAR generado
java -jar target/txt-to-pdf-converter-1.0.0.jar input.txt output.pdf

# Usando Maven
mvn exec:java -Dexec.mainClass="com.medismartsoft.converter.TxtToPdfConverter" -Dexec.args="input.txt output.pdf"
```

### Ejemplo de archivo de entrada

Crea un archivo `input.txt` con contenido binario:

```
0100100001100101011011000110110001101111
```

Este ejemplo contiene la palabra "Hello" en binario.

### Ejemplo con espacios

El programa también acepta cadenas binarias con espacios:

```
01001000 01100101 01101100 01101100 01101111
```

## Estructura del Proyecto

```
cli/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── medismartsoft/
│   │               └── converter/
│   │                   └── TxtToPdfConverter.java
│   └── test/
│       └── java/
│           └── com/
│               └── medismartsoft/
│                   └── converter/
│                       └── TxtToPdfConverterTest.java
├── pom.xml
└── README.md
```

## Dependencias

- **iText 7**: Para la generación de archivos PDF
- **JUnit 5**: Para tests unitarios

## Funcionalidades

### Validación de Entrada
- Verifica que el archivo de entrada exista
- Valida que el contenido sea una cadena binaria válida (solo 0s y 1s)
- Maneja archivos vacíos con mensajes de error apropiados

### Conversión Binaria
- Convierte secuencias de 8 bits a caracteres ASCII
- Maneja caracteres no imprimibles mostrando su representación binaria
- Padea automáticamente cadenas que no son múltiplos de 8

### Generación de PDF
- Crea documentos PDF con formato profesional
- Incluye título y referencia al archivo fuente
- Usa fuentes legibles y formato apropiado

## Ejemplos de Uso

### Convertir texto simple
```bash
# Crear archivo con "Hola" en binario
echo "01001000011011110110110001100001" > mensaje.txt

# Convertir a PDF
java -jar target/txt-to-pdf-converter-1.0.0.jar mensaje.txt salida.pdf
```

### Convertir con espacios
```bash
# Crear archivo con espacios para mejor legibilidad
echo "01001000 01101111 01101100 01100001" > mensaje_espacios.txt

# Convertir a PDF
java -jar target/txt-to-pdf-converter-1.0.0.jar mensaje_espacios.txt salida_espacios.pdf
```

## Tests

Ejecutar todos los tests:
```bash
mvn test
```

Los tests incluyen:
- Conversión exitosa de binario a PDF
- Manejo de cadenas con espacios
- Validación de archivos inexistentes
- Validación de archivos vacíos
- Validación de contenido no binario

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Parte del Ecosistema MediSmartSoft

Este conversor forma parte del ecosistema de herramientas CLI de MediSmartSoft para el procesamiento de datos médicos y administrativos.