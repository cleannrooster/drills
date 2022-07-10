package com.cleannrooster.theascent;

import com.cleannrooster.theascent.registry.items.ModItems;
import com.cleannrooster.theascent.registry.items.StatusEffectsModded;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.util.InputUtil;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;


public class Ascent implements ModInitializer {

    public static final String MOD_ID = "theascent";
    public static final Identifier HEAT = new Identifier(MOD_ID, "heat");
    @Override
    public void onInitialize() {
        Registry.register(Registry.CUSTOM_STAT, "interact_with_cool_block", HEAT);
        Stats.CUSTOM.getOrCreateStat(HEAT, StatFormatter.DEFAULT);
        ModItems.registerItems();
        StatusEffectsModded.registerStatusEffects();
        /*ServerPlayNetworking.registerGlobalReceiver(R_IDENTIFIER, (server, player, handler, buf, responseSender) -> {
            boolean val = buf.readBoolean();
            if (val == false){
                player.sendSystemMessage(new LiteralText("Your inventory is full."), Util.NIL_UUID);
                server.execute(() -> player.setRiptideTicks(20));
            }
        });*/

    }
}

