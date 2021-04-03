package net.dohaw.timecommand;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

public class CommandRunner extends BukkitRunnable {

    private HashSet<TimeCommandInfo> recentCommandsRan = new HashSet<>();
    private TimeCommandPlugin plugin;

    public CommandRunner(TimeCommandPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {

        ZonedDateTime currentTime = ZonedDateTime.now(TimeUtil.zone);

        List<TimeCommandInfo> timeCommands = plugin.getTimeCommands();
        for(TimeCommandInfo info : timeCommands){
            if(!recentCommandsRan.contains(info)){

                int hour = info.getHour();
                int minute = info.getMinute();
                if(isSameTime(currentTime, hour, minute)){

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), info.getCommand());
                    // so that it doesn't get ran again
                    recentCommandsRan.add(info);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        recentCommandsRan.remove(info);
                    }, 1300L);

                }

            }
        }

    }

    private boolean isSameTime(ZonedDateTime currentTime, int commandHour, int commandMinute){
        return currentTime.getHour() == commandHour && currentTime.getMinute() == commandMinute;
    }

}
