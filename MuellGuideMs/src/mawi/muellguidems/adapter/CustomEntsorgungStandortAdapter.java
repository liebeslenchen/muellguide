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
	private ArrayList<AdapterSingleItem> filteredData;
	private ArrayList<AdapterSingleItem> originalData;
	private StandorteFilter filter;
	private LayoutInflater inflater;

	public CustomEntsorgungStandortAdapter(Context context,
			ArrayList<AdapterSingleItem> items) {
		super(context, R.layout.entsorgung_standorte_item, items);

		this.context = context;
		this.filteredData = items;
		this.originalData = items;
		inflater = LayoutInflater.from(this.context);
	}

	public int getCount() {
		return filteredData.size();
	}

	public AdapterSingleItem getItem(int position) {
		return filteredData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.entsorgung_standorte_item,
					null);

			holder = new ViewHolder();
			holder.textViewMain = (TextView) convertView
					.findViewById(R.id.tvEntsorgungStandort);

			holder.imageView = (ImageView) convertView
					.findViewById(R.id.ivEntsorgungStandortItem);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textViewMain
				.setText(filteredData.get(position).getBezeichnung());
		holder.imageView
				.setImageResource(filteredData.get(position).getImage());
		return convertView;

	}

	static class ViewHolder {
		TextView textViewMain;
		ImageView imageView;
	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new StandorteFilter();
		}

		return filter;
	}

	private class StandorteFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			String filterString = constraint.toString().toLowerCase();

			FilterResults results = new FilterResults();

			final ArrayList<AdapterSingleItem> list = originalData;

			int count = list.size();
			final ArrayList<AdapterSingleItem> nlist = new ArrayList<AdapterSingleItem>(
					count);

			String filterableStringFuerBezeichnung;
			String filterableStringFuerPLZ;

			for (int i = 0; i < count; i++) {
				// Nach Bezeichnung durchfiltern...
				filterableStringFuerBezeichnung = list.get(i).getBezeichnung();
				filterableStringFuerPLZ = list.get(i).getPlz() == null ? null
						: list.get(i).getPlz();

				if (filterableStringFuerBezeichnung.toLowerCase().contains(
						filterString)
						|| (filterableStringFuerPLZ != null && filterableStringFuerPLZ
								.toLowerCase().contains(filterString))) {
					nlist.add(list.get(i));
				}

				// // // Nach PLZ durchfiltern... (nur, falls nichts unter
				// // 'Bezeichnung' gefunden wurde!)
				// if (nlist == null) {
				// if (list.get(i).getPlz() != null) {
				// filterableStringFuerBezeichnung = list.get(i).getPlz();
				// if (filterableStringFuerBezeichnung.toLowerCase()
				// .contains(filterString)) {
				// nlist.add(list.get(i));
				// }
				// }
				// }
			}

			results.values = nlist;
			results.count = nlist.size();

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			filteredData = (ArrayList<AdapterSingleItem>) results.values;
			notifyDataSetChanged();

		}

	}

}
