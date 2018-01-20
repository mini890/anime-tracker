package miguelsilva.a029187.pokemoncarddatabase.JavaClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by migue on 20/01/2018.
 */

public class AdaptadorBaseDados {
    private AjudaUsoBaseDados dbHelper;
    private SQLiteDatabase database;

    public AdaptadorBaseDados(Context context) {
        dbHelper = new AjudaUsoBaseDados(context.getApplicationContext());
    }

    public AdaptadorBaseDados open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor getCards() {
        String[] colunas = new String[3];
        colunas[0] = "id";
        colunas[1] = "card_id";
        colunas[2] = "card_name";

        return database.query("cards", colunas, null, null, null, null, null);
    }

    public List<String> getAllIds() {
        ArrayList<String> ids = new ArrayList<String>();
        Cursor cursor = getCards();
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return ids;
    }

    public List<String> getAllCardIds() {
        ArrayList<String> ids = new ArrayList<String>();
        Cursor cursor = getCards();
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return ids;
    }

    public List<String> getAllCardNames() {
        ArrayList<String> ids = new ArrayList<String>();
        Cursor cursor = getCards();
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return ids;
    }

    public boolean exists(String card_id) {
        String[] colunas = new String[1];
        colunas[0] = "card_id";
        Cursor cursor = database.query("cards", colunas, "card_id", new String[]{card_id}, null, null, null);

        boolean b = cursor.getCount() >= 1;
        cursor.close();
        return b;
    }

    public long insertCard(String card_id, String card_name) {
        ContentValues values = new ContentValues();
        values.put("card_id", card_id);
        values.put("card_name", card_name);

        return database.insert("cards", null, values);
    }

    public int deleteCard(String card_id) {
        return database.delete("cards", "card_id", new String[]{card_id});
    }

}