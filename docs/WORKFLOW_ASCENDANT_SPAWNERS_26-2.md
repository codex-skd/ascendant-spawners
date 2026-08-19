# Flujo de trabajo — Ascendant Spawners (NeoForge)

> **Versión del workflow**: 1.16.0 (codex-docs)
> Este archivo pertenece al proyecto **Ascendant Spawners**. Cambios aquí solo afectan a este proyecto.
> **Trabaja directamente con este archivo**: es el workflow operativo del mod, autocontenido. No leas `codex-docs/WORKFLOW_AGENT.md` ni `WORKFLOW_GENERIC.md` de forma rutinaria.
> On-demand (solo si la tarea lo necesita): `codex-docs/reference/CURSEFORGE.md` (formato HTML al publicar), `codex-docs/reference/GRAPHIFY.md` (backend LLM de Graphify), `codex-docs/reference/REPO_SETUP.md` (setup único de repo).

## Específico del mod

| Dato | Valor |
|---|---|
| Mod ID (`gradle.properties`) | `ascendant_spawners` |
| Clase principal | `AscendantSpawners` |
| Display name (Title Case) | `Ascendant Spawners` |
| Versiones de Minecraft | `26.2` |
| Rama | `minecraft/26.2/neoforge-26.2.0.57/production` |

| Última versión publicada | `0.0.0-beta.14` (CurseForge file ID 8687759) |
| Estado | ✅ Release estable 0.0.0-beta.14 |

### Notas específicas de este mod

