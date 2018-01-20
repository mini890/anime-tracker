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
        String[] colunas = new String[4];
        colunas[0] = "id";
        colunas[1] = "card_id";
        colunas[2] = "card_name";
        colunas[3] = "imageUrl";

        return database.query("deckCards", colunas, null, null, null, null, null);
    }

    public List<Card> getAllCards() {
        ArrayList<Card> cards = new ArrayList<Card>();
        Cursor cursor = getCards();
        if (cursor.moveToFirst()) {
            do {
                cards.add(new Card(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cards;
    }

    public long insertCard(String card_id, String card_name, String imageUrl) {
        ContentValues values = new ContentValues();
        values.put("card_id", card_id);
        values.put("card_name", card_name);
        values.put("imageUrl", imageUrl);

        return database.insert("deckCards", null, values);
    }

    public void deleteCard(String card_id) {
        String query = "DELETE FROM deckCards WHERE card_id = '" + card_id + "';";
        database.execSQL(query);
    }

}