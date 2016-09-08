package funny.euphoria.psycho.taobao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context, String fullName) {
        super(context, fullName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql = "CREATE TABLE IF NOT EXISTS  `a` (\n" +
//                "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
//                "`a`\tTEXT,`b`\tTEXT,`c`\tTEXT,`d`\tTEXT,`e`\tTEXT,`f`\tTEXT,`g`\tTEXT,`h`\tTEXT,`i`\tTEXT,`j`\tTEXT\n" +
//                ");";
//
//        db.execSQL(sql);
    }

    public void updateItem(String[] values) {
        if (values.length >3) {
            ContentValues contentValues = new ContentValues(10);
            // contentValues.put("{0}", values[{1}]);
            contentValues.put("a", values[0]);
            contentValues.put("b", values[1]);
            contentValues.put("c", values[2]);
            contentValues.put("d", values[3]);
            SQLiteDatabase db = this.getWritableDatabase();
            db.update("a",contentValues,"a=?" ,new String[]{values[0]});
        }
    }

    public String queryItem(String condition) {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "SELECT * FROM a  WHERE a LIKE '%" + condition + "%' COLLATE NOCASE OR f LIKE '%" + condition + "%' COLLATE NOCASE  LIMIT 1;";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            stringBuilder.append(cursor.getString(1)).append('\n')
                    .append(cursor.getString(2)).append('\n')
                    .append(cursor.getString(3)).append('\n')
                    .append(cursor.getString(4)).append('\n')
                    .append(cursor.getString(5)).append('\n')
                    .append(cursor.getString(6)).append('\n')
                    .append(cursor.getString(7)).append('\n')
                    .append(cursor.getString(8)).append('\n')
                    .append(cursor.getString(9)).append('\n')
                    .append(cursor.getString(10)).append('\n');
        }
        return stringBuilder.toString();
        //Cursor cursor = db.query("a",null,"Word"+" LIKE '"+condition+"%'", null, null, null, "1");
        //Cursor cursor = m_db.query(MY_TABLE, new String[] {"rowid","Word"},"Word LIKE '?'", new String[]{name+"%"}, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
