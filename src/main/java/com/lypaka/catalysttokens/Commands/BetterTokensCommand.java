package com.lypaka.catalysttokens.Commands;

import com.lypaka.catalysttokens.CatalystTokens;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = CatalystTokens.MOD_ID)
public class BetterTokensCommand {

    public static List<String> ALIASES = Arrays.asList("bettertokens", "btokens", "tokens");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new CheckCommand(event.getDispatcher());
        new MenuCommand(event.getDispatcher());
        new OpenCommand(event.getDispatcher());
        new PointsCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
