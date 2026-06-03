package neilamatthews.com.rPGPrisions.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import neilamatthews.com.rPGPrisions.Currency;
import neilamatthews.com.rPGPrisions.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Arrays;
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
            source.getSender().sendRichMessage("<gold>Your balances:");
            for (Currency currency : Currency.values()) {
                double balance = EconomyManager.getBalance(player, currency);
                source.getSender().sendRichMessage("<yellow>" + currency.name().toLowerCase() + ": <white>" + balance);
            }
            return;
        }

        // player specified but no currency - show all balances
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                source.getSender().sendRichMessage("<red>Player not found!");
                return;
            }
            source.getSender().sendRichMessage("<gold>Balances of <yellow>" + target.getName() + "<gold>:");
            for (Currency currency : Currency.values()) {
                double balance = EconomyManager.getBalance(target, currency);
                source.getSender().sendRichMessage("<yellow>" + currency.name().toLowerCase() + ": <white>" + balance);
            }
            return;
        }

        if (args.length != 2) {
            source.getSender().sendRichMessage("<red>Usage: /checkbalance <player>");
            return;
        }

        // check to make sure the player exists or can be found
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            source.getSender().sendRichMessage("<red> Player not found :(");
        }

        // parse the currency
        Currency currency;
        try {
            currency = Currency.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e){
            source.getSender().sendRichMessage("<red>Invalid currency! Use: Overworld, Nether, or End");
            return;
        }

        // the actual meat and potatoes of this command
        double playerBalance = EconomyManager.getBalance(target, currency);
        source.getSender().sendRichMessage("<gold>The balance of<gold> " + target.getName() + " is " + playerBalance + " " +
        currency.name().toLowerCase() + "s");
    }

    @Override
    public Collection<String> suggest(CommandSourceStack source, String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .toList();
        }
        if (args.length == 3) {
            return Arrays.stream(Currency.values())
                    .map(c -> c.name().toLowerCase())
                    .filter(c -> c.startsWith(args[2].toLowerCase()))
                    .toList();
        }
        return List.of();
    }

    @Override
    public @Nullable String permission(){
        return "rpgprisions.addmoney";
    }
}
