package cc.eumc;

import cc.eumc.command.BukkitCommand;
import cc.eumc.config.BukkitConfig;
import cc.eumc.config.PluginConfig;
import cc.eumc.config.ThirdPartySupportConfig;
import cc.eumc.controller.UniBanBukkitController;
import cc.eumc.listener.BukkitPlayerListener;
import cc.eumc.task.LocalBanListRefreshTask;
import cc.eumc.task.SubscriptionRefreshTask;
import cc.eumc.task.UpdateCheckTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public final class UniBanBukkitPlugin extends JavaPlugin {
    static UniBanBukkitPlugin instance;
    BukkitTask Task_LocalBanListRefreshTask;
    BukkitTask Task_SubscriptionRefreshTask;
    UniBanBukkitController controller;

    @Override
    public void onEnable() {
        instance = this;

        reloadConfig();

        controller = new UniBanBukkitController();

        registerTask();
        registerCommand();

        Bukkit.getPluginManager().registerEvents(new BukkitPlayerListener(this), this);

        getLogger().info("UniBan Enabled");
    }

    @Override
    public void onDisable() {
        if (Task_LocalBanListRefreshTask != null)
            Task_LocalBanListRefreshTask.cancel();
        if (Task_SubscriptionRefreshTask != null)
            Task_SubscriptionRefreshTask.cancel();
        controller.destruct();
        getLogger().info("UniBan Disabled");
    }

    @Override
    public void reloadConfig() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }

        super.reloadConfig();

        new BukkitConfig(this);

        showPluginInformation();
    }

    public void showPluginInformation() {
        getLogger().info("Subscriptions [" + BukkitConfig.Subscriptions.size() + "] -----");
        for (String address : BukkitConfig.Subscriptions.keySet()) {
            getLogger().info("* " + address + (BukkitConfig.Subscriptions.get(address)!=null?" | Encrypted":""));
        }
        getLogger().info("Third-party Banning Plugin Support -----");
        getLogger().info("* AdvancedBan: " + (ThirdPartySupportConfig.AdvancedBan?"§lEnabled":"§oNot Found"));
        //getLogger().info("* BungeeAdminTool: " + (ThirdPartySupportConfig.BungeeAdminTool?"§lEnabled":"§oNot Found"));
        //getLogger().info("* BungeeBan: " + (ThirdPartySupportConfig.BungeeBan?"§lEnabled":"§oNot Found"));
        getLogger().info("* LiteBans: " + (ThirdPartySupportConfig.LiteBans?"§lEnabled":"§oNot Found"));
    }

    public void reloadController() {
        if (controller != null)
            controller.destruct();
        controller = new UniBanBukkitController();
    }

    void registerCommand() {
        getCommand("uniban").setExecutor(new BukkitCommand(this));
    }

    public void registerTask() {
        if (Task_LocalBanListRefreshTask != null)
            Task_LocalBanListRefreshTask.cancel();
        if (PluginConfig.EnableBroadcast)
            Task_LocalBanListRefreshTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this, new LocalBanListRefreshTask(getController(), false), 1,
                20 * (int) (60 * PluginConfig.LocalBanListRefreshPeriod));

        if (Task_SubscriptionRefreshTask != null)
            Task_SubscriptionRefreshTask.cancel();
        Task_SubscriptionRefreshTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this, new SubscriptionRefreshTask(getController()), 20,
                20 * (int) (60 * PluginConfig.SubscriptionRefreshPeriod));

        Bukkit.getScheduler().runTaskAsynchronously(this, new UpdateCheckTask(getDescription().getVersion(), 74747));
        // TODO run IdentifySubscriptionTask
    }

    public UniBanBukkitController getController() {
        return controller;
    }

    public static UniBanBukkitPlugin getInstance() {
        return instance;
    }
}
