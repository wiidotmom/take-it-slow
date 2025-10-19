package mom.wii.takeitslow;

import com.mojang.blaze3d.platform.InputConstants;
import mom.wii.takeitslow.platform.FabricConfig;
import mom.wii.takeitslow.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import static mom.wii.takeitslow.Constants.MOD_ID;

public class TakeItSlowFabric implements ModInitializer {
    private static KeyMapping TOGGLE_KEY_MAPPING;
    
    @Override
    public void onInitialize() {
        TakeItSlow.init();

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);

        TOGGLE_KEY_MAPPING = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key." + MOD_ID + ".toggle",
                -1,
                KeyMapping.Category.MOVEMENT
        ));

        FabricConfig.HANDLER.load();
    }

    private void onClientTick(Minecraft client) {
        Player player = client.player;
        if (player != null && player.isSwimming()) {
            Vec3 delta = player.getDeltaMovement();
            double swimSpeedScale = Services.CONFIG.getSwimSpeedScale();
            Vec3 newDelta = delta.multiply(swimSpeedScale, swimSpeedScale, swimSpeedScale);
            player.setDeltaMovement(newDelta);
        }

        while (TOGGLE_KEY_MAPPING.consumeClick()) {
            Services.CONFIG.setEnabled(!Services.CONFIG.getEnabled());
            if (Services.CONFIG.getEnabled())
                client.gui.setOverlayMessage(Component.translatable("gui.takeitslow.enabled"), false);
            else
                client.gui.setOverlayMessage(Component.translatable("gui.takeitslow.disabled"), false);
            FabricConfig.HANDLER.save();
        }
    }
}
