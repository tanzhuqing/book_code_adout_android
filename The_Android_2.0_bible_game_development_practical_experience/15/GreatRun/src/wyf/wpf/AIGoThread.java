package wyf.wpf;							//���������
import static wyf.wpf.ConstantUtil.*;		//����ConstantUtil��ľ�̬��Ա
public class AIGoThread extends Thread{
	GameView gv;			//GameView���������
	Monster [] monsters;	//Moster��������
	boolean flag;			//�߳��Ƿ�ִ�б�־λ
	boolean isGameOn;		//��Ϸ�Ƿ���б�־λ
	int sleepSpan = AI_THREAD_SLEEP_SPAN;	//��Ϸ����ʱ�߳�ִ��ʱ��	
	public AIGoThread(GameView gv,Monster [] monsters){
		this.gv = gv;
		this.monsters = monsters;
		flag = true;
		isGameOn = true;
	}
	public void run(){		//�������߳�ִ�зŷ�
		while(flag){
			while(isGameOn){
				for(Monster m:gv.monsterArray){		//������������
					if(m.isLocked){					//����Ѽ���
						continue;
					}
					if(checkIfCrossWay(m)){			//����Ƿ񵽲�·
						int heroX = gv.hero.x+TILE_SIZE/2;
						int heroY = gv.hero.y+SPRITE_HEIGHT-TILE_SIZE/2;
						m.direction = searchForDirection(heroX, heroY, m);
					}
					if(checkIfTurnAround(m)){		//����Ƿ��ͷ
						m.direction = 3-m.direction;		//��ͷ
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
				Thread.sleep(1500);					//�߳̿�תʱ��
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//�������������Ƿ��ڲ�·��
	public boolean checkIfCrossWay(Monster m){
		int [][] notIn = gv.currNotIn;
		int mrow = m.row;
		int mcol = m.col;
		switch(m.direction){
		case 0:		//����
		case 3:		//����
			if(notIn[mrow][mcol-1] == 0 || notIn[mrow][mcol+1]==0){		//��������Ƿ���·
				return true;
			}
			break;
		case 1:		//����
		case 2:		//����
			if(notIn[mrow-1][mcol]==0 || notIn[mrow+1][mcol]==0){			//��������Ƿ���·
				return true;
			}
			break;
		default:
			return true;	//����ǳ�ʼ������ﻹû�з����򷵻�true����Ѱ��
		}
		return false;		//����false����ʾ�޲�·
	}
	//��������Ѱ·��ȷ������
	public int searchForDirection(int hx,int hy,Monster m){
		int [][] notIn = gv.currNotIn;
		int backDir = 3-m.direction;		//��¼��ͷ����
		int [][] options = {				//����·��ʱ�Ĳο�ѡ�񣬷ֱ�Ϊ
				{0,1},{-1,0},{1,0},{0,-1}
		};	
		int [] best = new int[2];			//������ŷ������������
		int bestCount=0;					//��¼�ж��ٸ����ŷ���
		int mid_d = Integer.MAX_VALUE;		//���ڴ洢���·������ʼΪ���ֵ
		for(int i=0;i<options.length;i++){			//�ӿ�ѡ��������һ��
			int tempCol = m.col+options[i][0];		//��ʱĿ�ĵ�����
			int tempRow = m.row+options[i][1];		//��ʱĿ�ĵ�����
			if(notIn[tempRow][tempCol] == 1){		//�ô�����ͨ��
				continue;
			}
			int mx = tempCol*TILE_SIZE+TILE_SIZE/2;	//�������ĵ�����
			int my = tempRow*TILE_SIZE+TILE_SIZE/2;	//�������ĵ�����
			int distance = Math.abs(hx-mx)+Math.abs(hy-my);		//�������ؿ��޾���
			if(distance < mid_d){			//���С��Ŀǰ����̾���
				best[0] = i;				//��¼��һ����ѷ���
				mid_d = distance;
				bestCount = 1;
			}
			else if(distance == mid_d){
				best[1] = i;				//��¼�ڶ�����ѷ���
				bestCount = 2;
			}
		}
		if(bestCount == 1){
			return best[0];
		}
		else if(bestCount == 2){			//���������ŷ���
			if(backDir == -1){				//��ʼ�����
				return best[(Math.random()>0.5?0:1)];		//���ѡ��һ������
			}
			else if(best[0]!=backDir && best[1]!=backDir){	//������Ž���û�л�ͷ�ķ���
				return best[(Math.random()>0.5?0:1)];		//���ѡ��һ������
			}
			else{											//���Ž�����һ���ǻ�ͷ�ķ���
				return best[(best[0]==backDir?1:0)];		//���طǻ�ͷ������Ǹ�����
			}
		}
		return backDir;	//���ػ�ͷ����
	}
	//����������Ƿ���Ҫ��ͷ
	public boolean checkIfTurnAround(Monster m){
		int [][] notIn = gv.currNotIn;
		int mrow = m.row;
		int mcol = m.col;
		switch(m.direction){
		case 0:		//����
			if(notIn[mrow+1][mcol]==1){			//��������Ƿ���·
				return true;
			}
			break;
		case 1:		//����
			if(notIn[mrow][mcol-1]==1){			//��������Ƿ���·
				return true;
			}
			break;
		case 2:		//����
			if(notIn[mrow][mcol+1]==1){			//��������Ƿ���·
				return true;
			}
			break;
		case 3:		//����
			if(notIn[mrow-1][mcol]==1){			//��������Ƿ���·
				return true;
			}
			break;
		}
		return false;
	}
}