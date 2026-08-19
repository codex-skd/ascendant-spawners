# CurseForge — Variables del proyecto

> Proyecto creado: `project_id = 1638524`.

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

## Icono / imagen del proyecto

Icono aún pendiente de diseñar (`assets/ascendant_spawners/icon.png`, actualmente el placeholder heredado de `ascendant_equipment` — sustituir antes de publicar, ver `logoFile` en `neoforge.mods.toml`). Este prompt define la **identidad visual de familia** (Ascendant Equipment / Ascendant Spawners / Ascendant Attributes...): pieza flotante dorada con aura violeta radiante sobre fondo oscuro, estilo loot icon WoW/Diablo — cada mod cambia solo el objeto central por su temática de dominio.

Prompt (motivo: jaula/spawner, no equipo ni cristal):

```
Fantasy RPG game item icon, a glowing golden monster spawner cage floating
and rotating slightly, ornate metal bars with a faint captured spark of
light flickering inside, radiant violet-purple magical aura swirling
around it, warm golden light core at its center, dark vignette background,
dramatic rim lighting, painterly digital art style matching World of
Warcraft / Diablo loot icon aesthetics, square composition, centered
subject, no text, no border, high detail, 1:1 aspect ratio
```

Generar en alta resolución (1024x1024 recomendado) y exportar dos tamaños: 64x64 para `assets/ascendant_spawners/icon.png` (icono in-game) y una versión cuadrada (mínimo 256x256, PNG con fondo) para el logo del proyecto en CurseForge. No reutilizar el icono de `ascendant_equipment` — cada mod necesita uno propio aunque compartan estilo de familia.

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

## Variables para script (lectura automática)

project_id = 1638524
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
release_type = release
game_versions = 9638, 9639, 16498, 10150
relations =

El script lee `project_id`, `api_token` y `game_versions` de este archivo, y `mod_id`, `mod_name`, `minecraft_version`, `mod_version` de `gradle.properties`. Sube automáticamente el JAR desde `build/libs/` con el changelog de `docs/curseforge/versions/<version>.md`.

## Versión actual

| Variable | Valor |
|----------|-------|
| `minecraft_version` | `26.2` |
| `framework` | `neoforge` |
| `java_version` | `25` |
| `environment` | `Client`, `Server` |

## Rama

```
minecraft/26.2/neoforge-26.2.0.57/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo (primera beta): `26.2-neoforge-beta.1`

## Nota

La **primera subida a CurseForge se hace manual** (proyecto recién creado, sin archivos previos que verificar por API). A partir de la segunda subida se puede usar el script `codex-docs/scripts/curseforge-upload.ps1`, que lee `project_id`, `api_token` y `game_versions` de este archivo (rellenar tras el alta) y `mod_id`, `mod_name`, `minecraft_version`, `mod_version` de `gradle.properties`.
