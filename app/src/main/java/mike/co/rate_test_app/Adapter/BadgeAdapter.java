package mike.co.rate_test_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mike.co.rate_test_app.Data.Object.CurrencyPair;
import mike.co.rate_test_app.R;

/**
 * Created by miguelalegria on 7/28/15.
 */
public class BadgeAdapter extends ArrayAdapter<String> {

    Context context;
    String[] strings;
    private LayoutInflater inflater;
    boolean first;
    CurrencyPair[] currencyPairList;
//    public BadgeAdapter(Activity context, int resource, boolean first) {
//        super(context, resource);
//        this.context = context;
//
//        this.first = first;
//    }

    Holder holder;

    public BadgeAdapter(Context ctx, int txtViewResourceId, CurrencyPair[] currencyPairList, String[] objects, boolean first) {
        super(ctx, txtViewResourceId, objects);
        this.context = ctx;
        this.first = first;
        this.strings = objects;
        this.currencyPairList = currencyPairList;

    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Holder(convertView);
        }
        convertView.setTag(currencyPairList[position]);
        holder.badge.setText(strings[position]);
//        if (!first)
//            holder.badge.setText(strings[position]);
//        else {
//            holder.badge.setText(CurrencyPair.GBP);
//        }

        return convertView;
    }

    public class Holder {
        @InjectView(R.id.badge)
        TextView badge;

        public Holder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
