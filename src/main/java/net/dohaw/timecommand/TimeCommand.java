package net.dohaw.timecommand;

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
                String timeStr = args[2];
                if(timeStr.contains(":")){

                    ZonedDateTime time = TimeUtil.parse(timeStr);
                    String[] commandArgs = Arrays.copyOfRange(args, 3, args.length);
                    String desiredCommand = String.join(" ", commandArgs);

                    plugin.addTimeCommand(new TimeCommandInfo(desiredCommand, TimeUtil.parse(timeStr)));
                    sender.sendMessage("A new time command has been made!");
                    sender.sendMessage("This command will run at " + TimeUtil.formatter.format(time) + ": " + "/" + desiredCommand);

                }else{
                    sender.sendMessage("This is not a valid time!");
                }
            }
        }
        return false;
    }



}
