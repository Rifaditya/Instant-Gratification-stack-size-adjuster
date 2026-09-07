<p align="center">
  <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/dasik-library"><img src="https://img.shields.io/badge/Requires-Dasik_Library-8A2BE2?style=for-the-badge" alt="Requires Dasik Library"></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/item-clumps"><img src="https://img.shields.io/badge/Requires-Item_Clumps-orange?style=for-the-badge" alt="Requires Item Clumps"></a>
  <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

<h2>📦 Stack Size Adjuster</h2>

<blockquote><p><strong>&ldquo;Take Control of Your Inventory. Custom Limits, No Boundaries.&rdquo;</strong></p></blockquote>

<blockquote><p><strong>1 Jar 1 Version Policy:</strong> I build <strong>1 dedicated JAR for each Minecraft version</strong> (e.g. MC 26.2, MC 26.3). Please download the exact build that matches your Minecraft installation.</p></blockquote>

<p>Tired of inventory clutter? Or do you want to create a harder, more restricted survival experience? <strong>Stack Size Adjuster</strong> grants you full authority over item stack limits. Scale weapons, armor, blocks, and consumables dynamically at runtime with zero mixin conflicts and full container safety.</p>

<p>Part of the <strong>Instant Gratification Collection</strong> &mdash; mods that respect the player's time.</p>

<hr>

<h2>✨ Features</h2>

<p align="center">
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/4bcbd52a43c30f910c522782ceb45175e023d9c0.webp" alt="Chest and Nested Shulker Box Storage Stacks" width="85%"><br>
  <em>Hold massive item stacks (from 64 up to 2.14 Billion) across chests, inventories, and nested Shulker Boxes with dynamic font scaling</em>
</p>

<h3>🎚️ Category-Based Customization</h3>
<p>Configure limits dynamically across the three standard Minecraft item categories:</p>
<ul>
  <li><strong>64-Stack Limit</strong>: Standard stackable items (blocks, raw food, ores, building materials). Default: <code>128</code>.</li>
  <li><strong>16-Stack Limit</strong>: Semi-stackable items (ender pearls, snowballs, eggs, signs). Default: <code>32</code>.</li>
  <li><strong>1-Stack Limit</strong>: Unstackable items (weapons, tools, armor, potions, stews, beds). Default: <code>1</code>.</li>
</ul>

<h3>🔢 Raw Integer Limits &amp; Dynamic Range</h3>
<p>No more artificial caps! Adjust limits dynamically to any integer value from <code>1</code> up to <code>2,147,483,647</code> (<code>Integer.MAX_VALUE</code>).</p>
<ul>
  <li><strong>Dynamic Warning Tooltip</strong>: The configuration GUI features a dynamic warning tooltip that alerts you if a limit is set above <code>39,768,215</code>. This threshold prevents total-container overflows when completely filling containers with identical items.</li>
  <li><strong>Flat vs. Nested Storage Safety</strong>:
    <ul>
      <li><strong>Player Inventory (Fully Immune)</strong>: Player inventories are saved flatly as direct separate tags inside <code>.dat</code> files without slot summing. You can safely fill all 36 player slots up to <code>2,147,483,647</code> with identical items!</li>
      <li><strong>Chests, Barrels &amp; Shulker Boxes (Vulnerable to Overflow)</strong>: World containers sum up total contents during tooltips and saving. Keep individual slot counts within the safe capacity thresholds to prevent 32-bit signed integer overflow deletion.</li>
    </ul>
  </li>
</ul>

<h3>🔓 Slot Capacity Bypass</h3>
<p>Say goodbye to the standard slot stack limit of 99 items. Stack Size Adjuster overrides default container and stack serializer limits, allowing you to hold massive stacks (e.g., <code>640</code>, <code>1,000</code>, or <code>100,000</code>) in any chest, hopper, or inventory slot.</p>

