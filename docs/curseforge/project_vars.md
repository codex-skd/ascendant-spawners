# CurseForge — Variables del proyecto

> **Rama 1.21.1**: `game_versions = 9638, 9639, 11779, 10150` (Client, Server, **1.21.1** id `11779`, NeoForge). `release_type = beta`. JAR `ascendant_spawners-1.21.1-neoforge-21.1.249-<version>.jar`. Tag `1.21.1-neoforge-<version>`. Proyecto CurseForge compartido con la rama 26.2 (`1638524`).

## Proyecto

| Variable | Valor |
|----------|-------|
| `curseforge_project_id` | `1638524` |
| `mod_id` | `ascendant_spawners` |
| `display_name` | `Ascendant Spawners` |

## Tokens

| API | Token | Uso |
|-----|-------|-----|
| Upload | `ee776b0a-ee95-4850-b554-06be02a8657f` | Subir archivos JAR (token de cuenta, compartido entre proyectos) |
| Core (GET) | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` | Consultar datos del mod |

Autenticación Upload: cabecera `X-Api-Token`
Autenticación Core: cabecera `x-api-key`

> Token de cuenta (mismo para todos los mods).

## Variables para script (lectura automática)

project_id = 1638524
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
release_type = beta
game_versions = 9638, 9639, 11779, 10150
relations =

El script lee `project_id`, `api_token` y `game_versions` de este archivo, y `mod_id`, `mod_name`, `minecraft_version`, `mod_version` de `gradle.properties`. Sube automáticamente el JAR desde `build/libs/` con el changelog de `docs/curseforge/versions/<version>.md`.

**Relations**: `common-toolkit` (required), `ascendant-equipment` (optional). Declararlas manualmente desde la web si la subida por API falla con `errorCode 1018`. El proyecto 26.2 ya tiene las relaciones configuradas.

## Datos usados para el alta ("Submit a Project")

| Campo del formulario | Valor |
|---|---|
| Project name | `Ascendant Spawners` |
| Slug / URL | `ascendant-spawners` |
| Summary | `Silk-touch mob spawners and modify their stats (delay, range, spawn count, redstone control, and more) via crafting recipes. Fork of ApothicSpawners — spawner module of the Ascendant Equipment family. Requires Common Toolkit.` |
| Project type | Mod |
| Game | Minecraft |
| Mod loader / categories | NeoForge · Addons |
| Client/Server side | Both |
| License | MIT |
| Description | Contenido de `docs/curseforge/project_description.md` (HTML) |
| Relations — Required | Common Toolkit (`common-toolkit`, CurseForge ID `1638419`) |
| Relations — Optional | Ascendant Equipment (`ascendant-equipment`) |

## Nota para revisores de CurseForge

> This project is an open-source port of "ApothicSpawners" by Shadows_of_Fire (https://www.curseforge.com/minecraft/mc-mods/apothic-spawners), originally MIT licensed. The `1.21.1` file is a re-fork from the upstream Apothic Spawners 1.21 sources, with all package/class identifiers renamed. Attribution is kept in LICENSE, README, and the mod's `credits` field. Not affiliated with or endorsed by the original author.

## Rama
minecraft/1.21.1/neoforge-21.1.249/production

## Tag
Formato: `<mc-version>-<framework>-<version>` — Ejemplo: `1.21.1-neoforge-0.0.0-beta.1`

## Repo GitLab
https://gitlab.com/stalking-dragons/minecraft/ascendant-spawners.git
