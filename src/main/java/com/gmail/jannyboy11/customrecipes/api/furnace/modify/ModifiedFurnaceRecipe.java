package com.gmail.jannyboy11.customrecipes.api.furnace.modify;

import com.gmail.jannyboy11.customrecipes.api.furnace.FurnaceRecipe;

public interface ModifiedFurnaceRecipe<R extends FurnaceRecipe> extends FurnaceRecipe {
    
    public R getBaseRecipe();
    
    public FurnaceModifier getModifier();

}