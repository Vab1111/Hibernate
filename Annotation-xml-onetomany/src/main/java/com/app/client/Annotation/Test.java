
package com.app.client.Annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javax.transaction.TransactionalException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import com.app.model.Annotation.Department;
import com.app.model.Annotation.Teacher;

public class Test {

	static Scanner sc = new Scanner(System.in);

	public static SessionFactory getSessionFactory() {
		Properties pro = new Properties();
		pro.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		pro.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/onetomany");
		pro.setProperty("hibernate.cnnection.username", "root");
		pro.setProperty("hibernate.connection.password", "root");
		pro.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		Configuration cfg = new Configuration();
		cfg.configure();
		cfg.addProperties(pro);
		cfg.addAnnotatedClass(Teacher.class);
		cfg.addAnnotatedClass(Department.class);

		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
		ssrb.applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(ssrb.build());

		return sf;
	}

	public void insert() {
		Session session = getSessionFactory().openSession();

		Transaction tx = session.beginTransaction();
		System.out.println("Enter no.  of teacher you want to allocate courses");
		int no = sc.nextInt();
		for (int i = 0; i < no; i++) {
			System.out.println("Enter teacher Name: ");
			Teacher t = new Teacher();
			t.setName(sc.next());
			System.out.println("Enter how many courses you want to allocate to selected teacher");
			int no1 = sc.nextInt();

			List dList = new ArrayList();
			for (int j = 0; j < no1; j++) {
				System.out.println("Enter course name");

				Department d = new Department();
				d.setdName(sc.next());
				t.setDepartments(dList);
				d.setTeacher(t);

			}
			t.setDepartments(dList);
			session.save(t);
			tx.commit();
			Boolean b = tx.wasCommitted();
			if (b == true) {
				System.out.println("inserted successfully");
			} else {
				System.out.println("not inserted plz try again");
			}
		}

	}

	public void update() {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		System.out.println("Enter id of teacher");
		Teacher t = (Teacher) session.load(Teacher.class, sc.next());
		System.out.println("name of teacher: " + t.getName());
		List l = t.getDepartments();
		for (Object o : l) {
			Department d = (Department) o;
			System.out.println("Do you want to update");
			System.out.println("Do you want to update enter y/n");
			if (sc.next().equalsIgnoreCase("y")) {
				System.out.println("Enter name of course");
				d.setdName(sc.next());
				session.save(d);
			}
		}
		tx.commit();
		Boolean b = tx.wasCommitted();
		if (b == true) {
			System.out.println("updated successfully");
		} else {
			System.out.println("not updated plz try again");
		}

	}

	public void delete() {  
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		System.out.println("Enter id of teacher to delete data");
		Teacher t = (Teacher) session.load(Teacher.class, sc.nextInt());

		session.delete(t);
		tx.commit();
		Boolean b = tx.wasCommitted();
		if (b == true) {
			System.out.println("deleted successfully");
		} else {
			System.out.println("not deleted plz try again ");
		}

	}
	
	public void read()
	{
		Session session=getSessionFactory().openSession();
		List <Teacher>list=session.createCriteria(Teacher.class).list();
		for (Teacher t : list) {
			System.out.println("Teacher name: "+ t.getName());
			System.out.println("Department");
			List<Department> dList=t.getDepartments();
			for (Department department : dList) {
				System.out.println("\t "+department.getdName());
			}
		}
		
	}
	public static void main(String[] args) {

		Test t = new Test();
		do {
			System.out.println("enter \n 1.insert \n 2.update \n 3.delete \n 4.read");
			switch (sc.nextInt()) {
			case 1:
				t.insert();
				break;
			case 2:
				t.update();
				break;
			case 3:
				t.delete();
				break;
			case 4:
				t.read();
			}
			System.out.println("do you want to contnue enter y/n");
		} while (sc.next().equalsIgnoreCase("y"));
	}
}
