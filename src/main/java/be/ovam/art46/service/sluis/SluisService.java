package be.ovam.art46.service.sluis;

import be.ovam.pad.model.dossieroverdracht.DossierOverdrachtDTO;
import be.ovam.art46.service.dossier.DossierService;
import be.ovam.art46.util.Application;
import be.ovam.pad.service.BasisDossierOverdrachtService;
import be.ovam.util.mybatis.SqlSession;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
@Transactional
public class SluisService {

    @Autowired
    @Qualifier("sqlSession")
    SqlSession sqlSession;

    @Autowired
    BasisDossierOverdrachtService basisDossierOverdrachtService;
    
    @Autowired
    private DossierService dossierService;

    public Map getOverdrachtenMap (Map params) {
        return basisDossierOverdrachtService.getOverdrachtenMap(params);
    }
    

    
    public void save(DossierOverdrachtDTO dossierOverdrachtDTO) throws Exception  {
        
        DossierOverdrachtDTO.OverdrachtDTO overdracht = dossierOverdrachtDTO.getOverdracht();
        
        if ("C".equals(overdracht.getStatus_crud()) ) {
            insert(overdracht);
        } else if ("U".equals(overdracht.getStatus_crud()) ) {
            update(overdracht);
        } else if ("R".equals(overdracht.getStatus_crud()) ) {
            // geen actie
        } else {
            throw new IllegalStateException("Ongeldige status_crud: " + overdracht.getStatus_crud() );
        }

        sqlSession.saveInTable("art46", "dossier_parameter", dossierOverdrachtDTO.getParameter_lijst());
        sqlSession.saveInTable("art46", "dossier_stofgroep", dossierOverdrachtDTO.getStofgroep_lijst());
        sqlSession.saveInTable("art46", "dossier_verontreinig_activiteit", dossierOverdrachtDTO.getActiviteit_lijst());
        sqlSession.saveInTable("art46", "dossier_instrument", dossierOverdrachtDTO.getInstrument_lijst());
        
    }
    
    public void insert(DossierOverdrachtDTO.OverdrachtDTO overdracht) throws Exception  {
        overdracht.setVersie_nr(1);
        overdracht.setStatus("registrati");
        overdracht.setWijzig_user(Application.INSTANCE.getUser_id());
        
        if ("B".equals(overdracht.getDossier_type())) {
            updateIvsDossier(overdracht);
        } else if ("A".equals(overdracht.getDossier_type())) {
            insert_ivs_afval_dossier(overdracht);
        } else {
            throw new RuntimeException("Overdracht met ongeldig dossier type : " + overdracht.getDossier_type());
        }
        
        sqlSession.insertInTable("art46", "dossier_overdracht", overdracht);
    }

    public void update(DossierOverdrachtDTO.OverdrachtDTO overdracht) throws Exception {
        
        overdracht.setVersie_nr(overdracht.getVersie_nr() + 1);
        overdracht.setWijzig_user(Application.INSTANCE.getUser_id());
        
        updateIvsDossier(overdracht);
        sqlSession.updateInTable("art46", "dossier_overdracht", overdracht);
        
    }

    
    private void  updateIvsDossier(DossierOverdrachtDTO.OverdrachtDTO overdracht) throws Exception {
        if (overdracht.getIvs_dirty() || "naar_ivs".equals(overdracht.getStatus()) ) {
            Map<String, Object> dos = buildDossierMap(overdracht);
            dossierService.saveDossier(dos); 
        }
    }

    private void insert_ivs_afval_dossier(DossierOverdrachtDTO.OverdrachtDTO overdracht) throws Exception {
        
        if (!"A".equals(overdracht.getDossier_type()) ||
                overdracht.getDossier_id() != null ||
                overdracht.getDossier_nr() != null ||
                !"registrati".equals(overdracht.getStatus())) {
            throw new IllegalArgumentException();
        }
        
        Map<String, Object> dos = buildDossierMap(overdracht);
        Integer dos_id = dossierService.saveDossier(dos);     
    
        overdracht.setDossier_id(dos_id);
    }

    
    private Map<String, Object> buildDossierMap(DossierOverdrachtDTO.OverdrachtDTO overdracht) throws RuntimeException {
        
        BeanMap doso = new BeanMap(overdracht);
        Map<String, Object> dos = new HashMap(doso);
        
        if ("naar_ivs".equals(overdracht.getStatus())) {
            String dossier_b = overdracht.getDossier_b();
            if (dossier_b == null || dossier_b.length() == 0) {
                throw new RuntimeException("Dossier_b moet ingevuld zijn om een ivs dossier aan te maken.");
            }
        } else {
            // indien geen ivs dossier moet dossier_b leeg gemaakt worden, anders wordt ivs-nummer aangemaakt
            if (overdracht.getDossier_nr() == null || overdracht.getDossier_nr().startsWith("_")) {
                dos.remove("dossier_b");
            }
        }
        
        dos.remove("commentaar"); //commentaar is een veld van overdracht.
        
        return dos;     
    }

    public void maakZip (Integer dossier_id) {
        Map dossier_zip = new HashMap();
        dossier_zip.put("dossier_id", dossier_id);
        dossier_zip.put("dossier_type", "B");
        dossier_zip.put("zip_d", new Date());
        
        sqlSession.deleteInTable("art46", "dossier_zip", dossier_zip);
        sqlSession.insertInTable("art46", "dossier_zip", dossier_zip);
    }
    
}
