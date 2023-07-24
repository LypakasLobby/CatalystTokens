package com.lypaka.catalysttokens.Commands;

import com.lypaka.catalysttokens.TokenMenus.Menu;
import com.lypaka.catalysttokens.TokenMenus.MenuRegistry;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class OpenCommand  {

    private static final SuggestionProvider<CommandSource> MENUS = (context, builder) ->
            ISuggestionProvider.suggest(MenuRegistry.menuMap.keySet(), builder);

    public OpenCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterTokensCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("open")
                                            .then(
                                                    Commands.argument("player", EntityArgument.player())
                                                            .then(
                                                                    Commands.argument("menu", StringArgumentType.word())
                                                                            .suggests(MENUS)
                                                                            .executes(c -> {

                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                    if (!PermissionHandler.hasPermission(player, "bettertokens.command.admin")) {

                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                        return 0;

                                                                                    }

                                                                                }

                                                                                ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                String menu = StringArgumentType.getString(c, "menu");
                                                                                if (!menu.contains(".conf")) {

                                                                                    menu = menu + ".conf";

                                                                                }
                                                                                if (!MenuRegistry.menuMap.containsKey(menu)) {

                                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&cNo menu with this name exist!"));
                                                                                    return 0;

                                                                                }
                                                                                Menu m = MenuRegistry.menuMap.get(menu);
                                                                                try {

                                                                                    m.openMenu(target);
                                                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aOpening " + menu + " to " + target.getName().getString() + "..."), true);

                                                                                } catch (ObjectMappingException e) {

                                                                                    e.printStackTrace();

                                                                                }
                                                                                return 1;

                                                                            })
                                                            )
                                            )
                            )
            );

        }

    }

}
