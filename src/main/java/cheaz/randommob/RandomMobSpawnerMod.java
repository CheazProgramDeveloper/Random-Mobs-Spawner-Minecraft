package cheaz.randommobspawner;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

public class RandomMobSpawnerMod implements ModInitializer {
    public static GameRules.Key<GameRules.BooleanRule> ENABLED_RULE;

    @Override
    public void onInitialize() {
        ENABLED_RULE = GameRuleRegistry.register("randomMobSpawnerEnabled", GameRules.Category.MISC,
                GameRuleFactory.createBooleanRule(true));

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
            dispatcher.register(CommandManager.literal("randommobs")
                    .then(CommandManager.literal("set")
                            .then(CommandManager.argument("seconds", IntegerArgumentType.integer(1))
                                    .executes(context -> {
                                        int sec = IntegerArgumentType.getInteger(context, "seconds");
                                        SpawnScheduler.setIntervalSeconds(sec);
                                        context.getSource().sendFeedback(
                                            () -> Text.translatable("command.interval.set", sec),
                                            true
                                        );
                                        return 1;
                                    })
                            )
                    )
            );
        });

        ServerTickEvents.START_SERVER_TICK.register(server -> {
            SpawnScheduler.onServerTick(server);
            BossBarManager.updateBossBar(server);
        });
    }
}