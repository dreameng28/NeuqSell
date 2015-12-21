package activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dreameng.neuqsell.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import beans.UserInfo;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends Activity implements OnClickListener {

	private EditText nickname, emailAddress, password, checkPasswprd;
	private Button register; 
	private RadioButton male;
	BmobUserManager userManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		
		nickname = (EditText) findViewById(R.id.nickname);
		emailAddress = (EditText) findViewById(R.id.email_account);
		password = (EditText) findViewById(R.id.password);
		checkPasswprd = (EditText) findViewById(R.id.checkPassword);
		register = (Button) findViewById(R.id.register);
		male = (RadioButton) findViewById(R.id.male);
		userManager = BmobUserManager.getInstance(this);
		
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.register:
			if(!(TextUtils.isEmpty(nickname.getText().toString().trim()))&&analyseEmail()&&analysePassword()&&analyseCheckPassword()){
				
				registerStepOneSuccess();

			}
			
			else 		
				registerStepOneFailure();
			
			break;
		
		}
		
	}
	
	private Boolean analyseNickname(){
		String register_nickname;
		register_nickname = nickname.getText().toString();
		return true;
		//return analyseInput("[0-9 | A-Z | a-z]{6,16}", register_nickname);
	}
	
	
	private Boolean analyseEmail(){
		
		String register_email;
		register_email = emailAddress.getText().toString().trim();
		
		return analyseInput("^[0-9a-z][a-z0-9._-]{1,}@[a-z0-9-]{1,}[a-z0-9].[a-z.]{1,}[a-z]$", register_email);
	}
	
	private Boolean analysePassword(){
		String register_password;
		register_password = password.getText().toString();
		
		return analyseInput("[0-9 | A-Z | a-z]{6,16}", register_password);
	}
	
	private Boolean analyseCheckPassword(){
		
		String register_check_password, register_password;
		register_password = password.getText().toString();
		register_check_password = checkPasswprd.getText().toString();
		
		if(register_check_password.equals(register_password)){
			return true;
		}
		
		else{
			return false;
		}
	}
	
	
	private void registerStepOneSuccess(){
		
		String register_nickname, register_password;
		final String register_email;
		String register_gender;
		
		register_nickname = nickname.getText().toString();
		register_password = password.getText().toString();
		register_email = emailAddress.getText().toString();
		register_gender = male.isChecked()? "��" : "Ů";
		
		final UserInfo userInfo = new UserInfo();  
		
		userInfo.setUsername(register_email);
		userInfo.setEmail(register_email);
		userInfo.setPassword(register_password);
		userInfo.setNickname(register_nickname);
		userInfo.setGender(register_gender);
		userInfo.signUp(RegisterActivity.this, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(RegisterActivity.this, "ע��ɹ��뵽" + register_email + "��֤", Toast.LENGTH_SHORT).show();
				Intent i = new Intent();
				i.putExtra("account", register_email);
				userManager.bindInstallationForRegister(userInfo.getUsername());
				setResult(RESULT_OK, i);
				RegisterActivity.this.finish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(RegisterActivity.this, "ע��ʧ��: " + arg1 + "������", Toast.LENGTH_LONG).show();
			}
		});
		
		
	}
	
	
	private void registerStepOneFailure(){
		
		if(TextUtils.isEmpty(nickname.getText().toString().trim())){
			Toast.makeText(RegisterActivity.this, "�ǳƲ���Ϊ��", Toast.LENGTH_SHORT).show();
		}
		
		else if(!analyseEmail()){
			Toast.makeText(RegisterActivity.this, "�����ʽ����ȷ", Toast.LENGTH_SHORT).show();
		}
		
		else if(!analysePassword()){
			Toast.makeText(RegisterActivity.this, "�����ʽ����ȷ", Toast.LENGTH_SHORT).show();
		}
		
		else if(!analyseCheckPassword()){
			Toast.makeText(RegisterActivity.this, "�������벻һ��", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public static Boolean analyseInput(String reg, String input){
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
}