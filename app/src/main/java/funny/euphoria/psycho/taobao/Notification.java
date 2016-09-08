package funny.euphoria.psycho.taobao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/18.
 */
public class Notification extends ArrayAdapter<String> {
    private final LayoutInflater mLayoutInflater;

    private final int mLayout;
    private final ArrayList<String> mList;
    public Notification(Context context,int resId, ArrayList<String> ls){
        super( context,resId,ls);
        mLayout=resId;
        mList=ls;
        mLayoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        NoteAdapterViewHolder noteAdapterViewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(mLayout, parent, false);
            noteAdapterViewHolder = new NoteAdapterViewHolder();


            noteAdapterViewHolder.content = (TextView) convertView.findViewById(R.id.value);
         convertView.setTag(noteAdapterViewHolder);

        } else {
            noteAdapterViewHolder = (NoteAdapterViewHolder) convertView.getTag();
        }



        noteAdapterViewHolder.content.setText(mList.get(position));


        return convertView;
    }

    class NoteAdapterViewHolder {



        TextView content;



    }
}
