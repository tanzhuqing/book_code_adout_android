package wyf.wpf;				//���������
public class WelcomeThread extends Thread{
	WelcomeView father;
	boolean flag;
	int sleepSpan = 100;//����ʱ��
	int characterCounter=0;	//ִ��2��ѭ�����Ż����
	int gateCounter=0;
	int charNumber=0;
	
	public WelcomeThread(WelcomeView father){
		this.father = father;
		flag = true;
	}
	//�̵߳�ִ�з���
	public void run(){
		while(flag){
			switch(father.status){
			case 0://���ڽ��Ա���ͼƬ
				father.alpha += 5;
				if(father.alpha == 255){
					father.status = 1;
				}
				break;
			case 1://���ڹ�������
				father.scrollX -=5;
				if(father.scrollX==20){
					father.status = 2;
				}
				break;
			case 2://��ʾ����
				characterCounter++;
				if(characterCounter == 2){
					characterCounter = 0;
					father.characterNumber++;//View����ʾ��������1
					charNumber++;//��������1
					if(charNumber == father.str.length){
						father.status = 3;
						father.alpha = 0;
					}					
				}
				break;
			case 3://��ʾ�˵�����
				father.scrollY -=5;//���������ƶ�
				father.textStartY -=5;//���������ƶ�
				father.alpha +=25;
				if(father.scrollY == 10){
					father.status = 4;
				}
				break;
			case 5://�а�ť����
				gateCounter++;
				if(gateCounter == 2){//ѭ��2�βŻ��һ��֡
					father.gateIndex++;
					gateCounter = 0;
					if(father.gateIndex == 3){//�����������
						int index = father.selectedIndex;
						father.father.myHandler.sendEmptyMessage(index);
						father.status = 4;//�ص�����״̬
					}					
				}
				break;
			default:
				break;
			}
			try{
				Thread.sleep(sleepSpan);
			}										//�߳�����
			catch(Exception e){
				e.printStackTrace();
			}										//���񲢴�ӡ�쳣
		}
	}
}