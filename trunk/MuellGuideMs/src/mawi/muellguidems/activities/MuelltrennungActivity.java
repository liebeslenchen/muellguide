package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterGroupItem;
import mawi.muellguidems.adapter.CustomMuelltrennungExpandableAdapter;
import mawi.muellguidems.util.DAO;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ExpandableListView;

public class MuelltrennungActivity extends BaseActivity {

	ExpandableListView elvMuelltrennung;
	CustomMuelltrennungExpandableAdapter adapter;
	EditText etSearchMuelltrennung;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_muelltrennung);

		etSearchMuelltrennung = (EditText) findViewById(R.id.etSearchMuelltrennung);

		elvMuelltrennung = (ExpandableListView) findViewById(R.id.elvMuelltrennung);
		setList();

		etSearchMuelltrennung.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				MuelltrennungActivity.this.adapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	public void setList() {

		ArrayList<AdapterGroupItem> data = DAO
				.getAlleGegenstaendeFuerExpandableAdapter();

		adapter = null;

		if (data != null) {
			adapter = new CustomMuelltrennungExpandableAdapter(this, data);
		}

		elvMuelltrennung.setAdapter(adapter);

	}
}
