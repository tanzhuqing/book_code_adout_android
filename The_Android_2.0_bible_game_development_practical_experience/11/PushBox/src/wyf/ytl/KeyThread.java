package wyf.ytl;
/**
 * 
 * ����Ϊ���̼����߳���
 * ��ʱ��⵱ǰ���̵�״̬
 * Ȼ�����״̬������Ӧ�Ĵ���
 * 
 */
public class KeyThread extends Thread{
	int sleepSpan = 150;
	PushBoxActivity pushBoxActivity;//Activity������
	boolean flag = true;//ѭ����־
	boolean keyFlag = true;//�Ƿ������־
	int action;//����״̬��
	
	private boolean KEY_UP = false;//���ϼ��Ƿ񱻰���
	private boolean KEY_DOWN = false;//���¼��Ƿ񱻰���
	private boolean KEY_LEFT = false;//����ļ�������
	private boolean KEY_RIGHT = false;//���ҵļ�������
	
	public KeyThread(PushBoxActivity pushBoxActivity){//������
		this.pushBoxActivity = pushBoxActivity;
	}
	@Override
	public void run(){//��д�ķ���
		while(flag){
			if(keyFlag){//�Ƿ���Ҫ���̼���	
				boolean typeFlag = true;//ʲô���͡������ӻ�����·
				action = pushBoxActivity.action;//�õ���ǰ���̵�״̬��
				if((action & 0x08) != 0){//��
					KEY_UP = true;
				}
				else{
					KEY_UP = false;
				}
				if((action & 0x04) != 0){//��
					KEY_DOWN = true;
				}
				else{
					KEY_DOWN = false;
				}
				if((action & 0x02) != 0){//��
					KEY_LEFT = true;
				}
				else{
					KEY_LEFT = false;
				}
				if((action & 0x01) != 0){//��
					KEY_RIGHT = true;
				}
				else{
					KEY_RIGHT = false;
				}
				
				if(KEY_UP == true){//���ϼ�������
					this.keyFlag = false;
		   			if(!(pushBoxActivity.mySprite.i-1 <=0)){//û�����Ϸ�ʱ
		     			if(pushBoxActivity.map2[pushBoxActivity.mySprite.i-1][pushBoxActivity.mySprite.j] == 1 || pushBoxActivity.map2[pushBoxActivity.mySprite.i-1][pushBoxActivity.mySprite.j] == 3){//���ϲ�������ʱ
		    				if(pushBoxActivity.map2[pushBoxActivity.mySprite.i-2][pushBoxActivity.mySprite.j] == 0){//������ӵ��ϱ��Ƿ�Ϊ�յ�
		    					if(pushBoxActivity.map1[pushBoxActivity.mySprite.i-2][pushBoxActivity.mySprite.j] == 2 || pushBoxActivity.map1[pushBoxActivity.mySprite.i-2][pushBoxActivity.mySprite.j] == 3){//�Ƿ�ΪĿ�ĵ�
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i-2][pushBoxActivity.mySprite.j] = 3;//��ɫ������
		    					}
		    					else{
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i-2][pushBoxActivity.mySprite.j] = 1;
		    					}
		    					pushBoxActivity.map2[pushBoxActivity.mySprite.i-1][pushBoxActivity.mySprite.j] = 0;
		    					pushBoxActivity.gameView.tempi = pushBoxActivity.mySprite.i-2;//�ƶ�����
		    					pushBoxActivity.gameView.tempj = pushBoxActivity.mySprite.j;
		    					BoxThread bt = new BoxThread(1,pushBoxActivity,pushBoxActivity.gameView.tempi,pushBoxActivity.gameView.tempj);//�޼��˶�����
		    					bt.start();//���������ƶ����߳�
		    					typeFlag = false;
		    				}
		    			}
	    			}	
	    			SpriteMoveThread smt = new SpriteMoveThread(1,pushBoxActivity, typeFlag);//�����޼��ƶ�
	    			smt.start();
	    			
	    			SpriteThread st = new SpriteThread(1, pushBoxActivity, typeFlag);//���黻֡
	    			st.start();
				}
				if(KEY_DOWN == true){//���¼�������
					this.keyFlag = false;
	    			if(!((pushBoxActivity.mySprite.i + 1) >= (pushBoxActivity.map2.length-1))){
		     			if(pushBoxActivity.map2[pushBoxActivity.mySprite.i+1][pushBoxActivity.mySprite.j] == 1 || pushBoxActivity.map2[pushBoxActivity.mySprite.i+1][pushBoxActivity.mySprite.j] == 3){//���²�������ʱ
		    				if(pushBoxActivity.map2[pushBoxActivity.mySprite.i+2][pushBoxActivity.mySprite.j] == 0){//������ӵ��±��Ƿ�Ϊ�յ�
		    					if(pushBoxActivity.map1[pushBoxActivity.mySprite.i+2][pushBoxActivity.mySprite.j] == 2 || pushBoxActivity.map1[pushBoxActivity.mySprite.i+2][pushBoxActivity.mySprite.j] == 3){//�Ƿ�ΪĿ�ĵ�
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i+2][pushBoxActivity.mySprite.j] = 3;
		    					}
		    					else{
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i+2][pushBoxActivity.mySprite.j] = 1;
		    					}
		    					pushBoxActivity.map2[pushBoxActivity.mySprite.i+1][pushBoxActivity.mySprite.j] = 0;
		    					
		    					pushBoxActivity.gameView.tempi = pushBoxActivity.mySprite.i+2;
		    					pushBoxActivity.gameView.tempj = pushBoxActivity.mySprite.j;
		    					BoxThread bt = new BoxThread(2,pushBoxActivity,pushBoxActivity.gameView.tempi,pushBoxActivity.gameView.tempj);//�޼��˶�����
		    					bt.start();	    					
		    					typeFlag = false;
		    				}
		    			}   				
	    			}
	    			SpriteMoveThread smt = new SpriteMoveThread(2,pushBoxActivity, typeFlag);//�����޼��ƶ�
	    			smt.start(); 
	    			SpriteThread st = new SpriteThread(2, pushBoxActivity,typeFlag);//���黻֡
	    			st.start();					
				}
				if(KEY_LEFT == true){//�����������
					this.keyFlag = false;//ȥ�����̼��� 
					if(!(pushBoxActivity.mySprite.j-1 <= 0)){
		     			if(pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-1] == 1 || pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-1] == 3){//�����������ʱ
		    				if(pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-2] == 0){//������ӵ�����Ƿ�Ϊ�յ�
		    					if(pushBoxActivity.map1[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-2] == 2 || pushBoxActivity.map1[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-2] == 3){//�Ƿ�ΪĿ�ĵ�
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-2] = 3;//������
		    					}
		    					else{
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-2] = 1;
		    					}
		    					pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-1] = 0;
		    					pushBoxActivity.gameView.tempi = pushBoxActivity.mySprite.i;
		    					pushBoxActivity.gameView.tempj = pushBoxActivity.mySprite.j-2;
		    					BoxThread bt = new BoxThread(3,pushBoxActivity,pushBoxActivity.gameView.tempi,pushBoxActivity.gameView.tempj);//�޼��˶�����
		    					bt.start();		    					
		    					typeFlag = false;
		    				}
		     			}
					}
	     			SpriteMoveThread smt = new SpriteMoveThread(3,pushBoxActivity, typeFlag);//�����޼��ƶ�
	     			smt.start(); 			
	    			SpriteThread st = new SpriteThread(3, pushBoxActivity,typeFlag);//���黻֡
	    			st.start();
				}
				if(KEY_RIGHT == true){//���Ҽ�������
					this.keyFlag = false;
	    			if(!(pushBoxActivity.mySprite.j+1 >= pushBoxActivity.map2[pushBoxActivity.mySprite.i].length-1)){
		    			if(pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+1] == 1 || pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+1] == 3){//�����������ʱ
		    				if(pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+2] == 0){//������ӵ�����Ƿ�Ϊ�յ�
		    					if(pushBoxActivity.map1[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+2] == 2 || pushBoxActivity.map1[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+2] == 3){//�Ƿ�ΪĿ�ĵ�
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+2] = 3;
		    					}	    					
		    					else{
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+2] = 1;
		    					}
		    					pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+1] = 0;
		    					pushBoxActivity.gameView.tempi = pushBoxActivity.mySprite.i;//�ƶ�����
		    					pushBoxActivity.gameView.tempj = pushBoxActivity.mySprite.j+2;
		    					BoxThread bt = new BoxThread(4,pushBoxActivity,pushBoxActivity.gameView.tempi,pushBoxActivity.gameView.tempj);//�޼��˶�����
		    					bt.start();			    					
		    					typeFlag = false;
		    				}
		    			}    				
	    			}
	    			SpriteMoveThread smt = new SpriteMoveThread(4,pushBoxActivity, typeFlag);//�����޼��ƶ�
	    			smt.start();    			
	    			SpriteThread st = new SpriteThread(4, pushBoxActivity,typeFlag);//���黻֡
	    			st.start();
				}	
			}
			try{
				Thread.sleep(sleepSpan);//˯��ָ��������
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}