package me.samuel.ultraping;

import me.samuel.ultraping.commands.PingCommand;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraPing extends JavaPlugin {

    private static UltraPing instance = UltraPing.getInstance();

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        MinecraftServer.getServer().server.getCommandMap().register("ping", "ultraping", new PingCommand());
    }

    public static UltraPing getInstance() {
        return instance;
    }

}
