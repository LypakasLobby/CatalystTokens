package com.lypaka.catalysttokens.Listeners;

import com.lypaka.catalysttokens.CatalystTokens;
import com.lypaka.catalysttokens.ConfigGetters;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber(modid = CatalystTokens.MOD_ID)
public class ServerShuttingDownListener {

    @SubscribeEvent
    public static void onServerShutdown (FMLServerStoppingEvent event) {

        CatalystTokens.configManager.getConfigNode(2, "Points").setValue(ConfigGetters.pointsMap);
        CatalystTokens.configManager.save();

    }

}
