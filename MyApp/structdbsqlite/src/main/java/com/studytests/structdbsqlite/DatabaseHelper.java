package com.studytests.structdbsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static SoftReference<DatabaseHelper> weakReferenceInstance;
    private Context context;
    private int version;
    private String filenameToCreateDb, filenameToUpdateDb, filenameToDropDb, name;

    public Context getContext() {
        return context;
    }

    public int getVersion() { return version; }

    public String getName() { return name; }

    public List<String> getTables() {
        List<String> tablesName = new ArrayList<>();
        DatabaseHelper instance = weakReferenceInstance.get();
        SQLiteDatabase db = instance.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table'", null);

        if (cursor != null && cursor.moveToFirst()) {
            for (;cursor.moveToNext();) {
                String name = cursor.getString(0);
                tablesName.add(name);
            }
            cursor.close();
        }

        return tablesName;
    }

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
