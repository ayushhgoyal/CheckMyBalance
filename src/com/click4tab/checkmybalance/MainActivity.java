package com.click4tab.checkmybalance;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button checkBalance;
	Context context;

	static ArrayList<String> nameList = new ArrayList<String>();
	static ArrayList<String> numberList = new ArrayList<String>();
	ListView numberListView;
	TextView emptyView;
	ArrayList<HashMap<String, String>> namesAndNumbers;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initElements();
		populateNumberList();

	}

	private void populateNumberList() {
		// TODO Auto-generated method stub

		namesAndNumbers = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < nameList.size(); i++) {
			HashMap entry = new HashMap();
			entry.put("name", nameList.get(i));
			entry.put("number", numberList.get(i));
			namesAndNumbers.add(entry);
		}
		SimpleAdapter sa = new SimpleAdapter(context, namesAndNumbers,
				R.layout.custom_row_view, new String[] { "name", "number" },
				new int[] { R.id.text1, R.id.text2 }) {
		};

		numberListView.setAdapter(sa);
		sa.notifyDataSetChanged();

	}

	private void initElements() {
		// TODO Auto-generated method stub
		checkBalance = (Button) findViewById(R.id.button1);
		setupButtonListener(checkBalance);
		context = this;
		// numberList = new ArrayList<String>();
		// nameList = new ArrayList<String>();
		numberListView = (ListView) findViewById(R.id.listView1);
		emptyView = (TextView) findViewById(R.id.textView2);
		numberListView.setEmptyView(emptyView);

		numberListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Log.e("clicked", arg0.getItemAtPosition(arg2).toString());

				HashMap<String, String> h = new HashMap<String, String>();
				h = (HashMap<String, String>) arg0.getItemAtPosition(arg2);

				Toast.makeText(context, h.get("number"), Toast.LENGTH_LONG)
						.show();
				String ussd2 = h.get("number");
				String Eussd2 = Uri.encode(ussd2);
				startActivityForResult(new Intent("android.intent.action.CALL",
						Uri.parse("tel:" + Eussd2)), 1);

			}
		});

		numberListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						HashMap<String, String> h = new HashMap<String, String>();
						h = (HashMap<String, String>) arg0
								.getItemAtPosition(arg2);

						Log.e("removed name",
								"" + nameList.remove(h.get("name")));
						Log.e("removed number",
								"" + numberList.remove(h.get("number")));
						populateNumberList();
						return true;
					}
				});

	}

	private void setupButtonListener(Button checkBalance2) {
		// TODO Auto-generated method stub
		checkBalance2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// String ussd2 = "*123#";
				// String Eussd2 = Uri.encode(ussd2);
				// startActivityForResult(new
				// Intent("android.intent.action.CALL",
				// Uri.parse("tel:" + Eussd2)), 1);

				// Show alertDialog to enter new number.
				AlertDialog.Builder addNewNumberAlert = new AlertDialog.Builder(
						context);
				addNewNumberAlert.setTitle("Add new number");
				final EditText editEnterName = new EditText(context);
				final EditText editEnterNumber = new EditText(context);
				editEnterName.setHint("Name goes here");
				editEnterNumber.setHint("Number goes here");
				editEnterNumber.setInputType(InputType.TYPE_CLASS_PHONE);

				final LinearLayout alertLayout = new LinearLayout(context);
				alertLayout.setOrientation(LinearLayout.VERTICAL);

				alertLayout.addView(editEnterName);
				alertLayout.addView(editEnterNumber);

				addNewNumberAlert.setView(alertLayout);

				addNewNumberAlert.setPositiveButton("Save",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								nameList.add(editEnterName.getText().toString()
										.trim());
								numberList.add(editEnterNumber.getText()
										.toString().trim());
								// populateNumberList();

								Toast.makeText(
										context,
										nameList.get(nameList.size() - 1)
												+ "  "
												+ numberList.get(numberList
														.size() - 1),
										Toast.LENGTH_SHORT).show();

								// namesAndNumbers = new
								// ArrayList<HashMap<String, String>>();
								// for (int i = 0; i < nameList.size(); i++) {
								// HashMap entry = new HashMap();
								// entry.put("name", nameList.get(i));
								// entry.put("number", numberList.get(i));
								// namesAndNumbers.add(entry);
								// }

								// SimpleAdapter sa = new SimpleAdapter(context,
								// namesAndNumbers,
								// R.layout.custom_row_view, new String[] {
								// "name", "number" }, new int[] {
								// R.id.text1, R.id.text2 }) {
								// };

								populateNumberList();
								// SimpleAdapter sa = new SimpleAdapter(context,
								// namesAndNumbers,
								// R.layout.custom_row_view, new String[] {
								// "name", "number" }, new int[] {
								// R.id.text1, R.id.text2 }) {
								// };
								//
								// numberListView.setAdapter(sa);
								// sa.notifyDataSetChanged();

							}
						}).setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});

				addNewNumberAlert.create().show();

			}
		});
	}

	@Override
	public void onAttachedToWindow() {

		super.onAttachedToWindow();

		Window window = getWindow();

		window.setFormat(PixelFormat.RGBA_8888);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
