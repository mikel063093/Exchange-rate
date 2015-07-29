package mike.co.rate_test_app.Data.Object;

import android.support.annotation.NonNull;
import android.util.Log;


/**
 * Created by miguelalegria on 7/28/15.
 */
public class Converter {


    //CurrencyPair[][] currencyPairs;
    String[] currencies;
    CurrencyConverter converter = new CurrencyConverter();


    public Converter(CurrencyPair[] currencyPairList) throws Exception {
        getCurrencies(currencyPairList);
    }


    public String[] getCurrencies(@NonNull CurrencyPair[] currencyPairList) {
        currencies = new String[currencyPairList.length];
        boolean isFirst = true;
        int count = 0;
        for (int i = 0; i < currencyPairList.length; i++) {
            String valFrom = currencyPairList[i].getFrom();
            String valTo = currencyPairList[i].getTo();
            setExchanges(currencyPairList[i]);
            //log(TAG, valFrom + " <----");
            //log(TAG, valTo + " <----");
            if (!isFirst) {
                count--;
            }
            if (!isOnCurriencie(currencies, valFrom)) {
                // log(TAG, valFrom + " <---- ADD");
                currencies[i + count] = valFrom;
                count++;
                isFirst = false;
            }
            if (!isOnCurriencie(currencies, valTo)) {
                // log(TAG, valTo + " <---- ADD ");
                currencies[i + count] = valTo;
                count++;
            }

        }
        return currencies;
    }

    private void setExchanges(CurrencyPair currencyPair) {
        converter.setExchangeRate(currencyPair.getFrom(), currencyPair.getTo(), currencyPair.rate);
    }

    public double getconvertCurrency(String fromCurrency, String toCurrency, double amount) throws ExchangeRateUndefinedException {
        double res = -1.0;
        Log.e("getconvertCurrency", fromCurrency + " , " + toCurrency);
        if (fromCurrency.equals(toCurrency)) {
            Log.e("getconvertCurrency", "===");
            return amount;
        } else {
            if (isOnCurriencie(getCurrencies(), fromCurrency) && isOnCurriencie(getCurrencies(), toCurrency)) {
                return converter.convertCurrency(fromCurrency, toCurrency, amount);
            } else {
                return res;
            }
        }
    }

    public String[] getCurrencies() {
        return currencies;
    }

    private boolean isOnCurriencie(String[] currencies, String val) {
        boolean resul = false;
        for (int i = 0; i < currencies.length; i++) {
            if (val.equalsIgnoreCase(currencies[i])) {
                resul = true;
                break;
            }
        }
        return resul;
    }


}
