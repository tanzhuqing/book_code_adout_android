package wyf.wpf;		//���������
import android.graphics.Canvas;		//���������
import android.graphics.Color;		//���������
import android.graphics.Paint;//���������
import android.view.SurfaceHolder;//���������
import android.view.SurfaceView;//���������

public class ProgressView extends SurfaceView implements SurfaceHolder.Callback{
	RunActivity father;
	ProgressDrawThread pdt;
	int progress; //����ֵ

	int processWidth = 310;//���������
	int processHeight = 20;//�������߶�
	int processStartX = 5;//��������ʼλ��x
	int processStartY = 230;//��������ʼλ��y
	int target = -1;//����������������Ŀ�꣬0ΪGameView��3ΪWelcomeView
	
	public ProgressView(RunActivity father,int target) {
		super(father);
		this.father = father;
		this.target = target;
		getHolder().addCallback(this);
		pdt = new ProgressDrawThread(this,getHolder());
	}
	//��Ļ�Ļ��Ʒ���
	public void doDraw(Canvas canvas){
		//��������
		BitmapManager.drawSystemPublic(0, canvas, processStartX, processStartY, null);
		//���ڵ���
		Paint paint = new Paint();
		paint.setColor(Color.DKGRAY);
		int p = this.progress;
		int x = (int)(p/100.0 * processWidth);
		canvas.drawRect(processStartX+x, processStartY, processStartX+processWidth, processStartY+processHeight, paint);
		paint.setColor(Color.BLUE);
		paint.setAntiAlias(true);
		canvas.drawText("��ȡ��...", 120, 260, paint);
		if(p == 100){//�ж��Ƿ����������
			father.myHandler.sendEmptyMessage(target);		
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		pdt.flag = true;//����ѭ�����Ʊ���
		if(!pdt.isAlive()){//�Ƿ���Ҫ�����߳�
			pdt.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		pdt.flag = false;//ͣ����̨�����߳�
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	
}