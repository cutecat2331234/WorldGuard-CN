/*
 * WorldGuard, a suite of tools for Minecraft
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldguard.bukkit.util;

import com.sk89q.util.yaml.YAMLFormat;
import com.sk89q.util.yaml.YAMLProcessor;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.util.i18n.I18n;
import com.sk89q.worldguard.util.i18n.LanguageManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import java.util.logging.Level;

public final class I18nLoader {

    private I18nLoader() {
    }

    public static void load(WorldGuardPlugin plugin) {
        File dataFolder = plugin.getDataFolder();
        dataFolder.mkdirs();
        plugin.createDefaultConfiguration(new File(dataFolder, "config.yml"), "config.yml");
        plugin.createDefaultConfiguration(new File(dataFolder, "lang/zh_CN.yml"), "lang/zh_CN.yml");
        plugin.createDefaultConfiguration(new File(dataFolder, "lang/en.yml"), "lang/en.yml");

        String locale = "zh_CN";
        try {
            YAMLProcessor config = new YAMLProcessor(new File(dataFolder, "config.yml"), true, YAMLFormat.EXTENDED);
            config.load();
            locale = config.getString("language", "zh_CN");
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Failed to read language from config.yml, using zh_CN", e);
        }

        Function<String, InputStream> bundled = name -> plugin.getResource("defaults/" + name);
        File langDir = new File(dataFolder, "lang");
        try {
            I18n.init(LanguageManager.load(langDir, locale, bundled));
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE,
                    "Failed to load language files from " + langDir.getAbsolutePath() + ", using bundled defaults", e);
            try {
                I18n.init(LanguageManager.loadFromResources(locale, bundled));
            } catch (IOException e2) {
                plugin.getLogger().log(Level.SEVERE, "Failed to load bundled language files", e2);
            }
        }
    }

    public static void reload(WorldGuardPlugin plugin) {
        load(plugin);
    }

}
