package wyf.ytl;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
public class GameViewDrawThread extends Thread{
	private int sleepSpan = 100;//˯�ߵĺ�����
	private boolean flag = true;//ѭ�����λ
	GameView gameView;//��Ϸ���������
	SurfaceHolder surfaceHolder = null;	
	public GameViewDrawThread(GameView gameView,SurfaceHolder surfaceHolder){//������
		this.gameView = gameView;
		this.surfaceHolder = surfaceHolder;
	}
	public void run(){
		Canvas c;//����
		while(flag){
			c = null;
			try {
				// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
			    c = surfaceHolder.lockCanvas(null);
			    synchronized (this.surfaceHolder) {
			    	try{
			    		gameView.onDraw(c);
			    	}
			    	catch(Exception e){}
			    }
			} finally {
			    if (c != null) {
			    	//������Ļ��ʾ����
			        surfaceHolder.unlockCanvasAndPost(c);
			    }
			}
			try{
				Thread.sleep(sleepSpan);//˯��sleepSpan����
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
    public void setFlag(boolean flag) {//����ѭ�����
    	this.flag = flag;
    }	
}