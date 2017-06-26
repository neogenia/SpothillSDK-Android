package com.neogenia.examplespothillsdk;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import cz.neogenia.spothill_library.CampaignsReceiver;
import cz.neogenia.spothill_library.SpothillLibrary;
import cz.neogenia.spothill_library.api.user.User;
import cz.neogenia.spothill_library.models.Callback;
import cz.neogenia.spothill_library.models.Campaign;

public class ExampleActivity extends AppCompatActivity implements CampaignsReceiver {

	private SpothillLibrary spothillLibrary;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		spothillLibrary = ((ExampleSDKApplication)getApplicationContext()).getSpothillLibrary();

		spothillLibrary.temporalLogin(new Callback<User>() {
			@Override
			public void success(User object) {
				spothillLibrary.startSendingCampaigns(ExampleActivity.this);
			}

			@Override
			public void error() {

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_example, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void nearByCampaigns(ArrayList<Campaign> arrayList) {
		Log.e("Yolo", arrayList.size() + "");
	}
}
