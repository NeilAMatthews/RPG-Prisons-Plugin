package neilamatthews.com.rPGPrisions.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import neilamatthews.com.rPGPrisions.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class CheckBalanceCommand implements BasicCommand{

    @Override
    public void execute(CommandSourceStack source, String[] args){

        // no args - check own balance
        if (args.length == 0) {
            if (!(source.getSender() instanceof Player player)) {
                source.getSender().sendRichMessage("<red>Console must specify a player!");
                return;
            }
            double balance = EconomyManager.getBalance(player);
            source.getSender().sendRichMessage("<gold>Your balance: <yellow>" + balance + " coins");
            return;
        }

        if (args.length > 1) {
            source.getSender().sendRichMessage("<red>Usage: /checkbalance <player>");
            return;
        }

        // check to make sure the player exists or can be found
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            source.getSender().sendRichMessage("<red> Player not found :(");
        }

        // the actual meat and potatoes of this command
        double playerBalance = EconomyManager.getBalance(target);
        source.getSender().sendRichMessage("<gold>The balance of<gold> " + target.getName() + " is " + playerBalance);
    }

    @Override
    public Collection<String> suggest(CommandSourceStack source, String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .toList();
        }
        return List.of();
    }

    @Override
    public @Nullable String permission(){
        return "rpgprisions.addmoney";
    }
}
