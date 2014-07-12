package com.example.db;

import java.util.ArrayList;

import com.example.bean.Note;
import android.content.Context;


/**
 * �������ݿ����ݵ�һЩ����
 * @author leehy
 *
 */
public class DBServer {
	
		/**
		 * ��Ӽ���
		 * @param fileItem
		 */
		public static void addNote(Context context,Note note){
			DBDao dbDao = new DBDao(context);
			dbDao.addNote(note);
		}
		
		/**
		 * �������ڻ�ü����б�
		 * @param date ����
		 * @return  
		 */
		public static ArrayList<Note> searchNoteByDate(Context context,String date){
			DBDao dbDao = new DBDao(context);
			return dbDao.searchNoteByDate(date);
		}
	
		/**
		 * ����id��ü���
		 * @param context
		 * @param note
		 */
		public static Note searchNoteById(Context context,int id){
			DBDao dbDao = new DBDao(context);
			return dbDao.searchNoteById(id);
		}
		
		/**
		 * �޸�ĳ������
		 * @param context
		 * @param note
		 */
		public static void updateNote(Context context,Note note){
			DBDao dbDao = new DBDao(context);
			dbDao.updateNote(note);
		}
			
		
		/**
		 * ����idɾ��ĳ������
		 * @param id
		 */
		public static void deleteNoteByDate(Context context,int id){
			DBDao dbDao = new DBDao(context);
			dbDao.deleteNoteByDate(id);
		}
	
		
		
}
