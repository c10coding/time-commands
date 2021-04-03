package net.dohaw.timecommand;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
public class TimeCommandInfo {

    @Getter
    private String command;

    @Getter
    private ZonedDateTime time;

}
