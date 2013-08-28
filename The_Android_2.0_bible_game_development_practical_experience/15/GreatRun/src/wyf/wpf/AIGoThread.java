package wyf.wpf;							//声明包语句
import static wyf.wpf.ConstantUtil.*;		//引入ConstantUtil类的静态成员
public class AIGoThread extends Thread{
	GameView gv;			//GameView对象的引用
	Monster [] monsters;	//Moster数组引用
	boolean flag;			//线程是否执行标志位
	boolean isGameOn;		//游戏是否进行标志位
	int sleepSpan = AI_THREAD_SLEEP_SPAN;	//游戏进行时线程执行时间	
	public AIGoThread(GameView gv,Monster [] monsters){
		this.gv = gv;
		this.monsters = monsters;
		flag = true;
		isGameOn = true;
	}
	public void run(){		//方法：线程执行放法
		while(flag){
			while(isGameOn){
				for(Monster m:gv.monsterArray){		//遍历怪物数组
					if(m.isLocked){					//如果已加锁
						continue;
					}
					if(checkIfCrossWay(m)){			//检查是否到岔路
						int heroX = gv.hero.x+TILE_SIZE/2;
						int heroY = gv.hero.y+SPRITE_HEIGHT-TILE_SIZE/2;
						m.direction = searchForDirection(heroX, heroY, m);
					}
					if(checkIfTurnAround(m)){		//检查是否掉头
						m.direction = 3-m.direction;		//掉头
					}
					m.move();
				}
				try{
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			try{
				Thread.sleep(1500);					//线程空转时间
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//方法：检查怪物是否在岔路口
	public boolean checkIfCrossWay(Monster m){
		int [][] notIn = gv.currNotIn;
		int mrow = m.row;
		int mcol = m.col;
		switch(m.direction){
		case 0:		//向下
		case 3:		//向上
			if(notIn[mrow][mcol-1] == 0 || notIn[mrow][mcol+1]==0){		//检查左右是否有路
				return true;
			}
			break;
		case 1:		//向左
		case 2:		//向右
			if(notIn[mrow-1][mcol]==0 || notIn[mrow+1][mcol]==0){			//检查上下是否有路
				return true;
			}
			break;
		default:
			return true;	//如果是初始情况怪物还没有方向，则返回true进行寻径
		}
		return false;		//返回false，表示无岔路
	}
	//方法：搜寻路径确定方向
	public int searchForDirection(int hx,int hy,Monster m){
		int [][] notIn = gv.currNotIn;
		int backDir = 3-m.direction;		//记录回头方向
		int [][] options = {				//搜索路径时的参考选择，分别为
				{0,1},{-1,0},{1,0},{0,-1}
		};	
		int [] best = new int[2];			//存放最优方向，最多有两个
		int bestCount=0;					//记录有多少个最优方向
		int mid_d = Integer.MAX_VALUE;		//用于存储最短路径，初始为最大值
		for(int i=0;i<options.length;i++){			//从可选方向中找一个
			int tempCol = m.col+options[i][0];		//临时目的地列数
			int tempRow = m.row+options[i][1];		//临时目的地行数
			if(notIn[tempRow][tempCol] == 1){		//该处不可通过
				continue;
			}
			int mx = tempCol*TILE_SIZE+TILE_SIZE/2;	//怪物中心点坐标
			int my = tempRow*TILE_SIZE+TILE_SIZE/2;	//怪物中心点坐标
			int distance = Math.abs(hx-mx)+Math.abs(hy-my);		//计算门特卡罗距离
			if(distance < mid_d){			//如果小于目前的最短距离
				best[0] = i;				//记录第一个最佳方向
				mid_d = distance;
				bestCount = 1;
			}
			else if(distance == mid_d){
				best[1] = i;				//记录第二个最佳方向
				bestCount = 2;
			}
		}
		if(bestCount == 1){
			return best[0];
		}
		else if(bestCount == 2){			//有两个最优方向
			if(backDir == -1){				//初始情况下
				return best[(Math.random()>0.5?0:1)];		//随机选择一个即可
			}
			else if(best[0]!=backDir && best[1]!=backDir){	//如果最优解中没有回头的方向
				return best[(Math.random()>0.5?0:1)];		//随机选择一个即可
			}
			else{											//最优解中有一个是回头的方向
				return best[(best[0]==backDir?1:0)];		//返回非回头方向的那个方向
			}
		}
		return backDir;	//返回回头方向
	}
	//方法：检查是否需要掉头
	public boolean checkIfTurnAround(Monster m){
		int [][] notIn = gv.currNotIn;
		int mrow = m.row;
		int mcol = m.col;
		switch(m.direction){
		case 0:		//向下
			if(notIn[mrow+1][mcol]==1){			//检查向下是否有路
				return true;
			}
			break;
		case 1:		//向左
			if(notIn[mrow][mcol-1]==1){			//检查向下是否有路
				return true;
			}
			break;
		case 2:		//向右
			if(notIn[mrow][mcol+1]==1){			//检查向下是否有路
				return true;
			}
			break;
		case 3:		//向上
			if(notIn[mrow-1][mcol]==1){			//检查向下是否有路
				return true;
			}
			break;
		}
		return false;
	}
}