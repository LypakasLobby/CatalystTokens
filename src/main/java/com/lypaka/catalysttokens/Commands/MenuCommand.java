package com.lypaka.catalysttokens.Commands;

import com.lypaka.catalysttokens.GUIs.MainGUI;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class MenuCommand {

    public MenuCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTokensCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                                    .then(
                                            Commands.literal("menu")
                                                    .executes(c -> {

                                                        if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                            ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                            MainGUI.open(player);

                                                        }

                                                        return 1;

                                                    })
                                    )
            );

        }

    }

}
