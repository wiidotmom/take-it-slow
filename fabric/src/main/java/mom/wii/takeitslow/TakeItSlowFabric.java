package mom.wii.takeitslow;

import mom.wii.takeitslow.platform.FabricConfig;
import mom.wii.takeitslow.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class TakeItSlowFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        TakeItSlow.init();

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);

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
    }
}
