package dz.atelier.ntic.remplircases.game;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dz.atelier.ntic.remplircases.game.adapter.AdapterLevel;
import dz.atelier.ntic.remplircases.game.model.LevelData;
import dz.atelier.ntic.remplircases.game.model.RowData;

public class LevelsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView listview;
    List<LevelData> levelDatas;
    String level_title[];

    String word[];
    TypedArray image;


    ImageView image_sphinx;
    ArrayList<Cat> tab_image_grammar = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        getSupportActionBar().setTitle("Levels");

        image_sphinx= (ImageView)findViewById(R.id.caption_image);






        Bundle bundle1= getIntent().getExtras();
        RowData rowData =(RowData)bundle1.getSerializable("data");



//////////////////////////////////////////////////////////////////////////////////

        tab_image_grammar = new ArrayList<Cat>();
        word = getResources().getStringArray(R.array.main_title);
        image = getResources().obtainTypedArray(R.array.image_title);



////////////////////////////////////////////////////////////////////////////////////
        listview= (ListView)findViewById(R.id.listID);
        listview.setOnItemClickListener(this);

        levelDatas = new ArrayList<LevelData>();
        level_title = getResources().getStringArray(R.array.level_title);
        for(int i=0;i<level_title.length;i++){

            LevelData levelDat = new LevelData(level_title[i]);
            levelDatas.add(levelDat);

        }

        AdapterLevel adapterLevel = new AdapterLevel(getApplicationContext(),levelDatas);
        adapterLevel.notifyDataSetChanged();
        listview.setAdapter(adapterLevel);



        tab_image_grammar.add(new Cat("animals", R.drawable.animals, "mot.gram"));

        tab_image_grammar.add(new Cat("food", R.drawable.food, "menu.gram"));

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("grammar", tab_image_grammar.get(position).grammar_sphinx);
        bundle.putInt("image", tab_image_grammar.get(position).image_sphinx);
        bundle.putString("word", tab_image_grammar.get(position).word_sphinx);


        Intent myintent = new Intent(this,GameMic.class);
        myintent.putExtras(bundle);
        startActivity(myintent);
    }
}
