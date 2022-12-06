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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public class MixinShapedRecipe {
	private static final ThreadLocal<NbtCompound> nbtLocal = new ThreadLocal<>();

	@Redirect(method = "outputFromJson", at = @At(value = "INVOKE", target = "com/google/gson/JsonObject.has(Ljava/lang/String;)Z", remap = false))
	private static boolean redirectHas(JsonObject json, String key) {
		if (json.has(key)) {
			JsonObject data = JsonHelper.getObject(json, key);
			NbtCompound nbt = (NbtCompound) JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, data);
			nbtLocal.set(nbt);
		} else {
			nbtLocal.set(null);
		}
		//if we don't return false this will throw so do that
		return false;
	}

	@Inject(method = "outputFromJson", at = @At(value = "RETURN"))
	private static void addNbt(JsonObject json, CallbackInfoReturnable<ItemStack> info) {
		if (nbtLocal.get() != null) {
			info.getReturnValue().setNbt(nbtLocal.get());
		}
	}
}
