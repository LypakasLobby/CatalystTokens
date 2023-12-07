package com.lypaka.catalysttokens.Commands;

import com.lypaka.catalysttokens.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class PointsCommand  {

    public PointsCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTokensCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("points")
                                            .then(
                                                    Commands.literal("add")
                                                            .then(
                                                                    Commands.argument("player", EntityArgument.player())
                                                                            .then(
                                                                                    Commands.argument("amount", IntegerArgumentType.integer(1))
                                                                                            .executes(c -> {

                                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                                    if (!PermissionHandler.hasPermission(player, "catalysttokens.command.admin")) {

                                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                                        return 0;

                                                                                                    }

                                                                                                }

                                                                                                ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                                int amount = IntegerArgumentType.getInteger(c, "amount");
                                                                                                ConfigGetters.addPoints(target, amount);
                                                                                                return 1;

                                                                                            })
                                                                            )
                                                            )
                                            )
                                            .then(
                                                    Commands.literal("remove")
                                                            .then(
                                                                    Commands.argument("player", EntityArgument.player())
                                                                            .then(
                                                                                    Commands.argument("amount", IntegerArgumentType.integer(1))
                                                                                            .executes(c -> {

                                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                                    if (!PermissionHandler.hasPermission(player, "catalysttokens.command.admin")) {

                                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                                        return 0;

                                                                                                    }

                                                                                                }

                                                                                                ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                                int amount = IntegerArgumentType.getInteger(c, "amount");
                                                                                                ConfigGetters.removePoints(target, amount);
                                                                                                return 1;

                                                                                            })
                                                                            )
                                                            )
                                            )
                            )
            );

        }

    }

}
