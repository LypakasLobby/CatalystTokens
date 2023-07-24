package com.lypaka.bettertokens.Listeners;

import com.lypaka.bettertokens.BetterTokens;
import com.lypaka.bettertokens.ConfigGetters;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber(modid = BetterTokens.MOD_ID)
public class ServerShuttingDownListener {

    @SubscribeEvent
    public static void onServerShutdown (FMLServerStoppingEvent event) {

        BetterTokens.configManager.getConfigNode(2, "Points").setValue(ConfigGetters.pointsMap);
        BetterTokens.configManager.save();

    }

}
