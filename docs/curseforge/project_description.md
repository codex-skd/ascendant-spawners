<h1 align="center">&#129686; Ascendant Spawners</h1>

<p align="center"><strong>Silk-touch mob spawners and reshape their stats with crafting recipes.</strong></p>

<p align="center">
<img src="https://img.shields.io/badge/loader-NeoForge-orange?style=plastic&logo=curseforge" alt="NeoForge">
<img src="https://img.shields.io/badge/minecraft-26.2%20%7C%201.21.1-blue?style=plastic" alt="Minecraft 26.2 and 1.21.1">
<img src="https://img.shields.io/badge/type-addon-brightgreen?style=plastic" alt="Addon">
<img src="https://img.shields.io/badge/license-MIT-lightgrey?style=plastic" alt="MIT License">
</p>

<br>

---

<br>

<h2>&#10024; Overview</h2>

<table>
<tr>
<td width="65%">
<p>Ascendant Spawners makes vanilla mob spawners a real, tunable part of the game: pick them up with Silk Touch (via the <strong>Capturing</strong> enchantment), then modify their behaviour &mdash; spawn delay, spawn count, activation range, redstone control, AI, entity type and more &mdash; by using items on the placed spawner. Every modification is a data-driven recipe, so packs can rebalance or extend the list.</p>

<p>A fork of <a href="https://www.curseforge.com/minecraft/mc-mods/apothic-spawners"><strong>ApothicSpawners</strong></a> by <em>Shadows_of_Fire</em> / Stormraven Studios, rebranded and ported to NeoForge. Not affiliated with or endorsed by the original author. It is the spawner module of <strong>Ascendant Equipment</strong> (our port of Apotheosis).</p>
</td>
<td width="35%" align="center">
<a href="https://codex.skdragons.com/" target="_blank"><img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="160"></a>
</td>
</tr>
</table>

<br>

<h2>&#128295; Features</h2>

<h3>Capturing Enchantment</h3>
<p>An enchantment for pickaxes that lets you break a spawner and keep it, preserving its configured mob and every stat modification applied to it.</p>

<h3>Spawner Stat Modification</h3>
<p>Use items on a placed spawner to change its stats: <strong>min / max delay</strong>, <strong>spawn count</strong>, <strong>max nearby entities</strong>, <strong>required player range</strong>, <strong>spawn range</strong>, plus toggles for <strong>ignore light</strong>, <strong>ignore player proximity</strong>, <strong>ignore spawn conditions</strong>, <strong>redstone control</strong>, <strong>no AI</strong>, <strong>silent</strong>, <strong>youthful</strong> (spawn babies), <strong>initial health</strong>, <strong>burning</strong> and more. Each is a recipe with a forward and an <code>_inverse</code> variant.</p>

<h3>Live Stat Tooltip</h3>
<p>Hold Shift over a spawner item (or look at a placed spawner with Jade installed) to see its current entity and the full list of stat modifications.</p>

<h3>Advancements</h3>
<p>A dedicated advancement tab tracks each type of spawner modification you perform, up to fully mastering the system.</p>

<br>

<h2>&#129521; Mod Structure</h2>

<table>
<tr><th align="left">Area</th><th align="left">What it provides</th></tr>
<tr><td><code>block</code></td><td>The modified spawner block, its item, and the block entity with the extended spawner logic.</td></tr>
<tr><td><code>stats</code></td><td>The configurable spawner stats (boolean / level / percentage / custom / vanilla-backed) and their registry.</td></tr>
<tr><td><code>modifiers</code></td><td>The recipe type that binds an item + stat changes into a spawner modification.</td></tr>
<tr><td><code>advancements</code></td><td>The custom trigger and item predicate that power the advancement tab.</td></tr>
<tr><td><code>compat</code></td><td>JEI (recipe category) and Jade (tooltip overlay) integration &mdash; optional, only active when those mods are present.</td></tr>
<tr><td><code>mixin</code></td><td>The small vanilla patches that swap in the modified spawner block and item.</td></tr>
</table>

<br>

<h2>&#128203; Requirements</h2>

<table>
<tr><td><strong>Minecraft / NeoForge / Java</strong></td><td>see <em>Available Versions</em> below</td></tr>
<tr><td><strong>Common Toolkit</strong></td><td>Required</td></tr>
<tr><td><strong>Ascendant Equipment</strong></td><td>Optional</td></tr>
<tr><td><strong>JEI / Jade</strong></td><td>Optional (compat only)</td></tr>
<tr><td><strong>Side</strong></td><td>Client and Server (required on both)</td></tr>
</table>

<br>

<h2>&#128230; Available Versions</h2>

<table>
<tr><th align="left">Minecraft</th><th align="left">NeoForge</th><th align="left">Java</th><th align="left">Latest build</th><th align="left">Status</th></tr>
<tr><td>26.2</td><td>26.2.0.57+</td><td>25</td><td><code>1.0.0</code></td><td>Stable</td></tr>
<tr><td>1.21.1</td><td>21.1.249+</td><td>21</td><td><code>0.0.0-beta.1</code></td><td>Beta &mdash; re-fork port from upstream Apothic Spawners 1.21</td></tr>
</table>

<p><em>Both versions share this CurseForge project. Pick the file that matches your Minecraft version.</em></p>

<br>

<h2>&#127918; How to Use</h2>

<ol>
<li>Install Common Toolkit, then this mod, on both client and server.</li>
<li>Get the <strong>Capturing</strong> enchantment on a pickaxe (enchanting table / anvil) and mine a spawner to pick it up.</li>
<li>Place the spawner and right-click it with modifier items to tune its stats &mdash; check JEI for the full recipe list.</li>
</ol>

<br>

---

<br>

<h2>&#128591; Credits &amp; License</h2>

<p>Ascendant Spawners is a fork of <a href="https://www.curseforge.com/minecraft/mc-mods/apothic-spawners">ApothicSpawners</a> by <strong>Shadows_of_Fire</strong> / <strong>Stormraven Studios, LLC</strong>, rebranded and ported to NeoForge by <strong>Stalking Dragons</strong>. The <code>1.21.1</code> build is a re-fork from the upstream Apothic Spawners 1.21 sources.</p>

<p><strong>License:</strong> <strong>MIT</strong>, same as upstream (the original <code>Copyright (c) 2024-2025 Stormraven Studios, LLC</code> notice is kept in the jar and repository <code>LICENSE</code>). All in-game assets are original to this project. The required dependency <strong>Common Toolkit</strong> (our fork of Placebo) is LGPL-2.1-or-later and is depended on, not bundled.</p>

<br>
<br>

<p align="center">
  <a href="https://codex.skdragons.com/" target="_blank">
    <img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="200">
  </a>
  <br>
  <a href="https://codex.skdragons.com/">https://codex.skdragons.com/</a>
  <br>
  <em>Codex Stalking Dragons &mdash; Minecraft Modding</em>
</p>
