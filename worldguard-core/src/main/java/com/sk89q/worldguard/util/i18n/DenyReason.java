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
import java.util.HashMap;
import java.util.Map;

/**
 * Identifies why a player was denied an action, mapped to i18n action keys.
 */
public enum DenyReason {

    PLACE_FIRE("deny.action.place_fire"),
    USE_FROSTWALKER("deny.action.use_frostwalker"),
    PLACE_BLOCK("deny.action.place_block"),
    USE_DYNAMITE("deny.action.use_dynamite"),
    BREAK_BLOCK("deny.action.break_block"),
    USE_GENERIC("deny.action.use_generic"),
    OPEN("deny.action.open"),
    TAKE("deny.action.take"),
    SLEEP("deny.action.sleep"),
    USE_ANCHORS("deny.action.use_anchors"),
    USE_EXPLOSIVES("deny.action.use_explosives"),
    PLACE_VEHICLES("deny.action.place_vehicles"),
    DROP_ITEMS("deny.action.drop_items"),
    DROP_XP("deny.action.drop_xp"),
    USE_LINGERING_POTIONS("deny.action.use_lingering_potions"),
    PLACE_THINGS("deny.action.place_things"),
    BREAK_VEHICLES("deny.action.break_vehicles"),
    PICK_UP_ITEMS("deny.action.pick_up_items"),
    BREAK_THINGS("deny.action.break_things"),
    CHANGE("deny.action.change"),
    RIDE("deny.action.ride"),
    HIT("deny.action.hit"),
    PVP("deny.action.pvp"),
    DAMAGE("deny.action.damage"),
    HARM("deny.action.harm"),
    CHAT("deny.action.chat"),
    CREATE_PORTALS("deny.action.create_portals"),
    USE_COMMAND("deny.action.use_command");

    private final String actionKey;

    DenyReason(String actionKey) {
        this.actionKey = actionKey;
    }

    public String getActionKey() {
        return actionKey;
    }

    public String formatMessage(@Nullable String customTemplate) {
        String action = I18n.tr(actionKey);
        if (customTemplate != null && !customTemplate.isEmpty()) {
            String message = applyActionToTemplate(customTemplate, action);
            if (message != null) {
                return message;
            }
            return customTemplate;
        }
        return I18n.tr("deny.template", "action", action);
    }

    public String formatMessage(@Nullable String customTemplate, Map<String, String> extra) {
        if (this == USE_COMMAND && extra != null && extra.containsKey("command")) {
            String action = I18n.tr(actionKey, "command", extra.get("command"));
            if (customTemplate != null && !customTemplate.isEmpty()) {
                String message = applyActionToTemplate(customTemplate, action);
                if (message != null) {
                    return message;
                }
            }
            return I18n.tr("deny.template", "action", action);
        }
        return formatMessage(customTemplate);
    }

    @Nullable
    private static String applyActionToTemplate(String template, String action) {
        if (template.contains("%what%")) {
            return template.replace("%what%", action);
        }
        if (template.contains("{action}")) {
            return template.replace("{action}", action);
        }
        return null;
    }

    public static Map<String, String> command(String command) {
        Map<String, String> map = new HashMap<>();
        map.put("command", command);
        return map;
    }

}
