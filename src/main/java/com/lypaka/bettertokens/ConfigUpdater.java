package com.lypaka.bettertokens;


import com.google.common.reflect.TypeToken;
import com.lypaka.bettertokens.BetterTokens;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigUpdater {

    public static void updateConfig() throws ObjectMappingException {

        boolean needsSaving = false;

        if (BetterTokens.configManager.getConfigNode(0, "Message").isVirtual()) {

            needsSaving = true;
            BetterTokens.configManager.getConfigNode(0, "Message").setComment("Sent to the player when the check tokens command is ran");
            BetterTokens.configManager.getConfigNode(0,"Message").setValue("You have %amount% points!");


        }
        if (needsSaving) {

            BetterTokens.configManager.save();
        }
    }

}
