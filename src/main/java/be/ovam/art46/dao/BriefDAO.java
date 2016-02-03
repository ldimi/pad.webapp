package be.ovam.art46.dao;

import be.ovam.pad.model.Brief;
import be.ovam.pad.model.BriefView;
import be.ovam.pad.model.Dossier;
import be.ovam.pad.model.Schuldvordering;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.struts.actionform.BriefForm;
import be.ovam.art46.struts.actionform.BriefZoekForm;
import be.ovam.art46.util.DateFormatArt46;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import be.ovam.util.mybatis.SqlSession;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.criteria.CriteriaBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Koen on 7/02/14.
 */
@Repository
public class BriefDAO extends GenericDAO<Brief> {
    @Autowired
    private SqlSession sqlSession;

    private final static Logger log = Logger.getLogger(BriefService.class);

    public BriefDAO() {
        super(Brief.class);
    }

    public List<Brief> getBrievenVoorInAard(Long bestekId, Integer aardId) {
        return sessionFactory.getCurrentSession().createCriteria(Brief.class).add(Restrictions.and(Restrictions.eq("bestek_id", bestekId), Restrictions.eq("in_aard_id", aardId))).list();
    }

    public void updateBriefInfo(Integer briefId, Integer briefTypeId, String dmsId, String dmsFileName, String dmsFolder) throws Exception {
        Brief brief = get(briefId);
        brief.setDms_filename(dmsFileName);
        brief.setDms_id(dmsId);
        brief.setDms_folder(dmsFolder);
        brief.setLtst_wzg_d(new Timestamp(System.currentTimeMillis()));
        if (briefTypeId != null && briefTypeId.intValue() != 0) {
            brief.setUit_type_id_vos(briefTypeId);
        }
        save(brief);
    }

    public List<Brief> getBrieven(Integer dossierId, Integer uitTypeIdVos, Integer bestekId, Date datumVan, Date datumTot) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dossierId", dossierId);
        params.put("uitTypeIdVos", uitTypeIdVos);
        params.put("bestekId", bestekId);
        params.put("inschrijfVan", datumVan);
        params.put("inschrijfTot", datumTot);
        return sqlSession.selectList("be.ovam.art46.mappers.BriefMapper.getBrievenFor", params);
    }

    public List getBriefCategorien(Integer dossier_id) throws Exception {
        return sqlSession.selectList("be.ovam.art46.mappers.BriefMapper.briefCategorieList", dossier_id);
    }

    public Integer getDienst(String doss_hdr_id) {
        return 1; // IVS
    }

    public Map getBriefCategorie(Integer briefCategorieId) throws Exception {
        return sqlSession.selectOne("be.ovam.art46.mappers.BriefMapper.getBriefCategorieById", briefCategorieId);
    }

    public Integer getVolgnummer(Integer dossier_id) throws Exception {
        Integer maxVolgnummer = (Integer) sqlSession.selectOne("be.ovam.art46.mappers.BriefMapper.getMaxVolgNrByDossierId", dossier_id);
        return maxVolgnummer + 1;
    }


    public void saveOrUpdate(BriefForm briefForm) throws Exception {
        if (briefForm.getBrief_nr() == null || briefForm.getBrief_nr().length() == 0) {
            briefForm.setBrief_nr(generateBriefNr(briefForm));
        }
        saveFromForm(briefForm, Brief.class);
    }

    @SuppressWarnings("rawtypes")
    public List getContacts(Integer adresId) throws Exception {
        return sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getAdresContacten", adresId);
    }

    private String generateBriefNr(BriefForm briefForm) throws Exception {
        return "DS/" + briefForm.getDossier_nr() + "/" + briefForm.getAuteur_id().trim() + "-" + DateFormatArt46.getYear() + "/" + getVolgnummer(briefForm);
    }

    private String getVolgnummer(BriefForm briefForm) throws Exception {
        String aard = null;
        Integer volgnummer = null;
        int jaar = 0;
        if (briefForm.getInschrijf_d() != null) {
            jaar = DateFormatArt46.getYear(briefForm.getInschrijf_d());
        } else {
            jaar = Calendar.getInstance().get(Calendar.YEAR);
        }
        if (briefForm.getIn_aard_id() != null && !"0".equals(briefForm.getIn_aard_id())) {
            aard = briefForm.getIn_aard_id();
        } else if (briefForm.getUit_aard_id() != null && !"0".equals(briefForm.getUit_aard_id())) {
            aard = briefForm.getUit_aard_id();
        } else {
            throw new Exception("Er is geen brief aard gespecifieerd.");
        }
        volgnummer = getVolgnummer(briefForm.getDossier_id());
        briefForm.setVolgnummer(volgnummer.toString());
        if ("5".equals(briefForm.getCategorie_id())) {
            // scan
            return "S" + volgnummer;
        }
        if ("1".equals(aard)) {
            // nota
            return "N" + volgnummer;
        }
        if ("9".equals(aard)) {
            // besluit
            return "B" + volgnummer;
        }
        if ("3".equals(aard)) {
            // fax
            return "F" + volgnummer;
        }
        return StringUtils.leftPad(volgnummer.toString(), 4, "0");
    }


    public String generateBriefNr(Brief doc, String dossier_id) {
        return "DS/" + dossier_id + "/" + doc.getAuteur_id().trim() + "-" + DateFormatArt46.getYear() + "/" + StringUtils.leftPad("" + doc.getVolgnummer(), 4, "0");
    }

    public Brief getBriefForQr(Long code) {
        return (Brief) sessionFactory.getCurrentSession().createCriteria(Brief.class, "b").add(Restrictions.eq("b.qr_code", code)).uniqueResult();
    }

    public Brief getBriefForDmsId(String dms_id) {
        return (Brief) sessionFactory.getCurrentSession().createCriteria(Brief.class, "b").add(Restrictions.eq("b.dms_id", dms_id)).uniqueResult();
    }

}

