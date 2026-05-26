# TECHCUP FÚTBOL

> [!IMPORTANT]
> Este repositorio contiene el *BackEnd* para el servicio de **Competencia**

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

* **Product Owner:** [KEVYN DANIEL FORERO GONZALEZ](https://github.com/kevyn1005) → [kevyn.forero-g@mail.escuelaing.edu.co](mailto:kevyn.forero-g@mail.escuelaing.edu.co)
* **Líder técnico:** [MARÍA JULIANA RODRÍGUEZ CAICEDO](https://github.com/JuliRodC) → [maria.rcaicedo@mail.escuelaing.edu.co](mailto:maria.rcaicedo@mail.escuelaing.edu.co)
* **Analista funcional:** [DIEGO ALEJANDRO MONTES BONILLA](https://github.com/Banettchi) → [diego.montes-b@mail.escuelaing.edu.co](mailto:diego.montes-b@mail.escuelaing.edu.co)
* **Desarrollador:** [JUAN ANGEL SALAS GÓMEZ](https://github.com/Juanangels1403) → [juan.salas-g@mail.escuelaing.edu.co](mailto:juan.salas-g@mail.escuelaing.edu.co)
---

## Descripción general

> [!NOTE]
> Gestiona el desarrollo deportivo del torneo

### Funcionalidades del servicio

| Funcionalidad | Descripción | Roles permitidos |
|---------------|-------------|-----------------|
| Registro de partidos | El organizador podrá crear partidos indicando: fecha y hora, cancha y equipos. Igualmente, asignará al árbitro. | Organizador |
| Eliminación de partidos | El organizador podrá eliminar un partido siempre y cuando la fecha en la que está programado sea posterior a la fecha actual. | Organizador |
| Actualización de partidos | El organizador podrá actualizar un partido siempre y cuando la fecha en la que está programado sea posterior a la fecha actual. Solo se podrá actualizar: fecha, hora, cancha y/o árbitro. | Organizador |
| Alineaciones | El capitán podrá organizar la formación antes de cada partido: seleccionar titulares y reservas, elegir formación (4-3-3, 1-4-4-2, 1-4-2-3-1, 1-5-3-2/1-3-5-2) y ubicar jugadores visualmente en la cancha. Las alineaciones podrán ser consultadas por jugadores y capitanes del mismo equipo. | Capitán (gestión) / Jugador, Capitán (consulta) |
| Resultados | El organizador del torneo registrará: marcadores, goles (relacionando el jugador que marcó), tarjetas amarillas y tarjetas rojas. | Organizador |
| Consultas | El árbitro podrá consultar: partidos asignados, fecha y hora de los partidos, cancha del partido y equipos que jugarán. | Árbitro |
| Auditoría | Registrar las acciones de creación, actualización o eliminación de un partido. | Admin |

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
│       └── 📄 pipeline.yml                                   # CI/CD: build, test, SonarQube y deploy a Azure
├── 📂 docs/
│   └── 📂 images/
│       ├── 🖼️ cobertura.png                                  # Captura del reporte de cobertura JaCoCo
│       ├── 🖼️ postman_create_match.png                       # Captura Postman: crear partido
│       ├── 🖼️ postman_register_goal.png                      # Captura Postman: registrar gol
│       ├── 🖼️ postman_standings.png                          # Captura Postman: tabla de posiciones
│       ├── 🖼️ swagger1.png                                   # Captura Swagger UI (vista 1)
│       └── 🖼️ swagger2.png                                   # Captura Swagger UI (vista 2)
├── 📂 src/
│   ├── 📂 main/
│   │   ├── 📂 java/edu/eci/dosw/competitions/
│   │   │   ├── 📄 App.java                                   # Punto de entrada Spring Boot (@SpringBootApplication)
│   │   │   ├── 📂 config/
│   │   │   │   ├── 📄 AccessDeniedHandlerImpl.java           # Manejador HTTP 403 – acceso denegado
│   │   │   │   ├── 📄 AuthenticationEntryPointImpl.java      # Manejador HTTP 401 – no autenticado
│   │   │   │   ├── 📄 CorsConfig.java                        # Configuración CORS para el frontend
│   │   │   │   ├── 📄 JwtAuthenticationFilter.java           # Filtro que valida el token JWT en cada request
│   │   │   │   ├── 📄 JwtService.java                        # Generación, firma y validación de tokens JWT
│   │   │   │   ├── 📄 MatchAccessPolicy.java                 # Reglas de autorización por rol (Organizador, Árbitro, Capitán)
│   │   │   │   └── 📄 SecurityConfig.java                    # Cadena de filtros Spring Security y rutas protegidas
│   │   │   ├── 📂 controller/
│   │   │   │   ├── 📄 HomeController.java                    # GET / → health-check del servicio
│   │   │   │   ├── 📄 LineupController.java                  # Endpoints REST /api/lineups
│   │   │   │   └── 📄 MatchController.java                   # Endpoints REST /api/matches
│   │   │   ├── 📂 document/                                  # (reservado) Documentos MongoDB futuros
│   │   │   ├── 📂 dtos/
│   │   │   │   ├── 📄 CreateLineupDTO.java                   # Request: crear alineación
│   │   │   │   ├── 📄 CreateMatchDTO.java                    # Request: crear partido
│   │   │   │   ├── 📄 LineupResponseDTO.java                 # Response: datos de una alineación
│   │   │   │   ├── 📄 MatchEventResponseDTO.java             # Response: evento de partido (gol / tarjeta)
│   │   │   │   ├── 📄 MatchResponseDTO.java                  # Response: datos de un partido
│   │   │   │   ├── 📄 RegisterCardDTO.java                   # Request: registrar tarjeta (amarilla / roja)
│   │   │   │   ├── 📄 RegisterGoalDTO.java                   # Request: registrar gol
│   │   │   │   ├── 📄 StandingsResponseDTO.java              # Response: fila de la tabla de posiciones
│   │   │   │   ├── 📄 UpdateLineupDTO.java                   # Request: actualizar alineación
│   │   │   │   └── 📄 UpdateMatchDTO.java                    # Request: actualizar partido
│   │   │   ├── 📂 entity/
│   │   │   │   ├── 📄 Card.java                              # Entidad JPA: tarjeta (hereda MatchEvent)
│   │   │   │   ├── 📄 CardType.java                          # Enum: YELLOW / RED
│   │   │   │   ├── 📄 Goal.java                              # Entidad JPA: gol (hereda MatchEvent)
│   │   │   │   ├── 📄 Lineup.java                            # Entidad JPA: alineación de un equipo por partido
│   │   │   │   ├── 📄 Match.java                             # Entidad JPA principal: partido
│   │   │   │   ├── 📄 MatchAudit.java                        # Documento MongoDB: log de auditoría de partidos
│   │   │   │   ├── 📄 MatchAuditAction.java                  # Enum: CREATE / UPDATE / DELETE
│   │   │   │   ├── 📄 MatchBuilder.java                      # Builder para construir objetos Match con validaciones
│   │   │   │   ├── 📄 MatchEvent.java                        # Clase abstracta JPA: evento de partido (herencia JOINED)
│   │   │   │   ├── 📄 MatchPhase.java                        # Enum: FIRST_HALF / SECOND_HALF / EXTRA_TIME
│   │   │   │   ├── 📄 MatchStatus.java                       # Enum: SCHEDULED → IN_PROGRESS → FINISHED / CANCELLED
│   │   │   │   └── 📄 Standings.java                         # Entidad JPA: posición de un equipo en el torneo
│   │   │   ├── 📂 exceptions/                                # (reservado) Excepciones personalizadas futuras
│   │   │   ├── 📂 mapper/
│   │   │   │   ├── 📄 LineupMapper.java                      # MapStruct: Lineup ↔ LineupResponseDTO
│   │   │   │   ├── 📄 MatchEventMapper.java                  # MapStruct: MatchEvent ↔ MatchEventResponseDTO
│   │   │   │   ├── 📄 MatchMapper.java                       # MapStruct: Match ↔ MatchResponseDTO / CreateMatchDTO
│   │   │   │   └── 📄 StandingsMapper.java                   # MapStruct: Standings ↔ StandingsResponseDTO
│   │   │   ├── 📂 model/
│   │   │   │   ├── 📄 CancelledMatch.java                    # Estado concreto: partido cancelado
│   │   │   │   ├── 📄 ConfirmedLineup.java                   # Estado concreto: alineación confirmada (bloqueada)
│   │   │   │   ├── 📄 FinishedMatch.java                     # Estado concreto: partido finalizado
│   │   │   │   ├── 📄 InProgressMatch.java                   # Estado concreto: partido en curso
│   │   │   │   ├── 📄 LineupModel.java                       # Contexto del patrón State para Lineup
│   │   │   │   ├── 📄 LineupState.java                       # Interfaz State para alineaciones
│   │   │   │   ├── 📄 MatchModel.java                        # Contexto del patrón State para Match
│   │   │   │   ├── 📄 MatchState.java                        # Interfaz State para partidos
│   │   │   │   ├── 📄 MatchStateFactory.java                 # Factory: crea el estado correcto según MatchStatus
│   │   │   │   ├── 📄 ScheduledMatch.java                    # Estado concreto: partido programado
│   │   │   │   └── 📄 UnconfirmedLineup.java                 # Estado concreto: alineación pendiente de confirmación
│   │   │   ├── 📂 repository/
│   │   │   │   ├── 📄 LineupRepository.java                  # JPA repo: alineaciones (por partido y equipo)
│   │   │   │   ├── 📄 MatchAuditRepository.java              # MongoDB repo: registros de auditoría
│   │   │   │   ├── 📄 MatchEventRepository.java              # JPA repo: eventos de partido (goles y tarjetas)
│   │   │   │   ├── 📄 MatchRepository.java                   # JPA repo: partidos (por torneo, estado, fecha)
│   │   │   │   └── 📄 StandingsRepository.java               # JPA repo: tabla de posiciones por torneo
│   │   │   └── 📂 service/
│   │   │       ├── 📄 LineupService.java                     # Lógica: crear, actualizar y confirmar alineaciones
│   │   │       └── 📄 MatchService.java                      # Lógica: CRUD de partidos, eventos, standings y auditoría
│   │   └── 📂 resources/
│   │       ├── 📄 application.properties                     # Config Spring: BD, JWT, servidor (puerto 8081)
│   │       └── 📂 docs/
│   │           ├── 📂 images/                                # Capturas para el README (Swagger, JaCoCo, Postman)
│   │           ├── 📂 requirements/
│   │           │   └── 📄 requirement.md                     # Requerimientos funcionales y no funcionales
│   │           └── 📂 uml/                                   # Diagramas de arquitectura (contenedores, clases, ER)
│   └── 📂 test/
│       ├── 📂 java/edu/eci/dosw/competitions/
│       │   ├── 📄 AppTest.java                               # Smoke test: carga del contexto Spring
│       │   ├── 📂 config/
│       │   │   ├── 📄 JwtServiceTest.java                    # Tests unitarios: generación y validación de JWT
│       │   │   ├── 📄 MatchAccessPolicyTest.java             # Tests unitarios: reglas de acceso por rol
│       │   │   └── 📄 SecurityConfigTest.java                # Tests de seguridad: rutas públicas y protegidas
│       │   ├── 📂 controller/
│       │   │   ├── 📄 GlobalExceptionHandler.java            # Manejador global de excepciones para MockMvc
│       │   │   ├── 📄 LineupControllerIntegrationTest.java   # Tests de integración: /api/lineups con H2
│       │   │   ├── 📄 LineupControllerTest.java              # Tests unitarios MockMvc: LineupController
│       │   │   ├── 📄 MatchControllerIntegrationTest.java    # Tests de integración: /api/matches con H2
│       │   │   └── 📄 MatchControllerTest.java               # Tests unitarios MockMvc: MatchController
│       │   ├── 📂 dtos/
│       │   │   └── 📄 DTOsTest.java                          # Tests de validación de DTOs (Bean Validation)
│       │   ├── 📂 entity/
│       │   │   ├── 📄 LineupTest.java                        # Tests de la entidad Lineup (builders, relaciones)
│       │   │   ├── 📄 MatchAuditTest.java                    # Tests de la entidad MatchAudit (documento MongoDB)
│       │   │   ├── 📄 MatchBuilderTest.java                  # Tests del Builder de Match
│       │   │   ├── 📄 MatchEventTest.java                    # Tests de Goal y Card (herencia MatchEvent)
│       │   │   ├── 📄 MatchTest.java                         # Tests de la entidad Match (estados, relaciones)
│       │   │   └── 📄 StandingsTest.java                     # Tests de la entidad Standings
│       │   ├── 📂 mapper/
│       │   │   ├── 📄 LineupMapperTest.java                  # Tests del mapper Lineup ↔ DTO
│       │   │   └── 📄 MatchMapperTest.java                   # Tests del mapper Match ↔ DTO
│       │   ├── 📂 model/
│       │   │   ├── 📄 LineupModelTest.java                   # Tests del patrón State para Lineup
│       │   │   └── 📄 MatchModelTest.java                    # Tests del patrón State para Match (transiciones)
│       │   ├── 📂 repository/
│       │   │   ├── 📄 LineupRepositoryTest.java              # Tests de repositorio Lineup con H2
│       │   │   ├── 📄 MatchAuditRepositoryTest.java          # Tests de repositorio MatchAudit (Mongo embebido)
│       │   │   ├── 📄 MatchEventRepositoryTest.java          # Tests de repositorio MatchEvent con H2
│       │   │   ├── 📄 MatchRepositoryTest.java               # Tests de repositorio Match con H2
│       │   │   └── 📄 StandingsRepositoryTest.java           # Tests de repositorio Standings con H2
│       │   └── 📂 service/
│       │       ├── 📄 LineupServiceTest.java                 # Tests unitarios: LineupService con Mockito
│       │       └── 📄 MatchServiceTest.java                  # Tests unitarios: MatchService con Mockito
│       └── 📂 resources/
│           └── 📄 application.properties                    # Config de tests: H2 en memoria y JWT de prueba
├── 📄 .dockerignore                                          # Archivos excluidos del contexto Docker
├── 📄 .gitignore                                             # Archivos excluidos de Git
├── 📄 docker-compose.yml                                     # Orquestación local: app + PostgreSQL + MongoDB
├── 📄 Dockerfile                                             # Imagen Docker del servicio (multi-stage build)
├── 📄 pom.xml                                                # Dependencias Maven y plugins (Spring Boot, JaCoCo, Sonar)
└── 📄 README.md                                              # Documentación principal del servicio
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

### Partidos

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

### Alineaciones

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

### Entorno de despliegue

| Campo | Valor |
|-------|-------|
| Plataforma | Azure Web Apps |
| URL del servicio | [https://techcupcompetitions.azurewebsites.net](https://techcupcompetitions.azurewebsites.net) |
| Swagger desplegado | [https://techcupcompetitions.azurewebsites.net/swagger-ui.html](https://techcupcompetitions.azurewebsites.net/swagger-ui.html) |
| Última versión | ![Deploy](https://github.com/techcup-futbol-dosw/techcup-competitions/actions/workflows/pipeline.yml/badge.svg) |
