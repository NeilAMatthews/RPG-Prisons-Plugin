package neilamatthews.com.rPGPrisions;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class EconomyManager {

    //create a hash to store the player currencies in
    private static final HashMap<UUID, HashMap<Currency, Double>> balances = new HashMap<>();

    // create a file shell to store all the players hashes in
    private static File file;
    private static FileConfiguration config;

    // create + setup new file
    public static void setup(File dataFolder){
        file = new File(dataFolder, "balances.yml");
        if(!file.exists()){
            try {file.createNewFile();}
            catch (IOException e){e.printStackTrace();};
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    // get the info from the file
    public static void loadPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        HashMap<Currency, Double> playerBalances = new HashMap<>();

        for (Currency currency : Currency.values()) {
            double balance = config.getDouble(uuid + "." + currency.name(), 0.0);
            playerBalances.put(currency, balance);
        }

        balances.put(uuid, playerBalances);
    }

    // store the info when the player completes a transaction or leaves the server
    public static void savePlayer(Player player){
        UUID uuid = player.getUniqueId();
        HashMap<Currency, Double> playerBalances = balances.get(uuid);

        for (Currency currency: Currency.values()){
            config.set(uuid + "." + currency.name(), playerBalances.get(currency));
        }

        try {
            config.save(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // utility functions
    public static double getBalance(Player player, Currency currency){
        return balances.getOrDefault(player.getUniqueId(), new HashMap<>())
                .getOrDefault(currency, 0.0);
    }

    public static void setBalance(Player player, Currency currency, double amount){
        balances.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .put(currency, Math.max(0, amount));
    }

    public static void addBalance(Player player, Currency currency, double amount){
        setBalance(player, currency, getBalance(player, currency) + amount);
    }

    public static void removeBalance(Player player, Currency currency, double amount){
        double current = getBalance(player, currency);
        setBalance(player, currency, Math.max(0, current - amount));
    }

    public static boolean hasBalance(Player player, Currency currency, double amount){
        return getBalance(player, currency) >= amount;
    }

}
