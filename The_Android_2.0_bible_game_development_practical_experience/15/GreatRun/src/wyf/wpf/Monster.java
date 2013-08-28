package wyf.wpf;		//���������
import static wyf.wpf.ConstantUtil.*;	//����ConstantUtil��ľ�̬��Ա
public class Monster extends Sprite{
	GameView gv;
	boolean isLocked;		//�����ֵΪtrue��˵�������޼��ߣ�������Ѱ��
	int direction=-1;			//������˶�����0�£�1��2�ң�3�ϣ����䶯�����б���ͬ
	int dx;					//�޼��ƶ���Ŀ��λ��x����
	int dy;					//�޼��ƶ���Ŀ��λ��y����
	public Monster(int col, int row,GameView gv) {
		super(col, row);
		this.gv = gv;
	}
	//��������ָ�������ƶ�����
	public void move(){
		int d = this.direction;	//��ȡ����ķ���
		int [] destination=null;		//Ŀ�ĵ�λ��
		switch(d){		//�жϷ���
		case 0:			//����
			destination = new int[]{col,row+1};
			break;
		case 1:			//����
			destination = new int[]{col-1,row};
			break;
		case 2:			//����
			destination = new int[]{col+1,row};
			break;
		case 3:			//����
			destination = new int[]{col,row-1};
			break;
		}
		this.dx = destination[0]*TILE_SIZE;			//�����ƶ���Ŀ�ĵ�ʱ���Ͻǵ�λ��
		this.dy = destination[1]*TILE_SIZE+TILE_SIZE-SPRITE_HEIGHT;	//���ﵽ��Ŀ�ĵ�ʱ���Ͻ�Y����
		col = destination[0];
		row = destination[1];
		new Thread(){
			public void run(){
				isLocked = true;				
				for(int i=0;i<TILE_SIZE/MONSTER_MOVING_SPAN;i++){
					if(dx>x){			//Ŀ�ĵ����ұ�
						x+=MONSTER_MOVING_SPAN;
						if(currentSegment!=6){		//�鿴�ǲ�����Ҫ�������ö�����
							currentSegment = 6;
						}
					}
					else if(dx<x){		//Ŀ�ĵ������
						x-=MONSTER_MOVING_SPAN;		//��������
						if(currentSegment != 5){		//�鿴�ǲ�����Ҫ�������ö�����
							currentSegment = 5;
						}
					}
					if(dy>y){			//Ŀ�ĵص����±�
						y+=MONSTER_MOVING_SPAN;		//��������
						if(currentSegment != 4){		//�鿴�ǲ�����Ҫ�������ö�����
							currentSegment = 4;
						}
					}
					else if(dy<y){		//Ŀ�ĵ����ϱ�
						y-=MONSTER_MOVING_SPAN;		//��������
						if(currentSegment != 7){	//�鿴�ǲ�����Ҫ�������ö�����
							currentSegment = 7;
						}
					}
					//����Ƿ�ץ��Ӣ��
					int heroX = 0;
					int heroY = 0;
					if(gv!=null){		//���GameView���ڣ���Ϊ�޼���ʱ������Ϸ�Ѿ�����
						heroX = gv.hero.x;
						heroY = gv.hero.y;
					}
					int max_x=0;//Ӣ�ۺ͹���x����Ľϴ�ֵ
					int max_y=0;//Ӣ�ۺ͹���y����Ľϴ�ֵ
					int min_x=0;//Ӣ�ۺ͹���x����Ľ�Сֵ
					int min_y=0;//Ӣ�ۺ͹���y����Ľ�Сֵ
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
					//��������Ӣ���Ƿ��غ�
					if(max_x < min_x+SPRITE_WIDTH-1 && max_y<min_y+SPRITE_HEIGHT-1){
						//�������
						float width = min_x+SPRITE_WIDTH-1 - max_x;
						float height = min_y+SPRITE_HEIGHT-1 - max_y;
						if((width*height)/(SPRITE_WIDTH*SPRITE_HEIGHT) > AREA_PERCENT){	//�ص�����ﵽһ��ֵ
							if(gv.getGameStatus() == STATUS_PLAYING){	//�ж��Ƿ���Ϸ��������
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
				x = dx;		//����x����
				y = dy;		//����y����				
				isLocked = false;	//��������ʱ���Զ�Monster�������Ѱ��
			}
		}.start();
	}
	
}