package cheaz.randommobspawner;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class MobSelector {
    // Списки по категориям
    private static final List<EntityType<?>> BOSSES = List.of(
            EntityType.ENDER_DRAGON,
            EntityType.WITHER
    );

    private static final List<EntityType<?>> HOSTILE = List.of(
            EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER, EntityType.SPIDER,
            EntityType.CAVE_SPIDER, EntityType.ENDERMAN, EntityType.WITCH, EntityType.SLIME,
            EntityType.MAGMA_CUBE, EntityType.GHAST, EntityType.BLAZE, EntityType.PHANTOM,
            EntityType.HOGLIN, EntityType.ZOGLIN, EntityType.PIGLIN_BRUTE, EntityType.DROWNED,
            EntityType.HUSK, EntityType.STRAY, EntityType.WITHER_SKELETON, EntityType.VEX,
            EntityType.VINDICATOR, EntityType.EVOKER, EntityType.PILLAGER, EntityType.RAVAGER,
            EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN, EntityType.SILVERFISH,
            EntityType.ENDERMITE, EntityType.SHULKER, EntityType.WARDEN,
            EntityType.BREEZE, EntityType.BOGGED
    );

    private static final List<EntityType<?>> NEUTRAL = List.of(
            EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.WOLF,
            EntityType.POLAR_BEAR, EntityType.LLAMA, EntityType.TRADER_LLAMA,
            EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.DOLPHIN,
            EntityType.PANDA, EntityType.BEE, EntityType.GOAT, EntityType.FOX
    );

    private static final List<EntityType<?>> PASSIVE = List.of(
            EntityType.COW, EntityType.PIG, EntityType.SHEEP, EntityType.CHICKEN,
            EntityType.RABBIT, EntityType.HORSE, EntityType.DONKEY, EntityType.MULE,
            EntityType.SKELETON_HORSE, EntityType.ZOMBIE_HORSE, EntityType.CAT,
            EntityType.OCELOT, EntityType.TURTLE, EntityType.PARROT, EntityType.BAT,
            EntityType.COD, EntityType.SALMON, EntityType.PUFFERFISH, EntityType.TROPICAL_FISH,
            EntityType.SQUID, EntityType.GLOW_SQUID, EntityType.AXOLOTL, EntityType.FROG,
            EntityType.TADPOLE, EntityType.ALLAY, EntityType.CAMEL, EntityType.SNIFFER,
            EntityType.ARMADILLO, EntityType.MOOSHROOM, EntityType.STRIDER,
            EntityType.VILLAGER, EntityType.WANDERING_TRADER
    );

    // Новые веса: безобидные (пассивные + нейтральные) теперь выше враждебных
    private static final int WEIGHT_BOSS = 1;
    private static final int WEIGHT_HOSTILE = 9;    // было 60
    private static final int WEIGHT_NEUTRAL = 30;   // без изменений
    private static final int WEIGHT_PASSIVE = 60;   // было 10
    // Сумма = 100

    public static EntityType<?> getRandomMob(Random random) {
        int roll = random.nextInt(100);
        List<EntityType<?>> list;

        if (roll < WEIGHT_BOSS) {
            list = BOSSES;
        } else if (roll < WEIGHT_BOSS + WEIGHT_HOSTILE) {
            list = HOSTILE;
        } else if (roll < WEIGHT_BOSS + WEIGHT_HOSTILE + WEIGHT_NEUTRAL) {
            list = NEUTRAL;
        } else {
            list = PASSIVE;
        }

        return list.get(random.nextInt(list.size()));
    }

    // Методы для определения категории моба
    public static boolean isBoss(EntityType<?> type) {
        return BOSSES.contains(type);
    }

    public static boolean isHostile(EntityType<?> type) {
        return HOSTILE.contains(type);
    }

    public static boolean isNeutral(EntityType<?> type) {
        return NEUTRAL.contains(type);
    }

    public static boolean isPassive(EntityType<?> type) {
        return PASSIVE.contains(type);
    }
}