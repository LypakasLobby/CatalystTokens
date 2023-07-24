package com.lypaka.catalysttokens.Commands;

import com.lypaka.catalysttokens.ConfigGetters;
import com.lypaka.catalysttokens.GUIs.MainGUI;
import com.lypaka.lypakautils.FancyText;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class MenuCommand {

    public MenuCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterTokensCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                                    .then(
                                            Commands.literal("menu")
                                                    .executes(c -> {

                                                        if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                            ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                            if (ConfigGetters.useBetterGUIs) {

                                                                player.sendMessage(FancyText.getFormattedText("&cThis command is currently disabled!"), player.getUniqueID());
                                                                return 0;

                                                            } else {

                                                                MainGUI.open(player);

                                                            }

                                                        }

                                                        return 1;

                                                    })
                                    )
            );

        }

    }

}
