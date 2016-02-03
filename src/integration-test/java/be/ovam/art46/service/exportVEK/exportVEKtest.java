package be.ovam.art46.service.exportVEK;

import be.ovam.art46.sap.model.Project;
import be.ovam.art46.sap.model.Spreiding;
import be.ovam.art46.service.ExportVekService;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml.txt"})
public class exportVEKtest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ExportVekService exportVekService;

	 @Test
	public void testData() {

		Assert.assertTrue(sessionFactory != null);

		org.hibernate.Session currentSession = sessionFactory.openSession();

		String id = "1200004139";
		Transaction beginTransaction = currentSession.beginTransaction();

		/*
		 * Project object = (Project)currentSession.get(Project.class, id);
		 * object.setSpreidingValidatieD(new Date());
		 * currentSession.saveOrUpdate(object);
		 */

		// beginTransaction = currentSession.beginTransaction();
		//
		// List<Spreiding> teVerwijderen = new ArrayList<Spreiding>();
		// for (Spreiding spreiding2 : object.getSpreiding()) {
		//
		// System.out.println(spreiding2.getBedrag().toString());
		//
		// if (spreiding2.getBedrag().equals(new BigDecimal(15000))) {
		//
		// teVerwijderen.add(spreiding2);
		//
		// }
		//
		// }
		//
		// object.getSpreiding().removeAll(teVerwijderen);
		// object.addSpreiding(2030, new BigDecimal(30000));
		//
		// currentSession.saveOrUpdate(object);
		beginTransaction.commit();

		//
		AuditReader reader = AuditReaderFactory.get(currentSession);
		List<Number> versions = reader.getRevisions(Project.class, id);

		for (Number number : versions) {
			System.out.print(number + " ");

			System.out.println(reader.getRevisionDate(number).toLocaleString());

			Project current = (Project) reader.find(Project.class, id, number);

			List<Spreiding> spreidingAtProject = reader.createQuery()
					.forEntitiesAtRevision(Spreiding.class, number)

					.add(AuditEntity.relatedId("project").eq(id))

					.getResultList();

			System.out.println(current);
			System.out.println(spreidingAtProject);

			// System.out.println(current.getSpreiding().size());

		}

	}

	//@Test
	public void testExport() throws IOException {

		Assert.assertTrue(sessionFactory != null);
		Assert.assertTrue(exportVekService != null);

		/*org.hibernate.Session currentSession = sessionFactory.openSession();
		String budgString = "7341200";
		
		Criteria projectCriteria = currentSession.createCriteria(Project.class, "p");
		projectCriteria.createAlias("p.spreiding", "s", JoinType.LEFT_OUTER_JOIN);
		
		
		
		
		projectCriteria.add(Restrictions.eq("p.budgetairArtikel", budgString));
		
		List list = projectCriteria.list(); 
		
		*/
		
	      int jaar = 2013; 
	      String budgettairArtikel = "7341200";
		    String datumString = jaar + "-01-01";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startDatum", datumString);
			params.put("jaar", jaar);
			params.put("budgettair_artikel", budgettairArtikel);
		
		//List<Project> vekData = exportVekService.getVekData(params);
		
		
		
		
		int startJaar = 2005 ; 
		int eindJaar =2020 ;
	
		

		File file = new File("/opt/exportVek.xls");
		
		FileOutputStream fop = new FileOutputStream(file);
		 
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		
		
		exportVekService.createVekExport("export", fop, params, startJaar,eindJaar,null);
	}

}
