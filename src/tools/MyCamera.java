package tools;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class MyCamera {
	public Uri takePic(int take_photo, Activity mActivity, int picNum){
		Uri imageUri;
		File outputImage = new File(Environment.getExternalStorageDirectory(),  "/NeuqSellItemPic" + picNum + ".jpeg");
		String path = Environment.getExternalStorageDirectory() + "/NeuqSellItemPic" + picNum + ".jpeg";
		Log.e("path0", path);
		try {
			if(outputImage.exists()){
				outputImage.delete();
			}
			outputImage.createNewFile();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		imageUri = Uri.fromFile(outputImage);
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		mActivity.startActivityForResult(intent, take_photo);
		return imageUri;
	}
}
