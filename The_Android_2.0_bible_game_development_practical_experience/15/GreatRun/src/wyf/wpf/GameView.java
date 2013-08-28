package wyf.wpf;					//声明包语句
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
	RunActivity father;		//Activity对象引用
	DrawThread dt;			//DrawThread对象引用
	KeyThread kt;			//KeyThread对象引用
	AIGoThread aigot;		//AIGoThread对象引用
	Hero hero;				//英雄对象引用
	Monster [] monsterArray;	//怪物数组引用
	int gameStatus = -1;		//游戏状态，0过关，1通全关，2失败，3游戏正在进行
	int gameAlertX = -320;		//游戏提示消息的X坐标
	int gameAlertY = 160;		//游戏提示消息的Y坐标
	int currStage = 0;		//当前关卡，从0算起,即0代表第1关
	LayerList layerList;		//存放当前关卡的地图矩阵
	int [][] currNotIn;		//存放当前关卡的不可通过矩阵
	int [] heroLocation;	//存放初始的英雄的位置，列在前行在后
	int monsterNumber;		//存放当前关卡中怪物的个数
	int [][] monsterLocation;	//存放怪物初始的位置，列在前，行在后
	int [] homeLocation;		//存放当前关卡家的位置
	int startRow = 0;			//屏幕左上角在大地图中的行数
	int startCol = 0;			//屏幕左上角在大地图中的行数
	int offsetX = 0;			//屏幕左上角相对于startCol的偏移量
	int offsetY = 0;			//屏幕左上角相对于startRow的偏移量
	Rect rectAlert = new Rect(0,160,320,310);		//游戏提示的矩形框
	SoundPool soundPool;					//SoundPooll对象引用
	HashMap<Integer,Integer> soundMap;		//用于存放声音的Map
	
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
		initStageData();					//初始化关卡数据
		getHolder().addCallback(this);				//添加Callback接口
		dt = new DrawThread(this, getHolder());		//创建DrawThreaed对象
		kt = new KeyThread(this);					//创建KeyThread对象
		aigot = new AIGoThread(this, monsterArray);	//创建AIGoThread对象
		if(father.wantSound){//需要的画就加载声音
			initSound(father);
		} 
		setGameStatus(STATUS_PLAYING);
	}
	//方法：初始化关卡信息
	public void initStageData(){
		BitmapManager.loadCurrentStage(getResources(), currStage);
		layerList = LayerList.getLayerListByStage(currStage);
		currNotIn = layerList.getTotalNotInMatrix();
		heroLocation = GameData.getHeroLocationByStage(currStage);
		monsterNumber = GameData.getMonsterNumberByStage(currStage);
		monsterLocation = GameData.getMonsterLocationByStage(currStage);
		homeLocation = GameData.getHomeLocationByStage(currStage);
		hero = new Hero(heroLocation[0], heroLocation[1]);	//创建英雄对象
		hero.makeAnimation(BitmapManager.getHeroFrmList());	//为英雄创建动画段
		monsterArray = new Monster[monsterNumber];			//创建怪物数组
		for(int i=0;i<monsterNumber;i++){					//创建每个怪物对象
			monsterArray[i] = new Monster(monsterLocation[i][0],monsterLocation[i][1],this);
			monsterArray[i].makeAnimation(BitmapManager.getMonsterFrmList());
		}
		father.keyState = 0;			//清空键盘状态
		startRow = 0;					//将startRow置零
		startCol = 0;					//将startCol置零
		offsetX = 0;					//将offsetX置零
		offsetY = 0;					//将offsetY置零
	}	
	//屏幕绘制方法
	public void doDraw(Canvas canvas){
		int heroX = hero.x;				//获取英雄X坐标
		int heroY = hero.y;				//获取英雄Y坐标
		int hRow = (heroY+SPRITE_HEIGHT-1) / TILE_SIZE ;//求出英雄右下角在大地图上的行和列
		int hCol = (heroX+SPRITE_WIDTH-1) / TILE_SIZE;//求出英雄右下角在大地图上的行列
		int tempStartRow = this.startRow;	//获取绘制起始行
		int tempStartCol = this.startCol;	//获取绘制起始列
		int tempOffsetX = this.offsetX;		//获取相对于tempStartRow的偏移量
		int tempOffsetY = this.offsetY;		//获取相对于tempStartCol的偏移量
		canvas.drawColor(Color.BLACK);		//清屏幕
		for(int i=-1; i<=SCREEN_ROWS; i++){     
			if(tempStartRow+i < 0 || tempStartRow+i>=MAP_ROWS){//如果多画的那一行不存在，就继续
				continue;		//进行下一轮循环
			}
			for(int j=-1; j<=SCREEN_COLS; j++){
				if(tempStartCol+j <0 || tempStartCol+j>=MAP_COLS){//如果多画的那一列不存在，就继续
					continue;		//进行下一轮循环
				}
				for(Layer l:layerList.layerList){
					if(l.mapMatrix[tempStartRow+i][tempStartCol+j] != null){
						l.mapMatrix[tempStartRow+i][tempStartCol+j].drawSelf(canvas, i, j, tempOffsetX, tempOffsetY);
					}
				}
				//检查是否需要绘制家
				if(homeLocation[0]-tempStartCol==j && homeLocation[1]-tempStartRow==i){	//如果家在这
					int homeX = j*TILE_SIZE - tempOffsetX;
					int homeY = i*TILE_SIZE - tempOffsetY;
					BitmapManager.drawGamePublic(36, canvas, homeX, homeY);
				}
				//检查是否需要绘制英雄
				if(hRow - tempStartRow == i && hCol-tempStartCol == j){		//英雄的右下角点位于此地图块
					int screenX = heroX - tempStartCol*TILE_SIZE - tempOffsetX;
					int screenY = heroY - tempStartRow*TILE_SIZE - tempOffsetY;
					hero.drawSelf(canvas, screenX, screenY);
				}
				//检查是否需要绘制怪物
				for(Monster m:monsterArray){	//遍历怪物数组
					int mx = m.x;
					int my = m.y;
					int mcol = (mx+SPRITE_WIDTH-1)/TILE_SIZE;	//右下角
					int mrow = (my+SPRITE_HEIGHT-1)/TILE_SIZE;	//右下角
					if(mrow - tempStartRow == i && mcol-tempStartCol == j){	//怪物的右下角位于此块地图
						int screenX = mx - tempStartCol*TILE_SIZE - tempOffsetX;
						int screenY = my - tempStartRow*TILE_SIZE - tempOffsetY;
						m.drawSelf(canvas, screenX, screenY);
					}
				}
			}
		}
		drawGameStatus(canvas);		//检查是否需要绘制游戏提示
		if(showMiniMap){			//检查是否需要绘制迷你小地图
			drawMiniMap(canvas);
		}
	}
	//方法：绘制迷你地图
	public void drawMiniMap(Canvas canvas){
		Paint paint = new Paint();			//创建画笔对象
		for(int i=0;i<MAP_ROWS;i++){
			for(int j=0;j<MAP_COLS;j++){
				switch(this.currNotIn[i][j]){
				case 0:		//绘制一个浅浅的矩形
					paint.setARGB(128, 128, 128, 128);
					break;
				case 1:		//绘制一个深深的矩形
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
		//画怪物
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
		//画自己
		paint.setARGB(128, 0, 255, 255);
		canvas.drawOval(
				new RectF(
					MINI_MAP_START_X+hero.col*MINI_MAP_TILE_SIZE, 
					MINI_MAP_START_Y+hero.row*MINI_MAP_TILE_SIZE, 
					MINI_MAP_START_X+(hero.col+1)*MINI_MAP_TILE_SIZE,
					MINI_MAP_START_Y+(hero.row+1)*MINI_MAP_TILE_SIZE
				), paint);
		//画家
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
	//初始化声音
	public void initSound(RunActivity father){
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
		soundMap = new HashMap<Integer,Integer>();
		soundMap.put(1, soundPool.load(father, R.raw.game_background, 1));
	}
	//播放声音
	public void playSound(int sound,int loop){
		AudioManager am = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
		
		float streamVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;  
	    soundPool.resume(soundMap.get(sound));
	    soundPool.play(soundMap.get(sound), volume, volume, 1, loop, 1);
	}
	//暂停播放声音
	public void pauseSound(int sound){
		soundPool.pause(soundMap.get(sound));
		
	}
	//停止播放声音
	public void stopSound(int sound){
		soundPool.stop(soundMap.get(sound));
	}
		
	//方法：处理用户点击屏幕事件
	public boolean myTouchEvent(int x,int y){
		if(rectAlert.contains(x, y)){		//点击的是游戏提示
			switch(gameStatus){
			case STATUS_WIN:		//通全关
			case STATUS_LOSE:		//游戏失败
				if(father.wantSound){		//如果需要播放声音
					stopSound(1);
					soundPool.release();
					soundPool = null;					
				}
				stopGame();
		        father.pv = new ProgressView(father, 3);					//创建一个ProgressView对象，目标为3，即走满后去WelcomeView
		        father.setContentView(father.pv);
		        father.currentView = father.pv;
		    	new Thread(){
		    		public void run(){
		    			Looper.prepare();
		    			BitmapManager.loadWelcomePublic(getResources());	//加载欢迎界面的图片资源
		    			father.updateProgress(60);
		    			father.wv = new WelcomeView(father);//初始化WelcomeView
		    			father.wv.setStandBy();				//设置WelcomeView状态为待命
		    			father.updateProgress(100);
		    		}
		    	}.start();
				father.gv = null;
				break;
			case STATUS_PASS:		//通过一关
				initStageData();
				startGame();
				gameStatus = STATUS_PLAYING;
				gameAlertX = -320;
				break;
			}
		}
		return true;
	}
	//方法：开始游戏
	public void startGame(){
		kt.isGameOn = true;	//恢复游戏进行
		if(!kt.isAlive()){
			kt.start();		//启动键盘线程
		}
		hero.startAnimation();	//启动英雄换帧线程
		for(Monster m:monsterArray){
			m.startAnimation();	//启动怪物换帧线程
		}
		aigot.isGameOn = true;	//启动AI追踪线程
		if(! aigot.isAlive()){
			aigot.start();
		}
		if(father.wantSound){//播放声音如果需要
			playSound(1, -1);
		}
	}
	//方法：暂停游戏
	public void pauseGame(){
		kt.isGameOn = false;		//暂停键盘线程
		hero.pauseAnimation();		//暂停英雄动画
		for(Monster m:monsterArray){	//暂停怪物动画
			m.pauseAnimation();
		}
		aigot.isGameOn = false;		//停止AI移动线程
	}
	//方法：结束游戏
	public void stopGame(){
		kt.flag = false;
		kt.isGameOn = false;
		for(Monster m:monsterArray){
			m.stopAnimation();
		}
		hero.stopAnimation();
	}
	//方法：根据游戏状态绘制不同的提示
	public void drawGameStatus(Canvas canvas){
		switch(gameStatus){
		case STATUS_LOSE:		//游戏失败
			BitmapManager.drawGamePublic(2, canvas, gameAlertX, gameAlertY);
			break;
		case STATUS_PASS:		//通过一关
			BitmapManager.drawGamePublic(0, canvas, gameAlertX, gameAlertY);
			break;
		case STATUS_WIN:		//通全关
			BitmapManager.drawGamePublic(1, canvas, gameAlertX, gameAlertY);
		}
	}
	//方法：启动游戏提示移动线程
	public void startMyAnimation(){
		new GameAlertThread().start();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {//重写接口方法
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {//重写surfaceCreated方法
		dt.isViewOn = true;//恢复游戏进行
		if(! dt.isAlive()){//如果没启动就启动
			dt.start();		//启动刷屏线程
		}
		startGame();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//重写surfaceDestroyed方法
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