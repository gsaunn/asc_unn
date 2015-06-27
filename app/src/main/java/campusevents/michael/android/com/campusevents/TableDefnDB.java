package campusevents.michael.android.com.campusevents;

import android.provider.BaseColumns;

public final class  TableDefnDB  {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TableDefnDB() {}

    /* Inner class that defines the table contents */
    public static abstract class DeptTableDfn implements BaseColumns {
        public static final String TABLE_NAME = "department";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_VENUE = "venue";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_ORGANIZER = "organizer";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_IMAGEURL = "imageurl";

    }

    public static abstract class SocialTableDfn implements BaseColumns {
        public static final String TABLE_NAME = "social";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_VENUE = "venue";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_ORGANIZER = "organizer";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_IMAGEURL = "imageurl";

    }

    public static abstract class ReligionTableDfn implements BaseColumns {
        public static final String TABLE_NAME = "religion";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_VENUE = "venue";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_ORGANIZER = "organizer";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_IMAGEURL = "imageurl";

    }

    public static abstract class AcademicsTableDfn implements BaseColumns {
        public static final String TABLE_NAME = "academics";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_VENUE = "venue";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_ORGANIZER = "organizer";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_IMAGEURL = "imageurl";

    }




}
