package br.com.caelum.revolution.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Author;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.domain.Modification;


public class HibernatePersistence {

	private Session session;
	private final Config config;
	private static SessionFactory sessionFactory;
	private static Integer lock = new Integer(3000);
	
	public HibernatePersistence(Config config) {
		this.config = config;
	}
	
	public void initMechanism(List<Class<?>> classes) {
		synchronized(lock) {
			Configuration configuration = new Configuration();
			configuration.setProperty("hibernate.connection.driver_class", config.asString("driver_class"));
			configuration.setProperty("hibernate.connection.url", config.asString("connection_string"));
			configuration.setProperty("hibernate.dialect", config.asString("dialect"));
			configuration.setProperty("hibernate.connection.username", config.asString("db_user"));
			configuration.setProperty("hibernate.connection.password", config.asString("db_pwd"));
			configuration.setProperty("hibernate.jdbc.batch_size", "20");
			
			configuration.setProperty("hibernate.c3p0.acquire_increment", "5");
			configuration.setProperty("hibernate.c3p0.idle_test_period", "100");
			configuration.setProperty("hibernate.c3p0.max_size", "20");
			configuration.setProperty("hibernate.c3p0.max_statements", "0");
			configuration.setProperty("hibernate.c3p0.min_size", "5");
			configuration.setProperty("hibernate.c3p0.timeout", "1800");
			
			configuration.addAnnotatedClass(Commit.class);
			configuration.addAnnotatedClass(Artifact.class);
			configuration.addAnnotatedClass(Modification.class);
			configuration.addAnnotatedClass(Author.class);
			
			for (Class<?> clazz : classes) {
				configuration.addAnnotatedClass(clazz);
			}
			
			if(config.asString("create_tables").equals("true")) {
				SchemaExport se = new SchemaExport(configuration);
				se.create(false, true);
			}
			
			sessionFactory = configuration.buildSessionFactory();
		}
	}
	
	public void beginTransaction() {
		openSession();
		session.beginTransaction();
	}

	public void openSession() {
		session = sessionFactory.openSession();
	}
	
	public void commit() {
		session.flush();
		session.getTransaction().commit();
		close();
	}
	
	public Session getSession() {
		return session;
	}

	public void end() {
		sessionFactory.close();
	}

	public void rollback() {
		if(session!=null && session.getTransaction()!=null) {
			session.getTransaction().rollback();
		}
		close();
	}

	public void close() {
		if(session!=null) {
			session.close();
		}
	}

	public void initMechanism() {
		initMechanism(new ArrayList<Class<?>>());
	}
	
}
