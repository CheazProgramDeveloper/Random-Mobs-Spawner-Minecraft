package cheaz.randommobspawner;

import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class BossBarManager {
    private static final ServerBossBar bossBar = new ServerBossBar(
            Text.translatable("bossbar.title", 60), // начальное значение
            BossBar.Color.BLUE,
            BossBar.Style.PROGRESS
    );

    public static void updateBossBar(MinecraftServer server) {
        if (server.getPlayerManager().getPlayerList().isEmpty()) {
            bossBar.clearPlayers();
            return;
        }

        boolean enabled = server.getOverworld().getGameRules().getBoolean(RandomMobSpawnerMod.ENABLED_RULE);
        if (!enabled) {
            bossBar.clearPlayers();
            bossBar.setVisible(false);
            return;
        }

        int remainingTicks = SpawnScheduler.getRemainingTicks();
        int seconds = (remainingTicks + 19) / 20;
        if (seconds < 0) seconds = 0;

        bossBar.setName(Text.translatable("bossbar.title", seconds));
        float progress = (float) remainingTicks / SpawnScheduler.getTotalTicks();
        bossBar.setPercent(Math.max(0, Math.min(1, progress)));

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            bossBar.addPlayer(player);
        }
        bossBar.setVisible(true);
    }

    public static void resetBossBar() {
        bossBar.setPercent(1.0f);
    }
}