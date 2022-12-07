package gay.lemmaeof.qdron.mixin;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public class MixinShapedRecipe {
	private static final ThreadLocal<NbtCompound> nbtLocal = new ThreadLocal<>();

	@Inject(method = "outputFromJson", at = @At("HEAD"))
	private static void grabNbt(JsonObject json, CallbackInfoReturnable<ItemStack> info) {
		if (json.has("data")) {
			JsonObject data = JsonHelper.getObject(json, "data");
			NbtCompound nbt = (NbtCompound) JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, data);
			nbtLocal.set(nbt);
			json.remove("data");
		} else {
			nbtLocal.set(null);
		}
	}

	@Inject(method = "outputFromJson", at = @At(value = "RETURN"))
	private static void addNbt(JsonObject json, CallbackInfoReturnable<ItemStack> info) {
		if (nbtLocal.get() != null) {
			info.getReturnValue().setNbt(nbtLocal.get());
		}
	}
}
