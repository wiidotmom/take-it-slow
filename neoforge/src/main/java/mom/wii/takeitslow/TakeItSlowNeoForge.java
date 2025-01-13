package mom.wii.takeitslow;


import mom.wii.takeitslow.platform.NeoForgeConfig;
import mom.wii.takeitslow.platform.Services;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@Mod(Constants.MOD_ID)
public class TakeItSlowNeoForge {

    public TakeItSlowNeoForge(IEventBus eventBus) {
        Constants.LOG.info("Hello NeoForge world!");
        TakeItSlow.init();

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> NeoForgeConfig.getScreenFromParent(parent)
        );

        NeoForgeConfig.HANDLER.load();

        NeoForge.EVENT_BUS.addListener(TakeItSlowNeoForge::onPlayerTick);
    }

    private static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.isSwimming()) {
            Vec3 delta = player.getDeltaMovement();
            double swimSpeedScale = Services.CONFIG.getSwimSpeedScale();
            Vec3 newDelta = delta.multiply(swimSpeedScale, swimSpeedScale, swimSpeedScale);
            player.setDeltaMovement(newDelta);
        }
    }
}