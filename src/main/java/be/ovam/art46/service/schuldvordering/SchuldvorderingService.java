package be.ovam.art46.service.schuldvordering;

import be.ovam.art46.model.SchuldvorderingDO;
import be.ovam.art46.model.SchuldvorderingData;
import be.ovam.pad.model.Schuldvordering;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface SchuldvorderingService {

    SchuldvorderingData save(SchuldvorderingDO schuldvorderingDO) throws Exception;

    SchuldvorderingData afkeuren(SchuldvorderingDO schuldvorderingDO) throws Exception;

    SchuldvorderingData goedkeuren(SchuldvorderingDO schuldvorderingDO) throws Exception;

    void deleteSchuldvordering(Integer vordering_id);

    void verwijderWbs(Integer vordering_id) throws Exception;

    List<Schuldvordering> getForBestek(Long bestekId);

    List<Schuldvordering> getTeKeurenSchuldvorderingen();

    void ondertekenen(Integer schuldvorderingId, String ondertekenaar) throws Exception;

    Schuldvordering get(Integer schuldvorderingId);

    SchuldvorderingData getSchuldvorderingData(Integer schuldvorderingId);

    SchuldvorderingData getNieuwSchuldvorderingData(Integer brief_id, Date vordering_d);

    void terugNaarDossierhouder(Integer id, String motivatie) throws Exception;

    List<Schuldvordering> getTeScannenSchuldvorderingen();

    String addScan(Integer schuldvorderingId, MultipartFile multipartFile) throws Exception;

    List<Schuldvordering> getTePrintenSchuldvorderingen();

    void isAfgedrukt(Integer id);

    void save(Schuldvordering schuldvordering);

    String handtekenningBeschikbaar();

    boolean print(Integer vordering_id);

}
