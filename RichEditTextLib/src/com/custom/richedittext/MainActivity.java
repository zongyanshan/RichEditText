package com.custom.richedittext;


import android.app.Activity;
import android.os.Bundle;
import com.custom.richedittext.R;

public class MainActivity extends Activity {
	
	private RichEditTextView richEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		richEditText = (RichEditTextView)findViewById(R.id.editview);
		richEditText.setText("我们都是中国人");
		
	}
	
}
