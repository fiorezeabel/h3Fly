package br.com.agatreix.h3fly.events;

import br.com.agatreix.h3fly.Main;
import br.com.agatreix.h3fly.Messages;
import br.com.agatreix.h3fly.PlayerData;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuEvents implements Listener {

    private final Messages msg;
    private final PlayerData playerData;
    private final double price;
    private Economy econ = null;

    public MenuEvents(JavaPlugin plugin, PlayerData playerData) {
        this.msg = new Messages(plugin);
        this.playerData = playerData;

        econ = Main.getEconomy();
        price = plugin.getConfig().getDouble("Config.price");

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (!event.getView().getTitle().equals("§6§lADQUIRA O FLY")) return;

        if (!(event.getWhoClicked() instanceof Player)) return;

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (!item.hasItemMeta()) return;
        if (!item.getItemMeta().hasDisplayName()) return;

        Player player = (Player) event.getWhoClicked();
        // String itemName = item.getItemMeta().getDisplayName();
        int itemSlot = event.getSlot();

        switch (itemSlot) {
            case (15):

                if (econ == null) {
                    player.sendMessage("§cSistema de economia indisponível.");
                    player.closeInventory();
                    return;
                }

                double saldo = econ.getBalance(player);
                if (saldo >= price ) {
                    econ.withdrawPlayer(player, price);
                    player.sendMessage(msg.get("Prefix") + msg.get("buy"));
                    player.closeInventory();
                    playerData.setFly(player.getUniqueId(), true);
                } else {
                    player.closeInventory();
                    player.sendMessage(msg.get("Prefix") + "§cVocê não possui dinheiro suficiente");
                }
                break;

            case 11:
                player.closeInventory();
                break;
        }
    }


}
