# TECHCUP FÚTBOL

> [!IMPORTANT]
> Este repositorio contiene el *BackEnd* para el servicio de **techcup-competitions**

> Para informacion general del proyecto consulta el [README general de la organización](https://github.com/techcup-futbol-dosw).

---

## Tabla de contenido

- [Integrantes](#integrantes)
- [Descripción general](#descripción-general)
- [Requerimientos](#requerimientos)
- [Stack tecnológico](#stack-tecnológico)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Configuración local](#configuración-local)
- [Modelación y diagramas](#modelación-y-diagramas)
- [Funcionalidades del servicio](#funcionalidades-del-servicio)
- [API y Endpoints](#api-y-endpoints)
- [Pruebas y calidad](#pruebas-y-calidad)
- [CI/CD](#cicd)

---

## Integrantes

<!--
  EDITAR: Completa con los datos reales de tu equipo.
-->

* **Product Owner:** [Nombre Apellido](https://github.com/usuario) → [correo@mail.escuelaing.edu.co](mailto:correo@mail.escuelaing.edu.co)
* **Líder técnico:** [Nombre Apellido](https://github.com/usuario) → [correo@mail.escuelaing.edu.co](mailto:correo@mail.escuelaing.edu.co)
* **Analista funcional:** [Nombre Apellido](https://github.com/usuario) → [correo@mail.escuelaing.edu.co](mailto:correo@mail.escuelaing.edu.co)
* **Analista funcional:** [Nombre Apellido](https://github.com/usuario) → [correo@mail.escuelaing.edu.co](mailto:correo@mail.escuelaing.edu.co)
* **Desarrollador:** [Nombre Apellido](https://github.com/usuario) → [correo@mail.escuelaing.edu.co](mailto:correo@mail.escuelaing.edu.co)
* **Desarrollador:** [Nombre Apellido](https://github.com/usuario) → [correo@mail.escuelaing.edu.co](mailto:correo@mail.escuelaing.edu.co)

---

## Descripción general

> [!NOTE]
> **TechCup Competitions** es el microservicio encargado de gestionar el desarrollo del torneo de fútbol TechCup. Cubre el ciclo de vida completo de los partidos, la administración de alineaciones, el registro de eventos en juego (goles y tarjetas), la auditoría de cambios y el cálculo automático de la tabla de posiciones. Utiliza almacenamiento híbrido: PostgreSQL para datos estructurados y relaciones, y MongoDB preparado para documentos o datos no estructurados.

### Funcionalidades del servicio

| Funcionalidad | Descripción | Roles permitidos |
|---------------|-------------|-----------------|
| Gestión de partidos | Creación y control del ciclo de vida del partido (Programado, En Curso, Finalizado, Cancelado) | Árbitro / Organizador |
| Alineaciones | Administración de la nómina titular y suplente por equipo en cada partido con validaciones de tamaño (11 titulares + arquero) | Capitán |
| Eventos de juego | Registro de goles (incluyendo autogoles y asistencias) y tarjetas (amarilla/roja) en tiempo real | Árbitro |
| Tabla de posiciones | Cálculo automático de puntos, diferencia de goles y estadísticas de equipos al finalizar un partido | Jugador / Capitán / Organizador / Árbitro / Admin |
| Auditoría de partidos | Trazabilidad completa de acciones y cambios realizados sobre los partidos | Admin |

---

### Requerimientos

> Los requerimientos funcionales y no funcionales de este servicio se encuentran documentados en [`src/main/resources/docs/requirements/requirement.md`](src/main/resources/docs/requirements/requirement.md)

---

## Stack tecnológico

### Backend

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.4.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct_1.6.3-009688?style=for-the-badge)
![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge)

### Base de datos

![PostgreSQL](https://img.shields.io/badge/PostgreSQL_15-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB_6-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![H2](https://img.shields.io/badge/H2_(tests)-4479A1?style=for-the-badge)

### Testing y calidad

![JUnit](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=java&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage_80%25-BB0A30?style=for-the-badge)
![SonarQube](https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white)

### Herramientas y DevOps

![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

---

## Estructura del proyecto

```
📦 techcup-competitions/
├── 📂 .github/
│   └── 📂 workflows/
│       └── 📄 pipeline.yml             # Pipeline de CI/CD (build, test, quality, deploy)
├── 📂 .mvn/                            # Maven Wrapper
├── 📂 src/
│   ├── 📂 main/
│   │   ├── 📂 java/
│   │   │   └── 📂 edu/eci/dosw/competitions/
│   │   │       ├── 📄 App.java
│   │   │       ├── 📂 config/          # SecurityConfig, CorsConfig, JWT (filter, service, handlers)
│   │   │       ├── 📂 controller/      # MatchController, LineupController, HomeController
│   │   │       ├── 📂 dtos/            # Data Transfer Objects (request/response)
│   │   │       ├── 📂 entity/          # Entidades JPA: Match, Lineup, Goal, Card, Standings, MatchAudit
│   │   │       ├── 📂 mapper/          # MapStruct mappers
│   │   │       ├── 📂 model/           # Modelos de patrón de estado
│   │   │       ├── 📂 repository/      # Spring Data JPA repositories
│   │   │       ├── 📂 service/         # Lógica de negocio (MatchService, LineupService)
│   │   │       └── 📂 document/        # Documentos MongoDB (preparado)
│   │   └── 📂 resources/
│   │       ├── 📄 application.properties
│   │       └── 📂 docs/
│   │           ├── 📂 uml/
│   │           ├── 📂 images/
│   │           └── 📂 requirements/
│   └── 📂 test/
│       └── 📂 java/                    # Pruebas unitarias JUnit 5 + Mockito + MockMvc
├── 📄 .gitignore
├── 📄 Dockerfile
├── 📄 pom.xml
└── 📄 README.md
```

---

## Configuración local

### 1. Clonar el repositorio

```bash
git clone https://github.com/techcup-futbol-dosw/techcup-competitions.git
cd techcup-competitions
```

### 2. Compilar el proyecto

```bash
mvn clean install
```

> [!NOTE]
> Este comando descarga dependencias y compila el proyecto completo.

### 3. Configurar variables de entorno

El servicio usa sustitución de variables de entorno en `application.properties`. Define las siguientes variables en tu entorno local o en un archivo `.env`:

```properties
# Base de datos PostgreSQL
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/techcup
SPRING_DATASOURCE_USERNAME=tu_usuario
SPRING_DATASOURCE_PASSWORD=tu_password

# Base de datos MongoDB
SPRING_MONGODB_URI=mongodb://localhost:27017/techcup

# Seguridad JWT
JWT_SECRET=tu_clave_secreta_base64
```

> [!WARNING]
> Nunca subas credenciales reales al repositorio. El archivo `application.properties` usa variables de entorno con valores por defecto solo para desarrollo local.

### 4. Ejecutar en desarrollo

```bash
mvn spring-boot:run
```

> [!TIP]
> El servicio se ejecutará en `http://localhost:8081`. Swagger en `http://localhost:8081/swagger-ui.html`.

### 5. Ejecutar en modo empaquetado

```bash
mvn clean package
java -jar target/techcup-competitions-0.0.1-SNAPSHOT.jar
```

### Ejecución con Docker

```bash
docker build -t techcup-competitions .

docker run -d \
  --name techcup-competitions \
  -p 8083:8083 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/techcup \
  -e SPRING_DATASOURCE_USERNAME=usuario \
  -e SPRING_DATASOURCE_PASSWORD=contrasena \
  -e SPRING_MONGODB_URI=mongodb://host:27017/techcup \
  -e JWT_SECRET=tu_clave_secreta \
  techcup-competitions
```

---

## Modelación y diagramas

### Diagrama de contenedores

![ContainerDiagram](src/main/resources/docs/uml/architecturalDesigns/containerDiagram.png)

> El servicio `techcup-competitions` recibe peticiones desde el API Gateway y persiste datos en PostgreSQL (entidades estructuradas: partidos, alineaciones, eventos, posiciones) y MongoDB (documentos de auditoría y datos no estructurados).

### Diagrama de clases

![ClassDiagram](src/main/resources/docs/uml/classDiagram.png)

> Las principales entidades son `Match` (con patrón de estado: SCHEDULED → IN_PROGRESS → FINISHED/CANCELLED), `Lineup`, `MatchEvent` (clase abstracta con herencia a `Goal` y `Card`), `Standings` y `MatchAudit`.

### Diagrama Entidad-Relación

![DatabaseDiagram](src/main/resources/docs/uml/dataBaseDiagram.png)

> El modelo relacional central es `Match` relacionado con `Lineup` (uno por equipo por partido), `MatchEvent` (polimórfico via JOIN: `Goal` y `Card`), `Standings` (por equipo y torneo) y `MatchAudit` (log de cambios).

---

## API y Endpoints

```
http://localhost:8081/swagger-ui.html
```

![Swagger UI](src/main/resources/docs/images/swaggerUi.png)

### Partidos (`/api/matches`)

| Método | Endpoint | Descripción | Roles |
|--------|----------|-------------|-------|
| POST | `/api/matches` | Crear nuevo partido | Organizador / Árbitro |
| PUT | `/api/matches/{id}` | Actualizar datos del partido | Organizador / Árbitro |
| DELETE | `/api/matches/{id}` | Eliminar partido (solo si está SCHEDULED) | Organizador |
| PUT | `/api/matches/{id}/start` | Iniciar partido (cambia estado a IN_PROGRESS) | Árbitro |
| PUT | `/api/matches/{id}/finish` | Finalizar partido (cambia estado a FINISHED) | Árbitro |
| POST | `/api/matches/goals` | Registrar evento de gol | Árbitro |
| POST | `/api/matches/cards` | Registrar evento de tarjeta | Árbitro |
| GET | `/api/matches/{id}/events` | Obtener todos los eventos del partido | Todos |
| GET | `/api/matches/standings/{tournamentId}` | Obtener tabla de posiciones del torneo | Todos |
| GET | `/api/matches/tournament/{tournamentId}` | Obtener todos los partidos del torneo | Todos |

### Alineaciones (`/api/lineups`)

| Método | Endpoint | Descripción | Roles |
|--------|----------|-------------|-------|
| POST | `/api/lineups` | Crear nueva alineación para un partido | Capitán |
| PUT | `/api/lineups/{id}` | Actualizar alineación (antes de confirmar) | Capitán |
| PUT | `/api/lineups/{id}/confirm` | Confirmar alineación (bloquea cambios) | Capitán |
| GET | `/api/lineups/match/{matchId}/team/{teamId}` | Obtener alineación de un equipo en un partido | Todos |

---

## Pruebas y calidad

### Cobertura (JaCoCo)

![JaCoCo Report](src/main/resources/docs/images/jacocoReport.png)

```bash
mvn test
mvn clean test jacoco:report
# Reporte: target/site/jacoco/index.html
```

> [!NOTE]
> El proyecto exige un mínimo del **80% de cobertura** configurado en el plugin JaCoCo. El build falla si no se alcanza.

### Calidad (SonarQube)

![SonarQube](src/main/resources/docs/images/sonarQubeAnalysis.png)

```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=<PROJECT_KEY> \
  -Dsonar.host.url=<SONAR_HOST_URL> \
  -Dsonar.token=<SONAR_TOKEN>
```

### Pruebas de integración (Postman)

![Postman Tests](src/main/resources/docs/images/postmanTests.png)

---

## CI/CD

El pipeline está definido en `.github/workflows/pipeline.yml` y se activa en pushes a `main`, `develop` y ramas `feature/*`, así como en Pull Requests a `main`. Consta de 4 jobs secuenciales:

1. **Build** — Compila el proyecto con JDK 21 (Temurin)
2. **Test** — Ejecuta la suite de pruebas
3. **Quality** — Genera reporte JaCoCo y análisis SonarQube (requiere secret `SONAR_TOKEN`)
4. **Deploy** — Empaqueta el JAR y despliega en Azure Web Apps (requiere secret `AZURE_WEBAPP_PUBLISH_PROFILE`)

### Entorno de despliegue

| Campo | Valor |
|-------|-------|
| Plataforma | Azure Web Apps |
| URL del servicio | [https://techcupcompetitions.azurewebsites.net](https://techcupcompetitions.azurewebsites.net) |
| Swagger desplegado | [https://techcupcompetitions.azurewebsites.net/swagger-ui.html](https://techcupcompetitions.azurewebsites.net/swagger-ui.html) |
| Última versión | ![Deploy](https://github.com/techcup-futbol-dosw/techcup-competitions/actions/workflows/pipeline.yml/badge.svg) |
