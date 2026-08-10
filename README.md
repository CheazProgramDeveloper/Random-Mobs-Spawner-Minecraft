📦 **Random Mob Spawner**
A lightweight Fabric mod for Minecraft 1.21.1 that spawns a random mob near a random online player every 60 seconds (configurable).
Features a BossBar countdown, a */gamerule toggle*, a command to change the interval, and full i18n support (7 languages).

✨ **Features**
⏱️ Automatic spawning – every 60 seconds a mob appears near a randomly chosen player.
🎯 Smart targeting – spawns within 5–15 blocks of the player, on solid ground.
📊 BossBar countdown – shows remaining seconds until next spawn (localized).
🎨 Colored & bold names – hostile/boss mobs keep their unique colors, neutral/passive mobs are gray; all are bold.
⚖️ Weighted selection – passive mobs: 60%, neutral: 30%, hostile: 9%, bosses: 1%.
🌍 Localization – supports English, Russian, Chinese (simplified), Japanese, Kazakh, Ukrainian, Spanish.
🛠️ Commands & gamerules – enable/disable with */gamerule*, change interval with */randommobs* set.

🔧 **Commands**
Command	Description	Permission
*/randommobs set <seconds>*	Changes the spawn interval to the specified amount of seconds.	op (or 2)
*/gamerule randomMobSpawnerEnabled <true/false>*	Enables or disables the spawner.	op (or 2)

🎮 **Game Rule**
Rule	Type	Default	Description
randomMobSpawnerEnabled	boolean	true	If false, the mod will not spawn any mobs.

📁 **Supported Languages:**
*English (US) - en_us*
*Русский (Russian) - ru_ru*
*简体中文 (Chinese Simplified) - zh_cn*
*日本語 (Japanese) - ja_jp*
*Қазақша (Kazakh) - kk_kz*
*Українська (Ukrainian) - uk_ua*
*Español (Spanish) - es_es*

🛠️ **Building from Source**
Clone the repository (or copy the source files).
Make sure you have JDK 17+ and Gradle (wrapper included).

**Run the build command:**
*gradlew clean build*
The built JAR will be in build/libs/ – pick the one without -sources (e.g. randommobspawner-1.0.0.jar).

📥 **Installation**
Install Fabric Loader for **Minecraft 1.21.1**.
Place the mod JAR into your mods folder (server or client).
Start the game/server – the mod works in both singleplayer and multiplayer.

🧪 **Configuration & Tuning**
Change spawn interval – use */randommobs* set <seconds>. The BossBar and timer will reset immediately.
Disable spawning – use */gamerule randomMobSpawnerEnabled false* (useful for events or debugging).
Weight tweaks – if you want different probabilities, edit MobSelector.java (the WEIGHT_* constants) and rebuild.

🖼️ **Example Messages**
*English: Zombie appeared near Steve*
*Russian: Зомби появился около Steve*
*Chinese: 僵尸 出现在 Steve 附近*
*Japanese: ゾンビがSteveの近くに現れました*
*Kazakh: Зомби Steve-тің маңында пайда болды*
*Ukrainian: Зомбі з'явився біля Steve*
*Spanish: Zombi apareció cerca de Steve*

📜 **License**
This project is licensed under the MIT License – feel free to use, modify, and distribute.

👨‍💻 **Authors**
*Cheaz* – original development

🙏 **Acknowledgements**
*Fabric API** – for providing the modding framework.
*Mojang** – for Minecraft.
