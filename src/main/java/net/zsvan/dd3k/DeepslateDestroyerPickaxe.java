package net.zsvan.dd3k;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.world.World;

public class DeepslateDestroyerPickaxe extends PickaxeItem {
    private PlayerEntity currentPlayer;
    private static final float BUFF_MULTIPLIER = 30.0f; // Increased for instamining

    public DeepslateDestroyerPickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            currentPlayer = player;
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        float baseSpeed = super.getMiningSpeedMultiplier(stack, state);

        if (!isDeepslate(state) || currentPlayer == null) {
            return baseSpeed;
        }

        if (isBuffActive(stack)) {
            // Apply additional multipliers when buffed
            float speedMultiplier = BUFF_MULTIPLIER;

            if (currentPlayer.hasStatusEffect(StatusEffects.HASTE)) {
                int hasteLevel = currentPlayer.getStatusEffect(StatusEffects.HASTE).getAmplifier() + 1;
                speedMultiplier *= (1.0f + 0.2f * hasteLevel);
            }

            return baseSpeed * speedMultiplier;
        }

        return baseSpeed;
    }

    private boolean isDeepslate(BlockState state) {
        return state.getBlock().toString().contains("deepslate");
    }

    public boolean isBuffActive(ItemStack stack) {
        if (currentPlayer == null) {
            return false;
        }

        boolean hasHasteII = currentPlayer.getStatusEffects().stream()
                .anyMatch(effect -> effect.getEffectType() == StatusEffects.HASTE
                        && effect.getAmplifier() >= 1);
        int efficiencyLevel = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);

        return hasHasteII && efficiencyLevel >= 5;
    }
}