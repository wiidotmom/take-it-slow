package mom.wii.takeitslow.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @Shadow @Final protected Minecraft minecraft;
    @Shadow public abstract boolean isUnderWater();

    @Inject(method = "updateIsUnderwater", at = @At("TAIL"))
    private void takeitslow$updateIsUnderwater(CallbackInfoReturnable<Boolean> cir) {
        GameType localPlayerMode = ((MultiPlayerGameModeAccessor)this.minecraft.gameMode).getLocalPlayerMode();

        boolean isUnderwater = this.isUnderWater();
        boolean isCreative = localPlayerMode == GameType.CREATIVE;
        boolean isSpectator = localPlayerMode == GameType.SPECTATOR;
        boolean isFlying = this.minecraft.player.getAbilities().flying;

        if (!isUnderwater && !isCreative && !isSpectator && !isFlying) {
            ((LivingEntity) (Object) this).setSprinting(false);
        }
    }
}
