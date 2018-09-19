package com.app.client.xml;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.internal.ConcurrentSecondLevelCacheStatisticsImpl;

import com.app.model.xml.Department;
import com.app.model.xml.Teacher;

public class Test {

	static Scanner sc = new Scanner(System.in);

	
	public static SessionFactory getSeessionFactory() {
		Configuration cfg = new Configuration();
		cfg.configure();

		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
		ssrb.applySettings(cfg.getProperties());

		SessionFactory sf = cfg.buildSessionFactory(ssrb.build());

		return sf;
	}


	public void insert() {
		Session session = getSeessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		System.out.println("Enter no. of teacher you want to insert ");
		int no = sc.nextInt();
		for (int i = 1; i <= no; i++) {
			
			Teacher t = new Teacher();
			System.out.println("Enter teacher name");
			t.setName(sc.next());

			System.out.println("Enter how many courses you want to allocate ");
			int no1 = sc.nextInt();
			List<Department> dList = new ArrayList();
			for (int j = 1; no1 >= j; j++) {
				Department d = new Department();
				System.out.println("Enter course name");
				d.setdName(sc.next());
				d.setTeacher(t);
				dList.add(d);

			}
			t.setDepartments(dList);
			session.save(t);
		}
		tx.commit();

		Boolean b = tx.wasCommitted();
		if (b == true) {
			System.out.println("courses allocated to teacher successfully");
		} else {
			System.out.println("courses not allocated to teacher plz try agaibn");
		}

	}

	public void update() {
		Session session = getSeessionFactory().openSession();
		
		System.out.println("Enter department id");
		Integer id = sc.nextInt();
		Object o = session.load(Teacher.class, id);
		Teacher t = (Teacher) o;
		System.out.println("mame: " + t.getName());
		List dlist = t.getDepartments();
		for (Object object : dlist) {
			
			Department d = (Department) object;
			System.out.println("departments:");
			System.out.println("\t\t" + d.getdName());
		}

		for (Object object : dlist) {
			Transaction tx = session.beginTransaction();
			Department d = (Department) object;
			System.out.println("department name: " + d.getdName());
			System.out.println("do you want to  update above this department enter y/n");

			if (sc.next().equalsIgnoreCase("y")) {
				System.out.println("Enter department name to update");
				d.setdName(sc.next());
				session.save(d);
				tx.commit();
				
				Boolean b = tx.wasCommitted();
				if (b == true) {
					System.out.println("department updated successfully");
				} else {
					System.out.println("department not updated plz try again");
				}
			}
		}
		
		

	}

	public void delete() {  
		Session session = getSeessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		System.out.println("Enter id of teacher to delete data");
		int id=sc.nextInt();
		Teacher t = (Teacher) session.load(Teacher.class,id);

		session.delete(t);
		tx.commit();
		Boolean b = tx.wasCommitted();
		if (b == true) {
			System.out.println("deleted successfully");
		} else {
			System.out.println("not deleted plz try again ");
		}

	}

	public void read() {
		Session session = getSeessionFactory().openSession();
		List<Teacher> list = session.createCriteria(Teacher.class).list();
		for (Teacher teacher : list) {
			System.out.println("Teacher name: " + teacher.getName());
			List<Department> list1 = teacher.getDepartments();
			System.out.println("department ");
			for (Department department : list1) {
				System.out.println("\t" + department.getdName());
			}
			System.out.println();
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
