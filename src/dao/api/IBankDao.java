package dao.api;

import java.util.List;

import model.Bank;

public interface IBankDao {
		
	int save(Bank obj);
	int update(Bank obj);
	int delete(Bank obj);
	
	Bank getByID(int bankID);
	List<Bank> getAll();
	
}
