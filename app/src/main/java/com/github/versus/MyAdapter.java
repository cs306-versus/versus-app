package com.github.versus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> books;

    public MyAdapter(Context context, List<String> books) {
        super(context, R.layout.list_item, books);
        this.context = context;
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);

        //TextView titleTextView = (TextView) view.findViewById(R.id.text_view_1);
        //TextView authorTextView = (TextView) view.findViewById(R.id.text_view_2);

        String book = books.get(position);
      //  titleTextView.setText(book);
       // authorTextView.setText("this is text2");

        return view;
    }
}
