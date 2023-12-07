package com.lypaka.catalysttokens.Listeners;

import com.lypaka.catalysttokens.ConfigGetters;
import com.lypaka.catalysttokens.EventRegistry;
import com.lypaka.catalysttokens.TokenEvent;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.pixelmonmod.pixelmon.api.events.BeatTrainerEvent;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NPCDefeatListener {

    @SubscribeEvent
    public void onNPCDefeat (BeatTrainerEvent event) {

        if (!ConfigGetters.allowEvents) return;
        if (EventRegistry.eventMap.containsKey("Defeating-NPC-Trainers")) {

            ServerPlayerEntity player = event.player;
            TokenEvent tokenEvent = EventRegistry.eventMap.get("Defeating-NPC-Trainers");
            if (!tokenEvent.getPermission().equalsIgnoreCase("")) {

                if (!PermissionHandler.hasPermission(player, tokenEvent.getPermission())) return;

            }

            if (RandomHelper.getRandomChance(tokenEvent.getChance()) || tokenEvent.getChance() >= 1.0) {

                double baseValue = tokenEvent.getBaseValue();
                double value;
                if (tokenEvent.doModification()) {

                    int level = event.trainer.pokemonLevel;
                    value = baseValue * level;

                } else {

                    value = baseValue;

                }

                ConfigGetters.addPoints(player, value);

            }

        }

    }

}
