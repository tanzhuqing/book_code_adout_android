package wyf.ytl;
/**
 * 
 * �����޼��ƶ���
 * ������ʼλ�úͽ���λ�����ڵ�����
 * �Զ������ʵ�����꣬Ȼ��������ƶ�
 * �����ķ���Ҳ�ڸ�����ʵ�֣�
 * ����߽�Ͻ�ʱ�Զ�����
 *
 */
public class SpriteMoveThread extends Thread {//�����޼��ƶ�
	PushBoxActivity pushBoxActivity; //Activity������
	int dir;//����
	boolean flag = true;
	int span = 350;//˯˯�ߵĺ�����
	public SpriteMoveThread(int dir, PushBoxActivity pushBoxActivity,boolean typeFlag){//������
		super.setName("SpriteMoveThread");
		this.dir = dir;
		this.pushBoxActivity = pushBoxActivity;
	}
	public void run(){
		pushBoxActivity.mySprite.X = pushBoxActivity.gameView.initX
									+36*pushBoxActivity.mySprite.j
									-15*pushBoxActivity.mySprite.i + 2;
		pushBoxActivity.mySprite.Y = pushBoxActivity.gameView.initY
									+10*pushBoxActivity.mySprite.j
									+25*pushBoxActivity.mySprite.i - 25;//��������
		if(dir == 1){//����
			if(!(pushBoxActivity.mySprite.i<=0) 
			&& pushBoxActivity.map2[pushBoxActivity.mySprite.i-1][pushBoxActivity.mySprite.j] == 0){//�����ƶ���ʱ
				//�ƶ�����
				pushBoxActivity.mySprite.isRun = true;
				for(int i=0; i<2; i++){
					pushBoxActivity.mySprite.X += 7;
					pushBoxActivity.mySprite.Y -= 12;
					try {
						Thread.sleep(span);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				pushBoxActivity.mySprite.i -= 1;
				pushBoxActivity.mySprite.isRun = false;
			}
		}
		else if(dir == 2){//����
			if(!(pushBoxActivity.mySprite.i >= pushBoxActivity.map2.length-1) 
			&& (pushBoxActivity.map2[pushBoxActivity.mySprite.i+1][pushBoxActivity.mySprite.j] == 0)){
				pushBoxActivity.mySprite.isRun = true;
				pushBoxActivity.mySprite.i += 1;
				for(int i=0; i<2; i++){
					pushBoxActivity.mySprite.X -= 7;
					pushBoxActivity.mySprite.Y += 12;
					try {
						Thread.sleep(span);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				pushBoxActivity.mySprite.isRun = false;
			}
		}
		else if(dir == 3){//����
			if(!(pushBoxActivity.mySprite.j <= 0) && pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-1] == 0){//�����ƶ�ʱ
				
				//�ƶ�����
				pushBoxActivity.mySprite.isRun = true;
				for(int i=0; i<2; i++){
					pushBoxActivity.mySprite.X -= 17;
					pushBoxActivity.mySprite.Y -= 5;
					try {
						Thread.sleep(span);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				pushBoxActivity.mySprite.j -= 1;
				pushBoxActivity.mySprite.isRun = false;
			}	
		}
		else if(dir == 4){//����
			if(!(pushBoxActivity.mySprite.j+1 > pushBoxActivity.map2.length-1) && pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+1] == 0){
				pushBoxActivity.mySprite.isRun = true;
				pushBoxActivity.mySprite.j += 1;
				for(int i=0; i<2; i++){
					pushBoxActivity.mySprite.X += 17;
					pushBoxActivity.mySprite.Y += 5;
					try {
						Thread.sleep(span);//˯�� 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				pushBoxActivity.mySprite.isRun = false;
			}
		}
		//����
        int newX = pushBoxActivity.gameView.initX+36*pushBoxActivity.mySprite.j-15*pushBoxActivity.mySprite.i;
        int newY = pushBoxActivity.gameView.initY+10*pushBoxActivity.mySprite.j+25*pushBoxActivity.mySprite.i;
        this.scroll(newX, newY);//���ù�����������Ҫʱ��������Ҫʱ����    
	}
	
	public void scroll(int x, int y){//��������
        if(x < 100){//����߾�100ʱ
        	pushBoxActivity.gameView.initX += 60;
        }
        if(y < 100){//���ϱ߾�100ʱ
        	pushBoxActivity.gameView.initY += 60;
        }
        if(x > 220){//���ұ߾�320-200ʱ
        	pushBoxActivity.gameView.initX -= 60;
        }
        if(y > 380){//���±߾�480-380ʱ
        	pushBoxActivity.gameView.initY -= 60;
        }
	}
}