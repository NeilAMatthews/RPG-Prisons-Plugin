package neilamatthews.com.rPGPrisions.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import neilamatthews.com.rPGPrisions.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class RemoveMoneyCommand implements BasicCommand{

    @Override
    public void execute(CommandSourceStack source, String[] args){
        // check to make sure we have the same amount of args
        if(args.length != 2){
            source.getSender().sendRichMessage("<red> Usage: /removemoney <player> <balance> ");
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
        } catch (NumberFormatException e){
            source.getSender().sendRichMessage("<red> Invalid amount!");
            return;
        }

        if(amount <= 0){
            source.getSender().sendRichMessage("<red> Amount must be greater than 0!");
        }

        // the actual meat and potatoes of this command
        EconomyManager.removeBalance(target, amount);
        EconomyManager.savePlayer(target);

        source.getSender().sendRichMessage("<red>Removed<yellow> " + amount + " from " + target.getName() + "'s balance!");
        target.sendRichMessage("<red>You have lost<red> " + amount);
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
        return "rpgprisions.removemoney";
    }
}
