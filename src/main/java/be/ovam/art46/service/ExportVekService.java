package be.ovam.art46.service;

import be.ovam.art46.model.VastleggingOrdonanceringLijstDO;
import be.ovam.art46.sap.model.Project;
import be.ovam.art46.sap.model.Spreiding;
import com.google.common.collect.Lists;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang3.StringUtils;
import be.ovam.util.mybatis.SqlSession;
import org.apache.log4j.Logger;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

@Component("exportVekService")
public class ExportVekService {
	
	 private Logger log = Logger.getLogger(ExportVekService.class);
	 
	@Autowired
	private SessionFactory sessionFactory;
	 
	 
	@Autowired
	private SqlSession sqlSession;
	 
	 
	 /*
	public void createVekExport(String title, OutputStream outputStream, Map<String,Object> zoekparams, int startJaar, int eindJaar){


        List<VastleggingOrdonanceringLijstDO>   dtoLijst = new ArrayList<VastleggingOrdonanceringLijstDO>();

        List<Project> vekData = getVekData(zoekparams, dtoLijst);
		
		List<Integer> jaren = new ArrayList<Integer>();
		for (int i = startJaar  ; i <= eindJaar; i++) {
			
			jaren.add(i);
		}
		
		exportToExcel(title, outputStream, vekData, jaren);
		
		
	}    */


    public void createVekExport(String title, OutputStream outputStream, Map<String,Object> zoekparams, int startJaar, int eindJaar,String ivs){


        List<VastleggingOrdonanceringLijstDO>   dtoLijst = new ArrayList<VastleggingOrdonanceringLijstDO>();

        List<Project> vekData = getVekData(zoekparams,dtoLijst);

        List<Integer> jaren = new ArrayList<Integer>();
        for (int i = startJaar  ; i <= eindJaar; i++) {

            jaren.add(i);
        }



        if(ivs!=null)   {     exportToExcel(title, outputStream, vekData, jaren,dtoLijst,ivs); }
        else{ exportToExcel(title, outputStream, vekData, jaren,dtoLijst,null);}




    }
	
	
	@Transactional 
	 public List<Project> getVekData(Map<String, Object> params, List<VastleggingOrdonanceringLijstDO> dtoLijst){
		 
		

			
			List<VastleggingOrdonanceringLijstDO> selectList = sqlSession
					.selectList(
							"be.ovam.art46.mappers.ProjectMapper.getOrdonannceringLijst",
							params);

            dtoLijst.addAll(selectList);
			
			List<List<VastleggingOrdonanceringLijstDO>> partition = Lists.partition(selectList, 100);
			List<Project> returnList = new ArrayList<Project>();
		// voor test: 	Session currentSession = sessionFactory.openSession();
			Session currentSession = sessionFactory.getCurrentSession(); 
			
			for (List<VastleggingOrdonanceringLijstDO> list : partition) {
				
				List<String> projectIds = new ArrayList<String>();
				for (VastleggingOrdonanceringLijstDO vastleggingOrdonanceringLijstDO : list) {
					
					projectIds.add(vastleggingOrdonanceringLijstDO.getProject_id());
				}
				
				Criteria projectCriteria = currentSession.createCriteria(Project.class, "p");
				projectCriteria.createAlias("p.spreiding", "s", JoinType.LEFT_OUTER_JOIN);
				projectCriteria.add(Restrictions.in("p.projectId", projectIds));
				
				projectCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				List list2 = projectCriteria.list();
				
				returnList.addAll(list2);
				
			}
			
		// voor test	currentSession.close();
		
			
			return returnList; 
			
	 }
	

	
	
