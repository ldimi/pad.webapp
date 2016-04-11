package be.ovam.art46.util;

import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.util.mybatis.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SuppressWarnings("rawtypes")
public enum DropDownHelper {
    INSTANCE;

	private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

    
	public List getProgrammaTypes () {
    	List programmaTypes  =  sqlSession.selectList("be.ovam.art46.mappers.ProgrammaMapper.getProgrammaList", null);
    	return programmaTypes;
    }

	public List getBudgetairartikel () {
    	return this.get("be.ovam.art46.mappers.BudgetairArtikelMapper.budgetairArtikels");
    }
	
	public List getMaanden () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.maanden");
    }
	
	public List getBestekBodemType () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.bestekBodemType");
    }
	
	public List getBestekBodemFase () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.bestekBodemFase");
    }
	
	public List getBestekBodemProcedure () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.bestekBodemProcedure");
    }

	
	public List getDossierFasen_dd () {
    	return this.get("be.ovam.pad.common.mappers.DossierDDMapper.fasen");
    }
	
   public List getDoelgroepen_dd () {
        return this.get("doelgroepen_dd");
    }

	public List getDiensten () {
    	return this.get("be.ovam.art46.mappers.DienstMapper.diensten");
    }
	
	public List getBriefAard () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.briefAard");
    }

	
	
	public List getBodemsaneringsdeskundigen () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.bodemsaneringsdeskundigen");
    }
		
	public List getBriefType () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.briefType");
    }
	
	public List getBriefTypeVos () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.briefTypeVos");
    }
	
	public List getLanden () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.landen");
    }

	
	
	public List getFaseRamingByDossierType (String dossierType) {
		return sqlSession.selectList("be.ovam.art46.mappers.DropDownMapper.faseRamingByDossierType", dossierType);
	}
	public List getFaseRamingAfval () {
    	return DropDownHelper.INSTANCE.getFaseRamingByDossierType("A");
    }
	public List getFaseRaming () {
    	return DropDownHelper.INSTANCE.getFaseRamingByDossierType("B");
    }
	public List getFaseRamingLijstByDossierType (String dossierType) {
		return sqlSession.selectList("be.ovam.art46.mappers.DropDownMapper.faseRamingLijstByDossierType", dossierType);
	}
	public List getFaseRamingLijstAfval () {
    	return DropDownHelper.INSTANCE.getFaseRamingLijstByDossierType("A");
    }
	public List getFaseRamingLijst () {
    	return DropDownHelper.INSTANCE.getFaseRamingLijstByDossierType("B");
    }

	
	public List getPrioriteitRamingLijst () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.prioriteitRamingLijst");
    }	
	public List getPrioriteitRaming () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.prioriteitRaming");
    }

	public List getJaartalRaming () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.jaartalRaming");
    }


	
	public List getActieTypesByDossierType (String dossierType) {
		return sqlSession.selectList("be.ovam.art46.mappers.ActieTypeMapper.actieTypesByDossierType", dossierType);
	}
	public List getIVSActiesTypes () {
    	return DropDownHelper.INSTANCE.getActieTypesByDossierType("B");
    }
	public List getAfvalActiesTypes () {
    	return DropDownHelper.INSTANCE.getActieTypesByDossierType("A");
    }
	public List getBestekActiesTypes () {
    	return DropDownHelper.INSTANCE.getActieTypesByDossierType("K");
    }
	public List getJDActies () {
    	return DropDownHelper.INSTANCE.getActieTypesByDossierType("J");
    }
	public List getActiesTypes () {
    	return this.get("be.ovam.art46.mappers.ActieTypeMapper.actiesTypes");
    }
	public List getActiesTypesArt46 () {
    	return this.get("be.ovam.art46.mappers.ActieTypeMapper.actiesTypesArt46");
    }
	public List getAlleActiesTypes () {
    	return this.get("be.ovam.art46.mappers.ActieTypeMapper.alleActiesTypes");
    }
	
	public List getActiesSubTypesArt46 () {
    	return this.get("be.ovam.art46.mappers.ActieTypeMapper.actiesSubTypesArt46");
    }
	
	public List getActiesubtypelijst () {
    	return this.get("be.ovam.art46.mappers.ActieTypeMapper.actiesubtypelijst");
    }

	public List getArtikels () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.artikels");
    }
	
	public List getDossierhouders () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.dossierhouders");
    }

	public List getBeheerDossierhouders () {
    	return this.get("be.ovam.art46.mappers.DossierhoudersMapper.beheerDossierhouders");
    }	
	
	public List getAmbtenaren () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.ambtenaren");
    }
	
	public List getAmbtenarenJD () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.ambtenarenJD");
    }
	
	public List getAmbtenarenBOA () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.ambtenarenBOA");
    }
	
	public List getFusiegemeenten () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.fusiegemeenten");
    }
	
    public List getProvincies () {
        return this.get("be.ovam.art46.mappers.DropDownMapper.provincies");
    }
    
	public List getAdrestypes () {
    	return this.get("be.ovam.art46.mappers.AdresTypeMapper.adrestypes");
    }
	
	public List getKadasterafdelingen () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.kadasterafdelingen");
    }
	
	public List getLijsten () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.lijsten");
    }
	
	public List getActieveLijsten () {
    	return this.get("be.ovam.art46.mappers.DropDownMapper.actieveLijsten");
    }
	
	public List getBudgetCode_dd () {
    	return this.get("be.ovam.art46.mappers.BudgetCodeMapper.getBudgetCode_dd");
	}
	
	
	private List get(String key) {
		return sqlSession.selectList(key, null);
	}
	
	public List getAmbtenaars() {
    	return this.get("be.ovam.art46.mappers.KlantAmbtenaarMapper.getAmbtenaars");
	}
	
	public Object getBeheerDossierhoudersActief() {
		return this.get("be.ovam.art46.mappers.DossierhoudersMapper.beheerDossierhoudersActief");
	}

    public List getJaren() {
        return this.getJaren_16();
    }

    public List getJaren_16() {
        return this.get("be.ovam.art46.mappers.DropDownMapper.jaren_16");
    }
    
    public List getFaseDD() {
        return this.get("be.ovam.art46.mappers.PlanningMapper.getFaseDD");    	
    }

    public List getBudgetCodeDD() {
        return this.get("be.ovam.art46.mappers.PlanningMapper.getBudgetCodeDD");    	
    }

    public List getProgrammaDD() {
        return this.get("be.ovam.art46.mappers.ProgrammaMapper.getProgrammaDD");    	
    }

    public List getPlanningMarkeringenDD() {
        return this.get("be.ovam.art46.mappers.PlanningMapper.getMarkeringenDD");    	
    }

    
    public List getDossierRechtsgronden () {
        return this.get("be.ovam.pad.common.mappers.DossierDDMapper.rechtsgronden");
    }

    public static List<Integer> getBtwTarieven(){
        List<Integer> btwTarieven = new ArrayList<Integer>();
        btwTarieven.add(21);
        btwTarieven.add(19);
        btwTarieven.add(0);
        return btwTarieven;
    }
    
    public List wrap(List l) {
    	ArrayList mapHolderList = new ArrayList();
    	
    	for (Object o : l) {
    		Map m = (Map) o;
			mapHolderList.add(new MapHolder(m));
		}
    	return mapHolderList;
    }
    
    public static class MapHolder {
    	private Map m;
    	
		public MapHolder(Map m) {
			super();
			this.m = m;
		}

		public Map getM() {
			return m;
		}

    }
}