package com.lypaka.catalysttokens.Commands;

import com.lypaka.lypakautils.FancyText;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.text.DecimalFormat;

import static com.lypaka.catalysttokens.ConfigGetters.getPlayerPoints;
import static com.lypaka.catalysttokens.ConfigGetters.message;

public class CheckCommand {

    private int amount;

    public CheckCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterTokensCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("check")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();


                                                    if ( getPlayerPoints(player) <= 0.0) {

                                                            amount = 0;
                                                        } else {
                                                            DecimalFormat df = new DecimalFormat("#");
                                                            amount = Integer.parseInt(df.format(getPlayerPoints(player)));
                                                        }
                                                    player.sendMessage(FancyText.getFormattedText(message.replace("%amount%" ,String.valueOf(amount))), player.getUniqueID());


                                                }



                                                return 1;

                                            })
                            )
            );

        }

    }

}
