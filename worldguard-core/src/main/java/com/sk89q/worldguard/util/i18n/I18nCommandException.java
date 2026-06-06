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

import com.sk89q.minecraft.util.commands.CommandException;

import java.util.Map;

public final class I18nCommandException {

    private I18nCommandException() {
    }

    public static CommandException of(String key) {
        return new CommandException(I18n.tr(key));
    }

    public static CommandException of(String key, String placeholder, String value) {
        return new CommandException(I18n.tr(key, placeholder, value));
    }

    public static CommandException of(String key, Map<String, String> placeholders) {
        return new CommandException(I18n.tr(key, placeholders));
    }

}
