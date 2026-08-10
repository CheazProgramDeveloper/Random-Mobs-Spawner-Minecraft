package cheaz.randommobspawner;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;

import java.util.List;

public class SpawnScheduler {
    private static int tickCounter = 0;
    private static int intervalTicks = 20 * 60;
    private static final Random random = Random.create();

    public static void setIntervalSeconds(int seconds) {
        intervalTicks = seconds * 20;
        tickCounter = 0;
        BossBarManager.resetBossBar();
        System.out.println("[RandomMobSpawner] Interval set to " + seconds + " seconds.");
    }

    public static int getRemainingTicks() {
        return Math.max(0, intervalTicks - tickCounter);
    }

    public static int getTotalTicks() {
        return intervalTicks;
    }

    public static void onServerTick(MinecraftServer server) {
        boolean enabled = server.getOverworld().getGameRules().getBoolean(RandomMobSpawnerMod.ENABLED_RULE);
        if (!enabled) {
            tickCounter = 0;
            return;
        }

        tickCounter++;
        if (tickCounter < intervalTicks) return;
        tickCounter = 0;

        System.out.println("[RandomMobSpawner] Timer triggered! Trying to spawn mob.");

        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        if (players.isEmpty()) {
            System.out.println("[RandomMobSpawner] No players online – spawn cancelled.");
            return;
        }

        ServerPlayerEntity targetPlayer = players.get(random.nextInt(players.size()));
        ServerWorld world = targetPlayer.getServerWorld();
        System.out.println("[RandomMobSpawner] Target player: " + targetPlayer.getName().getString());

        EntityType<?> mobType = MobSelector.getRandomMob(random);
        if (mobType == null) {
            System.out.println("[RandomMobSpawner] Failed to select mob type.");
            return;
        }
        System.out.println("[RandomMobSpawner] Selected mob: " + mobType.getName().getString());

        BlockPos spawnPos = findSpawnPosition(world, targetPlayer);
        if (spawnPos == null) {
            System.out.println("[RandomMobSpawner] No valid position found, using fallback.");
            int x = (int) targetPlayer.getX() + random.nextInt(10) - 5;
            int z = (int) targetPlayer.getZ() + random.nextInt(10) - 5;
            int topY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
            if (topY == world.getBottomY()) {
                System.out.println("[RandomMobSpawner] Could not find surface for fallback.");
                return;
            }
            spawnPos = new BlockPos(x, topY + 1, z);
        }

        MobEntity mob = (MobEntity) mobType.create(world);
        if (mob == null) {
            System.out.println("[RandomMobSpawner] Failed to create mob instance.");
            return;
        }

        mob.refreshPositionAndAngles(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5,
                random.nextFloat() * 360f, 0f);

        world.spawnEntity(mob);
        mob.initialize(world, world.getLocalDifficulty(spawnPos), SpawnReason.NATURAL, null);

        Text mobName;
        if (MobSelector.isHostile(mobType) || MobSelector.isBoss(mobType)) {
            mobName = mob.getType().getName().copy()
                    .formatted(MobColorManager.getFormatting(mobType))
                    .formatted(Formatting.BOLD);
        } else {
            mobName = mob.getType().getName().copy()
                    .formatted(Formatting.GRAY)
                    .formatted(Formatting.BOLD);
        }

        Text playerDisplay = targetPlayer.getDisplayName().copy().formatted(Formatting.BOLD);
        Text message = Text.translatable("mob.spawn.message", mobName, playerDisplay);
        server.getPlayerManager().broadcast(message, false);
        System.out.println("[RandomMobSpawner] Mob spawned successfully!");
    }

    private static BlockPos findSpawnPosition(ServerWorld world, ServerPlayerEntity player) {
        for (int attempt = 0; attempt < 5; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double distance = 5.0 + random.nextDouble() * 10.0;
            int x = (int) (player.getX() + Math.cos(angle) * distance);
            int z = (int) (player.getZ() + Math.sin(angle) * distance);

            int topY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
            if (topY == world.getBottomY()) continue;

            BlockPos candidate = new BlockPos(x, topY + 1, z);
            if (!world.getBlockState(candidate.down()).blocksMovement()) continue;
            if (!world.getBlockState(candidate).isAir()) continue;
            if (!world.getBlockState(candidate.up()).isAir()) continue;
            return candidate;
        }
        return null;
    }
}