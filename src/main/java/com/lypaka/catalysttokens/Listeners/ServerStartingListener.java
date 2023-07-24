package com.lypaka.catalysttokens.Listeners;

import com.lypaka.catalysttokens.CatalystTokens;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = CatalystTokens.MOD_ID)
public class ServerStartingListener {

    @SubscribeEvent
    public static void onServerStarting (FMLServerStartingEvent event) {

        Pixelmon.EVENT_BUS.register(new CaptureListener());
        Pixelmon.EVENT_BUS.register(new KillPokemonListener());
        Pixelmon.EVENT_BUS.register(new NPCDefeatListener());

    }

}
