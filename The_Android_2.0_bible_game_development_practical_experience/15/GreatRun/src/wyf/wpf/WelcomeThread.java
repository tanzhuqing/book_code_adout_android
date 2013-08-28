package wyf.wpf;				//声明包语句
public class WelcomeThread extends Thread{
	WelcomeView father;
	boolean flag;
	int sleepSpan = 100;//休眠时间
	int characterCounter=0;	//执行2次循环，才会加字
	int gateCounter=0;
	int charNumber=0;
	
	public WelcomeThread(WelcomeView father){
		this.father = father;
		flag = true;
	}
	//线程的执行方法
	public void run(){
		while(flag){
			switch(father.status){
			case 0://正在渐显背景图片
				father.alpha += 5;
				if(father.alpha == 255){
					father.status = 1;
				}
				break;
			case 1://正在滚动卷轴
				father.scrollX -=5;
				if(father.scrollX==20){
					father.status = 2;
				}
				break;
			case 2://显示文字
				characterCounter++;
				if(characterCounter == 2){
					characterCounter = 0;
					father.characterNumber++;//View中显示的字数加1
					charNumber++;//计数器加1
					if(charNumber == father.str.length){
						father.status = 3;
						father.alpha = 0;
					}					
				}
				break;
			case 3://显示菜单大门
				father.scrollY -=5;//卷轴向上移动
				father.textStartY -=5;//文字向上移动
				father.alpha +=25;
				if(father.scrollY == 10){
					father.status = 4;
				}
				break;
			case 5://有按钮点下
				gateCounter++;
				if(gateCounter == 2){//循环2次才会改一次帧
					father.gateIndex++;
					gateCounter = 0;
					if(father.gateIndex == 3){//动画播放完毕
						int index = father.selectedIndex;
						father.father.myHandler.sendEmptyMessage(index);
						father.status = 4;//回到待命状态
					}					
				}
				break;
			default:
				break;
			}
			try{
				Thread.sleep(sleepSpan);
			}										//线程休眠
			catch(Exception e){
				e.printStackTrace();
			}										//捕获并打印异常
		}
	}
}