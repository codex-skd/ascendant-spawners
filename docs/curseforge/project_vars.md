# CurseForge — Variables del proyecto

> Proyecto **aún no creado** en CurseForge. Esta tabla es la información para el alta manual (formulario "Create Project"). Rellenar `project_id` en cuanto se cree.

## Datos para el alta manual (formulario "Create Project")

| Campo del formulario | Valor a usar |
|---|---|
| Project Name | `Ascendant Spawners` |
| Slug/URL | `ascendant-spawners` (verificar disponibilidad; si está ocupado, `ascendant-spawners-mod`) |
| Summary (resumen corto) | `A NeoForge addon for Ascendant Equipment: silk-touch mob spawners and modify their stats (delay, range, spawn count, redstone control, and more) via crafting recipes.` |
| Category | Addons / Ores and Resources (según disponibilidad — mismo tipo usado por el ApothicSpawners original) |
| License | MIT (mod propio, port de código MIT de ApothicSpawners — ver `LICENSE` en la raíz del repo) |
| Game | Minecraft |
| Mod Loader | NeoForge |
| Client/Server | Both |
| Description | Contenido de `project_description.md` (HTML) |
| Relations — required dependency | Common Toolkit, Ascendant Equipment (una vez ambos tengan proyecto/JAR publicado) |
| Issue tracker | URL del repo (GitHub, tras el mirror) |
| Source URL | URL del repo (GitHub, tras el mirror) |

## Proyecto

| Variable | Valor |
|----------|-------|
| `curseforge_project_id` | *(pendiente — rellenar tras crear el proyecto)* |
| `mod_id` | `ascendant_spawners` |
| `display_name` | `Ascendant Spawners` |

## Tokens

| API | Token | Uso |
|-----|-------|-----|
| Upload | `ee776b0a-ee95-4850-b554-06be02a8657f` | Subir archivos JAR (token de cuenta, compartido entre proyectos) |
| Core (GET) | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` | Consultar datos del mod |

Autenticación Upload: cabecera `X-Api-Token`
Autenticación Core: cabecera `x-api-key`

## Versión actual

| Variable | Valor |
|----------|-------|
| `minecraft_version` | `26.2` |
| `framework` | `neoforge` |
| `java_version` | `25` |
| `environment` | `Client`, `Server` |

## Rama

```
minecraft/26.2/neoforge-26.2.0.32-beta/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo (primera beta): `26.2-neoforge-beta.1`

## Nota

La **primera subida a CurseForge se hace manual** (proyecto recién creado, sin archivos previos que verificar por API). A partir de la segunda subida se puede usar el script `codex-docs/scripts/curseforge-upload.ps1`, que lee `project_id`, `api_token` y `game_versions` de este archivo (rellenar tras el alta) y `mod_id`, `mod_name`, `minecraft_version`, `mod_version` de `gradle.properties`.
