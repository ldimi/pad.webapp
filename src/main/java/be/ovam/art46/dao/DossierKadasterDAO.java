package be.ovam.art46.dao;

import be.ovam.art46.struts.actionform.Art46BaseForm;
import be.ovam.art46.struts.actionform.DossierBOAForm;
import be.ovam.art46.util.DateFormatArt46;

import org.apache.commons.beanutils.DynaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;


public class DossierKadasterDAO extends BaseDAO {
	
	private static String sqlInsertVG = "insert into ART46.VOOR_GOEDK (DOSSIER_ID, KADASTER_ID, ARTIKEL_ID) values (?,?,?)";
	private static String sqlDeleteVG = "delete from ART46.VOOR_GOEDK where DOSSIER_ID = ? and KADASTER_ID = ?";
	private static String sqlDeleteVGDossier = "delete from ART46.VOOR_GOEDK where DOSSIER_ID = ?";

	private static String sqlCloseDosser = "update ART46.DOSSIER set ART46_GESLOTEN_S = '1', ART46_GESLOTEN_D = ? where ID = ?";
	private static String sqlUpdateVG = "update ART46.VOOR_GOEDK set GOEDKEURING_S = ? where DOSSIER_ID = ? and KADASTER_ID = ?";
	private static String sqlUpdateVGmetArtikelid = "update ART46.VOOR_GOEDK set GOEDKEURING_S = ?, ARTIKEL_ID = 2 where DOSSIER_ID = ? and KADASTER_ID = ?";
	
	private static String sqlInsertNG = "insert into ART46.NA_GOEDK (DOSSIER_ID, KADASTER_ID, ARTIKEL_ID, LIJST_ID, GOEDKEURING_D, COMMENTAAR) select DOSSIER_ID, KADASTER_ID, ARTIKEL_ID, CAST(? AS INTEGER) LIJST_ID, CAST(? AS DATE) GOEDKEURING_D, COMMENTAAR from ART46.VOOR_GOEDK where DOSSIER_ID = ? and KADASTER_ID = ? and ARTIKEL_ID = ?";

	private static String sqlDeleteNG = "delete from ART46.NA_GOEDK where DOSSIER_ID = ? and KADASTER_ID = ? and LIJST_ID = ?";
	private static String sqlUpdateNG = "update ART46.NA_GOEDK set PUBLICATIE_D = ? where DOSSIER_ID = ? and KADASTER_ID = ? and LIJST_ID = ?";
	private static String sqlInsertDossierKadasterTypeHist = "insert into ART46.DOS_KAD_TYPE_HIST  (select cast(? as integer), adkt.* from ART46.DOS_KAD_TYPE adkt where adkt.dossier_id = ? and adkt.kadaster_id = ?)";	
	private static String sqlDeleteDossierKadasterTypeHist = "delete from ART46.DOS_KAD_TYPE_HIST where lijst_id = ? and dossier_id = ? and kadaster_id = ?";
	private static String sqlUpdateNGDetails = "update ART46.NA_GOEDK set COMMENTAAR = ? where DOSSIER_ID = ? and KADASTER_ID = ? and ARTIKEL_ID = ? and GOEDKEURING_D = ?";
	private static String slqCheckForClose = "select kadaster_id from ART46.VOOR_GOEDK where DOSSIER_ID = ? and GOEDKEURING_S = '1'";
	private static String sqlDeleteDossierKadasterType = "delete from ART46.DOS_KAD_TYPE where DOSSIER_ID = ?";
	private static String sqlDeleteKadasterDossierType = "delete from ART46.DOS_KAD_TYPE where KADASTER_ID = ?";	
	private static String sqlInsertDossierKadasterType = "insert into ART46.DOS_KAD_TYPE (DOSSIER_ID, KADASTER_ID, ONSCHULDIGE_EIG_S, INGEBREKE_STEL_S) values (?,?,?,?) ";
	private static String sqlInsertKadasterDossierType = "insert into ART46.DOS_KAD_TYPE (KADASTER_ID, DOSSIER_ID, ONSCHULDIGE_EIG_S, INGEBREKE_STEL_S) values (?,?,?,?) ";
	private static String sqlSelectNGOld = "select * from ART46.NA_GOEDK where DOSSIER_ID=? and KADASTER_ID=? and LIJST_ID=?";	
	private static String sqlCheckBBO= "select rd.dossier_id from art46.v_dossier dos inner join SMEG.RS_DOSSIER_VIEW rd on (rd.dossier_id = dos.dossier_id_boa) where rd.conform_bbo_d is not null and dos.id = ?";
	private static String sqlCheckBSP= "select rd.dossier_id from art46.v_dossier dos inner join SMEG.RS_DOSSIER_VIEW rd on (rd.dossier_id = dos.dossier_id_boa) where rd.conform_bsp_d is not null and dos.id = ?";

		
	
    

