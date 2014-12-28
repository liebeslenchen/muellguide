package mawi.muellguidems.adapter;

import java.util.ArrayList;

import mawi.muellguidems.activities.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomEntsorgungsartenAdapter extends
		ArrayAdapter<AdapterSingleItem> {
	private Context context;
	private ArrayList<AdapterSingleItem> items;

	public CustomEntsorgungsartenAdapter(Context context,
			ArrayList<AdapterSingleItem> items) {
		super(context, R.layout.entsorgung_item, items);

		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater
				.inflate(R.layout.entsorgung_item, parent, false);

		TextView textViewMain = (TextView) rowView
				.findViewById(R.id.tvEntsorgungItem);
		ImageView imageView = (ImageView) rowView
				.findViewById(R.id.ivEntsorgungItemIcon);

		textViewMain.setText(items.get(position).getBezeichnung());
		imageView.setImageResource(items.get(position).getImage());

		return rowView;
	}

}
