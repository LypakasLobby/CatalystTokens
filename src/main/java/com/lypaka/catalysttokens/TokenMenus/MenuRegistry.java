package com.lypaka.catalysttokens.TokenMenus;

import com.google.common.reflect.TypeToken;
import com.lypaka.catalysttokens.CatalystTokens;
import com.lypaka.catalysttokens.ConfigGetters;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuRegistry {

    public static Map<String, Menu> menuMap;

    public static void load() throws ObjectMappingException {

        menuMap = new HashMap<>();
        for (int i = 0; i < ConfigGetters.menuList.size(); i++) {

            String menuName = ConfigGetters.menuList.get(i);
            int rows = CatalystTokens.menuConfigManager.getConfigNode(i, "Rows").getInt();
            Map<String, String> borderMap = CatalystTokens.menuConfigManager.getConfigNode(i, "Border").getValue(new TypeToken<Map<String, String>>() {});
            String displayName = CatalystTokens.menuConfigManager.getConfigNode(i, "Display-Name").getString();
            Map<String, Map<String, String>> slotsMap = CatalystTokens.menuConfigManager.getConfigNode(i, "Slots").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> canBuy = CatalystTokens.menuConfigManager.getConfigNode(i, "Lore", "Can-Buy").getList(TypeToken.of(String.class));
            List<String> canNotBuy = CatalystTokens.menuConfigManager.getConfigNode(i, "Lore", "Can-Not-Buy").getList(TypeToken.of(String.class));
            Menu menu = new Menu(menuName, i, rows, borderMap, displayName, canBuy, canNotBuy, slotsMap);
            menu.register();

        }

    }

}