    public void updateDossierKadasterNG(Art46BaseForm form) throws Exception {        
        if(form.getDossier_id() != null && form.getKadaster_id() != null) {
            executeUpdate(sqlUpdateNGDetails, new Object[] {
            		 form.getCommentaar(),
            		 Integer.parseInt(form.getDossier_id()),
            		 form.getKadaster_id(),
            		 Integer.parseInt(form.getArtikelid()),
            		 form.getGoedkeuring_d()});
        }
    }
    
    private boolean checkDate(String artikelid, String dossier_id, String kadaster_id) throws Exception {
        if ("3".equals(artikelid)) {
            return findFirstByDynaBeans(sqlCheckBBO, new Integer[] {Integer.parseInt(dossier_id)}) != null;        	    
        } 
        else if ("4".equals(artikelid)) {
        	return findFirstByDynaBeans(sqlCheckBSP, new Integer[] {Integer.parseInt(dossier_id)}) != null; 
        }        
        return true;
    }

    public List<String> insertDossierKadasterVG(String dossierkadasters[], String artikelid) throws Exception  {
        String dosKad[] = null;
        List<String> dosskadNietConform = new ArrayList<String>();        
        List<Object[]> params = new ArrayList<Object[]>();       
        if(dossierkadasters != null) {          
            for(int t = 0; t < dossierkadasters.length; t++) {
                dosKad = extractDossierKadaster(dossierkadasters[t]);
                if (checkDate(artikelid, dosKad[0], dosKad[1])) {
                    params.add(new Object[] {
                    		 Integer.parseInt( dosKad[0]),
                    		 dosKad[1],
                    		 Integer.parseInt(artikelid)});
                } else {
                    dosskadNietConform.add(dossierkadasters[t]);                        
                }
            }
            executeUpdateAll(sqlInsertVG, params);
        }       
        return dosskadNietConform;
    }

    public List<String> insertDossierKadasterNG(String dossierkadasters[], String artikelid, String goedkeuring_d, String lijst_id) throws Exception {    	
    	// Lijst met alle reeds bestaande dossierkadasters met lijst_id
		List<String> dosKadOld = new ArrayList<String>();	
        String dosKad[] = (String[])null;
        List<String> sql = new ArrayList<String>();
        sql.add(sqlInsertNG);
        sql.add(sqlInsertDossierKadasterTypeHist);
        List<List<Object[]>> params = new ArrayList<List<Object[]>>();
        List<Object[]> paramsInsert = new ArrayList<Object[]>();
        List<Object[]> paramsInsertHist = new ArrayList<Object[]>();
        params.add(paramsInsert);
        params.add(paramsInsertHist);        
        if(dossierkadasters != null) {            
            for(int t = 0; t < dossierkadasters.length; t++) {
				dosKad = extractDossierKadaster(dossierkadasters[t]);
				if (findFirstByDynaBeans(sqlSelectNGOld, new Object[] {
						Integer.parseInt(dosKad[0]),
						dosKad[1],
						Integer.parseInt(lijst_id)
						}) != null) {
					dosKadOld.add(dossierkadasters[t]);								
				}					
				else {   
					paramsInsert.add(new Object[] {
							Integer.parseInt(lijst_id),
							DateFormatArt46.getSQLDate(goedkeuring_d),
							Integer.parseInt(dosKad[0]),
							dosKad[1],
							Integer.parseInt(artikelid)
					});
					paramsInsertHist.add(new Object[] {
							Integer.parseInt(lijst_id),
							Integer.parseInt(dosKad[0]),
							dosKad[1]
					});
				}
            }
            executeUpdateAll(sql, params);        
        }      
        return dosKadOld;        
    }
    
    
	public void closeDossier(Integer dossier_id, String art46_afgesloten_d) throws Exception {		
		List<String> sql = new ArrayList<String>();
        sql.add(sqlDeleteVGDossier);
        sql.add(sqlCloseDosser);
        List<List<Object[]>> params = new ArrayList<List<Object[]>>();
        List<Object[]> paramsDelete = new ArrayList<Object[]>();
        List<Object[]> paramsClose = new ArrayList<Object[]>();
        params.add(paramsDelete);
        params.add(paramsClose);   
		if(dossier_id != null) {
			paramsDelete.add(new Object[] {dossier_id.intValue()});
			paramsClose.add(new Object[] {
					art46_afgesloten_d,
					dossier_id.intValue()});
			executeUpdateAll(sql, params);
		}
	}

    

