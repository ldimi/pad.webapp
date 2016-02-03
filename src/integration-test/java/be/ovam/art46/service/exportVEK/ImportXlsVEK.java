package be.ovam.art46.service.exportVEK;

import be.ovam.art46.sap.model.Project;
import be.ovam.art46.sap.model.ProjectDTO;
import be.ovam.art46.sap.model.Spreiding;
import be.ovam.art46.sap.model.SpreidingDTO;
import be.ovam.art46.service.ExportVekService;
import be.ovam.art46.util.Application;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml.txt"})
public class ImportXlsVEK extends AbstractJUnit4SpringContextTests {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ExportVekService exportVekService;

	 @Test
	public void testData() {

		 
		 try {
				org.hibernate.Session currentSession = sessionFactory.openSession();
			    FileInputStream file = new FileInputStream(new File("/home/fdmoor/Downloads/spreiding van betaling.xls"));
			     
			    //Get the workbook instance for XLS file 
			    HSSFWorkbook workbook = new HSSFWorkbook(file);
			 
			    //Get first sheet from the workbook
			    HSSFSheet sheet = workbook.getSheetAt(0);
			     
			    //Iterate through each rows from first sheet
			    Iterator<Row> rowIterator = sheet.iterator();
			   
			    rowIterator.next(); 
			    int i = 0;

             while (rowIterator.hasNext()) {
                 Row row = rowIterator.next();
			        i++; 
			        
			        if (row.getCell(0)!=null && row.getCell(0).getCellType()==(Cell.CELL_TYPE_NUMERIC) ) {
						
					
			        
			        int jaar = (int) row.getCell(0).getNumericCellValue(); 
			        int vastnr =(int) row.getCell(3).getNumericCellValue();
			        
			       
			        BigDecimal spreiding2013 = row.getCell(6)!=null && row.getCell(6).getCellType()==(Cell.CELL_TYPE_NUMERIC) ? new BigDecimal ( String.valueOf(row.getCell(6).getNumericCellValue())   ):null;
			        BigDecimal spreiding2014 = row.getCell(7)!=null && row.getCell(7).getCellType()==(Cell.CELL_TYPE_NUMERIC)?new BigDecimal ( String.valueOf(row.getCell(7).getNumericCellValue())   ):null;
			        BigDecimal spreiding2015 = row.getCell(8)!=null && row.getCell(8).getCellType()==(Cell.CELL_TYPE_NUMERIC)?new BigDecimal (String.valueOf(row.getCell(8).getNumericCellValue())    ):null;
			        BigDecimal spreiding2016 = row.getCell(9)!=null && row.getCell(9).getCellType()==(Cell.CELL_TYPE_NUMERIC)?new BigDecimal ( String.valueOf(row.getCell(9).getNumericCellValue())   ):null;
			        BigDecimal spreiding2017 = row.getCell(10)!=null && row.getCell(10).getCellType()==(Cell.CELL_TYPE_NUMERIC) ?new BigDecimal(String.valueOf(row.getCell(10).getNumericCellValue())   ):null;
			        BigDecimal spreiding2018 = row.getCell(11)!=null && row.getCell(11).getCellType()==(Cell.CELL_TYPE_NUMERIC)?new BigDecimal(String.valueOf(row.getCell(11).getNumericCellValue())   ):null;
			        
			    
							

					Criteria project = currentSession.createCriteria(Project.class, "p");
					Criteria spreidingCrit = project.createCriteria("p.spreiding", "s",
							JoinType.LEFT_OUTER_JOIN);

					project.add(Restrictions.eq("p.boekjaar", jaar));
					project.add(Restrictions.eq("p.initieelAchtNr", "0"+ vastnr));

					Project uniqueResult = (Project) project.uniqueResult();
					
				if (uniqueResult != null) {

					ProjectDTO projectDto = new ProjectDTO(uniqueResult);

					Collections.sort(projectDto.getSpreiding(),
							new Comparator<SpreidingDTO>() {

								public int compare(SpreidingDTO o1,
										SpreidingDTO o2) {
									return (-1)
											* o1.getJaar().compareTo(
													o2.getJaar());
								}
							});

					projectDto.setSpreiding(new ArrayList<SpreidingDTO>());

					if (spreiding2013 != null) {
						projectDto.getSpreiding().add(
								new SpreidingDTO(2013, spreiding2013));
					}
					if (spreiding2014 != null) {
						projectDto.getSpreiding().add(
								new SpreidingDTO(2014, spreiding2014));
					}
					if (spreiding2015 != null) {
						projectDto.getSpreiding().add(
								new SpreidingDTO(2015, spreiding2015));
					}
					if (spreiding2016 != null) {
						projectDto.getSpreiding().add(
								new SpreidingDTO(2016, spreiding2016));
					}
					if (spreiding2017 != null) {
						projectDto.getSpreiding().add(
								new SpreidingDTO(2017, spreiding2017));
					}
					if (spreiding2018 != null) {
						projectDto.getSpreiding().add(
								new SpreidingDTO(2018, spreiding2018));
					}

					saveProject(projectDto);

				}
			        
			        
			         
			        //For each row, iterate through each columns
			        /*				        Iterator<Cell> cellIterator = row.cellIterator();
			        while(cellIterator.hasNext()) {
			             
			            Cell cell = cellIterator.next();
			             
			            switch(cell.getCellType()) {
			                case Cell.CELL_TYPE_BOOLEAN:
			                    System.out.print(cell.getBooleanCellValue() + "\t\t");
			                    break;
			                case Cell.CELL_TYPE_NUMERIC:
			                    System.out.print(cell.getNumericCellValue() + "\t\t");
			                    break;
			                case Cell.CELL_TYPE_STRING:
			                    System.out.print(cell.getStringCellValue() + "\t\t");
			                    break;
			            }
			        }
			        System.out.println("");
			        */ 
		    }
			    }

			    file.close();
			    FileOutputStream out = 
			        new FileOutputStream(new File("C:\\test.xls"));
			    workbook.write(out);
			    out.close();
			    currentSession.close();
			     
			} catch (FileNotFoundException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			}
		 
		 
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED)
		public ProjectDTO saveProject(ProjectDTO projectDto) {

			Session currentSession = sessionFactory.openSession();
			

			be.ovam.art46.sap.model.Project project = (be.ovam.art46.sap.model.Project) currentSession
					.get(be.ovam.art46.sap.model.Project.class,
							projectDto.getProjectId());
			
			
			Transaction beginTransaction = currentSession.beginTransaction();
			
		//	
			

			List<Spreiding> spreidingBestaand = project.getSpreiding();
			HashMap<Integer, Spreiding> spreidingBestaandMap = new HashMap<Integer, Spreiding>();

			for (Spreiding spreiding : spreidingBestaand) {

				spreidingBestaandMap.put(spreiding.getJaar(), spreiding);

			}

			NumberFormat decimalFormattter = NumberFormat.getNumberInstance(Locale.GERMANY);

			List<SpreidingDTO> spreidingNieuw = projectDto.getSpreiding();

			BigDecimal totaalSpreiding = new BigDecimal(0);

			HashMap<Integer, SpreidingDTO> spreidingDtoMap = new HashMap<Integer, SpreidingDTO>();

			for (Iterator iterator = spreidingNieuw.iterator(); iterator.hasNext();) {
				SpreidingDTO schatting = (SpreidingDTO) iterator.next();

				totaalSpreiding = totaalSpreiding.add(schatting.getBedrag());

				if (spreidingDtoMap.get(schatting.getJaar()) != null) {

					String errorMsg = "In de spreiding betaling komt het jaar "
							+ schatting.getJaar() + "meerdere keren voor ";
					throw new RuntimeException(errorMsg);

				} else {

					spreidingDtoMap.put(schatting.getJaar(), schatting);
				}

			}

			BigDecimal controleBedrag;

			if (projectDto.getVoorgesteldAfTeBoekenBedrag() != null
					&& projectDto.isVastleggingMagAfgeboektWorden()) {

				controleBedrag = projectDto.getInitieelBedrag().subtract(
						projectDto.getVoorgesteldAfTeBoekenBedrag());

			} else {

				controleBedrag = projectDto.getInitieelBedrag();
			}

			BigDecimal verschil = projectDto.getInitieelBedrag().subtract(
					totaalSpreiding);

			if (totaalSpreiding.compareTo(controleBedrag) < 0) {

				String errorMsg = "de som van de verwachte vereffeningskredieten is kleiner ("
						+ decimalFormattter.format(verschil)
						+ ") dan het initiele vastleggingsbedrag"
						+ "gelieve dit te corrigeren of aan te vinken dat een overtollige gedeelte mag vrijgegeve worden";

				projectDto.setVoorgesteldAfTeBoekenBedrag(controleBedrag
						.subtract(totaalSpreiding));

				//throw new RuntimeException(errorMsg);
			}

			if (totaalSpreiding.compareTo(controleBedrag)> 0 && !projectDto.isVastleggingMagAfgeboektWorden() ) {

				String errorMsg = "de som van de verwachte vereffeningskredieten is groter ("
						+ decimalFormattter
								.format(verschil.multiply(new BigDecimal(-1)))
						+ ") dan het initiele vastleggingsbedrag"
						+ "gelieve dit te corrigeren of aan te vinken dat dit toch klopt";

				projectDto.setVoorgesteldAfTeBoekenBedrag(controleBedrag
						.subtract(totaalSpreiding));

				//throw new RuntimeException(errorMsg);
			}

			for (Iterator iterator = spreidingNieuw.iterator(); iterator.hasNext();) {
				SpreidingDTO spreidingDto = (SpreidingDTO) iterator.next();

				Spreiding spreidingItemBestaand = spreidingBestaandMap
						.get(spreidingDto.getJaar());

				if (spreidingItemBestaand != null) {

					spreidingItemBestaand.setBedrag(spreidingDto.getBedrag());
					/*if(currentSession.contains(spreidingItemBestaand)) {
						currentSession.evict(spreidingItemBestaand);
						}*/
					
					System.out.println("updating");
					currentSession.update(spreidingItemBestaand);

				} else {

					spreidingItemBestaand = new Spreiding();
					spreidingItemBestaand.setProject(project);
					spreidingItemBestaand.setJaar(spreidingDto.getJaar());
					spreidingItemBestaand.setBedrag(spreidingDto.getBedrag());

					System.out.println("updating");
					currentSession.save(spreidingItemBestaand);

				}
			}

			for (Spreiding spreiding : spreidingBestaand) {

			
				
				if(!spreidingDtoMap.containsKey(spreiding.getJaar())){
					
					currentSession.delete(spreiding);
				}

			}
			
			project.setSpreidingValidatieD(new  Date());
			project.setSpreidingValidatieUid(Application.INSTANCE.getUser_id());
			
			project.setAfTeBoekenBedrag(project.getVoorgesteldAfTeBoekenBedrag());
		
			currentSession.update(project);
			beginTransaction.commit();
			 currentSession.close();

			return projectDto;

		}
	 
	 

}
