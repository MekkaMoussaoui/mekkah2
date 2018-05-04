package dz.atelier.ntic.remplircases.game.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dz.atelier.ntic.remplircases.game.R;
import dz.atelier.ntic.remplircases.game.model.LevelData;


public class AdapterLevel extends BaseAdapter {

    Context applicationContext;
    List<LevelData> levelDatas;
    public AdapterLevel(Context applicationContext, List<LevelData>levelDatas){
        this.applicationContext=applicationContext;
        this.levelDatas=levelDatas;

    }
    @Override
    public int getCount() {
        return levelDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return levelDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        TextView textView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdapterLevel.ViewHolder ViewHolder= null;
        if(view == null){
            LayoutInflater layoutInflater=(LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= layoutInflater.inflate(R.layout.level_data,null);
            ViewHolder= new AdapterLevel.ViewHolder();
            ViewHolder.textView=(TextView) view.findViewById(R.id.levelID);
            LevelData levelData= levelDatas.get(i);
            ViewHolder.textView.setText(levelData.getLevel_title());
            view.setTag(ViewHolder);
        }

        else {
            ViewHolder =(AdapterLevel.ViewHolder)view.getTag();

        }
        return view;
    }
}
