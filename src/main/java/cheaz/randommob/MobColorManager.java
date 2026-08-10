package cheaz.randommobspawner;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

public class MobColorManager {
    private static final Map<EntityType<?>, Formatting[]> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put(EntityType.CREEPER, new Formatting[]{Formatting.GREEN});
        COLOR_MAP.put(EntityType.ENDERMAN, new Formatting[]{Formatting.DARK_PURPLE});
        COLOR_MAP.put(EntityType.SKELETON, new Formatting[]{Formatting.GRAY});
        COLOR_MAP.put(EntityType.ZOMBIE, new Formatting[]{Formatting.DARK_GREEN});
        COLOR_MAP.put(EntityType.SPIDER, new Formatting[]{Formatting.BLACK});
        COLOR_MAP.put(EntityType.WITCH, new Formatting[]{Formatting.DARK_BLUE});
        COLOR_MAP.put(EntityType.SLIME, new Formatting[]{Formatting.GREEN});
        COLOR_MAP.put(EntityType.MAGMA_CUBE, new Formatting[]{Formatting.GOLD});
        COLOR_MAP.put(EntityType.GHAST, new Formatting[]{Formatting.WHITE});
        COLOR_MAP.put(EntityType.BLAZE, new Formatting[]{Formatting.YELLOW});
        COLOR_MAP.put(EntityType.PHANTOM, new Formatting[]{Formatting.BLUE});
        COLOR_MAP.put(EntityType.HOGLIN, new Formatting[]{Formatting.RED});
        COLOR_MAP.put(EntityType.ZOGLIN, new Formatting[]{Formatting.DARK_RED});
        COLOR_MAP.put(EntityType.ENDERMITE, new Formatting[]{Formatting.LIGHT_PURPLE});
        // Боссы — жирный шрифт + цвет
        COLOR_MAP.put(EntityType.ENDER_DRAGON, new Formatting[]{Formatting.LIGHT_PURPLE, Formatting.BOLD});
        COLOR_MAP.put(EntityType.WITHER, new Formatting[]{Formatting.BLACK, Formatting.BOLD});
    }

    public static Formatting[] getFormatting(EntityType<?> type) {
        return COLOR_MAP.getOrDefault(type, new Formatting[]{Formatting.WHITE});
    }
}