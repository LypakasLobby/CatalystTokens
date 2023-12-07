package com.lypaka.catalysttokens;

import com.google.common.reflect.TypeToken;
import com.lypaka.catalysttokens.CatalystTokens;
import com.lypaka.catalysttokens.ConfigGetters;
import com.lypaka.catalysttokens.TokenEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.HashMap;
import java.util.Map;

public class EventRegistry {

    public static Map<String, TokenEvent> eventMap;

    public static void load() throws ObjectMappingException {

        eventMap = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : ConfigGetters.eventsMap.entrySet()) {

            String name = entry.getKey();
            double baseValue = Double.parseDouble(entry.getValue().get("Base-Value"));
            double chance = Double.parseDouble(entry.getValue().get("Chance"));
            String permission = entry.getValue().get("Permission");
            if (entry.getValue().containsKey("Modifiers")) {

                Map<String, Integer> modifiersMap = CatalystTokens.configManager.getConfigNode(1, "Events", name, "Modifiers").getValue(new TypeToken<Map<String, Integer>>() {});
                TokenEvent event = new TokenEvent(name, baseValue, chance, modifiersMap, permission);
                event.register();

            } else {

                boolean modify = Boolean.parseBoolean(entry.getValue().get("Modify"));
                TokenEvent event = new TokenEvent(name, baseValue, chance, modify, permission);
                event.register();

            }

        }

    }

}
