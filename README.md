# bibliotecas-backend

API REST para registrar bibliotecas y consultarlas junto con sus libros.

## Tecnologías

- Java 21
- Spring Boot 4.1.1 (Spring Web, Spring Data JPA, Bean Validation)
- PostgreSQL 16
- Maven Wrapper

## Requisitos

- JDK 21
- Docker con Docker Compose

## Ejecución

1. Crear el archivo de variables locales y definir una clave en `DB_PASSWORD`:

   Windows PowerShell:

   ```powershell
   Copy-Item .env.example .env
   ```

   Linux o macOS:

   ```bash
   cp .env.example .env
   ```

2. Iniciar PostgreSQL (puerto `5434`):

   ```bash
   docker compose up -d
   ```

3. Iniciar el backend:

   Windows PowerShell:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   Linux o macOS:

   ```bash
   ./mvnw spring-boot:run
   ```

4. Ejecutar las pruebas:

   Windows PowerShell:

   ```powershell
   .\mvnw.cmd test
   ```

   Linux o macOS:

   ```bash
   ./mvnw test
   ```

## Puerto

`http://localhost:8086`

| Método | Ruta           | Descripción                                   |
|--------|----------------|-----------------------------------------------|
| POST   | `/bibliotecas` | Registra una biblioteca (nombre, dirección y ciudad) |
| GET    | `/bibliotecas` | Consulta las bibliotecas con sus libros       |

Al iniciar se registran, sin duplicarse, la Biblioteca Central y la Biblioteca Norte con sus libros de ejemplo.
