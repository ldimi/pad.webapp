package be.ovam.art46.dao;

import be.ovam.art46.model.Raming;
import be.ovam.art46.util.DateFormatArt46;
import be.ovam.pad.model.Bestek;

import org.hibernate.criterion.Restrictions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BestekDAO extends BaseDAO {
	
    public Bestek getForBestekNr(String bestekNr) {
        return (Bestek) getSessionFactory().getCurrentSession().createCriteria(Bestek.class, "b").add(Restrictions.eq("b.bestek_nr",bestekNr)).uniqueResult();
    }
	
	public void saveBestekRaming(Integer bestekId, Integer ramingId) throws Exception {		
		List<String> sqls = new ArrayList<String>();
		List<List<Object[]>> params = new ArrayList<List<Object[]>>();
		sqls.add("delete from ART46.BESTEK_RAMING where bestek_id = ?");
		List<Object[]> paramDelete = new ArrayList<Object[]>();		
		paramDelete.add(new Integer[]  {bestekId});
		params.add(paramDelete);
		if (ramingId != 0) {
			sqls.add("insert into ART46.BESTEK_RAMING (bestek_id, raming_id) values (?,?)");
			List<Object[]> paramInsert = new ArrayList<Object[]>();
			paramInsert.add(new Integer[] {bestekId, ramingId});
			params.add(paramInsert);
		}
		executeUpdateAll(sqls, params);		
	}
	
	public List<Raming> getRamingByBestekId(Long bestekId) {
		return getHibernateTemplate().findByNamedQuery("raming.by.bestekId", bestekId);
	}
		
	public String generateBestekNr(boolean isRaamcontract) throws Exception {
		String result = null;
		String prefix_s = null;
		Connection conn = getConnection();
		try {
			if (isRaamcontract) {
				prefix_s = "RO";
			}
			else {				
				prefix_s = "BN";
			}		
			result = prefix_s + DateFormatArt46.getYear()+ DateFormatArt46.geMonth();		
			Statement stmtgetVolgnummer = conn.createStatement();
			ResultSet rsVolgnummer = stmtgetVolgnummer.executeQuery("select max(substr(bestek_nr, 7,2)) from ART46.BESTEK where substr(bestek_nr, 1,6) = '" + result + "'");
			if (rsVolgnummer.next()) {
				if (rsVolgnummer.getString(1) == null || "".equals(rsVolgnummer.getString(1))) {
					result += "01";
				}
				else {
					int volgnummer = Integer.parseInt(rsVolgnummer.getString(1));
					volgnummer++;
					if (volgnummer < 10) {
						result += "0" + volgnummer;
					}
					else {
						result += "" + volgnummer;
					}
				}
		 	}	
		}
		finally {
			conn.close();
		}
		return result;
	}	
	
    public Bestek get(Long bestekId) {
        return (Bestek) getObject(Bestek.class, bestekId);
    }

}
