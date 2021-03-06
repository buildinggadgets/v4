package com.direwolf20.core.traits.upgrade;

import com.direwolf20.core.DireCore20;
import com.direwolf20.core.traits.Trait;
import com.google.common.collect.ImmutableSet;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.UnaryOperator;

/**
 * An Upgrade type which can be installed in an {@link com.direwolf20.core.traits.ITraitContainer TraitContainer} to modify one or more
 * {@link Trait Traits}. Notice that as of this writing, there is no ordering imposed on Modifications - order may be arbitrary!
 * <p>
 * The Upgrade decides upon the effects of installing it, whereas the {@link UpgradeStack} provides additional runtime Information
 * (for example the level of the upgrade). The connection between an Upgrade and an {@link UpgradeStack} as similar to the one between
 * an Item and an ItemStack...
 */
public abstract class Upgrade extends ForgeRegistryEntry<Upgrade> {
    public static final ResourceLocation UPGRADE_BLANK_RL = new ResourceLocation(DireCore20.MOD_ID + ":blank");

    private final ImmutableSet<Trait<?>> modifcations;

    public Upgrade(ImmutableSet<Trait<?>> modifcations) {
        this.modifcations = modifcations;
    }

    public final ImmutableSet<Trait<?>> getAppliedModifications() {
        return modifcations;
    }

    protected abstract boolean isValidLevel(int level);

    /**
     * @param trait The {@link Trait} for which to retrieve a modification function.
     * @param tier  The {@link UpgradeStack} representing additional data for this upgrade.
     * @param <T>   The type of the trait. As this also defines the type of values represented by it, it also obviously defines the type on which the
     *              modification function operates on.
     * @return the Modification Function for the given Trait. May only be null, if the trait was no in the Set returned by {@link #getAppliedModifications()}
     * or if the data in the {@link UpgradeStack} did not match
     */
    public abstract <T> UnaryOperator<T> getModificatorFor(Trait<T> trait, UpgradeStack tier);
}
