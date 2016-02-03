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
public class HibernateTest3 extends AbstractJUnit4SpringContextTests  { 
	
	


	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void testData(){
	
		
		Session session = sessionFactory.openSession();

		session.beginTransaction();
//		VastleggingSpreiding vastlegging = new VastleggingSpreiding();
//
//		vastlegging.setProjecten("120004339");
//	
//		vastlegging.addSpreiding(2013, new BigDecimal(15000));
//		vastlegging.addSpreiding(2014, new BigDecimal(10000));
//		vastlegging.addSpreiding(2015, new BigDecimal(10000));
//
//		session.merge(vastlegging);
		
		
		VastleggingSpreiding vastlegging = (VastleggingSpreiding) session.get(VastleggingSpreiding.class , "120004339");
		
		vastlegging.getSpreiding().retainAll(new ArrayList<VastleggingSpreidingItem>());
		
		vastlegging.addSpreiding(2017, new BigDecimal(15000));
		vastlegging.addSpreiding(2018, new BigDecimal(10000));
		vastlegging.addSpreiding(2019, new BigDecimal(10000));
		
		session.getTransaction().commit();
		
		
		AuditReader reader = AuditReaderFactory.get(session);
		List<Number> versions = reader.getRevisions(VastleggingSpreiding.class, "120004339");
		for (Number number : versions) {
			System.out.print(number + " ");

			System.out.println(reader.getRevisionDate(number).toLocaleString());

			VastleggingSpreiding current = (VastleggingSpreiding) reader.find(VastleggingSpreiding.class,
					"120004339", number);
			System.out.println(current);

		}

		
	
	}
		
}