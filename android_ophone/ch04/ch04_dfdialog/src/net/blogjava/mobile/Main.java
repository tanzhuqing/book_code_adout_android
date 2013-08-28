package net.blogjava.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
	@Override
	public void onClick(View v)
	{
		new AlertDialog.Builder(this).setIcon(R.drawable.question).setTitle(
				"�Ƿ�ɾ���ļ�").setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						new AlertDialog.Builder(Main.this).setMessage(
								"�ļ��Ѿ���ɾ��.").create().show();
					}
				}).setNegativeButton("ȡ��",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{

						new AlertDialog.Builder(Main.this).setMessage(
								"���Ѿ�ѡ����ȡ����ť�����ļ�δ��ɾ��.").create().show();
					}
				}).show();

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);
	}
}