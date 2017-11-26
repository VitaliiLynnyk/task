package ark.todoapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import ark.todoapp.ToDoList;
/**
 * Created by linni on 11/26/2017.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_Version = 1;
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String MY_TABLE_TO_DO_LIST = "to_do_list";
    public static final String TO_DO_LIST_COLUMN_ID = "id";
    public static final String TO_DO_LIST_COLUMN_NAME = "name";
    public static final String TO_DO_LIST_COLUMN_COLOR = "color";
    public static final String TO_DO_LIST_COLUMN_CATEGORY = "category";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("LOG", "--- onCreate database ---");
        db.execSQL(
                "create table to_do_list" + "(id integer primary key, name text, color text, category text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS to_do_list");
        onCreate(db);
    }

    public boolean insertContact (String name,String color,String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("color", color);
        contentValues.put("category", category);
        db.insert(MY_TABLE_TO_DO_LIST, null, contentValues);
        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MY_TABLE_TO_DO_LIST);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String color, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("color", color);
        contentValues.put("category", category);
        db.update("to_do_list", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("to_do_list",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<ToDoList> getAllTODOLIST() {
        ArrayList<ToDoList> array_list = new ArrayList<ToDoList>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from to_do_list", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(new ToDoList(Integer.parseInt(res.getString(res.getColumnIndex(TO_DO_LIST_COLUMN_ID))),res.getString(res.getColumnIndex(TO_DO_LIST_COLUMN_NAME)),
                    res.getString(res.getColumnIndex(TO_DO_LIST_COLUMN_COLOR)),res.getString(res.getColumnIndex(TO_DO_LIST_COLUMN_CATEGORY))));
            res.moveToNext();
        }
        return array_list;
    }
}