package be.ovam.art46.dao;

import be.ovam.art46.struts.actionform.AdresZoekForm;
import be.ovam.pad.model.Adres;

import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdresDAO extends GenericDAO<Adres> {

	@Autowired
	private SqlSession sqlSession;

    public AdresDAO() {
        super(Adres.class);
    }
	
	@SuppressWarnings("rawtypes")
	public List getAdresZoekResult(AdresZoekForm adresZoekForm) throws Exception {
		List adreslijst = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.zoekAdres", adresZoekForm);
		return adreslijst;
	}
	
	public String getGemeenteNaam(String postcode) throws Exception {
		String gemeenteNaam = (String) sqlSession.selectOne("be.ovam.art46.mappers.AdresMapper.getGemeenteNaamByPostcode", postcode);
		return gemeenteNaam;
	}

	public List getForDossier(Integer id) {
		return sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierAlleAdressen", id);
	}
}
