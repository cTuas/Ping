package me.samuel.ultraping.commands;

import me.samuel.ultraping.UltraPing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PingCommand extends Command {

    public PingCommand() {
        super("ping");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', UltraPing.getInstance().getConfig().getString("MESSAGES.u-gotta-be-a-player-nigga")));
            return false;
        }
        Player player = (Player) sender;
        String formatOther = ChatColor.translateAlternateColorCodes('&', UltraPing.getInstance().getConfig().getString("MESSAGES.pingMessageOther"));
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            player.sendMessage(formatOther.replace("%player%", target.getName()).replace("%ping%", String.valueOf(getPing(target))));
            return false;
        }

        List<String> toReturn = new ArrayList<>();
        for (Entity entity : player.getLocation().getChunk().getEntities()) {
            if (!(entity instanceof Player)) continue;
            if (entity == player) continue;
            toReturn.add(formatOther.replace("%player%", entity.getName()).replace("%ping%", String.valueOf(getPing(entity))));
        }

        if (toReturn.size() != 0 && UltraPing.getInstance().getConfig().getBoolean("OPTIONS.pingInChunk")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', UltraPing.getInstance().getConfig().getString("MESSAGES.playersInChunk")));
            player.sendMessage(toReturn.toString().replace("[", "").replace("]", "").replace(",", ChatColor.GRAY + ","));
        }

        String formatYours = ChatColor.translateAlternateColorCodes('&', UltraPing.getInstance().getConfig().getString("MESSAGES.pingMessage"));
        player.sendMessage(formatYours.replace("%ping%", String.valueOf(getPing(player))));
        toReturn.clear();
        return false;
    }

    private int getPing(Entity entity) {
        int ping = (((CraftPlayer) entity).getHandle()).ping;
        if (UltraPing.getInstance().getConfig().getBoolean("OPTIONS.pingSpoof")) {
            if (ping > 8 && ping < 25) {
                ping = ping - 4;
            } else if (ping > 25 && ping < 60) {
                ping = ping - 8;
            } else if (ping > 60 && ping < 100) {
                ping = ping - 10;
            } else if (ping > 100 && ping < 200) {
                ping = ping - 23;
            }
        }
        return ping;
    }
}
