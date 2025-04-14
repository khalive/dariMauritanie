package mr.example.darimaritanie;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbConnect extends SQLiteOpenHelper {
    private static String dbname ="Friends";
    private static String dbTable = "users";
    private  static  int dbverion =1 ;
    private  static String ID = "id";
    private static String fullname = "fullname";
    private static String emailAddress = "emailAddress";
    private static String password = "password ";
    private static String dob = "dob";
    private  static String phonNumber = "phonNumber";
    private  String bio = "bio";


    public DbConnect(@Nullable Context context) {
        super(context, dbname, null, dbverion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + dbTable + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + fullname + " TEXT, "
                + emailAddress + " TEXT, "
                + password + " TEXT, "
                +phonNumber + " TEXT ,"
                + dob + " TEXT, "
                + phonNumber + " TEXT, "
                + bio + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + dbTable);
        onCreate(db);

    }
    public void addUser(Users user ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(fullname,user.getFullname());
        values.put(emailAddress,user.getEmailAddress());
        values.put(password ,user.getPassword());
        values.put(dob,user.getDob());
//        values.put(phonNumber,user.getPh)
        values.put(phonNumber ,user.getPhonNumber());
        values.put(dob ,user.getDob());
        values.put(bio , user.getBio());
        db.insert(dbTable ,null ,values);

    }
}
