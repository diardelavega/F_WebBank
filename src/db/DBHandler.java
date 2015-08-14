package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DBHandler {
	private static SessionFactory sessionFactory;

	private static void buildSessionFactory() {
		try {
			ServiceRegistry serviceRegistry = null;
			// Create the SessionFactory from hibernate.cfg.xml
			 sessionFactory = new
			// Configuration().configure().buildSessionFactory(serviceRegistry);
			 AnnotationConfiguration().configure()
			 .buildSessionFactory();

//			Configuration configuration = new Configuration();
//			configuration.configure();
//			serviceRegistry = new ServiceRegistryBuilder().applySettings(
//					configuration.getProperties()).buildServiceRegistry();
//			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory;
	}

	public static void closeSessionFactory() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}
