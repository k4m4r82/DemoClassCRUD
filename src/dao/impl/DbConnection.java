package dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbConnection extends SQLiteOpenHelper {
	
	// Database Version
    private static final int DATABASE_VERSION = 1;
    
    // Database Name
    public static final String DATABASE_NAME = "DbDemo";
    
    private static final String SCRIPT_SQL = "DbDemo.sql";
    
    private Context context = null;
    
    public DbConnection(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
    
    @Override
	public void onCreate(SQLiteDatabase db) {
		execSqlScript(db, SCRIPT_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		List<String> daftarTabel = new ArrayList<String>();
		
		daftarTabel.add("bank");
		
		for (String tabel : daftarTabel) {

			// Drop older books table if existed
	        db.execSQL("DROP TABLE IF EXISTS " + tabel);
		}
		
        // create fresh books table
        this.onCreate(db);
		
	}
	
    private void execSqlScript(SQLiteDatabase db, String fileName) {
    	
		try {
			
			InputStream inputStream = this.context.getAssets().open(fileName);
			InputStreamReader streamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(streamReader);
			
			while (reader.ready()) {
				String sql = reader.readLine();
				
				db.execSQL(sql);
			}
			
		} catch (IOException e) {
			Log.d("execSqlScript error", e.getMessage());
		}
    	
    }

}
