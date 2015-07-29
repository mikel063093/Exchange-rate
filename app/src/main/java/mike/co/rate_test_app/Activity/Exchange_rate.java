package mike.co.rate_test_app.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import butterknife.InjectView;
import mike.co.rate_test_app.Adapter.BadgeAdapter;
import mike.co.rate_test_app.Data.Object.Converter;
import mike.co.rate_test_app.Data.Object.CurrencyPair;
import mike.co.rate_test_app.Data.Object.ExchangeRateUndefinedException;
import mike.co.rate_test_app.R;

public class Exchange_rate extends BaseAcitivy {
    static String TAG = Exchange_rate.class.getSimpleName();
    @InjectView(R.id.toolbar_main)
    Toolbar toolbar_main;

    @InjectView(R.id.edt_exchange)
    EditText edt_exchange;

    @InjectView(R.id.edt_exchange_to)
    EditText edt_exchange_to;

    @InjectView(R.id.spinner_exchange)
    Spinner spinner_exchange;

    @InjectView(R.id.spinner_exchange_to)
    Spinner spinner_exchange_to;
    static Gson gson;
    BadgeAdapter adapterFrom, adapterTo;

    Converter converter;
    CurrencyPair[] currencyPairList;
    CurrencyPair currencyPairFrom, currencyPairTo;
    SpinnerOnItemSelectedListener fromItemListener, toItemListener;
    EdtTextWatcher edtTextWatcherFrom, edtTextWatcherTo;

    static {
        gson = new Gson();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate);
        Inject();
        edtTextWatcherFrom = new EdtTextWatcher(EdtTextWatcher.FROM);
        edtTextWatcherTo = new EdtTextWatcher(EdtTextWatcher.TO);
        edt_exchange.addTextChangedListener(edtTextWatcherFrom);
        renderSpinners(loadData());
    }

    private void renderSpinners(@NonNull CurrencyPair[] currencyPairList) {
        this.currencyPairList = currencyPairList;
        try {
            converter = new Converter(currencyPairList);
            adapterFrom = new BadgeAdapter(this, R.layout.item_spinner, currencyPairList, converter.getCurrencies(), true);
            adapterTo = new BadgeAdapter(this, R.layout.item_spinner, currencyPairList, converter.getCurrencies(), false);

            fromItemListener = new SpinnerOnItemSelectedListener(SpinnerOnItemSelectedListener.FROM);
            toItemListener = new SpinnerOnItemSelectedListener(SpinnerOnItemSelectedListener.TO);

            spinner_exchange.setAdapter(adapterFrom);
            spinner_exchange_to.setAdapter(adapterTo);

            spinner_exchange.setOnItemSelectedListener(fromItemListener);
            spinner_exchange_to.setOnItemSelectedListener(toItemListener);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setAmount(@NonNull Double amount) {
        if (!"-1.0".equalsIgnoreCase(amount + ""))
            edt_exchange_to.setText(amount + "");
    }

    public CurrencyPair[] loadData() {
        CurrencyPair[] jsonData = null;
        String json;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            log(TAG, json);
            jsonData = gson.fromJson(json, CurrencyPair[].class);


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        if (jsonData != null) {
            log(TAG, "CAST DATA OK");
        }
        return jsonData;
    }

    public class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        static final int FROM = 1;
        static final int TO = 0;
        int type;


        public SpinnerOnItemSelectedListener(int type) {
            this.type = type;
        }

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            CurrencyPair currencyPair = (CurrencyPair) view.getTag();
            log(TAG, "POSITION --> " + pos + " :: " + currencyPair.toString());

//            if (pos == 0) {
//                currencyPair = currencyPairList[pos];
//            } else {
//                currencyPair = currencyPairList[pos - 1];
//            }
            if (edt_exchange.getText().toString().length() > 0)
                try {
                    setAmount(converter.getconvertCurrency(spinner_exchange.getSelectedItem().toString(),
                            spinner_exchange_to.getSelectedItem().toString(),
                            Double.parseDouble(edt_exchange.getText().toString())));

                } catch (ExchangeRateUndefinedException e) {
                    e.printStackTrace();
                }

            switch (type) {
                case FROM:
                    if (pos == 0) {
                        currencyPairFrom = (CurrencyPair) view.getTag();
                    } else {
                        currencyPairFrom = (CurrencyPair) view.getTag();
                    }

                    break;
                case TO:
                    if (pos == 0) {
                        currencyPairTo = (CurrencyPair) view.getTag();
                    } else {
                        currencyPairTo = (CurrencyPair) view.getTag();
                    }

                    break;
            }
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
//            if (currencyPairList.length > 0)
//                switch (type) {
//                    case FROM:
//
//                        currencyPairFrom = currencyPairList[0];
//                        break;
//                    case TO:
//                        currencyPairTo = currencyPairList[0];
//                        break;
//                }

        }
    }

    public class EdtTextWatcher implements TextWatcher {
        static final int FROM = 1;
        static final int TO = 0;
        int type;

        public EdtTextWatcher(int type) {
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().length() > 0)
                switch (type) {
                    case FROM:
                        try {
                            setAmount(converter.getconvertCurrency(spinner_exchange.getSelectedItem().toString(),
                                    spinner_exchange_to.getSelectedItem().toString(),
                                    Double.parseDouble(edt_exchange.getText().toString())));
                        } catch (ExchangeRateUndefinedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case TO:
                        try {
                            setAmount(converter.getconvertCurrency(spinner_exchange.getSelectedItem().toString(),
                                    spinner_exchange_to.getSelectedItem().toString(),
                                    Double.parseDouble(edt_exchange.getText().toString())));
                        } catch (ExchangeRateUndefinedException e) {
                            e.printStackTrace();
                        }
                        break;
                }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}
