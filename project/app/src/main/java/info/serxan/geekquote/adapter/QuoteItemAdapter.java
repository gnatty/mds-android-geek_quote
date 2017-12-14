package info.serxan.geekquote.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import info.serxan.geekquote.R;
import info.serxan.geekquote.model.QuoteItemModel;

/**
 * Created by sercan on 14/12/2017.
 */

public class QuoteItemAdapter extends ArrayAdapter<QuoteItemModel> {

    private String[] colors = new String[] { "#ECF0F1", "#FFFFFF" };

    public QuoteItemAdapter(Context context, ArrayList<QuoteItemModel> quoteList) {
        super(context, 0, quoteList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        QuoteItemModel quote = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.quote_item, parent, false);
        }
        // Lookup view for data population
        RelativeLayout quoteBC = (RelativeLayout) convertView.findViewById(R.id.rl_quote);
        TextView quoteStr = (TextView) convertView.findViewById(R.id.tv_quote_str);
        TextView quoteRate = (TextView) convertView.findViewById(R.id.tv_quote_rate);
        TextView quoteDate = (TextView) convertView.findViewById(R.id.tv_quote_date);

        // Populate the data into the template view using the data object

        quoteBC.setBackgroundColor(Color.parseColor(colors[(position % 2)]));
        quoteStr.setText( quote.getMessage() );
        quoteRate.setText( "Rate : " + String.valueOf(quote.getRating()) );
        quoteDate.setText( "Created at " + String.valueOf(quote.getCreated_at()) );

        // Return the completed view to render on screen
        return convertView;
    }
}
