package neilamatthews.com.rPGPrisions;

import fr.mrmicky.fastboard.adventure.FastBoard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static java.lang.Math.round;

public class SideboardLogic {

    // store each player's board so we can update it
    private static final HashMap<UUID, FastBoard> boards = new HashMap<>();

    public static Component fakeGradient(String text) {
        java.awt.Color start = new java.awt.Color(0, 0, 255);
        java.awt.Color end = new java.awt.Color(128, 0, 128);
        TextComponent.Builder builder = Component.text();
        for (int i = 0; i < text.length(); i++) {
            float ratio = (text.length() == 1) ? 0 : (float) i / (text.length() - 1);
            int r = (int) (start.getRed()   + ratio * (end.getRed()   - start.getRed()));
            int g = (int) (start.getGreen() + ratio * (end.getGreen() - start.getGreen()));
            int b = (int) (start.getBlue()  + ratio * (end.getBlue()  - start.getBlue()));
            builder.append(Component.text(text.charAt(i)).color(TextColor.color(r, g, b)));
        }
        return builder.build();
    }

    public static void createScoreboard(Player player) {
        FastBoard board = new FastBoard(player);
        boards.put(player.getUniqueId(), board);
        updateScoreboard(player);
    }

    public static void updateScoreboard(Player player) {
        FastBoard board = boards.get(player.getUniqueId());
        if (board == null) return;

        // title
        board.updateTitle(fakeGradient("  Portal Hopper"));

        // lines
        board.updateLines(
                MiniMessage.miniMessage().deserialize("<dark_gray>▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
                MiniMessage.miniMessage().deserialize("  <blue>Overworld: <white>" + EconomyManager.getBalance(player, Currency.OVERWORLD)),
                MiniMessage.miniMessage().deserialize("  <red>Nether: <white>" + EconomyManager.getBalance(player, Currency.NETHER)),
                MiniMessage.miniMessage().deserialize("  <white>End: " + EconomyManager.getBalance(player, Currency.END)),
                MiniMessage.miniMessage().deserialize("  <yellow>Online: <white>" + Bukkit.getOnlinePlayers().size()),
                MiniMessage.miniMessage().deserialize("  <gray>Position: <white>" + round(player.getX()) + "/" + round(player.getY()) + "/" + round(player.getZ())),
                MiniMessage.miniMessage().deserialize("<dark_gray>▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")
        );
    }

    public static void removeScoreboard(Player player) {
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) board.delete();
    }
}