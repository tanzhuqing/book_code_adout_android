package wyf.wpf;		//声明包语句
import static wyf.wpf.ConstantUtil.*;	//引入ConstantUtil类的静态成员
public class Monster extends Sprite{
	GameView gv;
	boolean isLocked;		//如果该值为true，说明正在无极走，不进行寻径
	int direction=-1;			//怪物的运动方向，0下，1左，2右，3上，和其动画段列表相同
	int dx;					//无极移动的目的位置x坐标
	int dy;					//无极移动的目的位置y坐标
	public Monster(int col, int row,GameView gv) {
		super(col, row);
		this.gv = gv;
	}
	//方法：沿指定方向移动怪物
	public void move(){
		int d = this.direction;	//获取怪物的方向
		int [] destination=null;		//目的地位置
		switch(d){		//判断方向
		case 0:			//向下
			destination = new int[]{col,row+1};
			break;
		case 1:			//向左
			destination = new int[]{col-1,row};
			break;
		case 2:			//向右
			destination = new int[]{col+1,row};
			break;
		case 3:			//向上
			destination = new int[]{col,row-1};
			break;
		}
		this.dx = destination[0]*TILE_SIZE;			//怪物移动到目的地时左上角的位置
		this.dy = destination[1]*TILE_SIZE+TILE_SIZE-SPRITE_HEIGHT;	//怪物到达目的地时左上角Y坐标
		col = destination[0];
		row = destination[1];
		new Thread(){
			public void run(){
				isLocked = true;				
				for(int i=0;i<TILE_SIZE/MONSTER_MOVING_SPAN;i++){
					if(dx>x){			//目的地在右边
						x+=MONSTER_MOVING_SPAN;
						if(currentSegment!=6){		//查看是不是需要重新设置动画段
							currentSegment = 6;
						}
					}
					else if(dx<x){		//目的地在左边
						x-=MONSTER_MOVING_SPAN;		//怪物向左
						if(currentSegment != 5){		//查看是不是需要重新设置动画段
							currentSegment = 5;
						}
					}
					if(dy>y){			//目的地的在下边
						y+=MONSTER_MOVING_SPAN;		//怪物向下
						if(currentSegment != 4){		//查看是不是需要重新设置动画段
							currentSegment = 4;
						}
					}
					else if(dy<y){		//目的地在上边
						y-=MONSTER_MOVING_SPAN;		//怪物向上
						if(currentSegment != 7){	//查看是不是需要重新设置动画段
							currentSegment = 7;
						}
					}
					//检查是否抓到英雄
					int heroX = 0;
					int heroY = 0;
					if(gv!=null){		//如果GameView存在，因为无级走时可能游戏已经结束
						heroX = gv.hero.x;
						heroY = gv.hero.y;
					}
					int max_x=0;//英雄和怪物x坐标的较大值
					int max_y=0;//英雄和怪物y坐标的较大值
					int min_x=0;//英雄和怪物x坐标的较小值
					int min_y=0;//英雄和怪物y坐标的较小值
					if(heroX > x){
						max_x = heroX;
						min_x = x;
					}
					else{
						max_x = x;
						min_x = heroX;
					}
					if(heroY > y){
						max_y = heroY;
						min_y = y;
					}
					else{
						max_y = y;
						min_y = heroY;
					}
					//计算怪物和英雄是否重合
					if(max_x < min_x+SPRITE_WIDTH-1 && max_y<min_y+SPRITE_HEIGHT-1){
						//计算面积
						float width = min_x+SPRITE_WIDTH-1 - max_x;
						float height = min_y+SPRITE_HEIGHT-1 - max_y;
						if((width*height)/(SPRITE_WIDTH*SPRITE_HEIGHT) > AREA_PERCENT){	//重叠面积达到一定值
							if(gv.getGameStatus() == STATUS_PLAYING){	//判断是否游戏正常进行
								gv.pauseGame();
								gv.setGameStatus(STATUS_LOSE);
								gv.startMyAnimation();
							}
							break;
						}
					}
					try{
						Thread.sleep(MONSTER_GO_SLEEP_SPAN);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				x = dx;		//修正x坐标
				y = dy;		//修正y坐标				
				isLocked = false;	//解锁，此时可以对Monster对象进行寻径
			}
		}.start();
	}
	
}