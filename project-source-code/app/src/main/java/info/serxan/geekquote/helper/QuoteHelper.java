package info.serxan.geekquote.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sercan on 14/12/2017.
 */

public class QuoteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "quote_database.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "quote";

    // --- QUERY =>
    private static final String TABLE_CREATE =
            " CREATE TABLE " + TABLE_NAME + " ( " +
            " quo_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " quo_message VARCHAR, " +
            " quo_created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            " quo_rating FLOAT DEFAULT 0.0 " +
            " );"
    ;
    private static final String TABLE_DROP =
            "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public QuoteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(TABLE_DROP);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
