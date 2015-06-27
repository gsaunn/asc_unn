package campusevents.michael.android.com.campusevents;

import java.util.ArrayList;

public final class Parser {
    private static String department;
    private static String social;
    private static String religion;
    private static String academics;
    private static ArrayList<String> dateList;
    private static ArrayList<String> venueList;
    private static ArrayList<String> titleList;
    private static ArrayList<String> bodyList;
    private static ArrayList<String> categoryList;
    private static ArrayList<String> organizerList;
    private static ArrayList<String> timeList;
    private static ArrayList<String> imageurlList;

    public static ArrayList<Object> parseDept(String string) {
        dateList = new ArrayList<String>();
        venueList = new ArrayList<String>();
        titleList = new ArrayList<String>();
        bodyList = new ArrayList<String>();
        categoryList = new ArrayList<String>();
        organizerList = new ArrayList<String>();
        timeList = new ArrayList<String>();
        imageurlList = new ArrayList<String>();
        ArrayList<Object> deptItems = new ArrayList<Object>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<department>", i);
            if (found == -1) break;
            int start = found + 12; // start of actual name
            int end = string.indexOf("</department>", start);
            department = (string.substring(start, end));
            i = end + 1;
        }
        dateList = MyParser.parseDate(department);
        titleList = MyParser.parseTitle(department);
        venueList = MyParser.parseVenue(department);
        bodyList = MyParser.parseBody(department);
        categoryList = MyParser.parseCategory(department);
        organizerList = MyParser.parseOrganizer(department);
        timeList = MyParser.parseTime(department);
        imageurlList = MyParser.parseImageUrl(department);
        deptItems.add(dateList);
        deptItems.add(titleList);
        deptItems.add(venueList);
        deptItems.add(bodyList);
        deptItems.add(categoryList);
        deptItems.add(organizerList);
        deptItems.add(timeList);
        deptItems.add(imageurlList);
        return deptItems;
    }
    public static ArrayList<Object> parseSocial(String string) {
        dateList = new ArrayList<String>();
        venueList = new ArrayList<String>();
        titleList = new ArrayList<String>();
        bodyList = new ArrayList<String>();
        categoryList = new ArrayList<String>();
        organizerList = new ArrayList<String>();
        timeList = new ArrayList<String>();
        imageurlList = new ArrayList<String>();
        ArrayList<Object> socItems = new ArrayList<Object>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<social>", i);
            if (found == -1) break;
            int start = found + 8; // start of actual name
            int end = string.indexOf("</social>", start);
            social = (string.substring(start, end));
            i = end + 1;
        }
        dateList = MyParser.parseDate(social);
        titleList = MyParser.parseTitle(social);
        venueList = MyParser.parseVenue(social);
        bodyList = MyParser.parseBody(social);
        categoryList = MyParser.parseCategory(social);
        organizerList = MyParser.parseOrganizer(social);
        timeList = MyParser.parseTime(social);
        imageurlList = MyParser.parseImageUrl(social);
        socItems.add(dateList);
        socItems.add(titleList);
        socItems.add(venueList);
        socItems.add(bodyList);
        socItems.add(categoryList);
        socItems.add(organizerList);
        socItems.add(timeList);
        socItems.add(imageurlList);
        return socItems;
    }
    public static ArrayList<Object> parseAcademics(String string) {
        dateList = new ArrayList<String>();
        venueList = new ArrayList<String>();
        titleList = new ArrayList<String>();
        bodyList = new ArrayList<String>();
        categoryList = new ArrayList<String>();
        organizerList = new ArrayList<String>();
        timeList = new ArrayList<String>();
        imageurlList = new ArrayList<String>();
        ArrayList<Object> acaItems = new ArrayList<Object>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<academics>", i);
            if (found == -1) break;
            int start = found + 11; // start of actual name
            int end = string.indexOf("</academics>", start);
            academics = (string.substring(start, end));
            i = end + 1;
        }
        dateList = MyParser.parseDate(academics);
        titleList = MyParser.parseTitle(academics);
        venueList = MyParser.parseVenue(academics);
        bodyList = MyParser.parseBody(academics);
        categoryList = MyParser.parseCategory(academics);
        organizerList = MyParser.parseOrganizer(academics);
        timeList = MyParser.parseTime(academics);
        imageurlList = MyParser.parseImageUrl(academics);
        acaItems.add(dateList);
        acaItems.add(titleList);
        acaItems.add(venueList);
        acaItems.add(bodyList);
        acaItems.add(categoryList);
        acaItems.add(organizerList);
        acaItems.add(timeList);
        acaItems.add(imageurlList);
        return acaItems;
    }
    public static ArrayList<Object> parseReligion(String string) {
        dateList = new ArrayList<String>();
        venueList = new ArrayList<String>();
        titleList = new ArrayList<String>();
        bodyList = new ArrayList<String>();
        categoryList = new ArrayList<String>();
        organizerList = new ArrayList<String>();
        timeList = new ArrayList<String>();
        imageurlList = new ArrayList<String>();
        ArrayList<Object> relItems = new ArrayList<Object>();
        int i = 0;
        while (true) {
            int found = string.indexOf("<religion>", i);
            if (found == -1) break;
            int start = found + 10; // start of actual name
            int end = string.indexOf("</religion>", start);
            religion = (string.substring(start, end));
            i = end + 1;
        }
        dateList = MyParser.parseDate(religion);
        titleList = MyParser.parseTitle(religion);
        venueList = MyParser.parseVenue(religion);
        bodyList = MyParser.parseBody(religion);
        categoryList = MyParser.parseCategory(religion);
        organizerList = MyParser.parseOrganizer(religion);
        timeList = MyParser.parseTime(religion);
        imageurlList = MyParser.parseImageUrl(religion);
        relItems.add(dateList);
        relItems.add(titleList);
        relItems.add(venueList);
        relItems.add(bodyList);
        relItems.add(categoryList);
        relItems.add(organizerList);
        relItems.add(timeList);
        relItems.add(imageurlList);
        return relItems;
    }

}
