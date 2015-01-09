package mawi.muellguidems.adapter;

import java.util.ArrayList;

import mawi.muellguidems.activities.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomMuelltrennungExpandableAdapter extends
		BaseExpandableListAdapter implements Filterable {

	private Context context;
	private ArrayList<AdapterGroupItem> filteredData;
	private ArrayList<AdapterGroupItem> originalData;

	private LayoutInflater inflater;

	private MuelltrennungFilter filter;

	public CustomMuelltrennungExpandableAdapter(Context context,
			ArrayList<AdapterGroupItem> groups) {

		this.context = context;
		this.filteredData = groups;
		this.originalData = groups;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return filteredData.get(groupPosition).getChildren().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		AdapterChildItem child = (AdapterChildItem) getChild(groupPosition,
				childPosition);

		ChildViewHolder childViewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.muelltrennung_item_details,
					null);

			childViewHolder = new ChildViewHolder();
			childViewHolder.textView = (TextView) convertView
					.findViewById(R.id.tvMuelltrennungItemDetails);

			convertView.setTag(childViewHolder);
		} else {
			childViewHolder = (ChildViewHolder) convertView.getTag();
		}

		childViewHolder.textView.setText(child.getBezeichnung().toString());

		return convertView;
	}

	static class ChildViewHolder {
		TextView textView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return filteredData.get(groupPosition).getChildren().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return filteredData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return filteredData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		AdapterGroupItem group = (AdapterGroupItem) getGroup(groupPosition);
		GroupViewHolder groupViewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.muelltrennung_item, null);

			groupViewHolder = new GroupViewHolder();
			groupViewHolder.tv = (TextView) convertView
					.findViewById(R.id.tvMuelltrennungItem);
			groupViewHolder.iv = (ImageView) convertView
					.findViewById(R.id.imgMuelltrennung);

			convertView.setTag(groupViewHolder);
		} else {
			groupViewHolder = (GroupViewHolder) convertView.getTag();
		}

		groupViewHolder.tv.setText(group.getBezeichnung());
		groupViewHolder.iv.setImageResource(group.getImage());
		return convertView;

	}

	static class GroupViewHolder {
		TextView tv;
		ImageView iv;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public Filter getFilter() {
		if (filter == null)
			filter = new MuelltrennungFilter();

		return filter;
	}

	private class MuelltrennungFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			String filterString = constraint.toString().toLowerCase();

			FilterResults results = new FilterResults();

			final ArrayList<AdapterGroupItem> list = originalData;

			int count = list.size();
			final ArrayList<AdapterGroupItem> nlist = new ArrayList<AdapterGroupItem>(
					count);

			String filterableString;

			for (int i = 0; i < count; i++) {
				filterableString = list.get(i).getBezeichnung();
				if (filterableString.toLowerCase().contains(filterString)) {
					nlist.add(list.get(i));
				}
			}

			results.values = nlist;
			results.count = nlist.size();

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			filteredData = (ArrayList<AdapterGroupItem>) results.values;
			notifyDataSetChanged();

		}
	}
}
