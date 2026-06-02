package neilamatthews.com.rPGPrisions;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class EconomyManager {

    private static final HashMap<UUID, Double> balances = new HashMap<>();
    private static File file;
    private static FileConfiguration config;

    public static void setup(File dataFolder){
        file = new File(dataFolder, "balances.yml");
        if(!file.exists()){
            try {file.createNewFile();}
            catch (IOException e){e.printStackTrace();};
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void loadPlayer(Player player){
        UUID uuid = player.getUniqueId();
        double balance = config.getDouble(uuid.toString(), 0.0);
        balances.put(uuid, balance);
    }

    public static void savePlayer(Player player){
        UUID uuid = player.getUniqueId();
        config.set(uuid.toString(), balances.get(uuid));
        try {config.save(file);}
        catch(IOException e) { e.printStackTrace();}
    }

    // utility functions
    public static double getBalance(Player player){
        return balances.getOrDefault(player.getUniqueId(), 0.0);
    }

    public static void setBalance(Player player, double amount){
        balances.put(player.getUniqueId(), amount);
    }

    public static void addBalance(Player player, double amount){
        balances.put(player.getUniqueId(), getBalance(player) + amount);
    }

    public static void removeBalance(Player player, double amount){
        balances.put(player.getUniqueId(), getBalance(player) - amount);
    }

    public static boolean hasBalance(Player player, double amount){
        return getBalance(player) >= amount;
    }

}