	 public void exportToExcel(String title, OutputStream outputStream, List<Project> projecten,List<Integer> jaren, List<VastleggingOrdonanceringLijstDO> dtoLijst,String ivs) {
	     
		
		   Map<MultiKey, Spreiding> spreidingMap = prepareData( projecten) ;


         Map<String,VastleggingOrdonanceringLijstDO> dtoMap = new HashMap<String, VastleggingOrdonanceringLijstDO>();

         for (VastleggingOrdonanceringLijstDO dto: dtoLijst)    {

             dtoMap.put(dto.getProject_id(),dto);

         }
		 
		    HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet(title);
	        sheet.setColumnWidth(0, 15 * 256);
	        sheet.setColumnWidth(1, 10 * 256);
	        sheet.setColumnWidth(2, 15 * 256);
	        sheet.setColumnWidth(3, 15 * 256);

	        HSSFFont font_title = workbook.createFont();
	        short hoogte_title = 12;

	        font_title.setFontHeightInPoints(hoogte_title);
	        font_title.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        font_title.setUnderline(HSSFFont.U_NONE);

	        HSSFCellStyle kolomTitleStijl = workbook.createCellStyle();
	        kolomTitleStijl.setFont(font_title);

	      

	        HSSFRow titleRow = sheet.createRow(0);
	       
	      
	       
	        HSSFFont font_kleiner = workbook.createFont();
	        short hoogte_kleiner = 10;
	        font_kleiner.setFontHeightInPoints(hoogte_kleiner);
	        font_kleiner.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        font_kleiner.setUnderline(HSSFFont.U_NONE);
	        HSSFCellStyle kolomHoofdingStijl = workbook.createCellStyle();
	        kolomHoofdingStijl.setFont(font_kleiner);

         int vasteKolommen;

          if(ivs==null){

	        addCell(titleRow, 0, "achtnummer", kolomHoofdingStijl);
	        addCell(titleRow, 1, "boekjaar", kolomHoofdingStijl);
	        addCell(titleRow, 2, "twaalfnr", kolomHoofdingStijl);
	        addCell(titleRow, 3, "wbs nummer", kolomHoofdingStijl);
             vasteKolommen = 4 ;

          } else  {

              addCell(titleRow, 0, "achtnummer", kolomHoofdingStijl);
              addCell(titleRow, 1, "boekjaar", kolomHoofdingStijl);
              addCell(titleRow, 2, "twaalfnr", kolomHoofdingStijl);
              addCell(titleRow, 3, "wbs nummer", kolomHoofdingStijl);
              addCell(titleRow, 4, "DossierNr", kolomHoofdingStijl);
              addCell(titleRow, 5, "Dossierhouder", kolomHoofdingStijl);
              addCell(titleRow, 6, "Besteknr", kolomHoofdingStijl);

               vasteKolommen = 7 ;



          }
	        
	        
	      


	        for (Integer jaar: jaren){
	        	int positie = jaren.indexOf(jaar) +vasteKolommen ;
	            addCellGetal(titleRow, positie, jaar, kolomHoofdingStijl);
	           
	           
	        }
	        
	        int projectCounter = 1 ;
	        for (Project project : projecten) {
	        	
	        	
	        	HSSFRow projectrow = sheet.createRow(projectCounter);
	        	  
	        	addCellGetal(projectrow, 0, Double.parseDouble( project.getInitieelAchtNr()), null);
	        	addCellGetal(projectrow, 1, project.getBoekjaar() , null);
	        	addCellGetal(projectrow, 2,  Double.parseDouble( project.getProjectId()), null);
	        	addCell(projectrow, 3,  project.getWbsNr(), null);

                if (ivs!=null) {

                    VastleggingOrdonanceringLijstDO vastleggingOrdonanceringLijstDO = dtoMap.get(project.getProjectId());

                    addCell(projectrow, 4,  vastleggingOrdonanceringLijstDO.getDossier_nr(), null);
                    addCell(projectrow, 5,  vastleggingOrdonanceringLijstDO.getDoss_hdr_id(), null);
                    addCell(projectrow, 6,  vastleggingOrdonanceringLijstDO.getProgramma(), null);


                }
		  	       
	  	        
	  	        
	  	        
	  	        
	  	      for (Integer jaar: jaren){
	  	    	  
	  	    	    MultiKey key = new MultiKey(project.getProjectId(),jaar);
	  	    	     int positie = jaren.indexOf(jaar) +vasteKolommen ;
	  	    	   BigDecimal bedrag = new BigDecimal(0);
	  	    	    Spreiding spreiding = spreidingMap.get(key);
	  	    	    if (spreiding!=null) {
	  	    	    	
	  	    	    	bedrag = spreiding.getBedrag(); 
						
					}
	  	    	    
	  	    	    
	  	    	  
		        	
		            addCellGetal(projectrow, positie, bedrag.doubleValue(), null);
		           
		           
		        }
	  	        
	  	    projectCounter++;
				
			}
	       
	        
	        
	        
	        try {
	            workbook.write(outputStream);
	        } catch (IOException e) {
	            log.error(e,e);
	        } finally {
	            if (outputStream != null) {
	                try {
	                    outputStream.flush();
	                    outputStream.close();
	                } catch (IOException e) {
	                    log.error(e,e);
	                }
	            }
	        }
	        
	        

	 }



