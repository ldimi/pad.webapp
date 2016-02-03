package be.ovam.art46.service.schuldvordering;

import be.ovam.art46.dao.AanvraagSchuldvorderingDAO;
import be.ovam.art46.util.Application;
import be.ovam.pad.model.AsvDO;
import be.ovam.pad.model.AsvRegelDO;
import be.ovam.pad.model.SchuldvorderingStatusEnum;
import be.ovam.pad.model.SchuldvorderingStatusHistoryDO;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AsvServiceImpl implements AsvService {

    @Autowired
    AanvraagSchuldvorderingDAO aanvraagSchuldvorderingDAO;

    @Autowired
    SqlSession sqlSession;

    public AsvDO getAsv(Long aanvr_schuldvordering_id) {
        AsvDO asv = sqlSession.selectOne("be.ovam.pad.common.mappers.AsvMapper.getAsvDO", aanvr_schuldvordering_id);
        List<AsvRegelDO> regels = sqlSession.selectList("be.ovam.pad.common.mappers.AsvMapper.getAsvRegels", aanvr_schuldvordering_id);
        prepareRegels(regels);
        asv.setAsvRegels(regels);
        return asv;
    }

    private void prepareRegels(List<AsvRegelDO> regels) {
        Collections.sort(regels);
        int i = 1;
        for (AsvRegelDO regel : regels) {
            if (regel.getOfferte_regel_id() == null) {
                regel.setPostnr("M" + i);
                i++;
            }
            regel.initOriginalCopy();
        }
    }


    public Long save(String action, AsvDO asv, String gebruiker) throws Exception {
        SchuldvorderingStatusHistoryDO sshdo = null;

        if (!SchuldvorderingStatusEnum.INGEDIEND.getValue().equals(asv.getStatus())) {
            throw new RuntimeException("Alleen aanvraagschuldvorderingen in status INGEDIEND kunnen in dit scherm aangepast worden.");
        }

        asv.calcAndSetBedragen();

        if ("Accepteren".equals(action)) {
            // geen bijkomende actie,
            //  De dossierhouder zal naar schuldvorderingvordering scherm geleid worden 
            //  om daar in SAP aan te maken.
        } else if ("Afkeuren".equals(action)) {
            asv.setAfgekeurd_jn("J");
            asv.setGoedkeuring_bedrag(null);
            asv.setAcceptatie_d(new Date());
            asv.setStatus(SchuldvorderingStatusEnum.BEOORDEELD.getValue());

            sshdo = new SchuldvorderingStatusHistoryDO();
            sshdo.setDossierhouder_id(Application.INSTANCE.getUser_id());
            sshdo.setDatum(new Date());
            sshdo.setStatus(asv.getStatus());

        } else if ("Bewaar".equals(action)) {
            // geen bijkomende actie
        } else {
            throw new RuntimeException("Ongeldige actie :" + action);
        }


        if (asv.getAanvr_schuldvordering_id() == null) {
            sqlSession.insertInTable("art46", "aanvr_schuldvordering", asv);
            sqlSession.insertInTable("art46", "schuldvordering", asv);
        } else {
            sqlSession.updateInTable("art46", "aanvr_schuldvordering", asv);
            sqlSession.updateInTable("art46", "schuldvordering", asv);
        }

        // regels bewaren
        for (AsvRegelDO regel : asv.getAsvRegels()) {
            if (regel.getSchuldvordering_regel_id() == null) {
                regel.setAanvr_schuldvordering_id(new Long(asv.getAanvr_schuldvordering_id()));
                sqlSession.insertInTable("art46", "aanvr_schuldvordering_regel", regel);
            } else if (regel.isChanged()) {
                regel.setAanvr_schuldvordering_id(new Long(asv.getAanvr_schuldvordering_id()));
                sqlSession.updateInTable("art46", "aanvr_schuldvordering_regel", regel);
            }
        }

        if (sshdo != null) {
            sshdo.setSchuldvordering_id(asv.getVordering_id());
            sqlSession.insertInTable("art46", "schuldvordering_status_history", sshdo);
        }

        return asv.getAanvr_schuldvordering_id().longValue();
    }

}
