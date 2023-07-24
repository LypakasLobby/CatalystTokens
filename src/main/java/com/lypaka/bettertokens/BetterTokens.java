package com.lypaka.bettertokens;

import com.lypaka.bettertokens.Listeners.EventRegistry;
import com.lypaka.bettertokens.TokenMenus.MenuRegistry;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("bettertokens")
public class BetterTokens {

    public static final String MOD_ID = "bettertokens";
    public static final String MOD_NAME = "BetterTokens";
    public static final String VERSION = "1.0.0";
    public static Logger logger = LogManager.getLogger(MOD_NAME);
    public static BasicConfigManager configManager;
    public static ComplexConfigManager menuConfigManager;

    public BetterTokens() throws IOException, ObjectMappingException {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/bettertokens"));
        String[] files = new String[]{"bettertokens.conf", "events.conf", "storage.conf"};
        configManager = new BasicConfigManager(files, dir, BetterTokens.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        ConfigUpdater.updateConfig();
        ConfigGetters.load();
        menuConfigManager = new ComplexConfigManager(ConfigGetters.menuList, "menu-files", "template.conf", dir, BetterTokens.class, MOD_NAME, MOD_ID, logger);
        menuConfigManager.init();
        MenuRegistry.load();
        EventRegistry.load();

    }

}
