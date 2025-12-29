package br.com.agatreix.h3fly.commands;


import br.com.agatreix.h3fly.Messages;
import br.com.agatreix.h3fly.PlayerData;
import br.com.agatreix.h3fly.events.CancelFallDamage;
import br.com.agatreix.h3fly.utils.NumberFormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FlyCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final Messages msg;
    private final PlayerData playerData;

    private final String prefix;
    private final String permissionToUse;
    private final Double priceToBuy;
    private List<String> allowedWorlds;

    public FlyCommand(JavaPlugin plugin, PlayerData playerData) {


        FileConfiguration config = plugin.getConfig();
        permissionToUse = config.getString("Permissions.fly");
        priceToBuy = config.getDouble("Config.price");
        allowedWorlds = config.getStringList("WorldsAllowed");

        msg = new Messages(plugin);
        prefix = msg.get("Prefix");
        this.plugin = plugin;

        this.playerData = playerData;

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Somente jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) commandSender;
        World world = player.getWorld();

        if (!player.hasPermission(permissionToUse) && (!playerData.hasFly(player.getUniqueId()))) {

            openShopMenu(player);

            return true;
        }

        if (!allowedWorlds.contains(world.getName())) {
            player.sendMessage(prefix+msg.get("blockedWorld"));
            return true;
        }


        boolean flying = player.getAllowFlight();
        player.sendMessage(prefix + msg.get(flying ? "Disable" : "Enable"));

        player.setAllowFlight(!flying);
        player.setFlying(!flying);

        player.playSound(player.getLocation(), flying ? Sound.BAT_DEATH : Sound.BAT_HURT, 1.0f, 1.0f);

        if (flying) CancelFallDamage.addPlayer(player);

        return true;
    }

    public void openShopMenu (Player player) {

        Inventory menu = Bukkit.createInventory(null, 27, "§6§lADQUIRA O FLY");

        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta meta = info.getItemMeta();

        meta.setDisplayName("§a§lPoder de voo");
        meta.setLore(Arrays.asList("", "§7Adquira o poder de voar.", "", "§7Preço: §a$ " + NumberFormatUtil.format(priceToBuy)));
        info.setItemMeta(meta);
        menu.setItem(13, info);

        info = new ItemStack(Material.INK_SACK, 1, (short) 1);
        meta = info.getItemMeta();
        meta.setDisplayName("§c§lCancelar");
        info.setItemMeta(meta);
        menu.setItem(11, info);

        info = new ItemStack(Material.INK_SACK, 1, (short) 2);
        meta = info.getItemMeta();
        meta.setDisplayName("§a§lComprar");
        info.setItemMeta(meta);
        menu.setItem(15, info);

        player.openInventory(menu);

    }


}
