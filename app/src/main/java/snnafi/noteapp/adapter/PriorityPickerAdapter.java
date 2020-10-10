package snnafi.noteapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import snnafi.noteapp.R;
import snnafi.noteapp.model.Priority;


public class PriorityPickerAdapter extends BaseAdapter {

    Priority[] priorities;


    public PriorityPickerAdapter(Priority[] priorities) {
        this.priorities = priorities;

    }

    @Override
    public int getCount() {
        return priorities.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.priority_picker, null);
        TextView names = (TextView) view.findViewById(R.id.priority);
        names.setText(priorities[i].getName());
        return view;
    }
}




