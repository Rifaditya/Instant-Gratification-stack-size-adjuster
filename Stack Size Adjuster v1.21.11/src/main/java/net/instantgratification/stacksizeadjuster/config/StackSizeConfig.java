// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.stacksizeadjuster.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class StackSizeConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("StackSizeAdjuster");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static StackSizeConfig INSTANCE = new StackSizeConfig();
    private static Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    public int items64Limit = 128;
    public int items16Limit = 32;
    public int items1Limit = 1;
    public int maxDropEntities = 8;

    public static synchronized void load(Path configDir) {
        CONFIG_PATH = configDir.resolve("stack-size-adjuster.json");
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (json.has("configVersion")) {
                INSTANCE.configVersion = json.get("configVersion").getAsInt();
            }
            if (json.has("items64Limit")) {
                INSTANCE.items64Limit = json.get("items64Limit").getAsInt();
            }
            if (json.has("items16Limit")) {
                INSTANCE.items16Limit = json.get("items16Limit").getAsInt();
            }
            if (json.has("items1Limit")) {
                INSTANCE.items1Limit = json.get("items1Limit").getAsInt();
            }
            if (json.has("maxDropEntities")) {
                INSTANCE.maxDropEntities = json.get("maxDropEntities").getAsInt();
            }
            LOGGER.info("[StackSizeAdjuster] Loaded configuration from {}", CONFIG_PATH.getFileName());
        } catch (Exception e) {
            LOGGER.error("[StackSizeAdjuster] Failed to load configuration from {}", CONFIG_PATH, e);
        }
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        Path tempFile = CONFIG_PATH.resolveSibling(CONFIG_PATH.getFileName() + ".tmp");
        try {
            if (CONFIG_PATH.getParent() != null) {
                Files.createDirectories(CONFIG_PATH.getParent());
            }
            JsonObject json = new JsonObject();
            json.addProperty("configVersion", INSTANCE.configVersion);
            json.addProperty("items64Limit", INSTANCE.items64Limit);
            json.addProperty("items16Limit", INSTANCE.items16Limit);
            json.addProperty("items1Limit", INSTANCE.items1Limit);
            json.addProperty("maxDropEntities", INSTANCE.maxDropEntities);

            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {
                GSON.toJson(json, writer);
            }
            try {
                Files.move(tempFile, CONFIG_PATH, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(tempFile, CONFIG_PATH, StandardCopyOption.REPLACE_EXISTING);
            }
            LOGGER.debug("[StackSizeAdjuster] Saved configuration to {}", CONFIG_PATH.getFileName());
        } catch (IOException e) {
            LOGGER.error("[StackSizeAdjuster] Failed to save config to {}", CONFIG_PATH, e);
            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException ignored) {}
        }
    }

    public static StackSizeConfig get() {
        return INSTANCE;
    }
}
