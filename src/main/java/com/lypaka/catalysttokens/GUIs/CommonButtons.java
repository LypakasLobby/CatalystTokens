package com.lypaka.catalysttokens.GUIs;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import net.minecraft.item.ItemStack;

public class CommonButtons {

    public static Button getBorderButton (String id, int meta) {

        ItemStack item = ItemStackBuilder.buildFromStringID(id);
        if (meta > 0) {

            item.setDamage(meta);

        }

        item.setDisplayName(FancyText.getFormattedText(""));
        return GooeyButton.builder().display(item).build();

    }

}
