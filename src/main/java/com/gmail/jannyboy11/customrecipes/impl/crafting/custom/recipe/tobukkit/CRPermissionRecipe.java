package com.gmail.jannyboy11.customrecipes.impl.crafting.custom.recipe.tobukkit;

import java.util.Map;

import com.gmail.jannyboy11.customrecipes.impl.crafting.custom.recipe.PermissionRecipe;
import com.gmail.jannyboy11.customrecipes.impl.crafting.vanilla.recipe.CRShapedRecipe;
import com.gmail.jannyboy11.customrecipes.util.NBTUtil;

import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NonNullList;
import net.minecraft.server.v1_12_R1.RecipeItemStack;

public class CRPermissionRecipe extends CRShapedRecipe<PermissionRecipe> implements com.gmail.jannyboy11.customrecipes.api.crafting.custom.recipe.PermissionRecipe {

	public CRPermissionRecipe(PermissionRecipe nmsRecipe) {
		super(nmsRecipe);
	}
	
	public CRPermissionRecipe(NBTTagCompound recipeCompound) {
		this(deserializeNmsRecipe(recipeCompound));
	}
	
	public CRPermissionRecipe(Map<String, Object> map) {
		this(NBTUtil.fromMap(map));
	}
	
	protected static PermissionRecipe deserializeNmsRecipe(NBTTagCompound recipeCompound) {
		String group = recipeCompound.hasKeyOfType("group", NBTUtil.STRING) ? recipeCompound.getString("group") : "";
		int width = recipeCompound.getInt("width");
		int height = recipeCompound.getInt("height");
		NonNullList<RecipeItemStack> ingredients = NonNullList.a();
		NBTTagList nbtIngredients = recipeCompound.getList("ingredients", NBTUtil.COMPOUND);
		for (int i = 0; i < nbtIngredients.size(); i++) {
			NBTTagCompound ingredientTag = nbtIngredients.get(i);
			RecipeItemStack recipeItemStack = NBTUtil.deserializeRecipeItemStack(ingredientTag);
			ingredients.add(recipeItemStack);
		}
		NBTTagCompound resultCompound = (NBTTagCompound) recipeCompound.get("result");
		ItemStack result = new ItemStack(resultCompound);
		String permission = recipeCompound.getString("permission");
		PermissionRecipe permissionRecipe = new PermissionRecipe(group, width, height, ingredients, result, permission);
		if (recipeCompound.hasKey("key")) {
			permissionRecipe.setKey(NBTUtil.deserializeKey(recipeCompound.getCompound("key")));
		}		
		return permissionRecipe;
	}
	
	@Override
	public NBTTagCompound serializeToNbt() {
		NBTTagCompound serialized = super.serializeToNbt();
		serialized.setString("permission", getPermission());
		return serialized;
	}
	

	@Override
	public String getPermission() {
		return nmsRecipe.getPermission();
	}
	

}
