package neilamatthews.com.rPGPrisions;

import neilamatthews.com.rPGPrisions.commands.AddMoneyCommand;
import neilamatthews.com.rPGPrisions.commands.CheckBalanceCommand;
import neilamatthews.com.rPGPrisions.commands.RemoveMoneyCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPGPrisions extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Plugin Enabled!");
        getServer().getPluginManager().registerEvents(new ConnectionEvents(), this);
        EconomyManager.setup(getDataFolder());

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                SideboardLogic.setScoreboard(player);
            }
        }, 0L, 10L);

        // commands
        registerCommand("addmoney", new AddMoneyCommand());
        registerCommand("removemoney", new RemoveMoneyCommand());
        registerCommand("balance", new CheckBalanceCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin Disabled!");
    }
}
