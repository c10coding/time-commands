package net.dohaw.timecommand;

import net.dohaw.corelib.ResponderFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TimeCommand implements CommandExecutor {

    private TimeCommandPlugin plugin;

    public TimeCommand(TimeCommandPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("timecommand.use")){
            /* /timecommand create <time in military> .... */
            if(args[0].equalsIgnoreCase("create") && args.length > 2){
                String timeStr = args[1];
                if(timeStr.contains(":")){

                    String[] timeSplit = timeStr.split(":");
                    int hour = Integer.parseInt(timeSplit[0]);
                    int minute = Integer.parseInt(timeSplit[1]);
                    String[] commandArgs = Arrays.copyOfRange(args, 2, args.length);
                    String desiredCommand = String.join(" ", commandArgs);

                    plugin.addTimeCommand(new TimeCommandInfo(desiredCommand, hour, minute));
                    ResponderFactory rf = new ResponderFactory(sender);
                    rf.sendMessage("A new time command has been made!");
                    rf.sendMessage("This command will run at &6" + TimeUtil.format(hour, minute) + "&f: " + "/&b" + desiredCommand);

                }else{
                    sender.sendMessage("This is not a valid time!");
                }
            /* /timecommand list - Lists the time commands */
            }else if(args[0].equalsIgnoreCase("list")){
                ResponderFactory rf = new ResponderFactory(sender);
                List<TimeCommandInfo> timeCommands = plugin.getTimeCommands();
                rf.sendMessage("Time Commands: ");
                for(TimeCommandInfo info : timeCommands){
                    String line = "- Command: &b" + info.getCommand() + " &f| &6" + TimeUtil.format(info.getHour(), info.getMinute());
                    rf.sendMessage(line);
                }
            }
        }
        return false;
    }



}
