package mom.wii.takeitslow.mixin;

import mom.wii.takeitslow.platform.Services;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyVariable(method = "setSprinting(Z)V", at = @At("HEAD"))
    private boolean takeitslow$setSprinting(boolean bl) {
        if (((Object) this) instanceof LocalPlayer) {
            LocalPlayer player = (LocalPlayer) (Object) this;

            GameType localPlayerMode = ((LocalPlayerAccessor)player).getMinecraft().gameMode.getPlayerMode();

            boolean allowSwimming = Services.CONFIG.getAllowSwimming();
            boolean allowCreative = Services.CONFIG.getAllowCreative();
            boolean allowFlying = Services.CONFIG.getAllowFlying();;

            boolean isUnderwater = player.isUnderWater();
            boolean isCreative = localPlayerMode == GameType.CREATIVE;
            boolean isSpectator = localPlayerMode == GameType.SPECTATOR;
            boolean isFlying = player.getAbilities().flying;


            return (isUnderwater && bl && allowSwimming) || (isCreative && bl && allowCreative) || (isSpectator && bl && allowCreative) || (isFlying && bl && allowFlying);
        }
        return bl;
    }
}
