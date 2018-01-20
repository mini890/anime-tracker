package miguelsilva.a029187.pokemoncarddatabase.JavaClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by migue on 20/01/2018.
 */

public class AjudaUsoBaseDados extends SQLiteOpenHelper {

    protected static final String DB_NAME = "PokemonCardDatabase.db";
    public static final int VERSION = 2;

    public AjudaUsoBaseDados(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query = "CREATE TABLE cards(id INTERGET primary key autoincrement, card_id varchar(30), card_name varchar(100);";

        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}