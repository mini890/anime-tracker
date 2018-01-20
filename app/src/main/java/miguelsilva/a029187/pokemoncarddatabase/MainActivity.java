package miguelsilva.a029187.pokemoncarddatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;

import miguelsilva.a029187.pokemoncarddatabase.JavaClasses.Card;

public class MainActivity extends Activity {

    protected Button my_deck_button, search_name_button, search_id_button;
    protected EditText SearchCardByName, SearchCardById;

    protected Activity activity;

    protected ArrayList<Card> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_deck_button = findViewById(R.id.my_deck_button);
        search_name_button = findViewById(R.id.search_name_button);
        search_id_button = findViewById(R.id.search_id_button);

        SearchCardByName = findViewById(R.id.SearchCardByName);
        SearchCardById = findViewById(R.id.SearchCardById);
    }

    @Override
    protected void onStart() {
        super.onStart();

        activity = this;
        cardList = new ArrayList<Card>();
        search_name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DO ASYNC TASK
                //ADD TO cardList;
                OpenSearchActivity(cardList);
            }
        });
    }

    private void OpenSearchActivity(ArrayList<Card> cardList) {
        Intent intent = new Intent(activity, SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("cardList", (Serializable) cardList);
        intent.putExtra("bundledCardArrayList", bundle);
        startActivity(intent);
    }
}