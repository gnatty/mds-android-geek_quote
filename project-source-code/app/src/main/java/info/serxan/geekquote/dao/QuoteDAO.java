package info.serxan.geekquote.dao;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

import info.serxan.geekquote.model.QuoteItemModel;

/**
 * Created by sercan on 14/12/2017.
 */

public class QuoteDAO {

    private static ArrayList<QuoteItemModel> quoteList;

    public QuoteDAO() {
        quoteList = new ArrayList<QuoteItemModel>();
    }

    public ArrayList<QuoteItemModel> getAll() {
        return quoteList;
    }

    public void add(QuoteItemModel quoteItem) {
        quoteList.add(0, quoteItem);
    }

    public void addQuoteItem(int id, String message, float rating, String created_at) {
        QuoteItemModel quoteItem = new QuoteItemModel(id, message, rating, created_at);
        quoteList.add(0, quoteItem);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean removeQuoteById(int quoteId) {
        return quoteList.removeIf(quote -> quote.getId() == quoteId);
    }

    public boolean updateQuoteRatting(int quoteId, float quoteRatting) {
        for(QuoteItemModel quoteItem: quoteList) {
            if( quoteItem.getId() == quoteId ) {
                quoteItem.setRating(quoteRatting);
                break;
            }
        }
        return true;
    }

}

