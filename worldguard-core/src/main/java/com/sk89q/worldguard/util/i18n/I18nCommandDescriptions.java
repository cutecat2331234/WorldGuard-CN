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

import com.sk89q.minecraft.util.commands.CommandsManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Replaces {@code i18n:} command description placeholders after command registration.
 */
public final class I18nCommandDescriptions {

    private static final Logger log = Logger.getLogger(I18nCommandDescriptions.class.getCanonicalName());
    private static final String PREFIX = "i18n:";
    private static final Map<String, String> DESCRIPTION_KEYS = new ConcurrentHashMap<>();

    private I18nCommandDescriptions() {
    }

    public static void patch(CommandsManager<?> manager) {
        if (!I18n.isLoaded()) {
            return;
        }
        patchMap(manager, "descs");
        patchMap(manager, "helpMessages");
    }

    private static void patchMap(CommandsManager<?> manager, String fieldName) {
        try {
            Field field = CommandsManager.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, String> map = (Map<String, String>) field.get(manager);
            for (Map.Entry<String, String> entry : new HashMap<>(map).entrySet()) {
                String alias = entry.getKey();
                String desc = entry.getValue();
                if (desc != null && desc.startsWith(PREFIX)) {
                    String key = desc.substring(PREFIX.length());
                    DESCRIPTION_KEYS.put(alias, key);
                    map.put(alias, I18n.tr(key));
                } else {
                    String key = DESCRIPTION_KEYS.get(alias);
                    if (key != null) {
                        map.put(alias, I18n.tr(key));
                    }
                }
            }
        } catch (ReflectiveOperationException e) {
            log.log(Level.WARNING, "Failed to patch command descriptions (" + fieldName + ")", e);
        }
    }

}
