package mom.wii.takeitslow;


import com.mojang.blaze3d.platform.InputConstants;
import mom.wii.takeitslow.platform.NeoForgeConfig;
import mom.wii.takeitslow.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static mom.wii.takeitslow.Constants.MOD_ID;

@Mod(value = MOD_ID, dist = Dist.CLIENT)
public class TakeItSlowNeoForge {

    private static final Lazy<KeyMapping> TOGGLE_KEY_MAPPING = Lazy.of(() -> new KeyMapping("key." + MOD_ID + ".toggle", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, -1, KeyMapping.Category.MOVEMENT));

    public TakeItSlowNeoForge(IEventBus eventBus) {
        TakeItSlow.init();

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> NeoForgeConfig.getScreenFromParent(parent)
        );

        NeoForgeConfig.HANDLER.load();

        eventBus.addListener(TakeItSlowNeoForge::onRegisterKeyMappings);
        NeoForge.EVENT_BUS.addListener(TakeItSlowNeoForge::onPlayerTick);
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_KEY_MAPPING.get());
    }

    private static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!event.getEntity().isLocalPlayer()) return;
        LocalPlayer player = (LocalPlayer) event.getEntity();
        if (player.isSwimming()) {
            Vec3 delta = player.getDeltaMovement();
            double swimSpeedScale = Services.CONFIG.getSwimSpeedScale();
            Vec3 newDelta = delta.multiply(swimSpeedScale, swimSpeedScale, swimSpeedScale);
            player.setDeltaMovement(newDelta);
        }

        while (TOGGLE_KEY_MAPPING.get().consumeClick()) {
            Services.CONFIG.setEnabled(!Services.CONFIG.getEnabled());
            if (Services.CONFIG.getEnabled())
                player.displayClientMessage(Component.translatable("gui.takeitslow.enabled"), true);
            else
                player.displayClientMessage(Component.translatable("gui.takeitslow.disabled"), true);
            NeoForgeConfig.HANDLER.save();
        }
    }
}