<h3>📐 Dynamic Font Scale-Down</h3>
<p>Worried about large numbers overflowing slot boxes? When stack counts exceed 2 digits (&gt; 99), the client-side renderer dynamically scales down the count numbers to fit perfectly within slot boundaries, ensuring text never overlaps.</p>

<h3>🛡️ Container Destruction Protection &amp; Item Clumps</h3>
<p>Includes the <strong>Max Drop Entities</strong> GameRule (<code>stack-size-adjuster:max_drop_entities</code>, default: <code>8</code>) and integrates natively with <strong>Item Clumps</strong> to compress dropped item entities when large containers break or players die, preventing severe server lag and client FPS drops.</p>

<p align="center">
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/f21e494bf04044a72d24e7d8f073bf4f79c14427.webp" alt="Zero-Lag Container Destruction with Item Clumps" width="85%"><br>
  <em>Breaking containers with millions of items merges drops into single clean 3D text entities, locking framerates at a smooth 60 FPS</em>
</p>

<h3>🎯 Drag-Splitting &amp; Consolidation Precision</h3>
<p>Overrides container drag-splitting with double-precision math during QUICK_CRAFT operations and <code>ItemStack</code> consolidation, preventing item duplication or leftovers when distributing massive stacks.</p>

<h3>🛠️ Give Command Integer Overflow Safeguard</h3>
<p>Re-routes <code>/give</code> command calculations with <code>long</code> math and safe clamping to <code>Integer.MAX_VALUE</code>, ensuring players can safely <code>/give</code> items even with extreme stack limits active.</p>

<h3>🔌 Programmatic Addon Override API</h3>
<p>Exposes <code>StackSizeManager.registerOverride(...)</code> and <code>CustomStackSizeOverride</code>, allowing companion addon mods (such as Potion Stacker Addon or Stew Stacker Addon) to programmatically register custom item stack limits without colliding with general category rules.</p>

<p align="center">
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/a70a6c3f1cdd7492b8bc2228d51eb939d852ca9f.webp" alt="Addons and Semi-Stackable Items Showcase" width="85%"><br>
  <em>Stack 16-limit items to billions, and stack soups, stews, and potions (x16) via companion Addons</em>
</p>

<h3>🔒 Forward Compatibility &amp; Version Guard</h3>
<p>Pre-configured with <code>"minecraft": "&gt;=26.2-"</code> open-ended bounds and zero-dependency <code>ModVersionGuard</code> Knot ClassLoader startup protection to display human-readable guidance if an incompatible Minecraft API version is encountered.</p>

<h3>🧩 Compatibility &amp; HUD Integration</h3>
<ul>
  <li><strong>Item Clumps</strong>: Full native integration for ground item compression.</li>
  <li><strong>YetAnotherConfigLib (YACL) &amp; ModMenu</strong>: Optional clean in-game GUI configuration screen.</li>
  <li><strong>Server-Side Vanilla Client Support</strong>: Synchronizes limits to vanilla clients via standard networking packets.</li>
</ul>

<p align="center">
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/5821fb6d63cdb776536a3fe955ab0539a34dbe91.webp" alt="Jade WTHIT Tooltip and Hotbar Capacity" width="85%"><br>
  <em>Full Jade / WTHIT tooltip HUD support displaying real-time massive container stack counts</em>
</p>

<hr>

<h2>📊 Quick Reference &amp; Mechanics Matrix</h2>

