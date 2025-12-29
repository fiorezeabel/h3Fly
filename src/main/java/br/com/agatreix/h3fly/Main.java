package br.com.agatreix.h3fly;

import br.com.agatreix.h3fly.commands.FlyCommand;
import br.com.agatreix.h3fly.events.CancelFallDamage;
import br.com.agatreix.h3fly.events.MenuEvents;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;


public final class Main extends JavaPlugin {

    private PlayerData playerData;
    private static Economy econ = null;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        this.playerData = new PlayerData(this);
        Bukkit.getConsoleSender().sendMessage("§aFLY: Plugin inciado");

        if (!setupEconomy()) {
            getLogger().severe("Vault não encontrado ou plugin de economia ausente!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Vault vinculado com sucesso!");
        getLogger().info("Economy: " + getEconomy());

        registerEvents(playerData);
        registerCommands(playerData);
        getLogger().info("Eventos e comandos registrados");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerEvents (PlayerData playerData) {

        getServer().getPluginManager().registerEvents(new CancelFallDamage(), this);
        getServer().getPluginManager().registerEvents(new MenuEvents(this, playerData), this);

    }

    public void registerCommands (PlayerData playerData) {
        this.getCommand("fly").setExecutor(new FlyCommand(this, playerData));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }

        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
