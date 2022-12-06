package gay.lemmaeof.qdron.mixin;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.SmithingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SmithingRecipe.class)
public class MixinSmithingRecipe {
	@Inject(method = "craft", at = @At(value = "INVOKE", target = "net/minecraft/item/ItemStack.setNbt(Lnet/minecraft/nbt/NbtCompound;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void mergeInsteadOfClobber(Inventory inventory, CallbackInfoReturnable<ItemStack> info, ItemStack stack, NbtCompound nbt) {
		if (stack.hasNbt()) {
			stack.getNbt().copyFrom(nbt);
			info.setReturnValue(stack);
		}
	}
}
