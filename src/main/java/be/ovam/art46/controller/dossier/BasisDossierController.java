package be.ovam.art46.controller.dossier;

import be.ovam.art46.service.dossier.DossierDTO;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.service.dossier.DossierJDService;
import be.ovam.art46.struts.actionform.DossierArt46Form;
import be.ovam.art46.struts.actionform.DossierBOAForm;
import be.ovam.art46.util.Application;
import be.ovam.util.mybatis.SqlSession;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

public class BasisDossierController {
    
    @Autowired
    protected DossierJDService dossierJDService;
    
    @Autowired
    protected SqlSession sqlSession;
    
    @Autowired
    protected BriefService briefService;

    protected DossierDTO putDossierInModelAndSession(Integer dossier_id, Model model, HttpSession session) throws Exception, IllegalAccessException, RuntimeException, InvocationTargetException {

        DossierDTO dossierDTO = sqlSession.selectOne("getDossierDTObyId", dossier_id);
        
        if (dossierDTO == null) {
            throw new RuntimeException("Er werd geen dossier gevonden voor id " + dossier_id);
            //+ ", of dossier_nr "  + request.getParameter("dossier_nr")) ;
        }

        model.addAttribute("dossier", dossierDTO);
        model.addAttribute("custom_title", "Dossier " + dossierDTO.getDossier_nr() + " (" + dossierDTO.getDossier_type() + ")");

        DossierDTO dossier_in_session = (DossierDTO) session.getAttribute("dossier");
        
        if (dossier_in_session != null && 
                dossier_in_session.getDossier_type() != null && 
                dossier_id.equals(dossier_in_session.getId()) ) {
            // geen actie
        } else {

            plaatsVersDossierInModelEnSessie(dossierDTO, model, session);
        }
        
        return dossierDTO;
    }

    protected void plaatsVersDossierInModelEnSessie(DossierDTO dossierDTO, Model model, HttpSession session) throws InvocationTargetException, IllegalAccessException {
        // nieuwe gegevens in sessie plaatsen
        session.removeAttribute("briefFilterParams");
        session.removeAttribute("dossierart46form");
        session.removeAttribute("dossier");
        session.removeAttribute("dossierboaform");
        
        session.setAttribute("briefFilterParams", new BriefFilterParamsDO());
        
        DossierArt46Form dossierart46form = new DossierArt46Form();
        BeanUtils.copyProperties(dossierart46form, dossierDTO);
        
        if (Application.INSTANCE.isUserInRole("adminArt46") || Application.INSTANCE.isUserInRole("adminIVS")) {
            if (!"B".equals(dossierart46form.getDossier_type())) {
                // afval of ander dossier
                dossierart46form.setDisabled(false);
            } else {
                // bodem dossier
                if (dossierart46form.getDossier_b() != null && dossierart46form.getDossier_b().length() > 0) {
                    // ivs dossier editeerbaar
                    dossierart46form.setDisabled(false);
                } else {
                    // nog geen ivs-dossier : alleen editeerbaar door admin
                    if (Application.INSTANCE.isUserInRole("adminArt46")) {
                        dossierart46form.setDisabled(false);
                    } else {
                        dossierart46form.setDisabled(true);
                    }
                }
            }
        } else {
            dossierart46form.setDisabled(true);        
        }
        
        session.setAttribute("dossier", dossierDTO);
        session.setAttribute("dossierart46form", dossierart46form);
        
        
        if (dossierDTO.getId() != null && "B".equals(dossierDTO.getDossier_type())) {
            DossierBOAForm dossierBOAForm = new DossierBOAForm();
            Map dossierSmeg = sqlSession.selectOne("be.ovam.art46.mappers.DossierMapper.getDossierSmeg", dossierDTO.getId());
            BeanUtils.copyProperties(dossierBOAForm, dossierSmeg);
            session.setAttribute("dossierboaform", dossierBOAForm);
        }
        
        model.addAttribute("dossier", dossierDTO);
        model.addAttribute("custom_title", "Dossier " + dossierDTO.getDossier_nr() + " (" + dossierDTO.getDossier_type() + ")");
        
    }

    protected List getOpenBestekLijst(Integer dossier_id) {
	    Map<String, Object> params= new HashMap<String, Object>();
	    params.put("id", dossier_id);
	    params.put("afgesloten_jn", "N");
	    	    
		return sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getBestekLijst", params);
    }
    
    
}
