package be.ovam.art46.service.sap;

import be.ovam.art46.sap.model.ProjectDTO;

import java.math.BigDecimal;
import java.util.Map;

public interface SapService {
	
	public Map<String, String> getOrCreateProject(Integer id)  throws Exception;
	
	public String getOrCreateBestek(Long bestek_id) throws Exception;
	public String getOrCreateDeelOpdracht(Integer deelOpdracht_id) throws Exception;

	public String getOrCreateVastlegging(String twaalfNr) throws Exception;
	
	//public String getOrCreateSchuldVordering(Integer vorderingId, String twaalfNr) throws Exception;
	public String createSchuldVorderingen(Integer vorderingId, String twaalfNr, BigDecimal bedrag, String twaalfNr_2, BigDecimal bedrag_2) throws Exception;
	public  String createWbsMigratie(Integer id) throws Exception;

	ProjectDTO saveProject(ProjectDTO project);

	void checkForInitialData(String projectId); 
}
