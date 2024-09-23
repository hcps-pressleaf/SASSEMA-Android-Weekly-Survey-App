package com.example.surveyapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

//the whole point of this class is to change the display color of the spinner (dropdown question)

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private final LayoutInflater inflater;
    private final List<String> items;
    private Context context;

    public CustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> items) {
        super(context, resource, items);
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        // Set text color for the selected item
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setTextColor(ContextCompat.getColor(context, R.color.questionBlue)); // Change to your desired color
        textView.setText(items.get(position));

        return convertView;
    }

}