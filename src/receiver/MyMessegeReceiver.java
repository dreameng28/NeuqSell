package receiver;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.bmob.im.inteface.EventListener;
import cn.bmob.im.util.BmobLog;

public class MyMessegeReceiver extends BroadcastReceiver{
	public static ArrayList<EventListener> ehList = new ArrayList<EventListener>();

    @Override
    public void onReceive(Context context, Intent intent) {
        String json = intent.getStringExtra("msg");
        BmobLog.i("收到的message = " + json);
        //省略其他代码
    }
}
