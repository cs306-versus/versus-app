package com.github.versus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<List<String>> {

    private Context context;
    private List<List<String>> books;

    public ListViewAdapter(Context context, List< List<String>> books) {
        super(context, R.layout.list_item, books);
        this.context = context;
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        List<String> book = books.get(position);
        ((TextView)view.findViewById(R.id.sport_text)).setText(book.get(0));
        ((TextView)view.findViewById(R.id.Location_text)).setText(book.get(1));
        ((TextView)view.findViewById(R.id.Meeting_time)).setText(book.get(2));

        return view;
    }
}
