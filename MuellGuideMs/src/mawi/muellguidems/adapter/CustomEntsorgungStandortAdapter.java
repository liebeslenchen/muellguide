package mawi.muellguidems.adapter;

import java.util.ArrayList;

import mawi.muellguidems.activities.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomEntsorgungStandortAdapter extends
		ArrayAdapter<AdapterSingleItem> implements Filterable {

	private Context context;
	private ArrayList<AdapterSingleItem> items;
	private StandorteFilter filter;

	public CustomEntsorgungStandortAdapter(Context context,
			ArrayList<AdapterSingleItem> items) {
		super(context, R.layout.entsorgung_standorte_item, items);

		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.entsorgung_standorte_item,
				parent, false);

		TextView textViewMain = (TextView) rowView
				.findViewById(R.id.tvEntsorgungStandort);
		ImageView imageView = (ImageView) rowView
				.findViewById(R.id.ivEntsorgungStandortItem);

		textViewMain.setText(items.get(position).getBezeichnung());
		imageView.setImageResource(items.get(position).getImage());

		return rowView;
	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new StandorteFilter();
		}

		return filter;
	}

	private class StandorteFilter extends Filter {

		ArrayList<AdapterSingleItem> allItems = items;

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			items = allItems;

			FilterResults filterResults = new FilterResults();

			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				filterResults.values = items;
				filterResults.count = items.size();
			} else {
				ArrayList<AdapterSingleItem> newList = new ArrayList<AdapterSingleItem>();

				for (AdapterSingleItem p : items) {
					if (p.getBezeichnung().toUpperCase()
							.startsWith(constraint.toString().toUpperCase()))
						newList.add(p);
				}

				filterResults.values = newList;
				filterResults.count = newList.size();

			}

			return filterResults;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			if (results.count == 0)
				notifyDataSetInvalidated();
			else {
				items = (ArrayList<AdapterSingleItem>) results.values;
				notifyDataSetChanged();
			}

		}

	}

}
