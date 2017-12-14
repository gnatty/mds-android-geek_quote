package info.serxan.geekquote.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import info.serxan.geekquote.R;
import info.serxan.geekquote.adapter.QuoteItemAdapter;
import info.serxan.geekquote.dao.QuoteDAO;
import info.serxan.geekquote.helper.QuoteHelper;
import info.serxan.geekquote.model.QuoteItemModel;

/**
 * Created by sercan on 14/12/2017.
 */

public class QuoteItemListActivity  extends Activity {

    // ---
    private static final int MY_ACTIVITY_CODE = 1;
    private static final String TAG = "QuoteItemListActivity";

    // ---
    private static QuoteDAO quoteDAO;
    private static ArrayList<QuoteItemModel> quoteList;
    private static QuoteItemAdapter quoteAdapter;
    private static QuoteHelper quoteHelper;

    /**
     * @onCreate
     *
     * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --- RENDER.
        setContentView(R.layout.quote_layout);

        // --- INIT.
        initDefault();
        initContextDB();
        getQuoteInDatabase();
    }

    /**
     * Init default variable.
     *
     */
    public void initDefault() {
        // --- INIT.
        quoteDAO        = new QuoteDAO();
        quoteAdapter    = new QuoteItemAdapter(this, quoteDAO.getAll());

        ListView quoteContainer = (ListView) findViewById(R.id.quoteContainer);
        quoteContainer.setAdapter(quoteAdapter);

        // -- On List View -> ITEM CLICK
        quoteContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                QuoteItemModel quoteItem = quoteDAO.getAll().get(i);
                showItemActivity(quoteItem);
            }
        });

    }

    /**
     * Init context database.
     *
     */
    public void initContextDB() {
        Context context = getApplicationContext();
        quoteHelper     = new QuoteHelper(context);
    }

    /**
     * Open Quote Item Activity.
     *
     * @param quoteItem
     *
     */
    public void showItemActivity(QuoteItemModel quoteItem) {
        Intent intent = new Intent(this, QuoteItemActivity.class);
        // -- Put extra data.
        intent.putExtra("quoteItem", quoteItem);
        // -- Launch Activity.
        startActivityForResult(intent, MY_ACTIVITY_CODE);
    }

    /**
     * Show a Toast on screen.
     *
     * @param str
     *
     */
    public void createNotificationPopup(String str) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, str, duration);
        toast.show();
    }

    /**
     * Add new QuoteItem in ArrayList and update adapter view.
     *
     * @param id
     * @param message
     * @param rating
     * @param created_at
     *
     */

    /**
     * Get Quote stored in database.
     *
     */
    public void getQuoteInDatabase() {
        // -- READ.
        SQLiteDatabase db           = quoteHelper.getReadableDatabase();
        // -- SELECT.
        String[] projection = {
                "quo_id",
                "quo_message",
                "quo_created_at",
                "quo_rating"
        };
        // --- RESULT.
        Cursor result = db.query(
                QuoteHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if( result.getCount() > 0 ) {
            while( result.moveToNext() ) {
                // -- VALUES.
                int id              = result.getInt(result.getColumnIndexOrThrow("quo_id"));
                String message      = result.getString(result.getColumnIndexOrThrow("quo_message"));
                Float rating        = result.getFloat(result.getColumnIndexOrThrow("quo_rating"));
                String created_at   = result.getString(result.getColumnIndexOrThrow("quo_created_at"));
                quoteDAO.addQuoteItem(id, message, rating, created_at);
            }
        }
    }

    /**
     * Insert new quote in SqlList database.
     *
     * @param message - Quote message.
     *
     */
    public void insertQuoteInDatabase(String message) {
        // -- WRITE
        SQLiteDatabase db           = quoteHelper.getWritableDatabase();
        // --- INSERT
        ContentValues values        = new ContentValues();
        values.put("quo_message", message);
        int newQuoteItemId          = (int) db.insert(QuoteHelper.TABLE_NAME, null, values);
        // --- Add in view.
        quoteDAO.addQuoteItem(newQuoteItemId, message, 0, "");
        quoteAdapter.notifyDataSetChanged();
        createNotificationPopup("New Quote added !");
    }


    public void btnOnSubmitNewQuoteItem(View v) {
        EditText etQuoteNewMessage = (EditText) findViewById(R.id.et_quote_new_message);
        String quoteItemNewMessage = etQuoteNewMessage.getText().toString();

        if(quoteItemNewMessage.length() == 0) {
            createNotificationPopup("Message cannot be empty !");
        } else {
            insertQuoteInDatabase(quoteItemNewMessage);
            etQuoteNewMessage.setText(null);
        }

    }

    public void updateQuoteItem(QuoteItemModel quoteItem) {
        // --- UPDATE FROM DATABASE.
        // -- WRITE
        SQLiteDatabase db           = quoteHelper.getWritableDatabase();
        float quoteRating   = quoteItem.getRating();
        int quoteId         = quoteItem.getId();
        String args[] = {
                String.valueOf(quoteRating),
                String.valueOf(quoteId)
        };
        db.execSQL("UPDATE quote SET quo_rating = ? WHERE quo_id = ? ;", args);
        // --- UPDATE FROM ARRAY LIST.
        quoteDAO.updateQuoteRatting(quoteItem.getId(), quoteItem.getRating());
        quoteAdapter.notifyDataSetChanged();
        // --
        createNotificationPopup("quote item UPDATE !");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void removeQuoteItem(QuoteItemModel quoteItem) {
        // --- REMOVE FROM DATABASE.
        // -- WRITE
        SQLiteDatabase db           = quoteHelper.getWritableDatabase();
        db.execSQL("DELETE FROM quote WHERE quo_id = " + quoteItem.getId() + ";");

        // --- REMOVE FROM ARRAY LIST.
        quoteDAO.removeQuoteById(quoteItem.getId());
        quoteAdapter.notifyDataSetChanged();
        // --
        createNotificationPopup("quote item REMOVE !");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ---
        switch(requestCode) {
            case MY_ACTIVITY_CODE:
                switch(resultCode) {
                    case RESULT_CANCELED:
                        // -- NOTHING HAPPENS.
                        break;
                    case RESULT_OK:
                        // -- UPDATE.
                        Bundle b                    = data.getExtras();
                        QuoteItemModel quoteItem    = (QuoteItemModel) b.getSerializable("quoteItem");
                        switch (b.getString("action")) {
                            case "update":
                                updateQuoteItem(quoteItem);
                                break;
                            case "remove":
                                removeQuoteItem(quoteItem);
                                break;
                        }
                        break;
                }
                break;
        }
    }

}
