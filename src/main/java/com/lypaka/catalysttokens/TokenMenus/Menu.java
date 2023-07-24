package com.lypaka.catalysttokens.TokenMenus;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.google.common.reflect.TypeToken;
import com.lypaka.catalysttokens.CatalystTokens;
import com.lypaka.catalysttokens.ConfigGetters;
import com.lypaka.catalysttokens.GUIs.CommonButtons;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBuilder;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class Menu {

    private final String menuName;
    private final int index;
    private final int rows;
    private final Map<String, String> borderMap;
    private final String displayName;
    private final List<String> canBuyLore;
    private final List<String> canNotBuyLore;
    private final Map<String, Map<String, String>> slotsMap;

    public Menu (String menuName, int index, int rows, Map<String, String> borderMap, String displayName, List<String> canBuyLore, List<String> canNotBuyLore, Map<String, Map<String, String>> slotsMap) {

        this.menuName = menuName;
        this.index = index;
        this.rows = rows;
        this.borderMap = borderMap;
        this.displayName = displayName;
        this.canBuyLore = canBuyLore;
        this.canNotBuyLore = canNotBuyLore;
        this.slotsMap = slotsMap;

    }

    public void register() {

        MenuRegistry.menuMap.put(this.menuName, this);

    }

    public void openMenu (ServerPlayerEntity player) throws ObjectMappingException {

        ChestTemplate template = ChestTemplate.builder(this.rows).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedString(this.displayName))
                .build();

        if (!this.borderMap.isEmpty()) {

            String id = this.borderMap.get("ID");
            int meta = 0;
            if (this.borderMap.containsKey("Meta")) {

                meta = Integer.parseInt(this.borderMap.get("Meta"));

            }
            String[] slots = this.borderMap.get("Slots").split(", ");
            for (String s : slots) {

                page.getTemplate().getSlot(Integer.parseInt(s)).setButton(CommonButtons.getBorderButton(id, meta));

            }

        }
        for (Map.Entry<String, Map<String, String>> entry : this.slotsMap.entrySet()) {

            int slot = Integer.parseInt(entry.getKey().replace("Slot-", ""));
            String displayName = entry.getValue().get("Display-Name");
            String id = entry.getValue().get("ID");
            int meta = 0;
            if (entry.getValue().containsKey("Meta")) {

                meta = Integer.parseInt(entry.getValue().get("Meta"));

            }
            double price = Double.parseDouble(entry.getValue().get("Price"));
            ListNBT lore = new ListNBT();
            ItemStack icon = getItemStack(player, displayName, id, meta);
            if (ConfigGetters.canBuy(player, price)) {

                List<String> commands = CatalystTokens.menuConfigManager.getConfigNode(index, "Slots", entry.getKey(), "Redeems").getList(TypeToken.of(String.class));
                List<String> loreStrings = this.canBuyLore;
                for (String s : loreStrings) {

                    lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s.replace("%price%", String.valueOf(price))))));

                }

                icon.getOrCreateChildTag("display").put("Lore", lore);
                page.getTemplate().getSlot(slot).setButton(

                        GooeyButton.builder()
                                .display(icon)
                                .onClick(() -> {

                                    try {

                                        processRedemption(player, price, commands);

                                    } catch (ObjectMappingException e) {

                                        e.printStackTrace();

                                    }

                                })
                                .build()

                );

            } else {

                List<String> loreStrings = this.canNotBuyLore;
                for (String s : loreStrings) {

                    lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s.replace("%price%", String.valueOf(price))))));

                }

                icon.getOrCreateChildTag("display").put("Lore", lore);
                page.getTemplate().getSlot(slot).setButton(

                        GooeyButton.builder()
                                .display(icon)
                                .build()

                );

            }

        }

        UIManager.openUIForcefully(player, page);

    }

    private void processRedemption (ServerPlayerEntity player, double price, List<String> commands) throws ObjectMappingException {

        ConfigGetters.removePoints(player, price);
        for (String c : commands) {

            player.world.getServer().getCommandManager().handleCommand(player.world.getServer().getCommandSource(), c.replace("%player%", player.getName().getString()));

        }
        openMenu(player); // refreshes the icons to update the lore if the player no longer has the points to redeem for something

    }

    private ItemStack getItemStack (ServerPlayerEntity player, String displayName, String id, int meta) {

        ItemStack item;
        if (id.contains("pixelmon:pixelmon_sprite")) {

            String form = "default";
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
        return item;

    }

    public String getMenuName() {

        return this.menuName;

    }

    public int getIndex() {

        return this.index;

    }

    public int getRows() {

        return this.rows;

    }

    public Map<String, String> getBorderMap() {

        return this.borderMap;

    }

    public String getDisplayName() {

        return this.displayName;

    }

    public Map<String, Map<String, String>> getSlotsMap() {

        return this.slotsMap;

    }

}