<table>
  <thead>
    <tr>
      <th>Item Category / Storage Type</th>
      <th>Vanilla Limit</th>
      <th>SSA Default</th>
      <th>Maximum Safe Limit</th>
      <th>Key Behavior &amp; Safety Notes</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>64-Stack Items</strong></td>
      <td>64</td>
      <td><strong>128</strong></td>
      <td><code>2,147,483,647</code></td>
      <td>Blocks, ores, crops, materials. Scale up for bulk storage.</td>
    </tr>
    <tr>
      <td><strong>16-Stack Items</strong></td>
      <td>16</td>
      <td><strong>32</strong></td>
      <td><code>2,147,483,647</code></td>
      <td>Ender pearls, snowballs, eggs, buckets.</td>
    </tr>
    <tr>
      <td><strong>1-Stack Items</strong></td>
      <td>1</td>
      <td><strong>1</strong></td>
      <td><code>2,147,483,647</code></td>
      <td>Tools, weapons, armor, beds. Keeps vanilla balance by default.</td>
    </tr>
    <tr>
      <td><strong>Player Inventory (36 slots)</strong></td>
      <td>64</td>
      <td>&mdash;</td>
      <td><strong><code>2,147,483,647</code></strong></td>
      <td><strong>Fully Immune</strong>: Flat NBT serialization prevents slot count summing.</td>
    </tr>
    <tr>
      <td><strong>Hopper (5 slots)</strong></td>
      <td>64</td>
      <td>&mdash;</td>
      <td><strong><code>429,496,729</code></strong></td>
      <td>Safe per-slot limit to prevent hopper sum overflow.</td>
    </tr>
    <tr>
      <td><strong>Dispenser / Dropper (9 slots)</strong></td>
      <td>64</td>
      <td>&mdash;</td>
      <td><strong><code>238,609,294</code></strong></td>
      <td>Safe per-slot limit when filled with identical items.</td>
    </tr>
    <tr>
      <td><strong>Single Chest / Shulker Box (27 slots)</strong></td>
      <td>64</td>
      <td>&mdash;</td>
      <td><strong><code>79,536,431</code></strong></td>
      <td>Safe per-slot limit to avoid Shulker Box NBT overflow.</td>
    </tr>
    <tr>
      <td><strong>Double Chest (54 slots)</strong></td>
      <td>64</td>
      <td>&mdash;</td>
      <td><strong><code>39,768,215</code></strong></td>
      <td>Safe per-slot limit when storing 54 identical item slots.</td>
    </tr>
    <tr>
      <td><strong>Custom / Modded Backpack (N slots)</strong></td>
      <td>&mdash;</td>
      <td>&mdash;</td>
      <td><code>2,147,483,647 / N</code></td>
      <td>Apply formula: <code>Safe Limit = 2,147,483,647 / [Total Slots]</code>.</td>
    </tr>
  </tbody>
</table>

<hr>

<h2>🚀 In-Game Commands &amp; Quick Start</h2>

<p>Stack sizes can be adjusted live in-game without restarting using standard Minecraft <code>/gamerule</code> commands:</p>

<pre><code>/gamerule stack-size-adjuster:items_64_limit &lt;value&gt;
/gamerule stack-size-adjuster:items_16_limit &lt;value&gt;
/gamerule stack-size-adjuster:items_1_limit &lt;value&gt;
/gamerule stack-size-adjuster:max_drop_entities &lt;value&gt;</code></pre>

<p>Changes made via <code>/gamerule</code> immediately sync to all connected clients and refresh active container screens.</p>

<hr>

<h2>⚙️ Configuration (Native GameRules)</h2>

<blockquote>
  <p><strong>💡 Important: Config vs. In-Game GameRules:</strong> The global configuration file (<code>config/stack-size-adjuster.json</code>) only defines default values for newly created worlds. In existing worlds, change settings in-game via the <strong>Edit Game Rules</strong> UI screen or the <code>/gamerule</code> command.</p>
</blockquote>

