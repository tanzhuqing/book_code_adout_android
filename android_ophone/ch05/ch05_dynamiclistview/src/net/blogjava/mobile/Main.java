package net.blogjava.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Main extends Activity implements OnClickListener,
		OnItemSelectedListener
{

	private static String[] data = new String[]
	{ "��������", "���ν�գ����˰棩2", "�ھ���", "��������", "�˹�����", "������", "���۰�ͯľ ", "δ��սʿ",
			"�Ǽʴ���",
			"٪�޼͹�԰2:ʧ�������   ��飺��Ƭԭ����ʧ������硷����ʷ���ģ�˹Ƥ���������졶٪�޼͹�԰���ĸ߸�����ѧר�ҽܷ�߲��ף��ػ�������������ɣ�ɵ���" };

	private ListView lvDynamic;
	private ViewAdapter viewAdapter;
	private int selectedIndex = -1;

	private class ViewAdapter extends BaseAdapter
	{
		private Context context;
		private List textIdList = new ArrayList();

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(inflater);
			LinearLayout linearLayout = null;
			if (textIdList.get(position) instanceof String)
			{

				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.text, null);
				TextView textView = ((TextView) linearLayout
						.findViewById(R.id.textview));
				textView.setText(String.valueOf(textIdList.get(position)));
			}
			else if (textIdList.get(position) instanceof Integer)
			{
				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.image, null);
				ImageView imageView = (ImageView) linearLayout
						.findViewById(R.id.imageview);
				imageView.setImageResource(Integer.parseInt(String
						.valueOf(textIdList.get(position))));
			}
			return linearLayout;
		}

		public ViewAdapter(Context context)
		{
			this.context = context;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public int getCount()
		{
			return textIdList.size();
		}

		@Override
		public Object getItem(int position)
		{
			return textIdList.get(position);
		}

		public void addText(String text)
		{
			textIdList.add(text);
			notifyDataSetChanged();
		}

		public void addImage(int resId)
		{
			textIdList.add(resId);
			notifyDataSetChanged();
		}

		public void remove(int index)
		{
			if (index < 0)
				return;
			textIdList.remove(index);
			notifyDataSetChanged();
		}

		public void removeAll()
		{
			textIdList.clear();
			notifyDataSetChanged();
		}

	}

	private int getImageResourceId()
	{
		int[] resourceIds = new int[]
		{ R.drawable.item1, R.drawable.item2, R.drawable.item3,
				R.drawable.item4, R.drawable.item5 };
		return resourceIds[new Random().nextInt(resourceIds.length)];
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnAddText:
				int randomNum = new Random().nextInt(data.length);
				viewAdapter.addText(data[randomNum]);
				break;

			case R.id.btnAddImage:
				viewAdapter.addImage(getImageResourceId());
				break;
			case R.id.btnRemove:
				viewAdapter.remove(selectedIndex);
				selectedIndex = -1;
				break;
			case R.id.btnRemoveAll:
				viewAdapter.removeAll();
				break;

		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id)
	{
		selectedIndex = position;

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lvDynamic = (ListView) findViewById(R.id.lvDynamic);
		Button btnAddText = (Button) findViewById(R.id.btnAddText);
		Button btnAddImage = (Button) findViewById(R.id.btnAddImage);
		Button btnRemove = (Button) findViewById(R.id.btnRemove);
		Button btnRemoveAll = (Button) findViewById(R.id.btnRemoveAll);
		btnAddText.setOnClickListener(this);
		btnAddImage.setOnClickListener(this);
		btnRemove.setOnClickListener(this);
		btnRemoveAll.setOnClickListener(this);

		viewAdapter = new ViewAdapter(this);
		lvDynamic.setAdapter(viewAdapter);
		lvDynamic.setOnItemSelectedListener(this);

	}
}