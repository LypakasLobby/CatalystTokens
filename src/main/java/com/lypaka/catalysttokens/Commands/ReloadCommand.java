package com.lypaka.catalysttokens.Commands;

import com.lypaka.catalysttokens.CatalystTokens;
import com.lypaka.catalysttokens.ConfigGetters;
import com.lypaka.catalysttokens.Listeners.EventRegistry;
import com.lypaka.catalysttokens.TokenMenus.MenuRegistry;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterTokensCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "bettertokens.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 0;

                                                    }

                                                }

                                                try {

                                                    CatalystTokens.configManager.getConfigNode(2, "Points").setValue(ConfigGetters.pointsMap);
                                                    CatalystTokens.configManager.save();
                                                    CatalystTokens.configManager.load();
                                                    ConfigGetters.load();
                                                    CatalystTokens.menuConfigManager.setFileNames(ConfigGetters.menuList);
                                                    CatalystTokens.menuConfigManager.load(); // reload your own fucking config managers you twat
                                                    MenuRegistry.load();
                                                    EventRegistry.load();
                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded BetterTokens!"), true);

                                                } catch (ObjectMappingException e) {

                                                    e.printStackTrace();

                                                }

                                                return 1;

                                            })
                            )
            );

        }

    }

}
