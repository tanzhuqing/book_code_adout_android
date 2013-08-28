package wyf.ytl;
public class BoxThread extends Thread{
	int span = 130;//˯�ߵ�ʱ��
	PushBoxActivity pushBoxActivity;//Activity����
	int dir;//����
	int i;
	int j;
	public BoxThread(int dir,PushBoxActivity pushBoxActivity, int i, int j){//������
		this.pushBoxActivity = pushBoxActivity;//�õ�activity������
		this.dir = dir;//����
		this.i = i;
		this.j = j;
	}
	public void run(){//��д��run����
		if(pushBoxActivity.isSound){//��Ҫ��������ʱ
			pushBoxActivity.pushBoxSound.start();//��������
		}
		if(dir == 1){//���� 
	        int X = pushBoxActivity.gameView.initX+36*j-15*(i+1);//�����ʵ��x����
	        int Y = pushBoxActivity.gameView.initY+10*j+25*(i+1);//�����ʵ��y����
			for(int c=0; c<2; c++){//��������
				X += 7;
				Y -= 12;
				pushBoxActivity.gameView.tx = X;//����x����
				pushBoxActivity.gameView.ty = Y;//����y����
				try {
					Thread.sleep(span);//˯��
				} catch (InterruptedException e) {
					e.printStackTrace();//��ӡ�쳣�Ķ�ջ��Ϣ
				}
			}
			pushBoxActivity.gameView.tempi = -1;//���õ�ǰû�������ƶ�
			pushBoxActivity.gameView.tempj = -1;
		}
		else if(dir == 2){//����
	        int X = pushBoxActivity.gameView.initX+36*j-15*(i-1);//�������ǰ���ӵ�ʵ������
	        int Y = pushBoxActivity.gameView.initY+10*j+25*(i-1);
			for(int c=0; c<2; c++){//��������
				X -= 7;
				Y += 12;
				pushBoxActivity.gameView.tx = X;//�ƶ�
				pushBoxActivity.gameView.ty = Y;				
				try {
					Thread.sleep(span);//��Ϣ
				} catch (InterruptedException e) {
					e.printStackTrace();//��ӡ�쳣��Ϣ
				}
			}
			pushBoxActivity.gameView.tempi = -1;//���õ�ǰû�������ƶ�
			pushBoxActivity.gameView.tempj = -1;
		}
		else if(dir == 3){//����
	        int X = pushBoxActivity.gameView.initX+36*(j+1)-15*i;//����ʵ������
	        int Y = pushBoxActivity.gameView.initY+10*(j+1)+25*i;
			for(int c=0; c<2; c++){//�������ߣ�
				X -= 17;
				Y -= 5;//�����ƶ�
				pushBoxActivity.gameView.tx = X;//�ƶ�
				pushBoxActivity.gameView.ty = Y;				
				try {
					Thread.sleep(span);//˯��
				} catch (InterruptedException e) {
					e.printStackTrace();//��ӡ�쳣��Ϣ
				}
			}
			pushBoxActivity.gameView.tempi = -1;
			pushBoxActivity.gameView.tempj = -1; 
		}
		else if(dir == 4){//����
	        int X = pushBoxActivity.gameView.initX+36*(j-1)-15*i;//����ʵ������
	        int Y = pushBoxActivity.gameView.initY+10*(j-1)+25*i;
			for(int c=0; c<2; c++){
				X += 17;
				Y += 5; 
				pushBoxActivity.gameView.tx = X;
				pushBoxActivity.gameView.ty = Y;//�ƶ�����		
				try {
					Thread.sleep(span);//˯��ָ��������
				} catch (InterruptedException e) {
					e.printStackTrace();//��ӡ�쳣��Ϣ
				}
			}
			pushBoxActivity.gameView.tempi = -1;
			pushBoxActivity.gameView.tempj = -1;
		}
		if(isWin()){//��ʤ��ʱ 
			pushBoxActivity.gameView.status = 1;
			if(pushBoxActivity.isSound){
				pushBoxActivity.winSound.start();//��������
			}
		}
	}
	/**
	 * �жϵ�ǰ�Ƿ��Ѿ�ʤ��
	 * ֻ���鵱ǰ�����Ƿ񻹴���û�б��̵����Ӽ���
	 */
    public boolean isWin(){
    	for(int i=0; i<pushBoxActivity.map2.length; i++ ){
    		for(int j=0; j<pushBoxActivity.map2[i].length; j++){
    			if(pushBoxActivity.map2[i][j] == 1){//�в�����ɫ������
    				return false;
    			}
    		}
    	}
    	return true;
    }
}