package com.lypaka.catalysttokens;

import com.google.common.reflect.TypeToken;
import com.lypaka.catalysttokens.API.GivePointsEvent;
import com.lypaka.catalysttokens.API.RemovePointsEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static boolean useBetterGUIs;
    public static String mainGUIName;
    public static int mainGUIRows;
    public static String mainGUIBorderID;
    public static int mainGUIBorderMeta;

    public static String message;

    public static String mainGUIBorderSlots;
    public static Map<String, Map<String, String>> mainGUIMenuSlots;
    public static List<String> menuList;

    public static boolean allowEvents;
    public static Map<String, Map<String, String>> eventsMap; // generified in the event the user removes some shit

    public static Map<String, Double> pointsMap;

    public static void load() throws ObjectMappingException {

        message = CatalystTokens.configManager.getConfigNode(0,"Message").getString();
        useBetterGUIs = CatalystTokens.configManager.getConfigNode(0, "BetterGUIs-Installed").getBoolean();
        mainGUIName = CatalystTokens.configManager.getConfigNode(0, "GUI", "Name").getString();
        mainGUIRows = CatalystTokens.configManager.getConfigNode(0, "GUI", "Rows").getInt();
        mainGUIBorderID = CatalystTokens.configManager.getConfigNode(0, "GUI", "Slots", "Border", "ID").getString();
        mainGUIBorderMeta = 0;
        if (!CatalystTokens.configManager.getConfigNode(0, "GUI", "Slots", "Border", "Meta").isVirtual()) {

            mainGUIBorderMeta = CatalystTokens.configManager.getConfigNode(0, "GUI", "Slots", "Border", "Meta").getInt();

        }
        mainGUIBorderSlots = CatalystTokens.configManager.getConfigNode(0, "GUI", "Slots", "Border", "Slots").getString();
        mainGUIMenuSlots = CatalystTokens.configManager.getConfigNode(0, "GUI", "Slots", "Menus").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        menuList = CatalystTokens.configManager.getConfigNode(0, "Menus").getList(TypeToken.of(String.class));

        allowEvents = CatalystTokens.configManager.getConfigNode(1, "Allow-Events").getBoolean();
        eventsMap = CatalystTokens.configManager.getConfigNode(1, "Events").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

        pointsMap = CatalystTokens.configManager.getConfigNode(2, "Points").getValue(new TypeToken<Map<String, Double>>() {});

    }

    public static int getIndexFromMenuName (String name) {

        int index = -1;
        for (int i = 0; i < menuList.size(); i++) {

            if (menuList.get(i).equalsIgnoreCase(name)) {

                index = i;
                break;

            }

        }

        return index;

    }

    public static void createAccount (ServerPlayerEntity player) {

        if (!pointsMap.containsKey(player.getUniqueID().toString())) {

            pointsMap.put(player.getUniqueID().toString(), 0.0);

        }

    }

    public static double getPlayerPoints (ServerPlayerEntity player) {

        double points = 0.0;
        if (pointsMap.containsKey(player.getUniqueID().toString())) {

            points = pointsMap.get(player.getUniqueID().toString());

        }

        return points;

    }

    public static boolean canBuy (ServerPlayerEntity player, double price) {

        double points = getPlayerPoints(player);
        return points >= price;

    }

    public static void addPoints (ServerPlayerEntity player, double pointsToAdd) {

        double points = getPlayerPoints(player);
        GivePointsEvent event = new GivePointsEvent(player, pointsToAdd);;
        MinecraftForge.EVENT_BUS.register(event);
        if (!event.isCanceled()) {

            double updated = points + event.getPoints();
            pointsMap.put(player.getUniqueID().toString(), updated);

        }

    }

    public static void removePoints (ServerPlayerEntity player, double pointsToRemove) {

        double points = getPlayerPoints(player);
        RemovePointsEvent event = new RemovePointsEvent(player, pointsToRemove);
        MinecraftForge.EVENT_BUS.register(event);
        if (!event.isCanceled()) {

            double updated = Math.max(0, points - event.getPoints());
            pointsMap.put(player.getUniqueID().toString(), updated);

        }

    }

}
