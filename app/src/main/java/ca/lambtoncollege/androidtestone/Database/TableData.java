package ca.lambtoncollege.androidtestone.Database;

import android.provider.BaseColumns;

/**
 * Created by Ramandeep on 19-08-2015.
 */
public class TableData {
    public TableData() {

    }

    public static abstract class Tableinfo implements BaseColumns {

        public static final String publisher = "publisher";
        public static final String f2f_url = "f2f_url";
        public static final String title = "title";
        public static final String source_url = "source_url";
        public static final String recipe_id = "recipe_id";
        public static final String publisher_url = "publisher_url";
        public static final String image_url = "image_url";
        public static final String DATABASE_NAME = "dbrecipies";
        public static final String TABLE_NAME = "recipies_table";

        public static final int database_version = 1;
    }
}