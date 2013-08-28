package wyf.ytl;
import android.content.Context;//������ص���
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
public class MyView extends View{//�̳���View
	Bitmap myBitmap;//ͼƬ������
	Paint paint;//���ʵ�����
	public MyView(Context context, AttributeSet attrs) {//������
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.initBitmap(); 
	}
	public void initBitmap(){ 
		paint = new Paint();//����һ������
		myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);//���ͼƬ��Դ
	}
	@Override
	protected void onDraw(Canvas canvas) {//��д�Ļ��Ʒ���
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setAntiAlias(true);//�򿪿����
		paint.setColor(Color.WHITE);//���û��ʵ���ɫ
		paint.setTextSize(15);
		canvas.drawBitmap(myBitmap, 10, 10, paint);//����ͼƬ
		canvas.drawText("ͼƬ�Ŀ��: "+myBitmap.getWidth(), 20, 220, paint);//�����ַ�����ͼƬ�Ŀ��
		canvas.drawText("ͼƬ�ĸ߶�: "+myBitmap.getHeight(), 150, 220, paint);//�����ַ�����ͼƬ�ĸ߶�
	}
}
