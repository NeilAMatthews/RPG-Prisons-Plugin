package neilamatthews.com.rPGPrisions;

import io.papermc.paper.scoreboard.numbers.NumberFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import javax.swing.*;

import static java.lang.Math.round;

public class SideboardLogic {

    // a component that makes the color gradient
    public static Component fakeGradient(String text) {
        //define the colors
        java.awt.Color start = new java.awt.Color(0, 0, 255);
        java.awt.Color end = new java.awt.Color(128, 0, 128);

        TextComponent.Builder builder = Component.text();

        // loop through each character and change the color
        for (int i = 0; i < text.length(); i++) {
            float ratio = (text.length() == 1) ? 0 : (float) i / (text.length() - 1);

            int r = (int) (start.getRed() + ratio * (end.getRed() - start.getRed()));
            int g = (int) (start.getGreen() + ratio * (end.getGreen() - start.getGreen()));
            int b = (int) (start.getBlue() + ratio * (end.getBlue() - start.getBlue()));

            builder.append(Component.text(text.charAt(i)).color(TextColor.color(r, g, b)));
        }
        return builder.build();
    }

    public static void setScoreboard(Player player){
        // make the scoreboard object that we can put stuff into
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective(
                "sidebar",
                Criteria.DUMMY,
                MiniMessage.miniMessage().deserialize("<dark_gray>▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")
        );

        //place where the scoreboard is going to be
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //make each character a different color
        Score title = objective.getScore("title");
        title.setScore(6);
        title.numberFormat(NumberFormat.blank());
        title.customName(fakeGradient("  Portal Hopper"));

        // assign each line to a variable to make the number disappear
        Score line5 = objective.getScore("Balance: " + EconomyManager.getBalance(player));
        line5.setScore(5);
        line5.numberFormat(NumberFormat.blank());

        Score line4 = objective.getScore("Online: " + Bukkit.getOnlinePlayers().size());
        line4.setScore(4);
        line4.numberFormat(NumberFormat.blank());

        Score line3 = objective.getScore("X-coord: " + round(player.getX()));
        line3.setScore(3);
        line3.numberFormat(NumberFormat.blank());

        Score line2 = objective.getScore("Y-coord: " + round(player.getY()));
        line2.setScore(2);
        line2.numberFormat(NumberFormat.blank());

        Score line1 = objective.getScore("Z-coord: " + round(player.getZ()));
        line1.setScore(1);
        line1.numberFormat(NumberFormat.blank());

        Score bottom = objective.getScore("bottom");
        bottom.setScore(0);
        bottom.numberFormat(NumberFormat.blank());
        bottom.customName(MiniMessage.miniMessage().deserialize("  <dark_gray>▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));

        player.setScoreboard(scoreboard);
    }
}
