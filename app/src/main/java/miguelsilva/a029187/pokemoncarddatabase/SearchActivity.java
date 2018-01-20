package miguelsilva.a029187.pokemoncarddatabase;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import miguelsilva.a029187.pokemoncarddatabase.JavaClasses.Card;

public class SearchActivity extends ListActivity {

    protected ArrayList<Card> cardList;
    protected ArrayAdapter<Card> arrayAdapter;

    protected Intent intent;
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_search);

        activity = this;
        intent = getIntent();

        Bundle bundle = intent.getBundleExtra("bundledCardArrayList");
        cardList = (ArrayList<Card>) bundle.getSerializable("cardList");
    }

    @Override
    protected void onStart() {
        super.onStart();
        arrayAdapter = new ArrayAdapter<Card>(this, android.R.layout.simple_list_item_1, cardList);
        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        OpenActivity(CardInfo.class, cardList.get(position).getId());
    }

    protected void OpenActivity(Class<?> activity, String card_id) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("card_id", card_id);
        startActivity(intent);
    }
}