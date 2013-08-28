package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	PushBoxActivity pushBoxActivity;
	GameViewDrawThread gameViewDrawThread;
	int initX = 70;//����ʱ��һ��������,����ʼ���Ƶ�λ��
	int initY = 50;
	Paint paint;
	Bitmap greenBox;//��ɫ������ 
	Bitmap wall;//ǽ
	Bitmap box;//����
	Bitmap exit2;//�˳���ťͼƬ
	Bitmap goon;//��ϲ���ص�ͼƬ
	int tx = -1;
	int ty = -1;//��ǰ�ƶ����ӵ�����,-1��ʾû���ƶ���
	int tempi = 0;//��ǰ�ƶ����ӵ�i
	int tempj = 0;//��ǰ�ƶ����ӵ�j
	int status = 0 ;//0������Ϸ��,1ʤ��
	public GameView(PushBoxActivity pushBoxActivity) {//������
		super(pushBoxActivity);
		this.pushBoxActivity = pushBoxActivity;
		gameViewDrawThread = new GameViewDrawThread(this,getHolder());
		getHolder().addCallback(this);
		initBitmap();
	}
	public void initBitmap(){ 
		paint = new Paint();
		greenBox = BitmapFactory.decodeResource(getResources(), R.drawable.greenbox);//��ɫ������
		wall = BitmapFactory.decodeResource(getResources(), R.drawable.wall);//ǽ
		box = BitmapFactory.decodeResource(getResources(), R.drawable.box);//����
		exit2 = BitmapFactory.decodeResource(getResources(), R.drawable.exit2);//ǽ
		goon = BitmapFactory.decodeResource(getResources(), R.drawable.goon);
	}
	protected void onDraw(Canvas canvas) {
		if(status == 0){//������Ϸ��
			paint.setAntiAlias(true);//�����
			canvas.drawColor(Color.BLACK);//���ƺڱ���
			//���Ƶ�һ��,���ذ��
			for(int i=0; i<pushBoxActivity.map1.length; i++){
				for(int j=0; j<pushBoxActivity.map1[i].length; j++){
	                //��������ֵ��������ת��
	                int X = initX+36*j-15*i;
	                int Y = initY+10*j+25*i;
	                if(pushBoxActivity.map1[i][j] == 0){//��ɫ�յ�
	                	paint.setColor(Color.argb(255, 220, 220, 220));
	                	this.myDrawRect(canvas, X, Y);
	                }
	                else if(pushBoxActivity.map1[i][j] == 1){//��ɫ�յ�
	                	paint.setColor(Color.argb(255, 170, 170, 170));
	                	this.myDrawRect(canvas, X, Y);              	
	                }
	                else if(pushBoxActivity.map1[i][j] == 2){//Ŀ�ĵ�1
	                	paint.setColor(Color.argb(255, 127, 255, 130));
	                	this.myDrawRect(canvas, X, Y);
	                }
	                else if(pushBoxActivity.map1[i][j] == 3){//Ŀ�ĵ�2
	                	paint.setColor(Color.argb(255, 60, 255, 120));
	                	this.myDrawRect(canvas, X, Y);
	                }
				}
			}
			//��ʼ���Ƶڶ���,���������ڲ�
			for(int i=0; i<pushBoxActivity.map2.length; i++){
				for(int j=0; j<pushBoxActivity.map2[i].length; j++){
	                //��������ֵ��������ת��
	                int X = initX+36*j-15*i;
	                int Y = initY+10*j+25*i;			
	                if(pushBoxActivity.map2[i][j] == 1){//�ڶ����������Ӵ�
	                	
	                	if(tempi == i && tempj == j){//�ǵ�ǰ�ƶ�������
	                		canvas.drawBitmap(box, tx-1, ty-27, paint);
	                	}
	                	else{//�����ƶ�������
	                		canvas.drawBitmap(box, X-1, Y-27, paint);
	                	}
	                }
	                else if(pushBoxActivity.map2[i][j] == 2){//ǽ
	                	canvas.drawBitmap(wall, X, Y-25, paint);
	                }
	                else if(pushBoxActivity.map2[i][j] == 3){//��ɫ������
	                	if(tempi == i && tempj == j){//�ǵ�ǰ�ƶ�������
	                		canvas.drawBitmap(greenBox, tx-1 ,ty-27, paint);
	                	}
	                	else{//�����ƶ�������
	                		canvas.drawBitmap(greenBox, X-1, Y-27, paint);
	                	}
	                }
	                //���ƾ���
	                if(i == pushBoxActivity.mySprite.i && j == pushBoxActivity.mySprite.j){
	                	pushBoxActivity.mySprite.drawMySelf(canvas, paint);
	                }
				} 
			}		
//			canvas.drawBitmap(exit2, 0, 440, paint); //�����˳���ť
		}
		else if(status == 1){//��ϲ����
			paint.setAntiAlias(true);//�����
			canvas.drawColor(Color.BLACK);//���ƺڱ���
			canvas.drawBitmap(goon, -3, 70, new Paint());//���ƹ�ϲͼƬ
		}
	}	
	public void myDrawRect(Canvas canvas, int x ,int y){//���ƶ����
    	Path path = new Path();
    	path.moveTo(x+14, y);
    	path.lineTo(x+53, y+10);
    	path.lineTo(x+37, y+37);
    	path.lineTo(x-2, y+26);
    	path.lineTo(x+14, y);
    	canvas.drawPath(path, paint);
	}
	public boolean onTouchEvent(MotionEvent event) {
		if(status == 0){
			if(event.getX()>0 && event.getX()<exit2.getWidth()
					&& event.getY()>440 && event.getY()<exit2.getHeight()+440){//������˳���Ϸ��ť
				pushBoxActivity.myHandler.sendEmptyMessage(3);//��Activity������Ϣ
			}			
		}
		else if(status == 1){
			pushBoxActivity.myHandler.sendEmptyMessage(4);//��Activity������Ϣ
		}

		return super.onTouchEvent(event);
	}	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {		
	}
	public void surfaceCreated(SurfaceHolder holder) {
		gameViewDrawThread.setFlag(true);
		gameViewDrawThread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameViewDrawThread.setFlag(false);
        while (retry) {
            try {
            	gameViewDrawThread.join();
                retry = false;
            } 
            catch (InterruptedException e) {//���ϵ�ѭ����ֱ��ˢ֡�߳̽���
            }
        }		
	}
}