- **Es un port declarado**: de [ApothicSpawners](https://www.curseforge.com/minecraft/mc-mods/apothic-spawners) por Shadows_of_Fire (NeoForge 26.1.2 → 26.2), con todos los identificadores renombrados a la convención propia. Roadmap completo por fases: `docs/ROADMAP_ASCENDANT_SPAWNERS.md`.
- **package**: `com.skd.ascendantspawners`
- **Minecraft / NeoForge**: `26.2` / `26.2.0.57`
- **Es módulo de Ascendant Equipment**: en el original, ApothicSpawners es el "Spawner Module of Apotheosis". Aquí Ascendant Spawners es el módulo de spawners de **Ascendant Equipment** (`ascendant_equipment/neoforge/26.2`, nuestro port de Apotheosis) y depende de **Common Toolkit** (`common_toolkit/neoforge/26.2`, nuestro port de Placebo — dependencia obligatoria del original vía `placebo`).
- **Referencia en `lib_ext/`**: `ApothicSpawners-26.1.2-2.0.1.jar` (compilado, sin fuente). Se decompila en Fase 0 a `temp/apothic-spawners-src/` (no versionado) como base de lectura para portar clase a clase. `lib_ext/` y `temp/` no se versionan (ver `.gitignore`).
- **Dependencia de compilación real**: `libs/common_toolkit-26.2-neoforge-0.0.0-beta.1.jar` (versionado, ver `build.gradle`). Ascendant Equipment aún no tiene JAR — añadir a `libs/` en cuanto lo tenga.
- **Atribución obligatoria** (licencia MIT del original): mantener "port of ApothicSpawners by Shadows_of_Fire" en `README.md`, `LICENSE`, `docs/curseforge/project_description.md` y `credits` de `neoforge.mods.toml` durante todo el desarrollo.
- **Assets NO se copian**: el arte original (`assets/apothic_spawners/`: texturas de GUI/JEI, fondo de advancement) no se reutiliza. Todo `assets/ascendant_spawners/` es propio (placeholder al principio, arte final después) — ver Fase 9 del roadmap.
- **Compat opcional a confirmar en Fase 0**: JEI y Hwyla/Jade (verificar si el original sigue en Hwyla o ya migró a Jade antes de portar `compat/`). No asumir.

## Convenciones de nomenclatura

| Convención | Uso | Ejemplo |
|---|---|---|
| **snake_case** | `mod_id`, assets/, packages Java | `ascendant_spawners` |
| **PascalCase** | Clases Java principales | `AscendantSpawners` |
| **camelCase** | Variables, métodos, config keys | `ascendantSpawnersConfig` |
| **Title Case** | Display name (README, CHANGELOG, docs, CurseForge) | `Ascendant Spawners` |

## Organización y ramas

- Un repo GitLab por mod, una rama `minecraft/<mc>/neoforge-<neo>/production` por versión. Este clon local trabaja en la rama `production` de esta versión.
- Carpetas: `<mod_id>/<framework>/<mc-version>/` — este clon vive en `ascendant_spawners/neoforge/26.2/`.
- `*/main` y CI/CD: setup único al crear el repo (`codex-docs/reference/REPO_SETUP.md`) — no releer ni modificar.

## Estructura del proyecto

`build.gradle` · `gradle.properties` (mod_id, mod_version, mod_group_id, mod_framework) · `settings.gradle` · `src/main/java/<package>/` · `src/main/resources/assets/<mod_id>/` · `META-INF/neoforge.mods.toml` · `libs/` (versionado) · `lib_ext/` y `temp/` (no versionados) · `docs/` (WORKFLOW + curseforge/) · `CHANGELOG.md` · `README.md` · `graphify-out/` (versionado).

## Versionado

- Beta `0.0.0-beta.X` · Release `X.Y.Z` (SemVer: MAJOR breaking / MINOR feature / PATCH fix)
- `mod_version` y `mod_framework` en `gradle.properties`. JAR: `<mod_id>-<mc>-<framework>-<loader>-<version>.jar`

## Commits (Conventional Commits)

`<tipo>[<ámbito>]: <descripción>` · tipos `feat fix refactor docs chore style perf test` · el mensaje incluye la versión (`v<version>`).

## Tags

Cada subida a CurseForge crea tag: beta `<mc>-neoforge-beta.X` · release `<mc>-neoforge-X.Y.Z`.

## Flujo por tarea

**0. Alcance** — si el mod tiene varias versiones, preguntar con la herramienta `question`: **"Todas"** o una versión. No asumir.

**1. Desarrollo**

```bash
git checkout minecraft/26.2/neoforge-26.2.0.45-beta/production
./gradlew.bat build
git add -A
git commit -m "feat: <descripción>

v<version>"
git push
```

**2. CurseForge** — solo si el usuario confirma:
- Bump `mod_version` en gradle.properties → `./gradlew.bat clean build`
- Release notes `docs/curseforge/versions/<version>.md` (HTML) + actualizar `CHANGELOG.md`
- Commit `chore: bump version to <version>` → tag `<mc>-neoforge-<version>` → push
- Subir JAR: `powershell -File ../../codex-docs/scripts/curseforge-upload.ps1` (desde este repo)
- Formato HTML de descripciones/changelog: `codex-docs/reference/CURSEFORGE.md`

**3. Release estable** — bump `X.Y.Z` + tag.

**4. Graphify** — tras cada push a remoto. Versión 0.9.12: **`build` no existe**, usar `extract` (1ª vez) o `update . --force` (tras cambios):

```bash
GRAPHIFY="C:\Users\llagu\AppData\Local\Packages\PythonSoftwareFoundation.Python.3.13_qbz5n2kfra8p0\LocalCache\local-packages\Python313\Scripts\graphify.exe"
"$GRAPHIFY" update . --force
git add graphify-out/ && git commit -m "chore: update knowledge graph" && git push
```

Leer siempre `GRAPH_REPORT.md`, nunca `graph.json`/`graph.html` (pesan >1MB). Sin copias fechadas de `graphify-out/`. Backend LLM: `codex-docs/reference/GRAPHIFY.md`.

## Buenas prácticas

- Un commit por cambio lógico · commit+push tras cada cambio funcional y de docs
- `clean build` antes del JAR final · versionar antes de CurseForge · CHANGELOG al día
- Graphify actualizado tras cada release · nomenclatura consistente · sin basura en repo (`nul`, `*_errors.txt`, `TEMPLATE_LICENSE.txt`) · `.gitignore` excluye `temp/` y `lib_ext/`
- README en inglés siempre actualizado · sin residuos del template (paquetes, clases, toml, lang, assets)

## Idioma

| Ámbito | Idioma |
|---|---|
| código, logs, commits | en-US |
| README.md | en-US |
| docs internas (docs/, CHANGELOG, este archivo) | es-ES |
| CurseForge | en-US |
