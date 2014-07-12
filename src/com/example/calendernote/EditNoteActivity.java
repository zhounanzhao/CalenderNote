package com.example.calendernote;


import com.example.bean.Note;
import com.example.db.DBServer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * �༭��鿴�ճ̼��µĽ���
 * @author lee
 *
 */
public class EditNoteActivity extends Activity {
	
	RelativeLayout etNoteLayout = null; //��д���µĲ���
	EditText etNoteTitle  =  null;      //���µı���
	EditText etNoteContent=  null;      //���µ�����
	
	LinearLayout txvNoteLayout = null; //��д���µĲ���
	EditText txvNoteTitle  =  null;    //���µı���
	EditText txvNoteContent=  null;    //���µ�����

	
	Button btnSave	 = null; //�����¼ ��ť
	Button btnDelete = null; //ɾ����¼ ��ť
	Button btnEdit	 = null; //�޸ļ�¼ ��ť
	
	
	String noteTitle	=""; //����
	String noteContent	=""; //����
	String noteDate 	=""; //��ѡ���� �� yyyy-MM-dd ����ʽ����
	
	Note currentNote = null; //��ǰ�ļ���
	
	String UItype = "" ; //���ֽ���
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_note);
		setContentView(R.layout.activity_edit_note);
		setTitle("�ճ̼���");
		
		initView();
		
		UItype = getIntent().getStringExtra("UItype");
		if (UItype.equals("newNote")) {
			showNewNoteUI();//��ʾ�½�����
		}
		else if (UItype.equals("showNote")) {
			showTextNoteUI();//��ʾ�������ֽ���
		}
		else if (UItype.equals("editNote")) {
			showEditNoteUI();//��ʾ�޸ļ������ֽ���
		}
		
	}

	private void showTextNoteUI() {
		currentNote = (Note) getIntent().getSerializableExtra("note");
		etNoteLayout.setVisibility(View.VISIBLE);
		etNoteTitle.setText(currentNote.title);
		etNoteContent.setText(currentNote.content);
		etNoteTitle.setEnabled(false);
		etNoteContent.setEnabled(false);
		etNoteTitle.setBackgroundColor(Color.WHITE);
		etNoteTitle.setTextColor(Color.BLACK);
		etNoteContent.setHint("");
		etNoteContent.setBackgroundColor(Color.WHITE);
		etNoteContent.setTextColor(Color.BLACK);
		
		btnSave.setVisibility(View.GONE);
		btnDelete.setVisibility(View.VISIBLE);
		btnEdit.setVisibility(View.VISIBLE);
	}

	private void showNewNoteUI() {
		etNoteLayout.setVisibility(View.VISIBLE);
		btnSave.setVisibility(View.VISIBLE);
		btnDelete.setVisibility(View.GONE);
		btnEdit.setVisibility(View.GONE);
	}
	
	private void showEditNoteUI() {
		
		etNoteLayout.setVisibility(View.VISIBLE);
		btnSave.setVisibility(View.VISIBLE);
		btnDelete.setVisibility(View.GONE);
		btnEdit.setVisibility(View.GONE);
		
		currentNote = (Note) getIntent().getSerializableExtra("note");
		etNoteTitle.setText(currentNote.title);
		etNoteContent.setText(currentNote.content);
	}


	private void initView() {
		
		etNoteLayout =  (RelativeLayout) findViewById(R.id.etNoteLayout);
		etNoteTitle  =  (EditText) findViewById(R.id.etNoteTitle); 
	    etNoteContent=  (EditText) findViewById(R.id.etNoteContent);
	    
	    btnSave		 =  (Button)findViewById(R.id.btnSave);
	    btnDelete	 =  (Button)findViewById(R.id.btnDelete);
	    btnEdit		 =  (Button)findViewById(R.id.btnEdit);
		
	    
	    
	    btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (UItype.equals("newNote")) {
					saveNewNote();//����һ���¼�¼
				}
				if (UItype.equals("editNote")) {
					saveEditNote();//�����޸ĵļ�¼
				}
			}
		});
	    
	    btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				deleteNote(currentNote);//ɾ��һ���¼�¼
			}
		});
	
	    btnEdit.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View arg0) {
	    		// TODO Auto-generated method stub
	    		editNote(currentNote);//�޸�һ����¼
	    	}
	    });
	    
	
	    
	}
	
	/**
	 * �޸�һ����¼
	 */
	private void editNote(Note currentNote) {
		
		Intent intent = new Intent(EditNoteActivity.this,EditNoteActivity.class);
		intent.putExtra("UItype", "editNote");//�޸�
		intent.putExtra("note", currentNote);
		startActivity(intent);
		this.finish();
	}
	
	/**
	 * �����¼�¼
	 */
	private void saveNewNote() {
		
		noteDate = getIntent().getStringExtra("noteDate");
		noteTitle 	= etNoteTitle.getText().toString().trim();
		noteContent = etNoteContent.getText().toString().trim();
		
		if (!noteTitle.equals("")) {
			currentNote = new Note();
			currentNote.title= noteTitle;
			currentNote.content= noteContent;
			currentNote.date= noteDate;
			
			DBServer.addNote(getApplicationContext(), currentNote);

			EditNoteActivity.this.finish();
			
		}else {
			Toast.makeText(getApplicationContext(), "���������",Toast.LENGTH_SHORT).show();
		}
	}

	
	/**
	 * ɾ��һ����¼
	 */
	private void deleteNote(Note note) {
		DBServer.deleteNoteByDate(getApplicationContext(), note.id);
		EditNoteActivity.this.finish();
	}
	

	/**
	 * �����޸ĵļ�¼
	 */
	private void saveEditNote() {
		
		currentNote = (Note) getIntent().getSerializableExtra("note");
		noteTitle 	= etNoteTitle.getText().toString().trim();
		noteContent = etNoteContent.getText().toString().trim();
		
		if (!noteTitle.equals("")) {
			currentNote.title= noteTitle;
			currentNote.content= noteContent;
			
			DBServer.updateNote(getApplicationContext(), currentNote);
			EditNoteActivity.this.finish();
			
		}else {
			Toast.makeText(getApplicationContext(), "���������",Toast.LENGTH_SHORT).show();
		}
	}

	
	

}
