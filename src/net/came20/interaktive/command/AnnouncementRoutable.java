package net.came20.interaktive.command;

/**
 * Created by cameron on 8/20/2016.
 */
public class AnnouncementRoutable {
    private Announcements announcement;
    private String args;

    public AnnouncementRoutable(Announcements announcement, String args) {
        this.announcement = announcement;
        this.args = args;
    }

    public Announcements getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcements announcement) {
        this.announcement = announcement;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return announcement.toString() + args;
    }
}
