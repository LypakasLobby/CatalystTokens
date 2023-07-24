package com.lypaka.bettertokens.Commands;

import com.lypaka.bettertokens.BetterTokens;
import com.lypaka.bettertokens.ConfigGetters;
import com.lypaka.bettertokens.Listeners.EventRegistry;
import com.lypaka.bettertokens.TokenMenus.MenuRegistry;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
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

                                                    BetterTokens.configManager.getConfigNode(2, "Points").setValue(ConfigGetters.pointsMap);
                                                    BetterTokens.configManager.save();
                                                    BetterTokens.configManager.load();
                                                    ConfigGetters.load();
                                                    BetterTokens.menuConfigManager.setFileNames(ConfigGetters.menuList);
                                                    BetterTokens.menuConfigManager.load(); // reload your own fucking config managers you twat
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