<table>
  <thead>
    <tr>
      <th>GameRule Name</th>
      <th>Type</th>
      <th>Default</th>
      <th>Valid Range</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>stack-size-adjuster:items_64_limit</code></td>
      <td><code>Integer</code></td>
      <td><code>128</code></td>
      <td><code>1</code> to <code>2,147,483,647</code></td>
      <td>Maximum stack size for standard 64-stack items.</td>
    </tr>
    <tr>
      <td><code>stack-size-adjuster:items_16_limit</code></td>
      <td><code>Integer</code></td>
      <td><code>32</code></td>
      <td><code>1</code> to <code>2,147,483,647</code></td>
      <td>Maximum stack size for semi-stackable 16-stack items.</td>
    </tr>
    <tr>
      <td><code>stack-size-adjuster:items_1_limit</code></td>
      <td><code>Integer</code></td>
      <td><code>1</code></td>
      <td><code>1</code> to <code>2,147,483,647</code></td>
      <td>Maximum stack size for unstackable 1-stack items.</td>
    </tr>
    <tr>
      <td><code>stack-size-adjuster:max_drop_entities</code></td>
      <td><code>Integer</code></td>
      <td><code>8</code></td>
      <td><code>1</code> to <code>64</code></td>
      <td>Maximum item entities spawned per slot on container break.</td>
    </tr>
  </tbody>
</table>

<p align="center">
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/9906f4d23bb875a352c08c664b7458e2884805d9.webp" alt="Native Minecraft Edit Game Rules Menu" width="85%"><br>
  <em>Native in-game Edit Game Rules menu with bold Stack Size Adjuster (4 rules) category</em><br><br>
  <img src="https://cdn.modrinth.com/data/k73XOqgj/images/9cbe3d5cddd620840b24eb669c0a0d5cb15f3c5f.webp" alt="YetAnotherConfigLib YACL GUI Screen" width="85%"><br>
  <em>Optional YetAnotherConfigLib (YACL) GUI configuration screen with safe limit presets &amp; detailed tooltips</em>
</p>

<hr>

<h2>📖 In-Depth How-To &amp; Operational Playbook</h2>

<h3>1. Drop-In Setup &amp; Baseline Initialization</h3>
<ol>
  <li>Drop <code>stack-size-adjuster-&lt;version&gt;.jar</code> along with <strong>Fabric API</strong>, <strong>Dasik Library</strong>, and <strong>Item Clumps</strong> into your <code>mods</code> folder.</li>
  <li>On first launch, the mod auto-generates <code>config/stack-size-adjuster.json</code> with recommended baseline defaults (<code>128</code> for 64-stack items, <code>32</code> for 16-stack items, <code>1</code> for 1-stack items, and <code>8</code> for max drop entities).</li>
</ol>

<h3>2. Live In-Game Tuning vs. Global Template</h3>
<ul>
  <li><strong>For New Worlds</strong>: Modify values in <code>config/stack-size-adjuster.json</code> or through the ModMenu / YACL GUI before creating a world. The game copies these baseline defaults into the world's GameRules on initialization.</li>
  <li><strong>For Existing Worlds</strong>: Use <code>/gamerule stack-size-adjuster:&lt;rule&gt; &lt;value&gt;</code> or the <strong>Edit Game Rules</strong> menu. The mod will instantly push updated stack boundaries to all clients and force-refresh open inventories.</li>
</ul>

<h3>3. Container Safety Math &amp; Calculations</h3>
<ul>
  <li>When configuring stack limits in the millions or billions, use the <strong>Safe Container Capacity Formula</strong>:<br><br>
  <code>Safe Per-Slot Limit = 2,147,483,647 / [Total Container Slots]</code></li>
  <li><strong>Different Items</strong>: Storing different item types in the same container is 100% safe up to <code>2,147,483,647</code> per slot because Minecraft tracks distinct items independently in NBT.</li>
  <li><strong>Identical Items</strong>: Keep identical items below the container threshold (e.g. <code>39,768,215</code> for Double Chests, <code>79,536,431</code> for Shulker Boxes) to prevent total-count integer wrapping.</li>
</ul>

<h3>4. Companion Addon Integration</h3>
<p>Stack Size Adjuster includes an Addon Override API. Dedicated addon mods (such as <em>Potion Stacker Addon</em>) seamlessly register specific overrides for targeted items (e.g., allowing Potions to stack to <code>16</code> while keeping Weapons/Armor at <code>1</code>).</p>

