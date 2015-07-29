package mike.co.rate_test_app.Data.Object;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by miguelalegria on 7/28/15.
 */
public class CurrencyPair {


    String from;
    String to;
    float rate;


    public CurrencyPair(String from, String to) {
        this.from = from;

        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {

        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {

        return new Gson().toJson(this);
    }
}
