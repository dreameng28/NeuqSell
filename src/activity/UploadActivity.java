package activity;

import java.io.FileNotFoundException;
import java.util.List;

import org.dreameng.neuqsell.R;

import tools.MyCamera;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import beans.UserInfo;
import beans.UserItem;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class UploadActivity extends Activity implements OnClickListener, OnLongClickListener{
	
	private EditText mEditText;
	private TextView mTVUpload, mTVCancel;
	private ImageView mImageView1, mImageView2, mImageView3;
	private Uri imageUri;
	
	MyCamera myCamera;
	private UserInfo user = new UserInfo();
	UserItem userItem = new UserItem();

	private static final int TAKE_PHOTO1 = 1;
//	private static final int TAKE_PHOTO2 = 2;
//	private static final int TAKE_PHOTO3 = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
		
		user = BmobUser.getCurrentUser(this, UserInfo.class);
		
		mEditText = (EditText) findViewById(R.id.description);
		mEditText.setOnClickListener(this);
		mTVUpload = (TextView) findViewById(R.id.confirm_upload);
		mTVUpload.setOnClickListener(this);
		mTVCancel = (TextView) findViewById(R.id.cancel_uoload);
		mTVCancel.setOnClickListener(this);
		mImageView1 = (ImageView) findViewById(R.id.upload_pic1);
//		mImageView2 = (ImageView) findViewById(R.id.upload_pic2);
//		mImageView3 = (ImageView) findViewById(R.id.upload_pic3);
		mImageView1.setOnClickListener(this);
		mImageView2.setOnClickListener(this);
		mImageView3.setOnClickListener(this);
		mImageView1.setOnLongClickListener(this);
		mImageView2.setOnLongClickListener(this);
		mImageView3.setOnLongClickListener(this);

		myCamera = new MyCamera();
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.description:
			mEditText.setCursorVisible(true);
			break;
			
		case R.id.upload_pic1:
			imageUri = myCamera.takePic(TAKE_PHOTO1, UploadActivity.this, 1);
			break;
			
//		case R.id.upload_pic2:
//			imageUri = myCamera.takePic(TAKE_PHOTO2, UploadActivity.this, 2);
//			break;
//			
//		case R.id.upload_pic3:
//			imageUri = myCamera.takePic(TAKE_PHOTO3, UploadActivity.this, 3);
//			break;
		case R.id.confirm_upload:
			String picPath1 = Environment.getExternalStorageDirectory() + "/NeuqSellItemPic1.jpg";
//			String picPath2 = Environment.getExternalStorageDirectory() + "/NeuqSellItemPic2.jpg";
//			String picPath3 = Environment.getExternalStorageDirectory() + "/NeuqSellItemPic3.jpg";
//			String[] filePaths = new String[3];
//			filePaths[0] = picPath1;
//			filePaths[1] = picPath2;
//			filePaths[2] = picPath3;
			//Log.e("path", picPath);
//			final BmobFile picFile = new BmobFile(new File(picPath));
//			picFile.upload(UploadActivity.this, new UploadFileListener() {
//				
//				@Override
//				public void onSuccess() {
//					// TODO Auto-generated method stub
//					//Toast.makeText(Upload.this , "图片上传成功", Toast.LENGTH_SHORT).show();
//					uploadDescption(picFile);
//				}
//				
//				@Override
//				public void onFailure(int arg0, String arg1) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
			
			
//			Bmob.uploadBatch(UploadActivity.this, filePaths, new UploadBatchListener() {
//				
//				@Override
//				public void onSuccess(List<BmobFile> arg0, List<String> arg1) {
//					// TODO Auto-generated method stub
//					Toast.makeText(getBaseContext(),"" + arg0.size(), Toast.LENGTH_SHORT).show();
//					if(arg0.size() == 3){
//						uploadDescption(arg1);
//					}	
//				}
//				
//				@Override
//				public void onProgress(int arg0, int arg1, int arg2, int arg3) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onError(int arg0, String arg1) {
//					// TODO Auto-generated method stub
//					Toast.makeText(getBaseContext(), arg1, Toast.LENGTH_SHORT).show();
//					
//				}
//			});
			
//			BmobProFile.getInstance(this).uploadBatch(filePaths, new com.bmob.btp.callback.UploadBatchListener() {
//				
//				@Override
//				public void onError(int arg0, String arg1) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onSuccess(boolean arg0, String[] arg1, String[] arg2) {
//					// TODO Auto-generated method stub
//					if(arg0){
//						uploadDescption(arg2);
//					}
//				}
//				
//				@Override
//				public void onProgress(int arg0, int arg1, int arg2, int arg3) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
			
			
			Intent i = new Intent();
			i.putExtra("uploading", "isUpload");
			setResult(RESULT_OK, i);
			this.finish();
			break;
			
		case R.id.cancel_uoload:
			this.finish();
			break;
		}
		
	}
	
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	// TODO Auto-generated method stub
	    	super.onActivityResult(requestCode, resultCode, data);

	    	if((resultCode == RESULT_OK) && (requestCode < 4)){
    			Intent intent = new Intent("com.android.camera.action.CROP");
    			intent.setDataAndType(imageUri, "image/*");
    			intent.putExtra("scale", true);
    			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    			//startActivityForResult(intent, CROP_PHOTO);
    			Bitmap bitmap;
				try {
					bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
					switch(requestCode){
					case TAKE_PHOTO1:
						mImageView1.setImageBitmap(bitmap);
//						mImageView2.setVisibility(View.VISIBLE);
						break;
//					case TAKE_PHOTO2:
//						mImageView2.setImageBitmap(bitmap);
//						mImageView3.setVisibility(View.VISIBLE);
//						break;
//					case TAKE_PHOTO3:
//						mImageView3.setImageBitmap(bitmap);
//						break;
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	    	}
	    	
	    	if((resultCode == RESULT_OK) && (requestCode >= 4)){
	    		Uri uri = data.getData();  
	            ContentResolver cr = this.getContentResolver();  
	            try {  
	                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
	                /* 将Bitmap设定到ImageView */ 
	                switch(requestCode){
	                case 4:
	                	mImageView1.setImageBitmap(bitmap);
//	 					mImageView2.setVisibility(View.VISIBLE);
	 					break;
//	 				case 5:
//	 					mImageView2.setImageBitmap(bitmap);
//	 					mImageView3.setVisibility(View.VISIBLE);
//	 					break;
//	 				case 6:
//	 					mImageView3.setImageBitmap(bitmap);
//	 					break;
	                }
	               

	            } catch (FileNotFoundException e) {
	            	e.printStackTrace();
	            }    
	    		
	    	}
	    }
	   
	   public void uploadDescption(String mBmobPic){  
		   String description;
			description = mEditText.getText().toString();
			userItem.setItemDpn(description);
			userItem.setItemPic(mBmobPic);
			userItem.setUser(user);
//			userItem.setItemPic2(mBmobPic2);
//			userItem.setItemPic3(mBmobPic3);
			userItem.save(this, new SaveListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(UploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					addItemToUser();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					Toast.makeText(UploadActivity.this,arg1, Toast.LENGTH_SHORT).show();
				}
			});
	   }
	   
	   private void addItemToUser(){
			
			BmobRelation userItems = new BmobRelation();
			userItems.add(userItem);
			user.setUserItems(userItems);
			user.update(this, new UpdateListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(UploadActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();

				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
				}
			});
		}
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		i.setType("image/*");
		i.setAction(Intent.ACTION_GET_CONTENT);
		switch(v.getId()){
		case R.id.upload_pic1:	
			startActivityForResult(i, 4);
			break;
//		case R.id.upload_pic2:
//			startActivityForResult(i, 5);
//			break;
//		case R.id.upload_pic3:
//			startActivityForResult(i, 6);
//			break;
		}
		return false;
	}
}
