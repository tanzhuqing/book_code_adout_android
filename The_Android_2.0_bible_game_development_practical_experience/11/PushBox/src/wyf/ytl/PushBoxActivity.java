package wyf.ytl;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class PushBoxActivity extends Activity{
	WelcomeView welcomeView = null;//��ӭ����
	WelcomeViewGoThread welcomeViewGoThread = null;//��ӭ�����е��ƶ��߳�
	MenuView menuView = null;
	MenuViewGoThread menuViewGoThread = null;//�˵������е��ƶ��߳�
	GameView gameView = null;
	
	boolean isSound = true;//�Ƿ񲥷�����
	MediaPlayer pushBoxSound;//����������
	MediaPlayer backSound;//��������
	MediaPlayer winSound;//ʤ��������
	MediaPlayer startSound;//��ʼ�Ͳ˵�ʱ������
	
	int map1[][];
	int map2[][];//��ǰ��Ϸ�ĵ�ͼ
	int selectMap = 0;//ѡ�еĵ�ͼ
	
	MySprite mySprite;//����
	
	KeyThread kt;//���̼����߳�
	int action = 0;//���̵�״̬,�����Ʊ�ʾ �������ұ�ʾ��������
	
	Handler myHandler = new Handler(){//��������UI�߳��еĿؼ�
        public void handleMessage(Message msg) {
        	if(msg.what == 1){//�յ�WelcomeViewGoThread/Welcome��������Ϣ
        		welcomeViewGoThread.setFlag(false);
        		if(welcomeView != null){
        			welcomeView = null;  
        		}
        		initAndToMenuView();
        	}
        	else if(msg.what == 2){//�յ�MenuView��������Ϣ
        		if(menuView != null){
        			menuView = null;  
        		}
        		initAndToGameView();
        	}   
        	else if(msg.what == 3){
        		if(gameView != null){
        			gameView = null;  
        		}
        		initAndToMenuView();
        	}   
        	else if(msg.what == 4){//�յ�GameView������Ϣ��������һ��
    			if(selectMap+1<MapList.map1.length){
    				selectMap = selectMap+1;
    				initAndToGameView();
    			}
    			else {
    				selectMap = 0;
    				initAndToGameView();
    			}
        	}
        }
	}; 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        pushBoxSound = MediaPlayer.create(this, R.raw.sound2);//�����ӵ�����
        winSound = MediaPlayer.create(this, R.raw.winsound);//ʤ��������
        backSound  = MediaPlayer.create(this, R.raw.sound1);//��������
        backSound.setLooping(true); //����ѭ��
        startSound = MediaPlayer.create(this, R.raw.sound3);
        startSound.setLooping(true);
		initAndToWelcomeView();
    }
    
    public void initAndToWelcomeView(){
    	welcomeView = new WelcomeView(this);
        if(isSound){
        	startSound.start();
        }
    	this.setContentView(welcomeView);
    	welcomeViewGoThread = new WelcomeViewGoThread(this);
    	welcomeViewGoThread.start();
    }
    
    public void initAndToMenuView(){
    	menuView = new MenuView(this);
    	this.setContentView(menuView);
    	menuViewGoThread = new MenuViewGoThread(this);
    	menuViewGoThread.start();
    }
    
    public void initAndToGameView(){
		map1 = new int[MapList.map1[selectMap].length][MapList.map1[selectMap][0].length];
		for(int i=0; i<MapList.map1[selectMap].length; i++){
			for(int j=0; j<MapList.map1[selectMap][i].length; j++){
				map1[i][j] = MapList.map1[selectMap][i][j];//��ʼ����һ��
			}
		}
		map2 = new int[MapList.map2[selectMap].length][MapList.map2[selectMap][0].length];
		for(int i=0; i<MapList.map2[selectMap].length; i++){
			for(int j=0; j<MapList.map2[selectMap][i].length; j++){
				map2[i][j] = MapList.map2[selectMap][i][j];//��ʼ���ڶ���
			}
		}    	
    	gameView = new GameView(this);
    	mySprite = new MySprite(this);
        kt = new KeyThread(this);//��Ӽ��̼���
        kt.start();
		if(isSound){
			backSound.start();//��������
		}
    	this.setContentView(gameView);
    }
    
	public boolean onKeyUp(int keyCode, KeyEvent event) {//����̧��
    	if(keyCode == 19){//��
    		action = action & 0x37;
    	}
    	if(keyCode == 20){//��
    		action = action & 0x3B;
    	}    	
    	if(keyCode == 21){//��
    		action = action & 0x3D;
    	}    	
    	if(keyCode == 22){//��
    		action = action & 0x3E;
    	}
		return false;
	}
    
    public boolean onKeyDown(int keyCode, KeyEvent event){//���̰��¼���
    	if(keyCode == 19){//��
    		action = action | 0x08;
    	}
    	if(keyCode == 20){//��
    		action = action | 0x04;
    	}    	
    	if(keyCode == 21){//��
    		action = action | 0x02;
    	}    	
    	if(keyCode == 22){//��
    		action = action | 0x01;
    	}
		return false;
    }	
}