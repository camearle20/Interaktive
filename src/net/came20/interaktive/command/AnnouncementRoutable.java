package net.came20.interaktive.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cameron on 8/20/2016.
 */
public class AnnouncementRoutable {
    private Announcements announcement;
    private ArrayList<String> args;

    public AnnouncementRoutable(Announcements announcement, ArrayList<String> args) {
        this.announcement = announcement;
        this.args = args;
    }

    public AnnouncementRoutable(Announcements announcement) {
        this(announcement, new ArrayList<String>());
    }

    public AnnouncementRoutable(String encoded) {
        List<String> items = Arrays.asList(encoded.split(","));
        boolean gotAnnouncement = false;
        String announcement = null;
        ArrayList<String> args = new ArrayList<String>();
        for (String item : items) {
            if (!gotAnnouncement) {
                announcement = item;
                gotAnnouncement = true;
            } else {
                args.add(item);
            }
        }
        this.announcement = Announcements.valueOf(announcement);
        this.args = args;
    }

    public Announcements getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcements announcement) {
        this.announcement = announcement;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return announcement.toString() + "," +  String.join(",", args);
    }
}
