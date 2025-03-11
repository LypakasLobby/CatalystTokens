package com.lypaka.catalysttokens.Commands;

import com.lypaka.catalysttokens.CatalystTokens;
import com.lypaka.catalysttokens.ConfigGetters;
import com.lypaka.catalysttokens.EventRegistry;
import com.lypaka.catalysttokens.TokenMenus.MenuRegistry;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Map;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTokensCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "catalysttokens.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 0;

                                                    }

                                                }

                                                try {

                                                    Map<String, Double> temp = ConfigGetters.pointsMap;
                                                    CatalystTokens.configManager.load();
                                                    CatalystTokens.configManager.getConfigNode(2, "Points").setValue(temp);
                                                    CatalystTokens.configManager.save();
                                                    ConfigGetters.load();
                                                    CatalystTokens.menuConfigManager.setFileNames(ConfigGetters.menuList);
                                                    CatalystTokens.menuConfigManager.load(); // reload your own fucking config managers you twat
                                                    MenuRegistry.load();
                                                    EventRegistry.load();
                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded CatalystTokens!"), true);

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
