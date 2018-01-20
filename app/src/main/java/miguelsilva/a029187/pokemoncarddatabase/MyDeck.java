package miguelsilva.a029187.pokemoncarddatabase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import miguelsilva.a029187.pokemoncarddatabase.JavaClasses.AdaptadorBaseDados;

public class MyDeck extends Activity {

    protected TextView card_id, card_name;
    protected Button GoToCard;
    protected Spinner spinner;

    protected AdaptadorBaseDados adaptadorBaseDados;

    protected ArrayList<String> cardIds, cardNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deck);

        // FINDVIEWBYID card_id, card_name and spinner
    }

    @Override
    protected void onStart() {
        super.onStart();

        adaptadorBaseDados = new AdaptadorBaseDados(this).open();
        cardIds = (ArrayList<String>) adaptadorBaseDados.getAllCardIds();
        cardNames = (ArrayList<String>) adaptadorBaseDados.getAllCardNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cardNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (cardNames.size() <= 0) {
            spinner.setEnabled(false);
            //TOAST FOR NO CARDS IN DECK
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //GET ID
                //CLICK BUTTON TO GO TO CardInfo ACTIVITY
                //CREATE BUTTON LISTENER
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorBaseDados.close();
    }
}