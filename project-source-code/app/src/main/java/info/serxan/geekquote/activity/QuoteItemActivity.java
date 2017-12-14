package info.serxan.geekquote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import info.serxan.geekquote.R;
import info.serxan.geekquote.model.QuoteItemModel;

/**
 * Created by sercan on 13/12/2017.
 */

public class QuoteItemActivity extends Activity {

    private Bundle extras;
    private QuoteItemModel quote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // --- Render layout.
        setContentView(R.layout.quote_simple_item);
        // --- INIT.
        extras = getIntent().getExtras();
        quote = (QuoteItemModel) extras.getSerializable("quoteItem");
        // ---
        initValues();
    }

    public void initValues() {

        // --- RETRIEVE VIEW.
        TextView quoteStr       = (TextView) findViewById(R.id.tv_quote_str);
        RatingBar quoteRate     = (RatingBar) findViewById(R.id.rb_quote_rating);
        TextView quoteDate      = (TextView) findViewById(R.id.tv_quote_date);

        // --- SET VALUES.
        quoteStr.setText( quote.getMessage() );
        quoteRate.setRating( quote.getRating() );
        quoteDate.setText( String.valueOf(quote.getCreated_at()) );

    }

    public void btnOnClickCancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void btnOnClickRemove(View v) {
        Intent intent = new Intent();
        intent.putExtra("action", "remove");
        intent.putExtra("quoteItem", quote);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void btnOnClickUpdate(View v) {
        RatingBar quoteRate     = (RatingBar) findViewById(R.id.rb_quote_rating);
        quote.setRating(quoteRate.getRating());
        Intent intent = new Intent();
        intent.putExtra("action", "update");
        intent.putExtra("quoteItem", quote);
        setResult(RESULT_OK, intent);
        finish();
    }

}
