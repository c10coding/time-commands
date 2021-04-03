package net.dohaw.timecommand;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TimeCommandInfo {

    @Getter
    private String command;

    @Getter
    private int hour, minute;


}
