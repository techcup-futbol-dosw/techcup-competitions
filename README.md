# TechCup Competitions Service

## Descripción

Microservicio encargado de gestionar las competencias del sistema TechCup. Administra los partidos, alineaciones y eventos que ocurren dentro de un torneo, incluyendo el registro de goles, tarjetas, auditoría de acciones y el cálculo automático de estadísticas y tabla de posiciones.

## Funcionalidades principales

- Creación y gestión de partidos (Match)
- Registro de eventos de partido (goles, tarjetas)
- Gestión de alineaciones por equipo (Lineup)
- Auditoría de acciones sobre partidos (MatchAudit)
- Control de fases del torneo (GROUP_STAGE, QUARTERFINALS, SEMIFINALS, FINAL)
- Cálculo automático de estadísticas y tabla de posiciones por equipo:
    - Partidos jugados, ganados, empatados y perdidos
    - Goles a favor y en contra
    - Diferencia de gol
    - Puntos acumulados

## Tecnologías

- Java 21
- Spring Boot
- PostgreSQL / MongoDB
- MapStruct
- OpenAPI / Swagger

## Ejecución local

```bash
mvn spring-boot:run
```