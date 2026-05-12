# 📄 Requerimientos del Sistema

### 1.1 Requerimientos funcionales

El sistema de TECHCUP FÚTBOL debe tener la capacidad de:

1. 

### 1.2 Requerimientos no funcionales

El sistema de TECHCUP FÚTBOL debe tener:

1. 
---

## 2. Diagramas de caso de uso

### 2.1 Requerimiento Funcional 1


### 2.2 Requerimiento Funcional 2

### 2.3 Requerimiento Funcional 3
(este quedo muy bien hecho, usar de ejemplo)

| Campo | Descripción |
|------|-------------|
| **ID** | RF-03 |
| **Nombre del requerimiento** | Creación de Torneo |
| **Descripción** | El sistema debe permitir al Organizador crear un torneo mediante un formulario con validaciones, mostrando un resumen en tiempo real de la información ingresada antes de confirmar el registro. |
| **Precondiciones** | El usuario debe haber iniciado sesión con rol de Organizador. |
| **Actor** | Organizador |
| **Flujo principal** | 1. El Organizador inicia sesión seleccionando el rol "Organizador" e ingresando su correo institucional y contraseña en la pantalla de inicio.<br>2. El sistema redirige al Organizador al "Dashboard Organizador", donde puede visualizar la lista "Mis Torneos" con los torneos finalizados, en progreso o activos, junto con el botón "+ Crear Torneo".<br>3. El Organizador hace clic en el botón "+ Crear Torneo".<br>4. El sistema abre el formulario "Crear Torneo" con la sección "Información General", donde el Organizador ingresa la "Fecha de Inicio" y la "Fecha de Fin". El sistema valida que la Fecha de Inicio sea mayor o igual a la fecha actual y que la Fecha de Fin sea mayor a la Fecha de Inicio.<br>5. El Organizador completa la sección "Configuración" ingresando: el "Costo por Equipos" (debe ser un valor numérico mayor a 1.000 pesos), el "Máx. Equipos" (cantidad máxima de equipos permitidos) y el "Jugadores / Equipo" (cantidad máxima de jugadores por equipo).<br>6. El sistema actualiza automáticamente la sección "Resumen" mostrando: en "Información General" la Fecha de inicio, Fecha de fin y el Estado inicial fijo "En proceso"; en "Configuración" el Costo por equipos, Equipos máx. y Jugadores / equipo.<br>7. El Organizador hace clic en el botón "+ CREAR TORNEO".<br>8. El sistema registra el torneo con estado "En proceso" y redirige al "Dashboard Organizador", donde el nuevo torneo aparece en la lista "Mis Torneos". |
| **Diagrama de caso de uso** | *imagen y link* |
| **Poscondiciones** | El torneo queda registrado en el sistema con estado "En proceso" y visible en la lista "Mis Torneos" del Dashboard Organizador. |
---
