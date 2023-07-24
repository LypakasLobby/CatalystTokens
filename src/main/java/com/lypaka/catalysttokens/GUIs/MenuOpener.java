package com.lypaka.catalysttokens.GUIs;

import com.lypaka.catalysttokens.CatalystTokens;
import com.lypaka.catalysttokens.TokenMenus.Menu;
import com.lypaka.catalysttokens.TokenMenus.MenuRegistry;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class MenuOpener {

    public static void openMenu (ServerPlayerEntity player, String menuName) throws ObjectMappingException {

        if (MenuRegistry.menuMap.containsKey(menuName)) {

            Menu menu = MenuRegistry.menuMap.get(menuName);
            menu.openMenu(player);

        } else {

            CatalystTokens.logger.error("Could not find menu with name: " + menuName);

        }

    }

}
