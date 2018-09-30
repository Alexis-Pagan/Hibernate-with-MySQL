package hibernate;

import java.util.Properties;



import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import dao.DataAccOb;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:hibernate.properties"})
@ComponentScan(basePackages = {"dao", "models", "interfaces", "hibernate.implementation"})
public class Hibernate {
	
	@Value("${jdbc.driverClassName}")
	private String JDBCDriverClassName;
	
	@Value("${jdbc.url}")
	private String JDBCUrl;
	
	@Value("${jdbc.userName}")
	private String JDBCUserName;
	
	@Value("${jdbc.password}")
	private String JDBCPassword;
	
	@Value("${hibernate.dialect}")
	private String hibernateDialect;
	
	@Value("${hibernate.show_sql}")
	private String hibernateShowSQL;
	
	@Value("${hibernate.format_sql}")
	private String hibernateFormatSQL;
	
	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", hibernateDialect);
		properties.put("hibernate.show_sql", hibernateShowSQL);
		properties.put("hibernate.format_sql", hibernateFormatSQL);
		
		return properties;
	}
	
	@Bean
	public DataSource getDataSource() {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(JDBCDriverClassName);
		dataSource.setUrl(JDBCUrl);
		dataSource.setUsername(JDBCUserName);
		dataSource.setPassword(JDBCPassword);
		
		return dataSource;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(getDataSource()); 
		sessionFactory.setHibernateProperties(getHibernateProperties());
		sessionFactory.setAnnotatedClasses(DataAccOb.class); // search

		
		return sessionFactory;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	@Autowired
	public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory) {
		HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
		
		return hibernateTemplate;
	}
	
	@Autowired
	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txm = new HibernateTransactionManager();
		txm.setSessionFactory(sessionFactory);
		return txm;
	}
}