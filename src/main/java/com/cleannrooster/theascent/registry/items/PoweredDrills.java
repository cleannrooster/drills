package com.cleannrooster.theascent.registry.items;

import com.cleannrooster.theascent.Ascent;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.cleannrooster.theascent.Ascent.HEAT;

public class PoweredDrills extends MiningDrill{

    public PoweredDrills(Settings maxDamage) {
        super(maxDamage);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getOffHandStack().getItem() instanceof PoweredDrillsLeft && user.getMainHandStack().getItem() instanceof PoweredDrills) {
            NbtCompound nbtCompound = user.getStackInHand(hand).getOrCreateNbt();
            destroyBlocks(user.getBoundingBox(), world, user);
            if (nbtCompound.contains("heat")) {
                if (nbtCompound.getInt("heat") < 88) {
                    nbtCompound.putInt("heat", nbtCompound.getInt("heat") + 12);
                    user.getItemCooldownManager().set(this, 6);
                } else {
                    if (!user.isCreative()) {
                        user.getItemCooldownManager().set(ModItems.powereddrills, 160);
                        user.getItemCooldownManager().set(ModItems.left, 160);
                    }
                }
            } else {
                nbtCompound.putInt("heat", 20);
                user.getItemCooldownManager().set(this, 6);

            }

            return TypedActionResult.success(user.getStackInHand(hand));
        }
        else{
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            NbtCompound nbtCompound = stack.getOrCreateNbt();
            if (nbtCompound.contains("heat")) {
                if (nbtCompound.getInt("heat") > 0) {
                    nbtCompound.putInt("heat", nbtCompound.getInt("heat") -1);
                }
                if (selected) {
                    player.sendMessage(Text.of("Heat: " + (int) (nbtCompound.getInt("heat")) + "%"), true);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
