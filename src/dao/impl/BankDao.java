package dao.impl;

import java.util.ArrayList;
import java.util.List;

import model.Bank;

import dao.api.IBankDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class BankDao implements IBankDao {

	private DbConnection conn = null;
	private final String TABLE_NAME = "bank";
    private String strSql = "";
    
    public BankDao(Context context) {
    	conn = new DbConnection(context);
    }
    
    private Bank cursorToObject(Cursor cursor) {
		
    	Bank obj = new Bank();
    	
    	obj.setBankID(cursor.getInt(cursor.getColumnIndex("bank_id")));
    	obj.setNamaBank(cursor.getString(cursor.getColumnIndex("nama_bank")));
    	
    	return obj;
    }
    
	@Override
	public int save(Bank obj) {
		int result = 0;        	
    	
    	// get reference to writable DB
    	SQLiteDatabase db = conn.getWritableDatabase();
    	
    	// create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put("nama_bank", obj.getNamaBank());
    	
    	// insert
    	result = (int)db.insert(TABLE_NAME, null, values);
    	db.close();
    	
    	return result;
	}

	@Override
	public int update(Bank obj) {
		int result = 0;        	
    	
    	// get reference to writable DB
    	SQLiteDatabase db = conn.getWritableDatabase();
    	
    	// create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put("nama_bank", obj.getNamaBank());
    	
    	// set parameter query
    	String[] params = { Integer.toString(obj.getBankID()) };
    	
    	// update
    	result = db.update(TABLE_NAME, values, "bank_id = ?", params);
    	db.close();
    	
    	return result;
	}

	@Override
	public int delete(Bank obj) {
		int result = 0;        	
    	
    	// get reference to writable DB
    	SQLiteDatabase db = conn.getWritableDatabase();
    	
    	// set parameter query
    	String[] params = { Integer.toString(obj.getBankID()) };
    	
    	// delete
    	result = db.delete(TABLE_NAME, "bank_id = ?", params);
    	db.close();
    	
    	return result;
	}

	@Override
	public Bank getByID(int bankID) {
		Bank bank = null;
    	
    	// get reference to readable DB
		SQLiteDatabase db = conn.getReadableDatabase();
		Cursor cursor = null;
		
		try {
			
			// set parameter query
			String[] params = { Integer.toString(bankID) };
			
			// build query
			strSql = "SELECT * " +
					 "FROM bank " +
					 "WHERE bank_id = ?";		
			cursor = db.rawQuery(strSql, params);
			
			// if we got results get the first one
			if (cursor.moveToFirst())
				// build object
				bank = cursorToObject(cursor);
			
		} catch (SQLiteException e) {
			Log.d("SQLiteException", e.getMessage());
			
		} finally {
	    	if (cursor != null)
	    		cursor.close();
	    	
	    	db.close();
		}			        
    	
    	// return object
		return bank;
	}
	
	@Override
	public List<Bank> getAll() {
		List<Bank> oList = new ArrayList<Bank>();
		
		// get reference to readable DB
    	SQLiteDatabase db = conn.getReadableDatabase();		
    	
    	Cursor cursor = null;
    	
		try {
			
			// build query
	    	strSql = "SELECT * " +
					 "FROM bank " +
	    			 "ORDER BY nama_bank";
	    	
			cursor = db.rawQuery(strSql, null);
			
			while (cursor.moveToNext()) {
				
				// build object
				Bank bank = cursorToObject(cursor);
				
				// add to collection
				oList.add(bank);
				
			}
			
		} catch (SQLiteException e) {
			Log.d("SQLiteException", e.getMessage());
			
		} finally {
	    	if (cursor != null)
	    		cursor.close();
	    	
	    	db.close();
		}
		
		return oList;
	}

}
