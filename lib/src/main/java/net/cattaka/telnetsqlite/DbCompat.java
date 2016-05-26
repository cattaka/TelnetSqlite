package net.cattaka.telnetsqlite;

import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteCursor;
import android.os.Build;


public class DbCompat {

    public static final int FIELD_TYPE_NULL = 0;
    public static final int FIELD_TYPE_INTEGER = 1;
    public static final int FIELD_TYPE_FLOAT = 2;
    public static final int FIELD_TYPE_STRING = 3;
    public static final int FIELD_TYPE_BLOB = 4;

    public static int getType(Cursor cursor, int i) throws Exception {
        int type = -1;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            type = cursor.getType(i);

        }else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR) {
            try {
                cursor.getString( i );
                type = FIELD_TYPE_STRING;
                return type;
            } catch (Exception e) {
                // TODO:現在のところBLOBフィールドでしか落ちたことがないので
                try {
                    cursor.getBlob( i );
                    type = FIELD_TYPE_BLOB;
                } catch (Exception e1) {
                }
            }

        } else {
            SQLiteCursor sqLiteCursor = (SQLiteCursor) cursor;
            CursorWindow cursorWindow = sqLiteCursor.getWindow();
            int pos = cursor.getPosition();
            if (cursorWindow.isNull(pos, i)) {
                type = FIELD_TYPE_NULL;
            } else if (cursorWindow.isLong(pos, i)) {
                type = FIELD_TYPE_INTEGER;
            } else if (cursorWindow.isFloat(pos, i)) {
                type = FIELD_TYPE_FLOAT;
            } else if (cursorWindow.isString(pos, i)) {
                type = FIELD_TYPE_STRING;
            } else if (cursorWindow.isBlob(pos, i)) {
                type = FIELD_TYPE_BLOB;
            }
        }

        return type;
    }

}
