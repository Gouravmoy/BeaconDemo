package com.example.lenovo.beacondemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.Utils;
import com.example.lenovo.beacondemo.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lenovo on 12/11/2015.
 */
public class BeaconListAdapter extends BaseAdapter {


    private ArrayList<Beacon> beacons;
    private LayoutInflater inflater;

    public BeaconListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.beacons = new ArrayList<>();
    }

    public void replaceWith(Collection<Beacon> newBeacons) {
        this.beacons.clear();
        this.beacons.addAll(newBeacons);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beacons.size();
    }

    @Override
    public Beacon getItem(int position) {
        return beacons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflateIfRequired(view, position, parent);
        bind(getItem(position), view);
        return view;
    }

    private void bind(Beacon beacon, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.macTextView.setText(String.format("MAC: %s (%.2fm)", beacon.getMacAddress().toStandardString(), Utils.computeAccuracy(beacon)));
        holder.majorTextView.setText("Major: " + beacon.getMajor());
        holder.minorTextView.setText("Minor: " + beacon.getMinor());

    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.beacon_list, null);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }

    static class ViewHolder {
        final TextView macTextView;
        final TextView majorTextView;
        final TextView minorTextView;


        ViewHolder(View view) {
            macTextView = (TextView) view.findViewWithTag("mac");
            majorTextView = (TextView) view.findViewWithTag("major");
            minorTextView = (TextView) view.findViewWithTag("minor");

        }
    }

}
