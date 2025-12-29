package br.com.agatreix.h3fly;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerData {

    private final File file;
    private FileConfiguration config;

    public PlayerData (JavaPlugin plugin) {

        file = new File(plugin.getDataFolder(), "players.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

    }

    public boolean hasFly (UUID uuid) {
        return config.getBoolean("players."+uuid+".fly", false);
    }

    public void setFly (UUID uuid, boolean value) {
        config.set("players."+uuid+".fly", value);
        save();
    }

    public void save () {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
