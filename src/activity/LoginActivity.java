package activity;

import org.dreameng.neuqsell.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import beans.UserInfo;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity implements OnClickListener{
	
	private TextView register, forgetPassword;
	private EditText inputAccount, inputPassword;
	private Button login;
	private CheckBox autoLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		SharedPreferences sharedPreferences = getSharedPreferences("login_data", MODE_PRIVATE);
		String account = sharedPreferences.getString("user_account", "");
		
		register = (TextView) findViewById(R.id.login_register);
		forgetPassword = (TextView) findViewById(R.id.forgetPassword);
		inputAccount = (EditText) findViewById(R.id.account);
		inputPassword = (EditText) findViewById(R.id.login_password);
		login = (Button) findViewById(R.id.login);
		autoLogin = (CheckBox) findViewById(R.id.auto_login);		
		inputAccount.setText(account);

		if(!(TextUtils.isEmpty(inputAccount.getText()))){
			inputPassword.requestFocus();
		}
		
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		forgetPassword.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.login_register:
			Intent i = new Intent(this, RegisterActivity.class);
			startActivityForResult(i, 1);
			break;
			
		case R.id.login:
			login();
			break;
		
		case R.id.forgetPassword:
			Intent findPasswordIntent = new Intent(this, ForgetPasswordActivity.class);
			startActivity(findPasswordIntent);
			break;
		
		}
	}
	
	private void login(){
		final String login_account = inputAccount.getText().toString();
		final String login_password = inputPassword.getText().toString();
		final UserInfo userInfo = new UserInfo();
		userInfo.setUsername(login_account);
		userInfo.setPassword(login_password);
		userInfo.login(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				
				SharedPreferences sharedPreferences = getSharedPreferences("login_data", MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				
				// TODO Auto-generated method stub
				if(autoLogin.isChecked()){
					
					
					editor.putString("user_account", login_account);
					editor.putString("user_password", login_password);
					editor .commit();
					
				}
				
				else{
					editor.clear();
					editor.putString("user_account", login_account);
					editor.commit();
				}
						
				Intent i = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(i);
				LoginActivity.this.finish();
				
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "µ«¬º ß∞‹: " + arg1 + "«Î÷ÿ ‘", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(requestCode == 1){
				inputAccount.setText(data.getStringExtra("account"));
				Log.e("account", data.getStringExtra("account"));
				inputPassword.setCursorVisible(true);
			}
		}
		
	}

}
