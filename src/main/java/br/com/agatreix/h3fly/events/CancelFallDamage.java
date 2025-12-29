package br.com.agatreix.h3fly.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CancelFallDamage implements Listener {

    public static final Set<UUID> playerBlocked = new HashSet<>();

    public static void addPlayer(Player player) {
        playerBlocked.add(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFall(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        Player player = (Player) event.getEntity();

        if (playerBlocked.remove(player.getUniqueId())) {
            event.setCancelled(true);
        }

    }

}
