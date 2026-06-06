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

package com.sk89q.worldguard.util.i18n;

import com.sk89q.util.yaml.YAMLFormat;
import com.sk89q.util.yaml.YAMLProcessor;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads and resolves localized message bundles.
 */
public class LanguageManager {

    private static final Logger log = Logger.getLogger(LanguageManager.class.getCanonicalName());
    private static final String FALLBACK_LOCALE = "en";

    private final String locale;
    private final Map<String, String> messages;

    private LanguageManager(String locale, Map<String, String> messages) {
        this.locale = locale;
        this.messages = messages;
    }

    public String getLocale() {
        return locale;
    }

    public String translate(String key) {
        String value = messages.get(key);
        if (value != null) {
            return value;
        }
        log.log(Level.WARNING, "Missing translation key: {0}", key);
        return key;
    }

    public java.util.Set<String> getKeys() {
        return java.util.Collections.unmodifiableSet(messages.keySet());
    }

    public static java.util.Set<String> loadLocaleKeys(File langDir, String locale,
            @Nullable Function<String, InputStream> defaultResource) throws IOException {
        return loadLocaleFile(langDir, locale, defaultResource).keySet();
    }

    public static LanguageManager load(File langDir, String locale, Function<String, InputStream> defaultResource) throws IOException {
        langDir.mkdirs();
        Map<String, String> primary = loadLocaleFile(langDir, locale, defaultResource);
        Map<String, String> fallback = loadLocaleFile(langDir, FALLBACK_LOCALE, defaultResource);
        Map<String, String> merged = new HashMap<>(fallback);
        merged.putAll(primary);
        return new LanguageManager(locale, merged);
    }

    /**
     * Load bundled language files directly from classpath resources (no data folder).
     */
    public static LanguageManager loadFromResources(String locale,
            Function<String, InputStream> resourceLoader) throws IOException {
        java.nio.file.Path tempDir = java.nio.file.Files.createTempDirectory("wg-lang");
        File langDir = tempDir.toFile();
        langDir.deleteOnExit();
        copyBundledLocale(resourceLoader, langDir, FALLBACK_LOCALE);
        if (!locale.equals(FALLBACK_LOCALE)) {
            copyBundledLocale(resourceLoader, langDir, locale);
        }
        return load(langDir, locale, null);
    }

    private static void copyBundledLocale(Function<String, InputStream> resourceLoader, File langDir, String locale)
            throws IOException {
        try (InputStream in = resourceLoader.apply("lang/" + locale + ".yml")) {
            if (in != null) {
                copyStream(in, new File(langDir, locale + ".yml"));
            }
        }
    }

    private static Map<String, String> loadLocaleFile(File langDir, String locale,
            @Nullable Function<String, InputStream> defaultResource) throws IOException {
        File file = new File(langDir, locale + ".yml");
        if (!file.exists() && defaultResource != null) {
            try (InputStream in = defaultResource.apply("lang/" + locale + ".yml")) {
                if (in != null) {
                    copyStream(in, file);
                }
            }
        }
        if (!file.exists()) {
            log.warning("Language file not found: " + file.getAbsolutePath());
            return new HashMap<>();
        }
        YAMLProcessor yaml = new YAMLProcessor(file, false, YAMLFormat.EXTENDED);
        yaml.load();
        Map<String, String> flat = new HashMap<>();
        flatten("", yaml.getMap(), flat);
        return flat;
    }

    @SuppressWarnings("unchecked")
    private static void flatten(String prefix, Map<String, Object> map, Map<String, String> out) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                flatten(key, (Map<String, Object>) value, out);
            } else if (value != null) {
                out.put(key, String.valueOf(value));
            }
        }
    }

    private static void copyStream(InputStream input, File dest) throws IOException {
        File parent = dest.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (java.io.FileOutputStream output = new java.io.FileOutputStream(dest)) {
            byte[] buf = new byte[8192];
            int len;
            while ((len = input.read(buf)) > 0) {
                output.write(buf, 0, len);
            }
        }
    }

}
