package net.came20.interaktive.command;

/**
 * Created by cameron on 8/9/2016.
 */
public enum Commands {
    NONE,
    //Checkin commands
    CHECKIN_REQUEST, //C->S
    CHECKIN_CONFIRM, //S->C
    CHECKIN_CONFIRMED, //C->S
    CHECKIN_COMPLETED //S->C
}
