package net.came20.interaktive.command;

/**
 * Created by cameron on 8/9/2016.
 */
public enum Commands {
    NONE, //CS->CS

    PING_REQUEST, //C->S
    PING_RESPONSE, //S->C

    AUTH_FAIL, //S->C

    //Login commands
    LOGIN_REQUEST, //C->S
    LOGIN_ACCEPT, //S->C
    LOGIN_REJECT, //S->C

    //Logout commands
    LOGOUT_REQUEST, //C->S
    LOGOUT_ACCEPT, //S->C
    LOGOUT_REJECT, //S->C

    //Checkin commands
    CHECKIN_REQUEST, //C->S
    CHECKIN_CONFIRM, //S->C
    CHECKIN_CONFIRMED, //C->S
    CHECKIN_COMPLETED //S->C
}
