package be.ovam.art46.service.dossier;

import be.ovam.art46.dao.DossierDAO;
import be.ovam.art46.util.DateFormatArt46;
import be.ovam.pad.model.Dossier;
import be.ovam.util.mybatis.SqlSession;

import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DossierService {
	
    @Autowired
    protected SqlSession sqlSession;

    @Autowired
    protected DossierDAO dossierDAO;
    
    
	public Dossier getDossier(Integer id) {
		return (Dossier) dossierDAO.getObject(Dossier.class, id);
	}
    
    public Integer saveDossier(DossierCompleetDTO dossierCompleet) throws Exception {
        Integer dossier_id = saveDossier(dossierCompleet.getDossier());
        
        sqlSession.saveInTable("art46", "dossier_verontreinig_activiteit", dossierCompleet.getActiviteit_lijst());
        sqlSession.saveInTable("art46", "dossier_instrument", dossierCompleet.getInstrument_lijst());

        return dossier_id;
    }
    
	private Integer saveDossier(DossierDTO dossier) throws Exception {
        Map dossierMap;
        dossierMap = new BeanMap(dossier);
        
        return saveDossier(dossierMap);
	}
    
	public Integer saveDossier(Map dossier) throws Exception {
        
        Integer id = (Integer) dossier.get("id");
        String dossier_type = (String) dossier.get("dossier_type");
        String dossier_nr = (String) dossier.get("dossier_nr");
        String dossier_b = (String) dossier.get("dossier_b");
        
        if (dossier_type == null) {
            throw new IllegalArgumentException("dossier_type is niet gekend.");
        }
        
        if (id == null) {
            // INSERT
            
            if ("X".equals(dossier_type)) {			
                dossier.put("dossier_nr", generateDossierNrAndere());
            } else if ("A".equals(dossier_type)) {
                if (dossier_b != null && dossier_b.length() > 0) {
                    dossier.put("dossier_nr", generateDossierNr());
                } else {
                    Integer maxId = (Integer) sqlSession.selectOne("getMaxIdDossier");
                    maxId = maxId + 1;
                    dossier.put("dossier_nr", "_a_" + maxId);
                }
            } else {
                // bodem dossiers ontstaan via trigger op smeg.dossier.
                throw new RuntimeException("Bodem dossiers kunnen niet via pad in de databank aangemaakt worden.");
            }
            
            sqlSession.insertInTable("art46", "dossier", dossier);
        } else {
            // UPDATE
            if (dossier_nr == null || dossier_nr.length() == 0) {
                throw new IllegalArgumentException("dossier_nr mag niet leeg zijn voor bestaand dossier.");
            }
            
            if (dossier.get("afsluit_d") != null && !dossierDAO.alleBestekkenAfgesloten(id)) {
                throw new RuntimeException("Alle bestekken moeten afgesloten zijn voordat het dossier kan worden afgesloten.");
            }
            
            if (dossier_nr.startsWith("_")) {
                if ( ("A".equals(dossier_type) && dossier_nr.startsWith("_a_")) ||
                        ("B".equals(dossier_type) && dossier_nr.startsWith("_"))    ){

                    if (dossier_b != null && dossier_b.length() > 0) {
                        dossier.put("dossier_nr", generateDossierNr());
                    }
                } else {
                    throw new RuntimeException("Combinate (dossier_type, dossier_b) is ongeldig.");
                }
            }
            
            sqlSession.updateInTable("art46", "dossier", dossier);
        }
        
        // opnieuw ophalen om de via triggers aangepaste velden mee te hebben.
        return (Integer) dossier.get("id");
	}
    
    
    
	public String generateDossierNr() throws Exception {
        String volgNr = ((Integer) sqlSession.selectOne("getNieuwVolgNrDossier")).toString();
        volgNr = StringUtils.leftPad(volgNr, 3, "0");
		String id = DateFormatArt46.getYear() + volgNr; 
		id += "-" + calculateMod(id);
		return id;
	}
	
	private String generateDossierNrAndere() throws Exception {
        String id = ((Integer) sqlSession.selectOne("getNieuwVolgNrDossierAnder")).toString();
		id = StringUtils.leftPad(id ,4, "0");			
		return "A" + id + "-" + calculateMod(id);			
	}

    private int calculateMod(String id) {
		int mod = 0;
		if (id != null && id.length()> 0) {
			for (int t=0; t<id.length(); t++) {
				mod += Integer.parseInt(String.valueOf(id.charAt(t)));
			}
			if (mod > 9) {
				mod = mod % 10;
			}
		}		
		return mod;
	}

	
	public List<Dossier> getIvsDossiers(String doss_hdr_id) {
		return dossierDAO.getIvsDossiers(doss_hdr_id);
	}
	
    public Dossier get(Integer id) {
        return dossierDAO.get(Dossier.class, id);
    }

    public List<Dossier> getIvsDossiersTypeNietAnders(String doss_hdr_id) {
        return dossierDAO.getIvsDossiersTypeNietAnders(doss_hdr_id);
    }
}
