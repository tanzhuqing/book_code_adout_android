package wyf.ytl;
/**
 * 
 * ����Ϊ����Ļ�֡�߳�
 * �Զ�����ܹ���֡��Ȼ����ݷ���û��һ��ʱ�任һ֡
 *
 */
public class SpriteThread extends Thread{//��֡
	int span = 205;//˯�ߵĺ�����
	int dir;//����
	PushBoxActivity pushBoxActivity;//Activity����
	boolean typeFlag;//����ʲô���͵�ͼƬ��trueΪ��·��ͼ��falseΪ�����ӵ�ͼ
	public SpriteThread(int dir, PushBoxActivity pushBoxActivity,boolean typeFlag){//������
		this.dir = dir;
		this.pushBoxActivity = pushBoxActivity;
		this.typeFlag = typeFlag;
	}
	public void run(){ 
		if(typeFlag){//������·��ͼ
			for(int i=0; i<pushBoxActivity.mySprite.manDown.length; i++){//��Ϊÿ������֡������ͬ������ֻѭ��һ��
				if(dir == 1){//��
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manUp[i];
				}
				if(dir == 2){//��
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manDown[i];
				} 
				if(dir == 3){//��
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manLeft[i];
				}
				if(dir == 4){//��
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manRight[i];
				}
				try{
					Thread.sleep(span);//˯��
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}		
		}
		else{//���������ӵ�ͼ
			for(int i=0; i<pushBoxActivity.mySprite.manPushDown.length; i++){//��Ϊÿ������֡������ͬ������ֻѭ��һ��
				if(dir == 1){//��
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manPushUp[i];
				}
				if(dir == 2){//��
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manPushDown[i];
				} 
				if(dir == 3){//��
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manPushLeft[i];
				}
				if(dir == 4){//��
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manPushRight[i];
				}
				try{
					Thread.sleep(span);//˯��
				}
				catch(Exception e){
					e.printStackTrace();//��ӡ�쳣��ջ��Ϣ
				}
			}			
		}
		pushBoxActivity.kt.keyFlag = true;//�ָ����̼���
	}
}