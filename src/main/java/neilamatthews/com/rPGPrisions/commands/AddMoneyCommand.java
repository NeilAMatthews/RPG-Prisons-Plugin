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

public class AddMoneyCommand implements BasicCommand{

    @Override
    public void execute(CommandSourceStack source, String[] args){
        // check to make sure we have the same amount of args
        if(args.length != 3){
            source.getSender().sendRichMessage("<red> Usage: /addmoney <player> <amount> <currency> ");
            return;
        }

        // check to make sure the player exists or can be found
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            source.getSender().sendRichMessage("<red> Player not found :(");
        }

        // check to make sure the amount is valid
        double amount;
        try{
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            source.getSender().sendRichMessage("<red> Invalid amount!");
            return;
        }

        if(amount <= 0){
            source.getSender().sendRichMessage("<red> Amount must be greater than 0!");
        }

        // parse the currency
        Currency currency;
        try {
            currency = Currency.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e){
            source.getSender().sendRichMessage("<red>Invalid currency! Use: Overworld, Nether, or End");
            return;
        }

        EconomyManager.addBalance(target, currency, amount);
        EconomyManager.savePlayer(target);

        source.getSender().sendRichMessage("<green>Added <yellow>" + amount + " " + currency.name().toLowerCase() + "<green> to " + target.getName() + "'s balance!");
        target.sendRichMessage("<green>You received <yellow>" + amount + " " + currency.name().toLowerCase() + "<green>!");
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
