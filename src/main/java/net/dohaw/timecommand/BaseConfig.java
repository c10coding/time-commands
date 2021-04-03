package net.dohaw.timecommand;

import net.dohaw.corelib.Config;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BaseConfig extends Config {

    public BaseConfig() {
        super("config.yml");
    }

    public List<TimeCommandInfo> loadTimeCommands() throws ParseException {

        List<TimeCommandInfo> timeCommands = new ArrayList<>();
        List<String> timeCommandsOnFile = config.getStringList("Time Commands");
        for (String configLine : timeCommandsOnFile) {
            // A time command config line may look like this:
            // fullanarchy reload;12:00
            String[] lineSplit = configLine.split(";");
            String command = lineSplit[0];
            timeCommands.add(new TimeCommandInfo(command, TimeUtil.parse(lineSplit[1])));
        }

        return timeCommands;

    }

    public void saveTimeCommands(List<TimeCommandInfo> timeCommandInfo){

        List<String> timeCommandsOnFile = config.getStringList("Time Commands");
        for(TimeCommandInfo info : timeCommandInfo){
            String configLine = info.getCommand() + ";" + TimeUtil.formatter.format(info.getTime());
            timeCommandsOnFile.add(configLine);
        }
        config.set("Time Commands", timeCommandsOnFile);
        saveConfig();

    }


}
