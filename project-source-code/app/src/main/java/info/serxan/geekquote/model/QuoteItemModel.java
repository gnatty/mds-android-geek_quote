package info.serxan.geekquote.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sercan on 14/12/2017.
 */

public class QuoteItemModel implements Serializable {

    private int id;
    private String message;
    private float rating;
    private Date created_at;

    public QuoteItemModel(int id, String message, float rating, String created_at) {
        this.id = id;
        this.message = message;
        this.rating = rating;
        this.created_at = parseCreated_at(created_at);
    }

    private Date parseCreated_at(String created_at) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = null;
        try {
            return df.parse(created_at);
        } catch (ParseException e) {
            return date;
        }
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

}
