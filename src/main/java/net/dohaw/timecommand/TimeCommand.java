package net.dohaw.timecommand;

import net.dohaw.corelib.ResponderFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

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
                if(hasTimeCommands()){
                    listTimeCommands(sender, false);
                }else{
                    sender.sendMessage("There are no time commands!");
                }
            }else if(args[0].equalsIgnoreCase("delete")){

                if(hasTimeCommands()){
                    if(sender instanceof Player){
                        listTimeCommands(sender, true);
                        ConversationFactory cf = new ConversationFactory(plugin);
                        Conversation conv = cf.withFirstPrompt(new TimeCommandDeletePrompt(plugin)).withLocalEcho(false).buildConversation((Player)sender);
                        conv.begin();
                    }else{
                        sender.sendMessage("Only players can use this command! This is because it prompts you in game upon which commands you want to delete via their numbers");
                        sender.sendMessage("If you wish to delete time commands without going in game, you can stop the server, and delete the time commands through the config");
                    }
                }else{
                    sender.sendMessage("There are no time commands!");
                }

            }else if(args[0].equalsIgnoreCase("help")){
                ResponderFactory rf = new ResponderFactory(sender);
                rf.sendMessage("&2This plugin allows you to have specific commands ran at specific times in the day");
                rf.sendMessage("\n&6TimeCommand commands: ");
                rf.sendMessage("&2- /tco list &f - Lists all the time commands");
                rf.sendMessage("&2- /tco create <hour:minute> <command> &f - Allows you to create a time command");
                rf.sendMessage("&2- /tco delete &f - Allows you to delete a time command");
            }
        }
        return false;
    }

    private void listTimeCommands(CommandSender sender, boolean isInDeleteMode){
        ResponderFactory rf = new ResponderFactory(sender);
        List<TimeCommandInfo> timeCommands = plugin.getTimeCommands();
        rf.sendMessage("Time Commands: ");
        int count = 1;
        for(TimeCommandInfo info : timeCommands){
            String line = "";
            // if it's in delete mode, add a number in the beginning of the line. If not, then we just add a dash
            if(isInDeleteMode){
                line += "(" + count + ") ";
                count++;
            }else{
                line += " - ";
            }
            line += "Command: &b" + info.getCommand() + " &f| &6" + TimeUtil.format(info.getHour(), info.getMinute());

            rf.sendMessage(line);
        }
    }

    private boolean hasTimeCommands(){
        return !plugin.getTimeCommands().isEmpty();
    }

}
