package mom.wii.takeitslow.platform;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import mom.wii.takeitslow.Constants;
import mom.wii.takeitslow.platform.services.IConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class FabricConfig implements IConfig {
    public static ConfigClassHandler<FabricConfig> HANDLER = ConfigClassHandler.createBuilder(FabricConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("takeitslow_v2.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
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
