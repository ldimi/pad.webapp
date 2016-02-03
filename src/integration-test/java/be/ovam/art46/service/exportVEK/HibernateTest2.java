package be.ovam.art46.service.exportVEK;

import be.ovam.art46.sap.model.VastleggingSpreiding;
import be.ovam.art46.sap.model.VastleggingSpreidingItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml.txt"} )
public class HibernateTest2 extends AbstractJUnit4SpringContextTests  { 
	
	


	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void testData(){
	
		
		Session session = sessionFactory.openSession();

		session.beginTransaction();
		VastleggingSpreiding vastlegging = new VastleggingSpreiding();

		vastlegging.setProjectId("120004339");
	
		vastlegging.addSpreiding(2009, new BigDecimal(15000));
		vastlegging.addSpreiding(2010, new BigDecimal(10000));
		vastlegging.addSpreiding(2011, new BigDecimal(10000));

		session.save(vastlegging);
		session.getTransaction().commit();
		
		
		

		
		session.beginTransaction();
		VastleggingSpreiding vastlegging2 = new VastleggingSpreiding();

		vastlegging2.setProjectId("120004500");
		
		vastlegging2.addSpreiding(2013, new BigDecimal(15000));
		vastlegging2.addSpreiding(2014, new BigDecimal(900));
		vastlegging2.addSpreiding(2015, new BigDecimal(800));

		session.save(vastlegging2);
		session.getTransaction().commit();
		String id2 = vastlegging2.getProjectId();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String id = vastlegging.getProjectId();

		session.beginTransaction();
		VastleggingSpreiding nieuwe = (VastleggingSpreiding) session.get(VastleggingSpreiding.class, id);

		List<VastleggingSpreidingItem> spreiding = nieuwe.getSpreiding();
		
		

		List<VastleggingSpreidingItem> teVerwijderen = new ArrayList<VastleggingSpreidingItem>();
		for (VastleggingSpreidingItem spreiding2 : spreiding) {

			System.out.println(spreiding2.getBedrag().toString());

			if (spreiding2.getBedrag().equals(new BigDecimal(15000))) {

				teVerwijderen.add(spreiding2);

			}

		}

		spreiding.removeAll(teVerwijderen);
		vastlegging.addSpreiding(2012, new BigDecimal(10000));

		session.saveOrUpdate(vastlegging);
		session.getTransaction().commit();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		session.beginTransaction();
		VastleggingSpreiding nieuwe2 = (VastleggingSpreiding) session.get(VastleggingSpreiding.class, id2);

		List<VastleggingSpreidingItem> spreiding2 = nieuwe2.getSpreiding();
		spreiding2.remove(0);
		
		
		session.save(vastlegging2);
		session.getTransaction().commit();
		

		

		AuditReader reader = AuditReaderFactory.get(session);
		List<Number> versions = reader.getRevisions(VastleggingSpreiding.class, id);
		for (Number number : versions) {
			System.out.print(number + " ");

			System.out.println(reader.getRevisionDate(number).toLocaleString());

			VastleggingSpreiding current = (VastleggingSpreiding) reader.find(VastleggingSpreiding.class,
					id, number);
			System.out.println(current);

		}
		
		List<Number> versions2 = reader.getRevisions(VastleggingSpreiding.class, id2);
	
		for (Number number : versions2) {
			System.out.print(number + " ");

			System.out.println(reader.getRevisionDate(number).toLocaleString());

			VastleggingSpreiding current = (VastleggingSpreiding) reader.find(VastleggingSpreiding.class,
					id2, number);
			System.out.println(current);

		}
		
		
	 System.out.println("tussen wijziging 1 en 2 de (eerste gewijzigd, tweede nog niet...");
	 
	 
	 VastleggingSpreiding vl1 = (VastleggingSpreiding) reader.find(VastleggingSpreiding.class,
				id, 3);
		
	
		

	 VastleggingSpreiding vl2 = (VastleggingSpreiding) reader.find(VastleggingSpreiding.class,
				id2, 3);
	
		
		System.out.println(vl1);
		System.out.println(vl2);
	}
		
}