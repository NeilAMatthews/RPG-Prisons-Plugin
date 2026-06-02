package neilamatthews.com.rPGPrisions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        // this greets the player on join
        event.getPlayer().sendRichMessage("<b><green>HELLO, " + event.getPlayer().getName() + "!<b> welcome:)<green>");

        // this reformats the server join message
        event.joinMessage(Component.text(event.getPlayer().getName() + " just joined!", NamedTextColor.GOLD));

        // gives each player who joins a scoreboard
        SideboardLogic.setScoreboard(event.getPlayer());

        // set up the hash to store currency
        EconomyManager.loadPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        // this reformats the server leave message
        event.quitMessage(Component.text(event.getPlayer().getName() + " just departed!", NamedTextColor.RED));

        //save the player currencies
        EconomyManager.savePlayer(event.getPlayer());
    }
}