    public void deleteDossierKadasterVG(String[] dossierkadasters) throws Exception {
        String dosKad[] = (String[]) null;
        List<Object[]> params = new ArrayList<Object[]>();
        if(dossierkadasters != null) {
	        for(int t = 0; t < dossierkadasters.length; t++) {                	
	            dosKad = extractDossierKadaster(dossierkadasters[t]);
	            System.out.println("dosKad[0]='" + dosKad[0] + "'");
	            System.out.println("dosKad[1]='" + dosKad[1] + "'");
	            params.add(new Object[] {
	            		Integer.parseInt(dosKad[0]),
	            		dosKad[1]
	            });
	        }     
	        System.out.print("Calling executeUpdateAll with: " + sqlDeleteVG);
	        executeUpdateAll(sqlDeleteVG, params);
	        System.out.println("Finished deleteDossierKadasterVG.");
        }
    }

    public void deleteDossierKadasterNG(String dossierkadasters[], String lijst_id) throws Exception {
        String dosKad[] = (String[]) null;
        List<String> sql = new ArrayList<String>();
        sql.add(sqlDeleteNG);
        sql.add(sqlDeleteDossierKadasterTypeHist);
        List<List<Object[]>> params = new ArrayList<List<Object[]>>();
        List<Object[]> paramsDelete = new ArrayList<Object[]>();
        List<Object[]> paramsDeleteHist = new ArrayList<Object[]>();
        params.add(paramsDelete);
        params.add(paramsDeleteHist);
        if(dossierkadasters != null) {
        	for(int t = 0; t < dossierkadasters.length; t++) {
        		dosKad = extractDossierKadaster(dossierkadasters[t]);
        		paramsDelete.add(new Object[] {
        				Integer.parseInt(dosKad[0]),
        				dosKad[1],
        				Integer.parseInt(lijst_id)
        		});
                paramsDeleteHist.add(new Object[] {
                		 Integer.parseInt(lijst_id),
                		 Integer.parseInt(dosKad[0]),
                		 dosKad[1]
                });
        	}
        	executeUpdateAll(sql, params);
        }
    }

    public void updateDossierKadasterVG(String dossierkadasters[], String goedkeuring_s)
        throws Exception
    {
    	String dosKad[] = null;
	    List<Object[]> params = new ArrayList<Object[]>();
	    if(dossierkadasters != null) {
            for(int t = 0; t < dossierkadasters.length; t++) {
                dosKad = extractDossierKadaster(dossierkadasters[t]);
                params.add(new Object[] {
                		goedkeuring_s,
                		Integer.parseInt(dosKad[0]),
                		dosKad[1]});
            }	       
	    	executeUpdateAll(sqlUpdateVG, params);
    	}    	
    }

    public void updateDossierKadasterNG(String dossierkadasters[], String publicatie_d, String artikelid, String lijst_id)
        throws Exception
    {
        String dosKad[] = (String[])null;
        List<String> sql = new ArrayList<String>();
        sql.add(sqlUpdateNG);
        if ("1".equals(artikelid)) {
        	sql.add(sqlUpdateVGmetArtikelid);			
        }
        else {
        	sql.add(sqlUpdateVG);        	
        }       
        List<List<Object[]>> params = new ArrayList<List<Object[]>>();
        List<Object[]> paramsNG = new ArrayList<Object[]>();
        List<Object[]> paramsVG = new ArrayList<Object[]>();
        params.add(paramsNG);
        params.add(paramsVG);
        if(dossierkadasters != null) {
            for(int t = 0; t < dossierkadasters.length; t++) {           	
                dosKad = extractDossierKadaster(dossierkadasters[t]);
                paramsNG.add(new Object[] {
                		DateFormatArt46.getSQLDate(publicatie_d),
                		Integer.parseInt(dosKad[0]),
                		dosKad[1],
                		Integer.parseInt(lijst_id)
                });
                paramsVG.add(new Object[] {
                		 "0",
                		 Integer.parseInt(dosKad[0]),
                		 dosKad[1],
                		 
                });
            }
            executeUpdateAll(sql, params);
        }
    }


	public String extractDistinctDossiers(String[] dossierkadaster)  {		
		if (dossierkadaster != null && dossierkadaster.length > 0) {
			String temp = "";
			for (int t=0; t<dossierkadaster.length; t++) {
				temp += "(" + extractDossierKadaster(dossierkadaster[t])[0] + ",'" + extractDossierKadaster(dossierkadaster[t])[1] + "'),";
			}				
			return temp.substring(0, temp.length()-1);	
		}
		else {
			return "";
		}		
	}

