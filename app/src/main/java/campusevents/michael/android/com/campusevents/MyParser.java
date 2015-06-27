package campusevents.michael.android.com.campusevents;

import java.util.ArrayList;

/**
 * Created by User Pc on 6/11/2015.
 */
public final class MyParser {
    private static ArrayList<String> dateList;
    private static ArrayList<String> titleList;
    private static ArrayList<String> venueList;
    private static ArrayList<String> bodyList;
    private static ArrayList<String> categoryList;
    private static ArrayList<String> organizerList;
    private static ArrayList<String> timeList;
    private static ArrayList<String> imageurlList;

    public MyParser(){}

    public static ArrayList<String> parseDate(String string) {
        dateList = new ArrayList<String>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<date>", i);
            if (found == -1) break;
            int start = found + 6; // start of actual name
            int end = string.indexOf("</date>", start);
            dateList.add(string.substring(start, end));
            i = end + 1;

        }
        return dateList;
    }
    public static ArrayList<String> parseTitle(String string){
        titleList = new ArrayList<String>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<summary>", i);
            if (found == -1) break;
            int start = found + 9; // start of actual name
            int end = string.indexOf("</summary>", start);
            titleList.add(string.substring(start, end));
            i = end + 1;

        }
        return titleList;
    }

    public static ArrayList<String> parseVenue(String string){
       venueList = new ArrayList<String>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<venue>", i);
            if (found == -1) break;
            int start = found + 7; // start of actual name
            int end = string.indexOf("</venue>", start);
            venueList.add(string.substring(start, end));
            i = end + 1;

        }
        return venueList;
    }



    public static ArrayList<String> parseBody(String string){
        bodyList = new ArrayList<String>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<content>", i);
            if (found == -1) break;
            int start = found + 9; // start of actual name
            int end = string.indexOf("</content>", start);
            bodyList.add(string.substring(start, end));
            i = end + 1;

        }
        return bodyList;
    }

    public static ArrayList<String> parseCategory(String string){
        categoryList = new ArrayList<String>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<category>", i);
            if (found == -1) break;
            int start = found + 10; // start of actual name
            int end = string.indexOf("</category>", start);
            categoryList.add(string.substring(start, end));
            i = end + 1;

        }
        return categoryList;
    }

    public static ArrayList<String> parseOrganizer(String string){
        organizerList = new ArrayList<String>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<organizer>", i);
            if (found == -1) break;
            int start = found + 11; // start of actual name
            int end = string.indexOf("</organizer>", start);
            organizerList.add(string.substring(start, end));
            i = end + 1;

        }
        return organizerList;
    }

    public static ArrayList<String> parseTime(String string){
        timeList = new ArrayList<String>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<time>", i);
            if (found == -1) break;
            int start = found + 6; // start of actual name
            int end = string.indexOf("</time>", start);
            timeList.add(string.substring(start, end));
            i = end + 1;

        }
        return timeList;
    }

    public static ArrayList<String> parseImageUrl(String string){
        imageurlList = new ArrayList<String>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<imageurl>", i);
            if (found == -1) break;
            int start = found + 10; // start of actual name
            int end = string.indexOf("</imageurl>", start);
            imageurlList.add(string.substring(start, end));
            i = end + 1;

        }
        return imageurlList;
    }


}