    public void exportToExcelIVS(String title, OutputStream outputStream, List<Project> projecten, List<Integer> jaren, List<VastleggingOrdonanceringLijstDO> dtoLijst) {


        Map<MultiKey, Spreiding> spreidingMap = prepareData( projecten) ;

        Map<String,VastleggingOrdonanceringLijstDO> dtoMap = new HashMap<String, VastleggingOrdonanceringLijstDO>();

        for (VastleggingOrdonanceringLijstDO dto: dtoLijst)    {

               dtoMap.put(dto.getProject_id(),dto);

        }


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 15 * 256);

        HSSFFont font_title = workbook.createFont();
        short hoogte_title = 12;

        font_title.setFontHeightInPoints(hoogte_title);
        font_title.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font_title.setUnderline(HSSFFont.U_NONE);

        HSSFCellStyle kolomTitleStijl = workbook.createCellStyle();
        kolomTitleStijl.setFont(font_title);



        HSSFRow titleRow = sheet.createRow(0);



        HSSFFont font_kleiner = workbook.createFont();
        short hoogte_kleiner = 10;
        font_kleiner.setFontHeightInPoints(hoogte_kleiner);
        font_kleiner.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font_kleiner.setUnderline(HSSFFont.U_NONE);
        HSSFCellStyle kolomHoofdingStijl = workbook.createCellStyle();
        kolomHoofdingStijl.setFont(font_kleiner);



        addCell(titleRow, 0, "achtnummer", kolomHoofdingStijl);
        addCell(titleRow, 1, "boekjaar", kolomHoofdingStijl);
        addCell(titleRow, 2, "twaalfnr", kolomHoofdingStijl);
        addCell(titleRow, 3, "wbs nummer", kolomHoofdingStijl);
        addCell(titleRow, 4, "DossierNr", kolomHoofdingStijl);
        addCell(titleRow, 5, "Dossierhouder", kolomHoofdingStijl);
        addCell(titleRow, 6, "Besteknr", kolomHoofdingStijl);




        int vasteKolommen = 7 ;
        for (Integer jaar: jaren){
            int positie = jaren.indexOf(jaar) +vasteKolommen ;
            addCellGetal(titleRow, positie, jaar, kolomHoofdingStijl);


        }

