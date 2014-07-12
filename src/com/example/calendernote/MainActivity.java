package com.example.calendernote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.bean.Note;
import com.example.db.DBServer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CalendarView.OnDateChangeListener;

/**
 * 
 * @author leehy
 *
 */
public class MainActivity extends Activity {

	CalendarView calendarView = null; //�����ؼ�

	Button btnNew 	= null;  //�½�һ����¼
	ListView noteListView 	= null;  //�����б�
	
	String noteDate = "";//��ѡ���� �� yyyy-MM-dd ����ʽ����
	
	ArrayList<Note> noteDatas = new ArrayList<Note>();//�����б�����
	Note currentNote = null; //��ǰ�ļ���
	
	TextView txvTip = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setTitle("�ճ̼���");
		
		initView();//��ʼ���������
		
	}
	
	/**
	 * ��ʼ���������	
     */
	private void initView() {
			
		txvTip = (TextView) findViewById(R.id.txv);
			
		btnNew = (Button) findViewById(R.id.btnNewNote);
		btnNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				newNote();//�½�һ����¼
			}
		});		
			
		calendarView = (CalendarView) findViewById(R.id.calendarView);
		calendarView.setShowWeekNumber(false);
			
		//�����ǵ�������е�ĳһ�գ��ᴥ�����¼�
		calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			public void onSelectedDayChange(CalendarView view, int year, int month,int dayOfMonth) {
	        // TODO Auto-generated method stub
	           	refreshNoteList();//ˢ�¼����б�
	        }
	     });	
				
		noteListView = (ListView) findViewById(R.id.lv);	 
		noteListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position,long id) {
			// TODO Auto-generated method stub
				showNote(noteDatas.get(position));//�鿴������¼
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshNoteList();//ˢ�¼����б�
	}

	/**
	 * ˢ�¼����б�
	 */
	private void refreshNoteList() {
		
		noteDate = getDateString(calendarView.getDate());//��õ�ǰ�����ؼ��е���ѡ����
		loadData();//��ȡ��ѡ���ڵļ����б�����
		
		if (noteDatas!=null && noteDatas.size()>0) {//��������м��£�����ʾ�����б�
			ListAdapter adapter = new ListAdapter(getApplicationContext());
		    noteListView.setAdapter(adapter);
		    txvTip.setText(noteDate+" �����б�");
		}else {//��������޼��£�����ʾ���޼���
			txvTip.setText(noteDate+" ���޼���");
		}
	}
	
	/**
	 * ��ȡ��ѡ���ڵļ����б�����
	 */
	private void loadData() {
		noteDatas = DBServer.searchNoteByDate(getApplicationContext(), noteDate);
		//	Log.e("noteDatas", "noteDatas.size = "+noteDatas.size());
	}

   
	
	
	/**
	 * �½�һ����¼
	 */
	private void newNote() {
		Intent intent = new Intent(MainActivity.this,EditNoteActivity.class);
		intent.putExtra("UItype", "newNote");//�½�
		intent.putExtra("noteDate", noteDate);
		startActivity(intent);
	}
		
	/**
	 * �鿴һ����¼
	 */
	private void showNote(Note note) {
			
		Intent intent = new Intent(MainActivity.this,EditNoteActivity.class);
		intent.putExtra("UItype", "showNote");//�鿴
		intent.putExtra("note", note);
		startActivity(intent);
	}
	
	
	/**
	 * ��ȡ��ǰ���ڣ� ������ʽΪ yyyy-MM-dd
	 */
	private String getDateString(long date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");     
		Date  curDate  =  new Date(date);     
		return  formatter.format(curDate);     
	}
	
	/**
	 * �����б���������
	 */
	class ListAdapter extends BaseAdapter {
		
		private Context mContext = null;
		private LayoutInflater mInflater = null;
		
		public ListAdapter(Context c) {
			mContext = c;
			mInflater = LayoutInflater.from(this.mContext);
		}
		
		@Override
		public int getCount() {
			return noteDatas.size();
		}
		
		@Override
		public Object getItem(int position) {
			return position;
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ListViewHolder holder = null;
			if (convertView == null) {
				holder = new ListViewHolder();
				convertView = mInflater.inflate(R.layout.list_item, null);
				//��ʼ�����
				holder.txvName   = (TextView) convertView.findViewById(R.id.txvName);
				holder.btnOpen   = (Button) convertView.findViewById(R.id.btnOpen);
				convertView.setTag(holder);
			} else {
				holder=(ListViewHolder) convertView.getTag();
			}
	
			holder.txvName.setText(noteDatas.get(position).title);
			holder.btnOpen.setTag(noteDatas.get(position));
			holder.btnOpen.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showNote((Note)v.getTag());//�鿴������¼
				}
			});
			
			return convertView;
		}
		
		class ListViewHolder {
			TextView txvName;
			Button btnOpen;
		}
	}
	 
	

}
