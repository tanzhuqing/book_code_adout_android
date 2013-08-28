package wyf.ytl;

public class WelcomeViewGoThread extends Thread{
	int sleepSpan = 200;//˯�ߵĺ�����
	private boolean flag = true;
	int status = 0;
	PushBoxActivity pushBoxActivity;//activity������
	public WelcomeViewGoThread(PushBoxActivity pushBoxActivity){
		this.pushBoxActivity = pushBoxActivity;
	}
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	public void run() {//��д��run����
		while(flag){
			switch(status){
				case 0://ľ���˶�
					pushBoxActivity.welcomeView.woodLeftX -= 2;
					pushBoxActivity.welcomeView.woodRightX += 2;
					if(pushBoxActivity.welcomeView.woodLeftX<-90){
						status = 1;
					}
					break;
				case 1://�����˶�
					pushBoxActivity.welcomeView.ironY -= 8;
					if(pushBoxActivity.welcomeView.ironY<-380){
						status = 2;
					}
					break;
				case 2:
					pushBoxActivity.welcomeView.wallLeftX -= 3;
					pushBoxActivity.welcomeView.wallRightX += 3;
					if(pushBoxActivity.welcomeView.wallLeftX<-90){
						status = 3;
					}
					break;
				case 3:
					this.flag = false;
					pushBoxActivity.myHandler.sendEmptyMessage(1);//����activity����Handler��Ϣ
					break;
			}
			try{
				Thread.sleep(sleepSpan);//˯��
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}