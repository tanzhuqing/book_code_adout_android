package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * 
 * ����Ϊ������
 * �Ծ������˷�װ
 * ��¼���Լ���λ�ú������Լ���ǰ��״̬
 *
 */
public class MySprite {
	int i = 3;
	int j = 1;//�����λ��
	Bitmap man;//��ǰͼƬ
	int X;
	int Y;//��ǰ��ʵ������
	boolean isRun = false;//�����Ƿ���������
	Integer[] manUpID = new Integer[]//ѭ����ͼƬID
							{
								R.drawable.c1,
								R.drawable.c4,
								R.drawable.c8,
								R.drawable.c9,
							};
	Integer[] manDownID = new Integer[]//ѭ����ͼƬID
							{
								R.drawable.a1,
								R.drawable.a4,
								R.drawable.a8,
								R.drawable.a9,
							};	
	Integer[] manLeftID = new Integer[]//ѭ����ͼƬID
  							{
  								R.drawable.b1,
  								R.drawable.b4,
  								R.drawable.b8,
  								R.drawable.b9,
  							};		
	Integer[] manRightID = new Integer[]//ѭ����ͼƬID
							{
								R.drawable.d1,
								R.drawable.d4,
								R.drawable.d8,
								R.drawable.d9,
							};		
	
	Integer[] manPushUpID = new Integer[]//ѭ����ͼƬID
   							{
   								R.drawable.g1,
   								R.drawable.g4,
   								R.drawable.g6,
   								R.drawable.g14,
   							};	
	Integer[] manPushDownID = new Integer[]//ѭ����ͼƬID
   							{
   								R.drawable.e1,
   								R.drawable.e3,
   								R.drawable.e4,
   								R.drawable.e14,
   							};
	Integer[] manPushLeftID = new Integer[]//ѭ����ͼƬID
 							{
 								R.drawable.f1,
 								R.drawable.f2,
 								R.drawable.f3,
 								R.drawable.f14,
 							};	
	Integer[] manPushRightID = new Integer[]//ѭ����ͼƬID
   							{
   								R.drawable.h1,
   								R.drawable.h2,
   								R.drawable.h3,
   								R.drawable.h14,
   							};		
	Bitmap manUp[];//���ϵ���·��ͼƬ
	Bitmap manDown[];//���µ���·��ͼƬ
	Bitmap manLeft[];//�������·��ͼƬ
	Bitmap manRight[];//���ҵ���·��ͼƬ
	Bitmap manPushUp[];//���ϵ������ӵ�ͼƬ
	Bitmap manPushDown[];//���µ������ӵ�ͼƬ
	Bitmap manPushLeft[];//����������ӵ�ͼƬ
	Bitmap manPushRight[];//���ҵ������ӵ�ͼƬ
	PushBoxActivity pushBoxActivity;//Activity������
	public MySprite(PushBoxActivity pushBoxActivity){//������
		this.pushBoxActivity = pushBoxActivity;
	    X = pushBoxActivity.gameView.initX+36*j-15*i + 2;
	    Y = pushBoxActivity.gameView.initY+10*j+25*i - 25;	
	    man = BitmapFactory.decodeResource(pushBoxActivity.getResources(), R.drawable.a1);//��ʼ��ͼƬ
	    this.initBitmap();//��ʼ�����е�ͼƬ
	}
	public void initBitmap(){//��ʼ�����е�ͼƬ
		manUp = new Bitmap[manUpID.length];
		manDown = new Bitmap[manDownID.length];
		manLeft = new Bitmap[manLeftID.length];
		manRight = new Bitmap[manRightID.length];
		manPushUp = new Bitmap[manPushUpID.length];
		manPushDown = new Bitmap[manPushDownID.length];
		manPushLeft = new Bitmap[manPushLeftID.length];
		manPushRight = new Bitmap[manPushRightID.length];
	    for(int i=0; i<manUpID.length; i++){
	    	manUp[i] = BitmapFactory.decodeResource(pushBoxActivity.getResources(), manUpID[i]);
	    }
	    for(int i=0; i<manDownID.length; i++){
	    	manDown[i] = BitmapFactory.decodeResource(pushBoxActivity.getResources(), manDownID[i]);
	    }
	    for(int i=0; i<manLeftID.length; i++){
	    	manLeft[i] = BitmapFactory.decodeResource(pushBoxActivity.getResources(), manLeftID[i]);
	    }
	    for(int i=0; i<manRightID.length; i++){
	    	manRight[i] = BitmapFactory.decodeResource(pushBoxActivity.getResources(), manRightID[i]);
	    }
	    for(int i=0; i<manPushUpID.length; i++){
	    	manPushUp[i] = BitmapFactory.decodeResource(pushBoxActivity.getResources(), manPushUpID[i]);
	    }
	    for(int i=0; i<manPushDownID.length; i++){
	    	manPushDown[i] = BitmapFactory.decodeResource(pushBoxActivity.getResources(), manPushDownID[i]);
	    }
	    for(int i=0; i<manPushLeftID.length; i++){
	    	manPushLeft[i] = BitmapFactory.decodeResource(pushBoxActivity.getResources(), manPushLeftID[i]);
	    }
	    for(int i=0; i<manPushRightID.length; i++){
	    	manPushRight[i] = BitmapFactory.decodeResource(pushBoxActivity.getResources(), manPushRightID[i]);
	    }
	}
	public void drawMySelf(Canvas canvas, Paint paint){//���Ʒ���
		if(this.isRun){
			canvas.drawBitmap(man, X, Y, paint);
		}
		else{
		    X = pushBoxActivity.gameView.initX+36*j-15*i + 2;
		    Y = pushBoxActivity.gameView.initY+10*j+25*i - 25;	
		    canvas.drawBitmap(man, X, Y, paint);
		}
	}
}