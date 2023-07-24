package com.lypaka.bettertokens.GUIs;

import com.lypaka.bettertokens.BetterTokens;
import com.lypaka.bettertokens.TokenMenus.Menu;
import com.lypaka.bettertokens.TokenMenus.MenuRegistry;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class MenuOpener {

    public static void openMenu (ServerPlayerEntity player, String menuName) throws ObjectMappingException {

        if (MenuRegistry.menuMap.containsKey(menuName)) {

            Menu menu = MenuRegistry.menuMap.get(menuName);
            menu.openMenu(player);

        } else {

            BetterTokens.logger.error("Could not find menu with name: " + menuName);

        }

    }

}
