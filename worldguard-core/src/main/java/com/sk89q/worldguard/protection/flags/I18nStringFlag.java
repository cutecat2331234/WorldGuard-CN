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

package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.commands.CommandUtils;
import com.sk89q.worldguard.util.i18n.I18n;

import javax.annotation.Nullable;

/**
 * A string flag whose default value is resolved from the i18n bundle at runtime.
 */
public class I18nStringFlag extends StringFlag {

    private final String i18nKey;

    public I18nStringFlag(String name, String i18nKey) {
        super(name);
        this.i18nKey = i18nKey;
    }

    public I18nStringFlag(String name, RegionGroup defaultGroup, String i18nKey) {
        super(name, defaultGroup);
        this.i18nKey = i18nKey;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    @Nullable
    @Override
    public String getDefault() {
        if (!I18n.isLoaded()) {
            return null;
        }
        return CommandUtils.replaceColorMacros(I18n.tr(i18nKey));
    }

}
