package wyf.wpf;	//声明包语句		
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
	RunActivity father;					//RunActivity的引用
	WelcomeThread wt;//后台修改数据线程
	WelcomeDrawThread wdt;//后台重绘线程
	
	int status=-1;		//记录当前状态,0：背景渐显，1：卷轴渐出，2：字体渐出，3：显示菜单，4：待命，5：有按钮点下
	int alpha = 0;//记录背景图片的背景色
	int scrollX=320;//记录卷轴左上角的x坐标
	int scrollY=60;//记录卷轴左上角的y坐标
	float textSize = 23f;//字体的大小
	int characterSpanX=34;//文字行之间的偏移
	int characterSpanY=25;//每个字的大小
	int characterNumber=1;//已在卷轴中显示的字的数目
	int textStartX = 267;//文字的起始x坐标 
	int textStartY = 130;//文字的起始y坐标
	int characterEachLine = 10;//每一行显示字的个数
	int gateSize = 64;//菜单大门
	int gateStartX = 40;//菜单的起始x坐标
	int gateStartY = 380;//菜单的起始y坐标
	int gateSpan = 24;//每道门之间距离
	int menuHeight = 28;//菜单文字高度，宽度和大门一样
	int gateIndex = 0;//大门的图片帧
	int selectedIndex=-1;//选择的菜单选项，如0为开始/1为帮助/2退出
	int startChar=0;//字符串中从哪里开始输出，为了滚屏使用
	int maxChar=80;//卷轴上最大能显示的字数
	char [] str={'明','朝','末','年','，','阉','党','当','道','，','败','坏','朝','纲','。','忠','臣','义',
			'士','都','被','杀','害','，','大','将','军','熊','廷','弼','曾','立','下','赫','赫','战','功',
			'，','因','反','对','魏','忠','贤','独','揽','朝','政','，','被','残','忍','杀','害','，','留',
			'下','其','子','熊','兆','圭','四','处','躲','避','东','厂','杀','手','的','追','杀','。','熊','兆',
			'圭','的','生','死','就','掌','握','在','了','你','的','手','上','，','不','要','让','他','落',
			'入','魏','忠','贤','的','虎','口','。'};
	Rect rectMenuStart = new Rect(40,380,104,444);//开始按钮矩形框
	Rect rectMenuHelp = new Rect(128,380,194,444);//帮助按钮矩形框
	Rect rectMenuQuit = new Rect(218,380,282,444);//退出按钮矩形框
	Rect rectSoundMenu = new Rect(94,445,226,475);//声音菜单
	SoundPool soundPool;		//SoundPool对象引用
	HashMap<Integer,Integer> soundMap;	//存放声音资源的Map
	
	//构造器，初始化主要成员变量
	public WelcomeView(RunActivity father) {
		super(father);
		this.father = father;
		getHolder().addCallback(this);
		wt = new WelcomeThread(this);
		wdt = new WelcomeDrawThread(this,getHolder());
		initSound(father);
		status = 0;
	}
	//方法：绘制屏幕
	public void doDraw(Canvas canvas){
		Paint paint = new Paint();	//创建画笔
		paint.setAlpha(alpha);		
		BitmapManager.drawWelcomePublic(0, canvas, 0, 0,paint);//画背景
		switch(status){
		case 1://状态：显示卷轴
			BitmapManager.drawWelcomePublic(1, canvas, scrollX, scrollY,paint);
			break;
		case 5://状态：有按钮点下
		case 4://状态：待命状态，等待按钮按下
			BitmapManager.drawWelcomePublic((father.wantSound?10:9), canvas, 94, 445, paint);//画声音菜单，9表示开启那帧，10表示关闭那帧
		case 3://状态：显示菜单选项和大门
			paint.setAlpha(alpha);		//设置画笔透明度
			for(int i=0;i<3;i++){	
				BitmapManager.drawWelcomePublic((i==selectedIndex?gateIndex+2:2), canvas, gateStartX+i*(gateSize+gateSpan), gateStartY, paint);
				BitmapManager.drawWelcomePublic(6+i, canvas, gateStartX+i*(gateSize+gateSpan), gateStartY, paint);
			}			    
		case 2://状态：显示文字
			BitmapManager.drawWelcomePublic(1, canvas, scrollX, scrollY,paint);	//绘制卷轴图片
			paint.setTextSize(textSize);			//设置字体大小
			paint.setTypeface(Typeface.DEFAULT_BOLD);	//设置粗体
			paint.setAntiAlias(true);					//设置抗锯齿
			if(characterNumber > maxChar){//如果要显示的字数超过了卷轴最大显示个数
				startChar += characterEachLine;		//修改字符数组中开始绘制的位置
				characterNumber -= characterEachLine;	//
			}
			int lines = characterNumber/characterEachLine + (characterNumber%characterEachLine==0?0:1);//计算 一共需要几行
			for(int i=0;i<lines;i++){
				if(i == lines-1){//最后一排了
					int n = characterNumber-characterEachLine*(lines-1);
					for(int j=0;j<n-1;j++){
						canvas.drawText(str[startChar+i*characterEachLine+j]+"", textStartX-i*characterSpanX, textStartY+j*characterSpanY, paint);
					}
				}
				else{//还不是最后一排
					for(int j=0;j<characterEachLine;j++){
						canvas.drawText(str[startChar+i*characterEachLine+j]+"", textStartX-i*characterSpanX, textStartY+j*characterSpanY, paint);
					}					
				}				
			}			
			break;
		}
	}
	//方法：复位菜单选项
	public void resetMenu(){
		this.selectedIndex = -1;//没有选中任何选项
		this.gateIndex = 0;//复位动画帧索引
		this.status = 4;//待命状态
	}
	//方法:设置状态为待命
	public void setStandBy(){
		status = 4;
		this.alpha = 255;		//透明度
		scrollX=20;				//卷轴的X坐标
		scrollY = 10;			//卷轴的Y坐标
		textStartY = 80;
		characterNumber = str.length;	//屏幕上显示的文字个数		
	}
	//方法：初始化声音
	public void initSound(RunActivity father){
		soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,100);
		soundMap = new HashMap<Integer,Integer>();
		int id = soundPool.load(father, R.raw.welcom_background, 1);
		soundMap.put(1, id);	//加载背景音乐
	}
	//方法：播放声音
	public void playSound(int sound,int loop){
		AudioManager am = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   	    
	    soundPool.play(soundMap.get(sound), volume, volume, 1, loop, 1);
	}
	//方法：停止声音
	public void stopSound(int sound){
		soundPool.stop(soundMap.get(sound));
	}	
	//方法：处理用户点击屏幕事件
	public boolean myTouchEvent(int x,int y){
		if(rectMenuStart.contains(x, y)){//点下开始按钮
			selectedIndex = 0;//设置按下的索引
			status = 5;//设置状态为5，即按下列按钮
		}
		else if(rectMenuHelp.contains(x, y)){//点下帮助按钮
			selectedIndex = 1;//设置按下的索引
			status = 5;//设置状态为5，即按下列按钮
		}
		else if(rectMenuQuit.contains(x, y)){//点下退出按钮
			selectedIndex = 2;//设置按下的索引
			status = 5;//设置状态为5，即按下列按钮
		}
		else if(rectSoundMenu.contains(x, y)){//点下了声音菜单按钮				
			if(father.wantSound){//按下之前是有声音的
				stopSound(1);//停止声音的播放
			}
			else{//按下之前没声音
				playSound(1, -1);
			}
			father.wantSound = !father.wantSound;//置反
		}
		return true;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {//重写接口方法
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {//重写surfaceCreated方法
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
	public void surfaceDestroyed(SurfaceHolder holder) {//重写surfaceDestroyed方法
		wdt.isDrawOn = false;
	}
	
}