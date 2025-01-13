package mom.wii.takeitslow.platform;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import mom.wii.takeitslow.Constants;
import mom.wii.takeitslow.platform.services.IConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;

public class NeoForgeConfig implements IConfig {
    public static ConfigClassHandler<NeoForgeConfig> HANDLER = ConfigClassHandler.createBuilder(NeoForgeConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FMLPaths.CONFIGDIR.get().resolve("takeitslow_v2.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public boolean allowSwimming = true;

    @SerialEntry
    public float swimSpeedScale = 1.0f;

    @SerialEntry
    public boolean allowCreative = true;

    @SerialEntry
    public boolean allowFlying = true;
}
