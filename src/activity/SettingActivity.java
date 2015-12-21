package activity;

import org.dreameng.neuqsell.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class SettingActivity extends Activity implements OnClickListener, OnItemClickListener {
	
	private TextView mTextView, mAboutTextView;
	private ImageView mAppIconImageView;
	private Button mBackButton;
	private ListView mSettingListView;
	private int backFlag;
	private static final String[] settings = new String[] {
	    "  ��������", "  �˻���Ϣ", "  ������", "  Ӧ�÷���", "  �˳���¼"
	    };
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_page);
				// ���÷�������
		mController.setShareContent("������ữ�����SDK�����ƶ�Ӧ�ÿ��������罻�����ܣ�http://www.umeng.com/social");
		// ���÷���ͼƬ, ����2ΪͼƬ��url��ַ
		mController.setShareMedia(new UMImage(this, 
		                                      "http://www.umeng.com/images/pic/banner_module_social.png"));
		
		
		String appID = "wx967daebe835fbeac";
		String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
		// ���΢��ƽ̨
		UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
		wxHandler.addToSocialSDK();
		// ���΢������Ȧ
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		
		backFlag = 0;
		mTextView = (TextView) findViewById(R.id.display_title_tv);
		mTextView.setText("����");
		mBackButton = (Button) findViewById(R.id.back_to_main);
		mBackButton.setOnClickListener(this);
		mSettingListView = (ListView) findViewById(R.id.setting_list);
		mAppIconImageView = (ImageView) findViewById(R.id.about_app_icon);
		mAppIconImageView.setVisibility(View.GONE);
		ArrayAdapter<String> mSettingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settings);
		mSettingListView.setAdapter(mSettingAdapter);
		mSettingListView.setOnItemClickListener(this);
		mAboutTextView = (TextView) findViewById(R.id.about_text);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.back_to_main:
			if(backFlag == 0){
			finish();
			}
			else{
				backFlag = 0;
				mTextView.setText("����");
				mSettingListView.setVisibility(View.VISIBLE);
				mAppIconImageView.setVisibility(View.GONE);
				mAboutTextView.setVisibility(View.GONE);
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		switch(arg2){
		case 0:
			backFlag = 1;
			mTextView.setText("����");
			mAppIconImageView.setVisibility(View.VISIBLE);
			mAboutTextView.setVisibility(View.VISIBLE);
			mSettingListView.setVisibility(View.GONE);
			try {
				mAboutTextView.setText("��ǰ�汾 : " + getVersionName() + "\n\n�����ǹ������ɰ�״���򶯱��ˡ�" +
						"\nŶ����������һ���ҿ����ϴ��Լ���������Ʒ���������߹������������Ʒ��Androidƽ̨�����" +
						"\n\n��Ӧ������Ϊ�Ǽ����רҵ����ѧ�����������ޡ�����Ŭ���������ƾ����������������⣬" +
						"ϣ�����ָ�����һᾡ�����ơ�" +
						"���⣬��Ӧ�ô󲿷�ͼƬ���Ի�������" +
						"����漰��Ȩ���⣬�һᾡ���޸ġ�" +
						"\n\n��лBmob�ƶ�������ṩ�������Ʒ���" +
						"��Ӧ����Ȼ��ӯ������Ȼʮ�ָ�л��ҵ����κ�֧�֡�" +
						"\n\n��ӭ��Ҽ��뽻��Ⱥ 281858260�����������Ľ���ɣ�");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:
			Intent changeAccountIntent = new Intent(this, ChangeAccountActivity.class);
			startActivity(changeAccountIntent);
			break;
		case 2:
			BmobUpdateAgent.forceUpdate(this);
			BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

				 @Override
				 public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
				     // TODO Auto-generated method stub
				     //����updateStatus���жϸ����Ƿ�ɹ�
					 if (updateStatus == UpdateStatus.Yes) {//�汾�и���

			            }else if(updateStatus == UpdateStatus.No){
			                Toast.makeText(SettingActivity.this, "�Ѿ������°汾", Toast.LENGTH_SHORT).show();
			            }else if(updateStatus==UpdateStatus.EmptyField){//����ʾֻ�����ѿ����߹�ע��Щ��������Գɹ���������û���ʾ
			                Toast.makeText(SettingActivity.this, "������AppVersion��ı����1��target_size���ļ���С���Ƿ���д��2��path����android_url���߱�������һ�", Toast.LENGTH_SHORT).show();
			            }else if(updateStatus==UpdateStatus.IGNORED){
			                Toast.makeText(SettingActivity.this, "�ð汾�ѱ����Ը���", Toast.LENGTH_SHORT).show();
			            }else if(updateStatus==UpdateStatus.ErrorSizeFormat){
			                Toast.makeText(SettingActivity.this, "����target_size��д�ĸ�ʽ����ʹ��file.length()������ȡapk��С��", Toast.LENGTH_SHORT).show();
			            }else if(updateStatus==UpdateStatus.TimeOut){
			                Toast.makeText(SettingActivity.this, "��ѯ������ѯ��ʱ", Toast.LENGTH_SHORT).show();
			            }
				 }
				});
			break;
			
		case 3 :
			mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
			mController.openShare(this, false);
			break;
		case 4 :
			Intent resaultIntent = new Intent();
			resaultIntent.putExtra("settingResult", "logout");
            //���÷�������
            setResult(RESULT_OK, resaultIntent);
            //�ر�Activity
			finish();
			break;
		
		}
	}
	
	 public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    	
	    	//������µ��Ƿ��ؼ��Ҵ���Ϊ1�Ҷ��Żظ������������У������Ի���
		 if (keyCode == KeyEvent.KEYCODE_BACK && backFlag == 1) {  
			 backFlag = 0;
			 mTextView.setText("����");
			 mSettingListView.setVisibility(View.VISIBLE);
			 mAppIconImageView.setVisibility(View.GONE);
			 mAboutTextView.setVisibility(View.GONE);
			 return true;  
		 }  
	        
	        //�����հ�׿�Ķ��巽��ִ��
		 else {
			 return super.onKeyDown(keyCode, event);
		 }
	 }  
	 
	 private String getVersionName() throws Exception {
		 // ��ȡpackagemanager��ʵ��
		 PackageManager packageManager = getPackageManager();
		 // getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
		 PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
		 String version = packInfo.versionName;
		 return version;
		 }
}
