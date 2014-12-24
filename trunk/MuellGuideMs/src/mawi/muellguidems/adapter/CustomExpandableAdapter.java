package mawi.muellguidems.adapter;

import java.util.ArrayList;

import mawi.muellguidems.activities.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomExpandableAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<AdapterGroupItem> groups;

	public CustomExpandableAdapter(Context context,
			ArrayList<AdapterGroupItem> groups) {

		this.context = context;
		this.groups = groups;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return groups.get(groupPosition).getChildren().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		AdapterChildItem child = (AdapterChildItem) getChild(groupPosition,
				childPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.muelltrennung_item_details, parent, false);
		}
		TextView tv = (TextView) convertView
				.findViewById(R.id.tvMuelltrennungItemDetails);

		tv.setText(child.getBezeichnung().toString());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).getChildren().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		try {
			AdapterGroupItem group = (AdapterGroupItem) getGroup(groupPosition);
			if (convertView == null) {
				LayoutInflater inf = (LayoutInflater) context
						.getSystemService(context.LAYOUT_INFLATER_SERVICE);
				convertView = inf.inflate(R.layout.muelltrennung_item, parent,
						false);
			}
			TextView tv = (TextView) convertView
					.findViewById(R.id.tvMuelltrennungItem);
			ImageView iv = (ImageView) convertView
					.findViewById(R.id.imgMuelltrennung);

			tv.setText(group.getBezeichnung());
			iv.setImageResource(group.getImage());
			return convertView;
		} catch (Exception ex) {

			return null;
		}
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
