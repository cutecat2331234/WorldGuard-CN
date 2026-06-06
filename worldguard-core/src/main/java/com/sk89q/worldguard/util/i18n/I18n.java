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

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Static facade for localized messages.
 */
public final class I18n {

    private static LanguageManager manager;
    @Nullable
    private static Runnable reloadCallback;

    private I18n() {
    }

    public static void init(LanguageManager languageManager) {
        manager = languageManager;
    }

    public static boolean isLoaded() {
        return manager != null;
    }

    @Nullable
    public static LanguageManager getManager() {
        return manager;
    }

    public static void reload(LanguageManager languageManager) {
        manager = languageManager;
    }

    public static void setReloadCallback(@Nullable Runnable callback) {
        reloadCallback = callback;
    }

    public static void runReloadCallback() {
        if (reloadCallback != null) {
            reloadCallback.run();
        }
    }

    public static String tr(String key) {
        if (manager == null) {
            return key;
        }
        return manager.translate(key);
    }

    public static String tr(String key, String placeholder, String value) {
        return tr(key, Collections.singletonMap(placeholder, value));
    }

    public static String tr(String key, Map<String, String> placeholders) {
        String message = tr(key);
        if (placeholders == null || placeholders.isEmpty()) {
            return message;
        }
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return message;
    }

    public static String tr(String key, String k1, String v1, String k2, String v2) {
        Map<String, String> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return tr(key, map);
    }

    /**
     * Resolve an i18n command description key from an annotation value.
     */
    public static String trCommandDesc(String annotationDesc) {
        if (annotationDesc != null && annotationDesc.startsWith("i18n:")) {
            return tr(annotationDesc.substring("i18n:".length()));
        }
        return tr("cmd." + annotationDesc.replace(' ', '_'));
    }

}
