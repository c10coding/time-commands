package net.dohaw.timecommand;

import lombok.Getter;
import lombok.SneakyThrows;
import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * A plugin that allows you to run commands at specific times. Currently only supports Amsterdam time
 * @author Caleb Owens (c10coding on Github)
 */
public final class TimeCommandPlugin extends JavaPlugin {

    private BaseConfig baseConfig;

    @Getter
    private List<TimeCommandInfo> timeCommands = new ArrayList<>();

    @SneakyThrows
    @Override
    public void onEnable() {

        CoreLib.setInstance(this);
        JPUtils.validateFiles("config.yml");
        JPUtils.registerCommand("timecommand", new TimeCommand(this));

        baseConfig = new BaseConfig();
        timeCommands = baseConfig.loadTimeCommands();
        new CommandRunner(this).runTaskTimer(this, 0L, 20L);
    }

    @Override
    public void onDisable() {
        baseConfig.saveTimeCommands(timeCommands);
    }

    public void addTimeCommand(TimeCommandInfo timeCommand){
        timeCommands.add(timeCommand);
    }

}
