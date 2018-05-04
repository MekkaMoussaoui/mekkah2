package dz.atelier.ntic.remplircases.game.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Youcef Mégoura on 21/04/2018.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    private final static String DB_NAME = "sqlite.db";
    private final static int DB_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "create table T_Scores ("
                + "    idScore integer primary key autoincrement,"
                + "    score integer not null"
                + ");";
        db.execSQL( strSql );
        Log.i( "DATABASE", "onCreate invoked" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //String strSql = "alter table T_Scores add column ...";
        String strSql = "drop table T_Scores";
        db.execSQL( strSql );
        this.onCreate( db );
        Log.i( "DATABASE", "onUpgrade invoked" );
    }

    public void insertScore( String name, int score ) {
        name = name.replace( "'", "''" );
        String strSql = "insert into T_Scores (name, score) values ('"
                + name + "', " + score + ")";
        this.getWritableDatabase().execSQL( strSql );
        Log.i( "DATABASE", "insertScore invoked" );
    }


    public void changeScore(int id, int newScore){
        String strSql = "UPDATE 'T_Scores' SET 'score'=" + newScore + " WHERE idScore='" + id + "';";
        this.getWritableDatabase().execSQL(strSql);

        Log.i( "DATABASE", "ChangeScore invoked" );
    }

    public ArrayList<ScoreData> readTop10() {
        ArrayList<ScoreData> scores = new ArrayList<>();

        // 1ère technique : SQL
        //String strSql = "select * from T_Scores order by score desc limit 10";
        //Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null );

        // 2nd technique "plus objet"
        Cursor cursor = this.getReadableDatabase().query( "T_Scores",
                new String[] { "idScore", "score" },
                null, null, null, null, null, null );
        cursor.moveToFirst();
        while( ! cursor.isAfterLast() ) {
            ScoreData score = new ScoreData( cursor.getInt( 0 ), cursor.getInt( 1 ));
            scores.add( score );
            cursor.moveToNext();
        }
        cursor.close();

        return scores;
    }

    public int calculateScore (){
        ArrayList<ScoreData> arrayList = new ArrayList<>(readTop10());
        int somme = 0;
        for(int i = 0; i<arrayList.size();i++){
            somme = somme + arrayList.get(i).getScore();
        }
        return somme;
    }
}
