package net.came20.interaktive.command.parameter;

/**
 * Created by cameron on 8/9/2016.
 */
public class ParameterCheckinRequest extends Parameter {
    private String firstName;
    private String lastName;
    private String middleInitial;
    private String finalDestination;
    private String flightNumber;
    private String seatNumber;
    private String ffNumber;

    public ParameterCheckinRequest(String firstName, String lastName, String middleInitial, String finalDestination, String flightNumber, String seatNumber, String ffNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
        this.finalDestination = finalDestination;
        this.flightNumber = flightNumber;
        this.seatNumber = seatNumber;
        this.ffNumber = ffNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getFfNumber() {
        return ffNumber;
    }

    public void setFfNumber(String ffNumber) {
        this.ffNumber = ffNumber;
    }
}
