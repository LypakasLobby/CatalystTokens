package com.lypaka.bettertokens.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.bettertokens.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.ItemStackBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBuilder;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Map;

public class MainGUI {

    public static void open (ServerPlayerEntity player) {

        ChestTemplate template = ChestTemplate.builder(ConfigGetters.mainGUIRows).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedString(ConfigGetters.mainGUIName))
                .build();

        String[] slotsString = ConfigGetters.mainGUIBorderSlots.split(", ");
        for (String s : slotsString) {

            page.getTemplate().getSlot(Integer.parseInt(s)).setButton(CommonButtons.getBorderButton(ConfigGetters.mainGUIBorderID, ConfigGetters.mainGUIBorderMeta));

        }

        for (Map.Entry<String, Map<String, String>> entry : ConfigGetters.mainGUIMenuSlots.entrySet()) {

            String slotID = entry.getKey();
            int slot = Integer.parseInt(slotID.replace("Slot-", ""));
            String id = entry.getValue().get("ID");
            String displayName = entry.getValue().get("Display-Name");
            int meta = 0;
            if (entry.getValue().containsKey("Meta")) {

                meta = Integer.parseInt(entry.getValue().get("Meta"));

            }
            String menu = entry.getValue().get("Menu");
            page.getTemplate().getSlot(slot).setButton(getMenuButton(player, id, displayName, meta, menu));

        }

        UIManager.openUIForcefully(player, page);

    }

    private static Button getMenuButton (ServerPlayerEntity player, String id, String displayName, int meta, String menu) {

        ItemStack item;
        if (id.contains("pixelmon:pixelmon_sprite")) {

            String form = "";
            String[] split = id.split("/");
            String species = "Bulbasaur";
            boolean shiny = false;
            for (String spec : split) {

                if (spec.contains("form:")) {

                    form = spec.replace("form:", "");

                } else if (spec.contains("species")) {

                    species = spec.replace("species:", "");

                } else if (spec.contains("shiny")) {

                    shiny = Boolean.parseBoolean(spec.replace("shiny:", ""));

                }

            }

            item = SpriteItemHelper.getPhoto(PokemonBuilder.builder().species(species).form(form).shiny(shiny).build());

        } else {

            item = ItemStackBuilder.buildFromStringID(id);
            if (meta > 0) {

                item.setDamage(meta);

            }

        }

        item.setDisplayName(FancyText.getFormattedText(displayName));
        return GooeyButton.builder()
                .display(item)
                .onClick(() -> {

                    try {

                        MenuOpener.openMenu(player, menu);

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                })
                .build();

    }

}
