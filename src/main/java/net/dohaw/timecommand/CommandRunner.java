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

                ZonedDateTime infoTime = info.getTime();
                if(isSameTime(currentTime, infoTime)){

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), info.getCommand());
                    // so that it doesn't get ran again
                    recentCommandsRan.add(info);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        recentCommandsRan.remove(info);
                    }, 200L);

                }

            }
        }

    }

    /**
     * If it's roughly the same time
     */
    private boolean isSameTime(ZonedDateTime currentTime, ZonedDateTime time2){
        int diffSeconds = time2.getSecond() - currentTime.getSecond();
        return currentTime.getHour() == time2.getHour() && currentTime.getMinute() == time2.getMinute() && diffSeconds <= 5;
    }

}
