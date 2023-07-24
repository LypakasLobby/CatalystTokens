package com.lypaka.catalysttokens.Listeners;

import com.lypaka.catalysttokens.ConfigGetters;
import com.lypaka.catalysttokens.TokenEvent;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class KillPokemonListener {

    @SubscribeEvent
    public void onKillPokemon (BeatWildPixelmonEvent event) {

        if (!ConfigGetters.allowEvents) return;
        if (EventRegistry.eventMap.containsKey("Killing-Pokemon")) {

            ServerPlayerEntity player = event.player;
            TokenEvent tokenEvent = EventRegistry.eventMap.get("Killing-Pokemon");
            if (!tokenEvent.getPermission().equalsIgnoreCase("")) {

                if (!PermissionHandler.hasPermission(player, tokenEvent.getPermission())) return;

            }

            if (RandomHelper.getRandomChance(tokenEvent.getChance()) || tokenEvent.getChance() >= 1.0) {

                double baseValue = tokenEvent.getBaseValue();
                double value;
                PixelmonEntity pokemon = event.wpp.controlledPokemon.get(0).entity;
                Map<String, Integer> modifiersMap = tokenEvent.getModifiersMap();
                if (pokemon.getPokemon().isShiny() && modifiersMap.containsKey("Shiny")) {

                    value = baseValue * modifiersMap.get("Shiny");

                } else if (PixelmonSpecies.getLegendaries(false).contains(pokemon.getSpecies().getDex()) && modifiersMap.containsKey("Legendary") ||
                        PixelmonSpecies.getUltraBeasts().contains(pokemon.getSpecies().getDex()) && modifiersMap.containsKey("Legendary")) {

                    value = baseValue * modifiersMap.get("Legendary");

                } else if (modifiersMap.containsKey("Bosses") && pokemon.isBossPokemon()) {

                    value = baseValue * modifiersMap.get("Bosses");

                } else {

                    value = baseValue;

                }

                ConfigGetters.addPoints(player, value);

            }

        }

    }

}