    private String[] extractDossierKadaster(String dossierkadaster)
    {
        String doskad[] = new String[2];
        int dosKadSeparator = dossierkadaster.indexOf("-");
        doskad[0] = dossierkadaster.substring(0, dosKadSeparator);
        doskad[1] = dossierkadaster.substring(dosKadSeparator + 1);
        System.out.println("Dossierid: " + doskad[0] + " kadaster_id: '" + doskad[1] + "'");
        return doskad;
    }    
    
    /**
     * Checks of voor alle kadasterdossiers van dit dossier goedkeuring_s = false.
     * 
     * @param form form met dossier_id waarvoor de controle gebeurd.
     * @return true indien alle goedkeuring_s = false 
     */
    
	public boolean dossierOkVoorSluiten(DossierBOAForm form) throws Exception {
		if(form.getDossier_id_boa() != null) {
			return findFirstByDynaBeans(slqCheckForClose, new Integer[] { Integer.valueOf(form.getDossier_id_boa())}) != null;
		}
		return false;	
	}
	
	public void setDossierKadasterType(String dossier_id, String[] kadasteridsOE, String[] kadasteridsIG) throws Exception {
		String kadasterid = null;
		//Get all kadasterids in OE and IG
		if (kadasteridsOE == null) {
			kadasteridsOE = new String[0];
		}
		if (kadasteridsIG == null) {
			kadasteridsIG = new String[0];
		}		
		Set allKadasterIds = uniteToSet(Arrays.asList(kadasteridsOE), Arrays.asList(kadasteridsIG));			
		List<String> sql = new ArrayList<String>();
        sql.add(sqlDeleteDossierKadasterType);
        sql.add(sqlInsertDossierKadasterType);
        List<List<Object[]>> params = new ArrayList<List<Object[]>>();
        List<Object[]> paramsDelete = new ArrayList<Object[]>();
        List<Object[]> paramsInsert = new ArrayList<Object[]>();
        params.add(paramsDelete);
        params.add(paramsInsert);		
		if(dossier_id != null) {								
			paramsDelete.add(new Integer[] {Integer.valueOf(dossier_id)});	
			Iterator<String> iter = allKadasterIds.iterator();
			while (iter.hasNext()) {
				kadasterid = (String) iter.next();
				paramsInsert.add(new Object[] {
						dossier_id,
						kadasterid,
						Arrays.asList(kadasteridsOE).contains(kadasterid) ? "1" : "0",
						Arrays.asList(kadasteridsIG).contains(kadasterid) ? "1" : "0"});					
			}		
			executeUpdateAll(sql, params);
		}			
	}
	
	public void setKadasterDossierType(String kadaster_id, String[] dossieridsOE, String[] dossieridsIG) throws Exception {
			String dossierid = null;
			//Get all kadasterids in OE and IG
			if (dossieridsOE == null) {
				dossieridsOE = new String[0];
			}
			if (dossieridsIG == null) {
				dossieridsIG = new String[0];
			}		
			Set allKadasterIds = uniteToSet(Arrays.asList(dossieridsOE), Arrays.asList(dossieridsIG));			
			List<String> sql = new ArrayList<String>();
	        sql.add(sqlDeleteKadasterDossierType);
	        sql.add(sqlInsertKadasterDossierType);
	        List<List<Object[]>> params = new ArrayList<List<Object[]>>();
	        List<Object[]> paramsDelete = new ArrayList<Object[]>();
	        List<Object[]> paramsInsert = new ArrayList<Object[]>();
	        params.add(paramsDelete);
	        params.add(paramsInsert);			
			if(kadaster_id != null) {
				paramsDelete.add(new Object[] {kadaster_id});
				Iterator iter = allKadasterIds.iterator();
				while (iter.hasNext()) {
					paramsInsert.add(new Object[] {
							kadaster_id,
							Integer.valueOf(dossierid.trim()),
							Arrays.asList(dossieridsOE).contains(dossierid) ? "1" : "0",
							Arrays.asList(dossieridsIG).contains(dossierid) ? "1" : "0"});					
				}			
				executeUpdateAll(sql, params);					
			}	
		}
	
	
	/**
	 * The objects in col1 and col2 are united in one set with unique objects.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    private Set uniteToSet(Collection col1, Collection col2 ) {
		HashSet set = new HashSet();
		set.addAll(col1);
		System.out.println("Added col1 to set: " + col1);
		set.addAll(col2);
		System.out.println("Added col2 to set: " + col2);
		System.out.println("Set: " + set);
		return set;
	}

}
