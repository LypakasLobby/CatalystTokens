package com.lypaka.catalysttokens;


import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ConfigUpdater {

    public static void updateConfig() throws ObjectMappingException {

        boolean needsSaving = false;

        if (CatalystTokens.configManager.getConfigNode(0, "Message").isVirtual()) {

            needsSaving = true;
            CatalystTokens.configManager.getConfigNode(0, "Message").setComment("Sent to the player when the check tokens command is ran");
            CatalystTokens.configManager.getConfigNode(0,"Message").setValue("You have %amount% points!");


        }
        if (needsSaving) {

            CatalystTokens.configManager.save();
        }
    }

}
