package mom.wii.takeitslow.platform;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import mom.wii.takeitslow.Constants;
import mom.wii.takeitslow.platform.services.IConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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

    public static Screen getScreenFromParent(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.takeitslow.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.takeitslow.category.movement"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.takeitslow.option.allowSwimming"))
                                .binding(true, Services.CONFIG::getAllowSwimming, Services.CONFIG::setAllowSwimming)
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        .option(Option.<Double>createBuilder()
                                .name(Component.translatable("config.takeitslow.option.swimSpeedScale"))
                                .binding(1.0, Services.CONFIG::getSwimSpeedScale, Services.CONFIG::setSwimSpeedScale)
                                .controller(option -> DoubleSliderControllerBuilder.create(option).range(0.75, 1.0).step(0.01))
                                .build()
                        )
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.takeitslow.option.allowCreative"))
                                .binding(true, Services.CONFIG::getAllowCreative, Services.CONFIG::setAllowCreative)
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.takeitslow.option.allowFlying"))
                                .binding(true, Services.CONFIG::getAllowFlying, Services.CONFIG::setAllowFlying)
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        .build()
                )
                .save(HANDLER::save)
                .build()
                .generateScreen(parent);
    }


    @SerialEntry
    public boolean allowSwimming = true;

    @SerialEntry
    public double swimSpeedScale = 1.0;

    @SerialEntry
    public boolean allowCreative = true;

    @SerialEntry
    public boolean allowFlying = true;

    @Override
    public boolean getAllowSwimming() {
        return this.allowSwimming;
    }

    @Override
    public void setAllowSwimming(boolean value) {
        this.allowSwimming = value;
    }

    @Override
    public double getSwimSpeedScale() {
        return this.swimSpeedScale;
    }

    @Override
    public void setSwimSpeedScale(double value) {
        this.swimSpeedScale = value;
    }

    @Override
    public boolean getAllowCreative() {
        return this.allowCreative;
    }

    @Override
    public void setAllowCreative(boolean value) {
        this.allowCreative = value;
    }

    @Override
    public boolean getAllowFlying() {
        return this.allowFlying;
    }

    @Override
    public void setAllowFlying(boolean value) {
        this.allowFlying = value;
    }
}
