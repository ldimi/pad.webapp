package be.ovam.art46.dao;

import be.ovam.art46.struts.actionform.SAPFactuurDossierForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SAPDAO extends BaseDAO {
    
    private static Logger logger = LoggerFactory.getLogger(SAPDAO.class);
	
	public void addFactuurDossier(SAPFactuurDossierForm form) throws Exception {		
		List<String> sql = new ArrayList<String>();
		List<List<Object[]>> params = new ArrayList<List<Object[]>>();
		List<Object[]> paramsDelete = new ArrayList<Object[]>();
		List<Object[]> paramsInsert = new ArrayList<Object[]>();
		params.add(paramsDelete);
		params.add(paramsInsert);
		sql.add("delete from ART46.DOSSIER_SAP_PROJECT_FACTUUR where project_id = ? and factuur_id = ?");
		sql.add("insert into ART46.DOSSIER_SAP_PROJECT_FACTUUR values (?,?,?)");
		String[] values = null;
		for (int i=0; i<form.getProject_factuur_dossier_id().length; i++) {
			values = form.getProject_factuur_dossier_id()[i].split("_");
			paramsDelete.add(new Object[] {values[0], values[1]});
			if (!"0".equals(values[2])) {
				paramsInsert.add(new Object[] {values[0], values[1], values[2]});					
			}
		}
		executeUpdateAll(sql, params);
	}
	
		
}
