package br.com.agatreix.h3fly;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Messages {

    private final Map <String, String> messages = new HashMap<>();

    public Messages (JavaPlugin plugin) {
        LoadMessages(plugin);
    }

    public void LoadMessages (JavaPlugin plugin ) {

        messages.clear();

        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("Messages.Fly");

        if (section == null) return;

        for (String key : section.getKeys(false)) {
            String text = section.getString(key).replace("&", "§");
            messages.put(key, text);
        }

    }

    public String get (String key) {
        return messages.getOrDefault(key, "");
    }

}
