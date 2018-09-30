package hibernate.implementation;

import java.io.PrintWriter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.DataAccOb;
import interfaces.IDataAccOb;

@Repository
@Transactional
public class DataAccObImpl implements IDataAccOb {

	@Autowired
	private SessionFactory sessionFactory;

	private PrintWriter console = new PrintWriter(System.out, true);

	@Override
	public void save(DataAccOb statements) {

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(statements);
			session.getTransaction().commit();

		} catch(Exception exception) {
			sessionFactory.openSession().close();
			console.println(exception);
		} finally {
			console.println("Process done");
		}
	}
}		