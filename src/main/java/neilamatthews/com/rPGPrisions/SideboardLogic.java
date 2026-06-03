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
import static neilamatthews.com.rPGPrisions.Currency.OVERWORLD;
import static neilamatthews.com.rPGPrisions.Currency.NETHER;
import static neilamatthews.com.rPGPrisions.Currency.END;

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

        // the currency section
        Score currency = objective.getScore("Currencies");
        currency.setScore(5);
        currency.numberFormat(NumberFormat.blank());
        currency.customName(MiniMessage.miniMessage().deserialize("<bold><gradient:#FFFFFF:#AAAAAA>Currencies</gradient></bold>"));


        Score overworld = objective.getScore("overworld");
        overworld.setScore(4);
        overworld.numberFormat(NumberFormat.blank());
        overworld.customName(MiniMessage.miniMessage().deserialize(
                "  <blue>Overworld Currency: <white>" + EconomyManager.getBalance(player, Currency.OVERWORLD)
        ));

        Score nether = objective.getScore("nether");
        nether.setScore(3);
        nether.numberFormat(NumberFormat.blank());
        nether.customName(MiniMessage.miniMessage().deserialize(
                "  <red>Nether Currency: <white>" + EconomyManager.getBalance(player, Currency.NETHER)
        ));

        Score end = objective.getScore("end");
        end.setScore(2);
        end.numberFormat(NumberFormat.blank());
        end.customName(MiniMessage.miniMessage().deserialize(
                "  <white>End Currency: " + EconomyManager.getBalance(player, Currency.END)
        ));

        // keep the bottom at the bottom
        Score bottom = objective.getScore("bottom");
        bottom.setScore(0);
        bottom.numberFormat(NumberFormat.blank());
        bottom.customName(MiniMessage.miniMessage().deserialize("  <dark_gray>▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));

        player.setScoreboard(scoreboard);
    }
}
