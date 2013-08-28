package wyf.ytl;
import android.app.Activity;//引入相关的类
import android.os.Bundle;
public class Sample_2_8 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {//重写onCreate回调方法
        super.onCreate(savedInstanceState);//调用基类的onCreate方法
        setContentView(R.layout.main);//设置当前显示的用户界面
    }
}