package mawi.muellguidems.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void onClickBtnParseTest(View view) {
		Intent intentParseTest = new Intent(this, ParseTestActivity.class);
		startActivity(intentParseTest);
	}
}
