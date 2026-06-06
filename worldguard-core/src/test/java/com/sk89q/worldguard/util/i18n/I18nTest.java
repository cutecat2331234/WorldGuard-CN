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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class I18nTest {

    private static Path tempDir;

    @BeforeAll
    static void setUp() throws Exception {
        tempDir = Files.createTempDirectory("wg-i18n-test");
        Files.copy(Path.of("src/test/resources/lang/en.yml"), tempDir.resolve("en.yml"));
        Files.copy(Path.of("src/test/resources/lang/zh_CN.yml"), tempDir.resolve("zh_CN.yml"));
        I18n.init(LanguageManager.load(tempDir.toFile(), "zh_CN", name -> null));
    }

    @AfterAll
    static void tearDown() {
        I18n.init(null);
    }

    @Test
    void denyReasonProducesChineseAction() {
        String message = DenyReason.BREAK_BLOCK.formatMessage(null);
        assertNotEquals("break that block", message);
        assertTrue(message.contains("破坏方块"));
    }

    @Test
    void placeholderReplacement() {
        String result = I18n.tr("error.region.not_found", "id", "test-region");
        assertTrue(result.contains("test-region"));
        assertFalse(result.contains("{id}"));
    }

    @Test
    void denyReasonKeysExist() {
        for (DenyReason reason : DenyReason.values()) {
            String action = I18n.tr(reason.getActionKey());
            assertNotEquals(reason.getActionKey(), action);
        }
    }

    @Test
    void enAndZhKeyParity() throws Exception {
        File langDir = tempDir.toFile();
        Set<String> enKeys = LanguageManager.loadLocaleKeys(langDir, "en", name -> null);
        Set<String> zhKeys = LanguageManager.loadLocaleKeys(langDir, "zh_CN", name -> null);
        assertEquals(enKeys, zhKeys, "en.yml and zh_CN.yml should have the same keys");
    }

}