        int projectCounter = 1 ;
        for (Project project : projecten) {


            VastleggingOrdonanceringLijstDO vastleggingOrdonanceringLijstDO = dtoMap.get(project.getProjectId());


            HSSFRow projectrow = sheet.createRow(projectCounter);

            addCellGetal(projectrow, 0, Double.parseDouble( project.getInitieelAchtNr()), null);
            addCellGetal(projectrow, 1, project.getBoekjaar() , null);
            addCellGetal(projectrow, 2,  Double.parseDouble( project.getProjectId()), null);
            addCell(projectrow, 3,  project.getWbsNr(), null);
            addCell(projectrow, 4,  vastleggingOrdonanceringLijstDO.getDossier_nr(), null);
            addCell(projectrow, 5,  vastleggingOrdonanceringLijstDO.getDoss_hdr_id(), null);
            addCell(projectrow, 6,  vastleggingOrdonanceringLijstDO.getProgramma(), null);





            for (Integer jaar: jaren){

                MultiKey key = new MultiKey(project.getProjectId(),jaar);
                int positie = jaren.indexOf(jaar) +vasteKolommen ;
                BigDecimal bedrag = new BigDecimal(0);
                Spreiding spreiding = spreidingMap.get(key);
                if (spreiding!=null) {

                    bedrag = spreiding.getBedrag();

                }




                addCellGetal(projectrow, positie, bedrag.doubleValue(), null);


            }

            projectCounter++;

        }




        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error(e,e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e,e);
                }
            }
        }



    }




    private void addCell(HSSFRow row, int position, Object value) {
			addCell(row, position, value, null);
		}

		private void addCell(HSSFRow row, int position, Object value, HSSFCellStyle style) {
			HSSFCell cel = row.createCell(position);
			if (value != null) {
				cel.setCellValue(new HSSFRichTextString(value.toString()));
			} else {
				cel.setCellValue(new HSSFRichTextString("-"));
			}
			if (style != null) {
				cel.setCellStyle(style);
			}
		}
		
		private void addCellStringAlsGetal(HSSFRow row, int position, String value, HSSFCellStyle style) {
			HSSFCell cel = row.createCell(position);
			
			if (StringUtils.isNotEmpty(value)) {
				cel.setCellValue(Double.parseDouble(value));
				
			}else {
				cel.setCellValue("");
			}
		
		
		
			
			if (style != null) {
				cel.setCellStyle(style);
			}
		}

		private void addCellGetal(HSSFRow row, int position, double value, HSSFCellStyle style) {
			HSSFCell cel = row.createCell(position);
			cel.setCellValue(value);
		
		
		
			if (style != null) {
				cel.setCellStyle(style);
			}
		}
		
		
		
		
		
		
		

		private void addCellDatum(HSSFRow row, int position, Date value, HSSFCellStyle style) {
			CreationHelper helper = row.getSheet().getWorkbook().getCreationHelper();
			HSSFCell cel = row.createCell(position);

			if (value != null) {
				cel.setCellValue(value);
			}

			style.setDataFormat(helper.createDataFormat().getFormat("DD/MM/yyyy"));
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			if (style != null) {
				cel.setCellStyle(style);
			}
			
		}

		private void addCellHtmlLink(HSSFRow row, int position, String text, String linkadress, HSSFCellStyle style) {
			CreationHelper helper = row.getSheet().getWorkbook().getCreationHelper();
			HSSFCell cel = row.createCell(position);

			if (text != null) {
				cel.setCellValue(new HSSFRichTextString(text.toString()));

				org.apache.poi.ss.usermodel.Hyperlink link = helper.createHyperlink(Hyperlink.LINK_URL);
				link.setAddress(linkadress);

				cel.setHyperlink(link);
			}
			if (style != null) {
				cel.setCellStyle(style);
			}
		}
		
		private Map<MultiKey, Spreiding> prepareData(List<Project> projecten) {
			// TODO Auto-generated method stub
			
			 Map<MultiKey, Spreiding>  returnObject = new HashMap<MultiKey, Spreiding>();
			 
			 for (Project project : projecten) {
				 
				 List<Spreiding> spreidingList = project.getSpreiding();
				 
				 for (Spreiding spreiding : spreidingList) {
					
					 MultiKey key = new MultiKey(project.getProjectId(),spreiding.getJaar());
				
					 returnObject.put(key, spreiding);
				}
				 
				
				
			}
			 
			 return returnObject; 
			 
		}


		public SessionFactory getSessionFactory() {
			return sessionFactory;
		}


		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}


		public SqlSession getSqlSession() {
			return sqlSession;
		}


		public void setSqlSession(SqlSession sqlSession) {
			this.sqlSession = sqlSession;
		}
		

}
