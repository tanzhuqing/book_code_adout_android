package wyf.wpf;					//���������
import static wyf.wpf.ConstantUtil.*;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	RunActivity father;		//Activity��������
	DrawThread dt;			//DrawThread��������
	KeyThread kt;			//KeyThread��������
	AIGoThread aigot;		//AIGoThread��������
	Hero hero;				//Ӣ�۶�������
	Monster [] monsterArray;	//������������
	int gameStatus = -1;		//��Ϸ״̬��0���أ�1ͨȫ�أ�2ʧ�ܣ�3��Ϸ���ڽ���
	int gameAlertX = -320;		//��Ϸ��ʾ��Ϣ��X����
	int gameAlertY = 160;		//��Ϸ��ʾ��Ϣ��Y����
	int currStage = 0;		//��ǰ�ؿ�����0����,��0�����1��
	LayerList layerList;		//��ŵ�ǰ�ؿ��ĵ�ͼ����
	int [][] currNotIn;		//��ŵ�ǰ�ؿ��Ĳ���ͨ������
	int [] heroLocation;	//��ų�ʼ��Ӣ�۵�λ�ã�����ǰ���ں�
	int monsterNumber;		//��ŵ�ǰ�ؿ��й���ĸ���
	int [][] monsterLocation;	//��Ź����ʼ��λ�ã�����ǰ�����ں�
	int [] homeLocation;		//��ŵ�ǰ�ؿ��ҵ�λ��
	int startRow = 0;			//��Ļ���Ͻ��ڴ��ͼ�е�����
	int startCol = 0;			//��Ļ���Ͻ��ڴ��ͼ�е�����
	int offsetX = 0;			//��Ļ���Ͻ������startCol��ƫ����
	int offsetY = 0;			//��Ļ���Ͻ������startRow��ƫ����
	Rect rectAlert = new Rect(0,160,320,310);		//��Ϸ��ʾ�ľ��ο�
	SoundPool soundPool;					//SoundPooll��������
	HashMap<Integer,Integer> soundMap;		//���ڴ��������Map
	
	boolean showMiniMap;
	private class GameAlertThread extends Thread{
		public void run(){
			while(true){
				gameAlertX+=10;
				if(gameAlertX>=0){
					gameAlertX = 0;
					break;
				}
				try{
					Thread.sleep(50);
				}
				catch(Exception e){
					e.printStackTrace();
				}				
			}
		}
	}
	public GameView(RunActivity context) {
		super(context);
		this.father = context;
		initStageData();					//��ʼ���ؿ�����
		getHolder().addCallback(this);				//���Callback�ӿ�
		dt = new DrawThread(this, getHolder());		//����DrawThreaed����
		kt = new KeyThread(this);					//����KeyThread����
		aigot = new AIGoThread(this, monsterArray);	//����AIGoThread����
		if(father.wantSound){//��Ҫ�Ļ��ͼ�������
			initSound(father);
		} 
		setGameStatus(STATUS_PLAYING);
	}
	//��������ʼ���ؿ���Ϣ
	public void initStageData(){
		BitmapManager.loadCurrentStage(getResources(), currStage);
		layerList = LayerList.getLayerListByStage(currStage);
		currNotIn = layerList.getTotalNotInMatrix();
		heroLocation = GameData.getHeroLocationByStage(currStage);
		monsterNumber = GameData.getMonsterNumberByStage(currStage);
		monsterLocation = GameData.getMonsterLocationByStage(currStage);
		homeLocation = GameData.getHomeLocationByStage(currStage);
		hero = new Hero(heroLocation[0], heroLocation[1]);	//����Ӣ�۶���
		hero.makeAnimation(BitmapManager.getHeroFrmList());	//ΪӢ�۴���������
		monsterArray = new Monster[monsterNumber];			//������������
		for(int i=0;i<monsterNumber;i++){					//����ÿ���������
			monsterArray[i] = new Monster(monsterLocation[i][0],monsterLocation[i][1],this);
			monsterArray[i].makeAnimation(BitmapManager.getMonsterFrmList());
		}
		father.keyState = 0;			//��ռ���״̬
		startRow = 0;					//��startRow����
		startCol = 0;					//��startCol����
		offsetX = 0;					//��offsetX����
		offsetY = 0;					//��offsetY����
	}	
	//��Ļ���Ʒ���
	public void doDraw(Canvas canvas){
		int heroX = hero.x;				//��ȡӢ��X����
		int heroY = hero.y;				//��ȡӢ��Y����
		int hRow = (heroY+SPRITE_HEIGHT-1) / TILE_SIZE ;//���Ӣ�����½��ڴ��ͼ�ϵ��к���
		int hCol = (heroX+SPRITE_WIDTH-1) / TILE_SIZE;//���Ӣ�����½��ڴ��ͼ�ϵ�����
		int tempStartRow = this.startRow;	//��ȡ������ʼ��
		int tempStartCol = this.startCol;	//��ȡ������ʼ��
		int tempOffsetX = this.offsetX;		//��ȡ�����tempStartRow��ƫ����
		int tempOffsetY = this.offsetY;		//��ȡ�����tempStartCol��ƫ����
		canvas.drawColor(Color.BLACK);		//����Ļ
		for(int i=-1; i<=SCREEN_ROWS; i++){     
			if(tempStartRow+i < 0 || tempStartRow+i>=MAP_ROWS){//����໭����һ�в����ڣ��ͼ���
				continue;		//������һ��ѭ��
			}
			for(int j=-1; j<=SCREEN_COLS; j++){
				if(tempStartCol+j <0 || tempStartCol+j>=MAP_COLS){//����໭����һ�в����ڣ��ͼ���
					continue;		//������һ��ѭ��
				}
				for(Layer l:layerList.layerList){
					if(l.mapMatrix[tempStartRow+i][tempStartCol+j] != null){
						l.mapMatrix[tempStartRow+i][tempStartCol+j].drawSelf(canvas, i, j, tempOffsetX, tempOffsetY);
					}
				}
				//����Ƿ���Ҫ���Ƽ�
				if(homeLocation[0]-tempStartCol==j && homeLocation[1]-tempStartRow==i){	//���������
					int homeX = j*TILE_SIZE - tempOffsetX;
					int homeY = i*TILE_SIZE - tempOffsetY;
					BitmapManager.drawGamePublic(36, canvas, homeX, homeY);
				}
				//����Ƿ���Ҫ����Ӣ��
				if(hRow - tempStartRow == i && hCol-tempStartCol == j){		//Ӣ�۵����½ǵ�λ�ڴ˵�ͼ��
					int screenX = heroX - tempStartCol*TILE_SIZE - tempOffsetX;
					int screenY = heroY - tempStartRow*TILE_SIZE - tempOffsetY;
					hero.drawSelf(canvas, screenX, screenY);
				}
				//����Ƿ���Ҫ���ƹ���
				for(Monster m:monsterArray){	//������������
					int mx = m.x;
					int my = m.y;
					int mcol = (mx+SPRITE_WIDTH-1)/TILE_SIZE;	//���½�
					int mrow = (my+SPRITE_HEIGHT-1)/TILE_SIZE;	//���½�
					if(mrow - tempStartRow == i && mcol-tempStartCol == j){	//��������½�λ�ڴ˿��ͼ
						int screenX = mx - tempStartCol*TILE_SIZE - tempOffsetX;
						int screenY = my - tempStartRow*TILE_SIZE - tempOffsetY;
						m.drawSelf(canvas, screenX, screenY);
					}
				}
			}
		}
		drawGameStatus(canvas);		//����Ƿ���Ҫ������Ϸ��ʾ
		if(showMiniMap){			//����Ƿ���Ҫ��������С��ͼ
			drawMiniMap(canvas);
		}
	}
	//���������������ͼ
	public void drawMiniMap(Canvas canvas){
		Paint paint = new Paint();			//�������ʶ���
		for(int i=0;i<MAP_ROWS;i++){
			for(int j=0;j<MAP_COLS;j++){
				switch(this.currNotIn[i][j]){
				case 0:		//����һ��ǳǳ�ľ���
					paint.setARGB(128, 128, 128, 128);
					break;
				case 1:		//����һ������ľ���
					paint.setARGB(128, 0, 0, 0);
					break;
				}
				canvas.drawRect(
						MINI_MAP_START_X+j*MINI_MAP_TILE_SIZE, 
						MINI_MAP_START_Y+i*MINI_MAP_TILE_SIZE, 
						MINI_MAP_START_X+(j+1)*MINI_MAP_TILE_SIZE,
						MINI_MAP_START_Y+(i+1)*MINI_MAP_TILE_SIZE,
						paint);
			}
		}
		//������
		paint.setARGB(128, 255, 0, 0);
		for(Monster m:monsterArray){
			int mx = m.x;
			int my = m.y;
			m.col = (mx+SPRITE_WIDTH/2)/TILE_SIZE;
			m.row = (my+SPRITE_HEIGHT-TILE_SIZE/2)/TILE_SIZE;
			canvas.drawOval(
					new RectF(
						MINI_MAP_START_X+m.col*MINI_MAP_TILE_SIZE, 
						MINI_MAP_START_Y+m.row*MINI_MAP_TILE_SIZE, 
						MINI_MAP_START_X+(m.col+1)*MINI_MAP_TILE_SIZE,
						MINI_MAP_START_Y+(m.row+1)*MINI_MAP_TILE_SIZE
					), paint);
		}
		//���Լ�
		paint.setARGB(128, 0, 255, 255);
		canvas.drawOval(
				new RectF(
					MINI_MAP_START_X+hero.col*MINI_MAP_TILE_SIZE, 
					MINI_MAP_START_Y+hero.row*MINI_MAP_TILE_SIZE, 
					MINI_MAP_START_X+(hero.col+1)*MINI_MAP_TILE_SIZE,
					MINI_MAP_START_Y+(hero.row+1)*MINI_MAP_TILE_SIZE
				), paint);
		//����
		paint.setARGB(128, 0, 0, 255);
		canvas.drawOval(
				new RectF(
				MINI_MAP_START_X + homeLocation[0]*MINI_MAP_TILE_SIZE,	
				MINI_MAP_START_Y + homeLocation[1]*MINI_MAP_TILE_SIZE,
				MINI_MAP_START_X + (homeLocation[0]+1)*MINI_MAP_TILE_SIZE,
				MINI_MAP_START_Y + (homeLocation[1]+1)*MINI_MAP_TILE_SIZE
				), paint
		);
	}
	//��ʼ������
	public void initSound(RunActivity father){
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
		soundMap = new HashMap<Integer,Integer>();
		soundMap.put(1, soundPool.load(father, R.raw.game_background, 1));
	}
	//��������
	public void playSound(int sound,int loop){
		AudioManager am = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
		
		float streamVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;  
	    soundPool.resume(soundMap.get(sound));
	    soundPool.play(soundMap.get(sound), volume, volume, 1, loop, 1);
	}
	//��ͣ��������
	public void pauseSound(int sound){
		soundPool.pause(soundMap.get(sound));
		
	}
	//ֹͣ��������
	public void stopSound(int sound){
		soundPool.stop(soundMap.get(sound));
	}
		
	//�����������û������Ļ�¼�
	public boolean myTouchEvent(int x,int y){
		if(rectAlert.contains(x, y)){		//���������Ϸ��ʾ
			switch(gameStatus){
			case STATUS_WIN:		//ͨȫ��
			case STATUS_LOSE:		//��Ϸʧ��
				if(father.wantSound){		//�����Ҫ��������
					stopSound(1);
					soundPool.release();
					soundPool = null;					
				}
				stopGame();
		        father.pv = new ProgressView(father, 3);					//����һ��ProgressView����Ŀ��Ϊ3����������ȥWelcomeView
		        father.setContentView(father.pv);
		        father.currentView = father.pv;
		    	new Thread(){
		    		public void run(){
		    			Looper.prepare();
		    			BitmapManager.loadWelcomePublic(getResources());	//���ػ�ӭ�����ͼƬ��Դ
		    			father.updateProgress(60);
		    			father.wv = new WelcomeView(father);//��ʼ��WelcomeView
		    			father.wv.setStandBy();				//����WelcomeView״̬Ϊ����
		    			father.updateProgress(100);
		    		}
		    	}.start();
				father.gv = null;
				break;
			case STATUS_PASS:		//ͨ��һ��
				initStageData();
				startGame();
				gameStatus = STATUS_PLAYING;
				gameAlertX = -320;
				break;
			}
		}
		return true;
	}
	//��������ʼ��Ϸ
	public void startGame(){
		kt.isGameOn = true;	//�ָ���Ϸ����
		if(!kt.isAlive()){
			kt.start();		//���������߳�
		}
		hero.startAnimation();	//����Ӣ�ۻ�֡�߳�
		for(Monster m:monsterArray){
			m.startAnimation();	//�������ﻻ֡�߳�
		}
		aigot.isGameOn = true;	//����AI׷���߳�
		if(! aigot.isAlive()){
			aigot.start();
		}
		if(father.wantSound){//�������������Ҫ
			playSound(1, -1);
		}
	}
	//��������ͣ��Ϸ
	public void pauseGame(){
		kt.isGameOn = false;		//��ͣ�����߳�
		hero.pauseAnimation();		//��ͣӢ�۶���
		for(Monster m:monsterArray){	//��ͣ���ﶯ��
			m.pauseAnimation();
		}
		aigot.isGameOn = false;		//ֹͣAI�ƶ��߳�
	}
	//������������Ϸ
	public void stopGame(){
		kt.flag = false;
		kt.isGameOn = false;
		for(Monster m:monsterArray){
			m.stopAnimation();
		}
		hero.stopAnimation();
	}
	//������������Ϸ״̬���Ʋ�ͬ����ʾ
	public void drawGameStatus(Canvas canvas){
		switch(gameStatus){
		case STATUS_LOSE:		//��Ϸʧ��
			BitmapManager.drawGamePublic(2, canvas, gameAlertX, gameAlertY);
			break;
		case STATUS_PASS:		//ͨ��һ��
			BitmapManager.drawGamePublic(0, canvas, gameAlertX, gameAlertY);
			break;
		case STATUS_WIN:		//ͨȫ��
			BitmapManager.drawGamePublic(1, canvas, gameAlertX, gameAlertY);
		}
	}
	//������������Ϸ��ʾ�ƶ��߳�
	public void startMyAnimation(){
		new GameAlertThread().start();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {//��д�ӿڷ���
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {//��дsurfaceCreated����
		dt.isViewOn = true;//�ָ���Ϸ����
		if(! dt.isAlive()){//���û����������
			dt.start();		//����ˢ���߳�
		}
		startGame();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//��дsurfaceDestroyed����
		dt.flag = false;
		dt.isViewOn = false;
	}
	public int getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}
}