<h3>5. Troubleshooting &amp; Crash Prevention</h3>
<ul>
  <li><strong>Loot Table Protection</strong>: Includes automated safeguards against negative stack sizes (<code>&le; 0</code>) during mob drops and egg laying.</li>
  <li><strong>Give Command Safety</strong>: Safe <code>long</code> calculations ensure large <code>/give</code> commands never overflow into negative quantities.</li>
</ul>

<hr>

<h2>🧩 Recommended Sister Mods</h2>

<p>If you enjoy <strong>Stack Size Adjuster</strong>, these companion mods from the <strong>Instant Gratification Collection</strong> plug in seamlessly:</p>

<ul>
  <li>📦 <a href="https://www.curseforge.com/minecraft/mc-mods/ig-item-clumps"><strong>Item Clumps</strong></a>: Automatically merges loose ground items into single high-density stacks with floating count labels, preventing entity lag when bursting massive inventories.</li>
  <li>🧲 <a href="https://www.curseforge.com/minecraft/mc-mods/instant-gratification-magnet"><strong>Magnet</strong></a>: Automatically vacuums loose item stacks and XP orbs directly toward your player within a configurable radius, effortlessly filling high-capacity storage.</li>
  <li>🧪 <a href="https://www.curseforge.com/minecraft/mc-mods/ig-potion-stacker-addon"><strong>Potion Stacker Addon</strong></a>: Seamlessly overrides potion limits to stack regular, splash, and lingering potions up to 16 or 64 per slot with safe empty bottle returns.</li>
</ul>

<p><em>Explore the full <a href="https://www.curseforge.com/members/dasikigaijin/projects"><strong>Instant Gratification Collection</strong></a> for more high-convenience enhancements.</em></p>

<hr>

<h2>☕ Support</h2>

<p>If you enjoy the <strong>Instant Gratification</strong> collection, consider supporting development!</p>

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

<blockquote><p><strong>🇮🇩 Indonesian Users:</strong> SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!</p></blockquote>

<blockquote><p><strong>Dedicated Server Hosting Partner:</strong><br>Looking for a reliable server to play with friends? Check out <strong>BisectHosting</strong> for 1-click modpack installations, automated backups, and 24/7 dedicated customer support.</p></blockquote>

<hr>

<h2>📜 Credits &amp; Modpack Permissions</h2>

<table>
  <thead>
    <tr>
      <th>Property</th>
      <th>Information</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Creator / Author</strong></td>
      <td><strong>Dasik</strong> (Rifaditya)</td>
    </tr>
    <tr>
      <td><strong>Collection</strong></td>
      <td><a href="https://www.curseforge.com/members/dasikigaijin/projects">Instant Gratification Collection</a></td>
    </tr>
    <tr>
      <td><strong>License</strong></td>
      <td><a href="https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public License v3.0 (GPLv3)</a></td>
    </tr>
    <tr>
      <td><strong>Source Code</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster">GitHub - Rifaditya/Instant-Gratification-stack-size-adjuster</a></td>
    </tr>
    <tr>
      <td><strong>Issue Tracker</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster/issues">GitHub Issues</a></td>
    </tr>
    <tr>
      <td><strong>Documentation / Wiki</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-stack-size-adjuster/wiki">GitHub Wiki</a></td>
    </tr>
  </tbody>
</table>

<blockquote>
  <p><strong>📦 Modpack Permissions &amp; Distribution:</strong><br>
  You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (<strong>CurseForge</strong> or <strong>Modrinth</strong>). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.</p>
  <p><strong>⚖️ License &amp; Fork Guidelines (No Zero-Change Re-uploads):</strong><br>
  This project is open-source under the <strong>GNU GPLv3</strong>. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports&mdash;provided your project remains open-source under GPLv3 with proper attribution.<br>
  <strong>However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.</strong></p>
</blockquote>

<hr>

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Instant Gratification Collection</em>
</p>
