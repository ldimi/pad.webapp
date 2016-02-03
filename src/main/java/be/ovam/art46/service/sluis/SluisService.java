package be.ovam.art46.service.sluis;

import be.ovam.art46.controller.dossier.DossierDO;
import be.ovam.pad.model.dossieroverdracht.DossierOverdrachtDto;
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
    

    
    public void save(DossierOverdrachtDto dossierOverdrachtDto) throws Exception  {
        
        DossierOverdrachtDto.OverdrachtDto overdracht = dossierOverdrachtDto.getOverdracht();
        
        if ("C".equals(overdracht.getStatus_crud()) ) {
            insert(overdracht);
        } else if ("U".equals(overdracht.getStatus_crud()) ) {
            update(overdracht);
        } else if ("R".equals(overdracht.getStatus_crud()) ) {
            // geen actie
        } else {
            throw new IllegalStateException("Ongeldige status_crud: " + overdracht.getStatus_crud() );
        }

        sqlSession.saveInTable("art46", "screening_parameter", dossierOverdrachtDto.getParameter_lijst());
        sqlSession.saveInTable("art46", "screening_stofgroep", dossierOverdrachtDto.getStofgroep_lijst());
        sqlSession.saveInTable("art46", "dossier_verontreinig_activiteit", dossierOverdrachtDto.getActiviteit_lijst());
        sqlSession.saveInTable("art46", "dossier_instrument", dossierOverdrachtDto.getInstrument_lijst());
        
    }
    
    public void insert(DossierOverdrachtDto.OverdrachtDto overdracht) throws Exception  {
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

    public void update(DossierOverdrachtDto.OverdrachtDto overdracht) throws Exception {
        
        overdracht.setVersie_nr(overdracht.getVersie_nr() + 1);
        overdracht.setWijzig_user(Application.INSTANCE.getUser_id());
        
        updateIvsDossier(overdracht);
        sqlSession.updateInTable("art46", "dossier_overdracht", overdracht);
        
    }

    
    private void  updateIvsDossier(DossierOverdrachtDto.OverdrachtDto overdracht) throws Exception {
        Map<String, Object> dos = buildDossierMap(overdracht);
        dossierService.saveDossier(dos);     
    }

    private void insert_ivs_afval_dossier(DossierOverdrachtDto.OverdrachtDto overdracht) throws Exception {
        
        if (!"A".equals(overdracht.getDossier_type()) ||
                overdracht.getDossier_id() != null ||
                overdracht.getDossier_nr() != null ||
                !"registrati".equals(overdracht.getStatus())) {
            throw new IllegalArgumentException();
        }
        
        Map<String, Object> dos = buildDossierMap(overdracht);
        DossierDO nieuwDos = dossierService.saveDossier(dos);     
    
        overdracht.setDossier_id(nieuwDos.getId());
    }

    
    private Map<String, Object> buildDossierMap(DossierOverdrachtDto.OverdrachtDto overdracht) throws RuntimeException {
        Map<String, Object> dos = new HashMap<String, Object>();
        
        dos.put("id", overdracht.getDossier_id()); // null ingeval van insert
        
        if ("naar_ivs".equals(overdracht.getStatus())) {
            String dossier_b = overdracht.getDossier_b();
            if (dossier_b == null || dossier_b.length() == 0) {
                throw new RuntimeException("Dossier_b moet ingevuld zijn om een ivs dossier aan te maken.");
            }
            dos.put("dossier_b", overdracht.getDossier_b());
            
        }
        dos.put("dossier_id", overdracht.getDossier_nr()); // null ingeval van insert
        dos.put("dossier_type", overdracht.getDossier_type());
        dos.put("nis_id", overdracht.getNis_id());
        dos.put("doss_hdr_id", overdracht.getDoss_hdr_id());
        dos.put("rechtsgrond_code", overdracht.getRechtsgrond_code());
        dos.put("dossier_fase_id", overdracht.getDossier_fase_id());
        dos.put("programma_code", overdracht.getProgramma_code());
        dos.put("doelgroep_type_id", overdracht.getDoelgroep_type_id());
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
