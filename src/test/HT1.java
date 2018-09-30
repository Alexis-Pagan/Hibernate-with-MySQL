package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.DataAccOb;
import hibernate.Hibernate;
import interfaces.IDataAccOb;
import models.Model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Hibernate.class})
public class HT1 {

	@Autowired
	private IDataAccOb ob;
	
	@Test
	public void saveTest() {
		
		DataAccOb model1 = new DataAccOb(); // entity of database
		
		Model model2 = new Model(); // entity of front end
		
		model2.setName("Alexis");
		String data = model2.getName();
		model1.setName(data);
		
		ob.save(model1);
		
	}
}
