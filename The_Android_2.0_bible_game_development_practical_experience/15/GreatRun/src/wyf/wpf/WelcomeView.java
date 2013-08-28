package wyf.wpf;	//���������		
import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback{
	RunActivity father;					//RunActivity������
	WelcomeThread wt;//��̨�޸������߳�
	WelcomeDrawThread wdt;//��̨�ػ��߳�
	
	int status=-1;		//��¼��ǰ״̬,0���������ԣ�1�����ὥ����2�����彥����3����ʾ�˵���4��������5���а�ť����
	int alpha = 0;//��¼����ͼƬ�ı���ɫ
	int scrollX=320;//��¼�������Ͻǵ�x����
	int scrollY=60;//��¼�������Ͻǵ�y����
	float textSize = 23f;//����Ĵ�С
	int characterSpanX=34;//������֮���ƫ��
	int characterSpanY=25;//ÿ���ֵĴ�С
	int characterNumber=1;//���ھ�������ʾ���ֵ���Ŀ
	int textStartX = 267;//���ֵ���ʼx���� 
	int textStartY = 130;//���ֵ���ʼy����
	int characterEachLine = 10;//ÿһ����ʾ�ֵĸ���
	int gateSize = 64;//�˵�����
	int gateStartX = 40;//�˵�����ʼx����
	int gateStartY = 380;//�˵�����ʼy����
	int gateSpan = 24;//ÿ����֮�����
	int menuHeight = 28;//�˵����ָ߶ȣ���Ⱥʹ���һ��
	int gateIndex = 0;//���ŵ�ͼƬ֡
	int selectedIndex=-1;//ѡ��Ĳ˵�ѡ���0Ϊ��ʼ/1Ϊ����/2�˳�
	int startChar=0;//�ַ����д����￪ʼ�����Ϊ�˹���ʹ��
	int maxChar=80;//�������������ʾ������
	char [] str={'��','��','ĩ','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��',
			'ʿ','��','��','ɱ','��','��','��','��','��','��','͢','��','��','��','��','��','��','ս','��',
			'��','��','��','��','κ','��','��','��','��','��','��','��','��','��','��','ɱ','��','��','��',
			'��','��','��','��','��','��','��','��','��','��','��','��','ɱ','��','��','׷','ɱ','��','��','��',
			'��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','Ҫ','��','��','��',
			'��','κ','��','��','��','��','��','��'};
	Rect rectMenuStart = new Rect(40,380,104,444);//��ʼ��ť���ο�
	Rect rectMenuHelp = new Rect(128,380,194,444);//������ť���ο�
	Rect rectMenuQuit = new Rect(218,380,282,444);//�˳���ť���ο�
	Rect rectSoundMenu = new Rect(94,445,226,475);//�����˵�
	SoundPool soundPool;		//SoundPool��������
	HashMap<Integer,Integer> soundMap;	//���������Դ��Map
	
	//����������ʼ����Ҫ��Ա����
	public WelcomeView(RunActivity father) {
		super(father);
		this.father = father;
		getHolder().addCallback(this);
		wt = new WelcomeThread(this);
		wdt = new WelcomeDrawThread(this,getHolder());
		initSound(father);
		status = 0;
	}
	//������������Ļ
	public void doDraw(Canvas canvas){
		Paint paint = new Paint();	//��������
		paint.setAlpha(alpha);		
		BitmapManager.drawWelcomePublic(0, canvas, 0, 0,paint);//������
		switch(status){
		case 1://״̬����ʾ����
			BitmapManager.drawWelcomePublic(1, canvas, scrollX, scrollY,paint);
			break;
		case 5://״̬���а�ť����
		case 4://״̬������״̬���ȴ���ť����
			BitmapManager.drawWelcomePublic((father.wantSound?10:9), canvas, 94, 445, paint);//�������˵���9��ʾ������֡��10��ʾ�ر���֡
		case 3://״̬����ʾ�˵�ѡ��ʹ���
			paint.setAlpha(alpha);		//���û���͸����
			for(int i=0;i<3;i++){	
				BitmapManager.drawWelcomePublic((i==selectedIndex?gateIndex+2:2), canvas, gateStartX+i*(gateSize+gateSpan), gateStartY, paint);
				BitmapManager.drawWelcomePublic(6+i, canvas, gateStartX+i*(gateSize+gateSpan), gateStartY, paint);
			}			    
		case 2://״̬����ʾ����
			BitmapManager.drawWelcomePublic(1, canvas, scrollX, scrollY,paint);	//���ƾ���ͼƬ
			paint.setTextSize(textSize);			//���������С
			paint.setTypeface(Typeface.DEFAULT_BOLD);	//���ô���
			paint.setAntiAlias(true);					//���ÿ����
			if(characterNumber > maxChar){//���Ҫ��ʾ�����������˾��������ʾ����
				startChar += characterEachLine;		//�޸��ַ������п�ʼ���Ƶ�λ��
				characterNumber -= characterEachLine;	//
			}
			int lines = characterNumber/characterEachLine + (characterNumber%characterEachLine==0?0:1);//���� һ����Ҫ����
			for(int i=0;i<lines;i++){
				if(i == lines-1){//���һ����
					int n = characterNumber-characterEachLine*(lines-1);
					for(int j=0;j<n-1;j++){
						canvas.drawText(str[startChar+i*characterEachLine+j]+"", textStartX-i*characterSpanX, textStartY+j*characterSpanY, paint);
					}
				}
				else{//���������һ��
					for(int j=0;j<characterEachLine;j++){
						canvas.drawText(str[startChar+i*characterEachLine+j]+"", textStartX-i*characterSpanX, textStartY+j*characterSpanY, paint);
					}					
				}				
			}			
			break;
		}
	}
	//��������λ�˵�ѡ��
	public void resetMenu(){
		this.selectedIndex = -1;//û��ѡ���κ�ѡ��
		this.gateIndex = 0;//��λ����֡����
		this.status = 4;//����״̬
	}
	//����:����״̬Ϊ����
	public void setStandBy(){
		status = 4;
		this.alpha = 255;		//͸����
		scrollX=20;				//�����X����
		scrollY = 10;			//�����Y����
		textStartY = 80;
		characterNumber = str.length;	//��Ļ����ʾ�����ָ���		
	}
	//��������ʼ������
	public void initSound(RunActivity father){
		soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,100);
		soundMap = new HashMap<Integer,Integer>();
		int id = soundPool.load(father, R.raw.welcom_background, 1);
		soundMap.put(1, id);	//���ر�������
	}
	//��������������
	public void playSound(int sound,int loop){
		AudioManager am = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   	    
	    soundPool.play(soundMap.get(sound), volume, volume, 1, loop, 1);
	}
	//������ֹͣ����
	public void stopSound(int sound){
		soundPool.stop(soundMap.get(sound));
	}	
	//�����������û������Ļ�¼�
	public boolean myTouchEvent(int x,int y){
		if(rectMenuStart.contains(x, y)){//���¿�ʼ��ť
			selectedIndex = 0;//���ð��µ�����
			status = 5;//����״̬Ϊ5���������а�ť
		}
		else if(rectMenuHelp.contains(x, y)){//���°�����ť
			selectedIndex = 1;//���ð��µ�����
			status = 5;//����״̬Ϊ5���������а�ť
		}
		else if(rectMenuQuit.contains(x, y)){//�����˳���ť
			selectedIndex = 2;//���ð��µ�����
			status = 5;//����״̬Ϊ5���������а�ť
		}
		else if(rectSoundMenu.contains(x, y)){//�����������˵���ť				
			if(father.wantSound){//����֮ǰ����������
				stopSound(1);//ֹͣ�����Ĳ���
			}
			else{//����֮ǰû����
				playSound(1, -1);
			}
			father.wantSound = !father.wantSound;//�÷�
		}
		return true;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {//��д�ӿڷ���
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {//��дsurfaceCreated����
		wdt.isDrawOn = true;
		if(!wdt.isAlive()){
			wdt.start();
		}
		wt.flag = true;
		if(!wt.isAlive()){
			wt.start();
		}		
		if(father.wantSound){
			playSound(1, -1);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//��дsurfaceDestroyed����
		wdt.isDrawOn = false;
	}
	
}