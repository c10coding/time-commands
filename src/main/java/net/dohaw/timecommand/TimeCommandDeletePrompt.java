package net.dohaw.timecommand;

import net.dohaw.corelib.helpers.MathHelper;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import java.util.ArrayList;
import java.util.List;

public class TimeCommandDeletePrompt extends StringPrompt {

    private TimeCommandPlugin plugin;

    public TimeCommandDeletePrompt(TimeCommandPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return "Please enter the number time command(s) you wish to delete. If you select multiple, make sure you separate them with a comma!";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {

        Conversable person = context.getForWhom();
        String[] inputSplit = input.split(",");
        // Makes sure that all the things they input are numbers and are within array bounds
        if(!isValidInput(inputSplit)){
            person.sendRawMessage("The input you gave is not valid! Aborting delete process...");
            return END_OF_CONVERSATION;
        }

        person.sendRawMessage("The following time commands have been deleted: ");
        for (String s : inputSplit) {
            int numCommand = Integer.parseInt(s);
            TimeCommandInfo timeCommand = plugin.getTimeCommands().remove(numCommand - 1);
            String line = "Command: " + timeCommand.getCommand() + " | " + TimeUtil.format(timeCommand.getHour(), timeCommand.getMinute());
            person.sendRawMessage(line);
        }

        return END_OF_CONVERSATION;
    }

    private boolean isValidInput(String[] input){
        boolean isAllNumbers = MathHelper.isInt(input);
        if(isAllNumbers){
            return areNumbersInBound(input);
        }
        return false;
    }

    /* If numbers are negative or greater than the length of the inputs they put in, then the numbers aren't in bound */
    private boolean areNumbersInBound(String[] input){
        for(String s : input){
            int num = Integer.parseInt(s);
            if(num < 0 || num > input.length){
                return false;
            }
        }
        return true;
    }

}
