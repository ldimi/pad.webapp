package be.ovam.art46.service.sap;

import be.ovam.art46.dao.BestekDAO;
import be.ovam.art46.dao.DossierDAO;
import be.ovam.art46.model.Project;
import be.ovam.art46.model.SchuldvorderingDO;
import be.ovam.art46.model.SchuldvorderingProjectDO;
import be.ovam.art46.sap.model.ProjectDTO;
import be.ovam.art46.sap.model.Spreiding;
import be.ovam.art46.sap.model.SpreidingDTO;
import be.ovam.art46.util.Application;
import be.ovam.esb.sap.client.factory.CreateProjectFactory;
import be.ovam.esb.sap.client.factory.CreateWbsFactory;
import be.ovam.esb.sap.client.factory.SearchProjectFactory;
import be.ovam.pad.model.Bestek;
import be.ovam.pad.model.DeelOpdrachtDO;
import be.ovam.pad.model.Dossier;
import be.ovam.pad.model.Schuldvordering;
import be.ovam.pad.model.SchuldvorderingStatusEnum;
import be.ovam.pad.model.SchuldvorderingStatusHistoryDO;
import be.ovam.sap.common.CreateWbsResponse;
import be.ovam.sap.common.ProjectResponse;
import be.ovam.sap.common.WbsNode;
import be.ovam.sap.common.WbsType;
import be.ovam.sap.createproject.CreateAfvalRequest;
import be.ovam.sap.createproject.CreateBodemRequest;
import be.ovam.sap.createproject.CreateProject;
import be.ovam.sap.createproject.CreateRaamcontractRequest;
import be.ovam.sap.createwbs.CreateWBS;
import be.ovam.sap.searchproject.SearchProject;
import be.ovam.util.mybatis.SqlSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component(value = "sapService")
@Scope("prototype")
public class SapServiceImpl implements SapService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired(required = true)
	private SearchProjectFactory searchProjectFactory;

	@Autowired(required = true)
	private CreateProjectFactory createProjectFactory;

	@Autowired(required = true)
	private CreateWbsFactory createWbsFactory;

	@Autowired(required = true)
	private SessionFactory factory;

	@Autowired
	private DossierDAO dossierDao;

	@Autowired
	private BestekDAO bestekDao;

	@Autowired
	private SqlSession sqlSession;

	private static Log log = LogFactory.getLog(SapServiceImpl.class);

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@SuppressWarnings("unchecked")
	public Map<String, String> getOrCreateProject(Integer id) throws Exception {
		Map<String, String> wbsNumbers = new HashMap<String, String>();
		Map<String, String> dossierDataForSap;

		Dossier doss = dossierDao.getDossierById(id);
		if (doss != null) {
			if (doss.getDossier_b() != null) {
				// het is een ivs-dossier.
				String wbs_ivs_nr = doss.getWbs_ivs_nr();
				if (wbs_ivs_nr != null && wbs_ivs_nr.length() > 0) {
					// project is al aangemaakt.
					wbsNumbers.put("PROJECT_DEF_NUMBER",
							doss.getSap_project_nr());
					wbsNumbers.put("WBS_IVS_NR", wbs_ivs_nr);

					log.info("return from DB - PROJECT_DEF_NUMBER : "
							+ wbsNumbers.get("PROJECT_DEF_NUMBER")
							+ "WBS_IVS_NR : " + wbsNumbers.get("WBS_IVS_NR"));
				} else {
					// haal gegevens op om project aan te maken.
					log.info("start ophalen gegevens voor sap van dossier : "
							+ id);
					dossierDataForSap = (Map<String, String>) sqlSession
							.selectOne(
									"be.ovam.art46.mappers.DossierMapper.getDataForSap",
									id);

					// maak project aan
					String dossier_type = dossierDataForSap.get("DOSSIER_TYPE");
					if ("B".equals(dossier_type)) {
						createBodemProject(dossierDataForSap);
					} else if ("A".equals(dossier_type)) {
						createAfvalProject(dossierDataForSap);
					} else if ("X".equals(dossier_type)) {
						createRaamContract(dossierDataForSap);
					} else {
						throw new RuntimeException(
								"Geen implementatie voor dossier_type "
										+ dossier_type
										+ "  -- dossier met id : " + id);
					}
					getWbs_ivs_nr(dossierDataForSap);

					// bewaar wbs_nrs
					doss.setSap_project_nr(dossierDataForSap
							.get("PROJECT_DEF_NUMBER"));
					doss.setWbs_ivs_nr(dossierDataForSap.get("WBS_IVS_NR"));
					dossierDao.saveObject(doss);

					wbsNumbers.put("PROJECT_DEF_NUMBER",
							dossierDataForSap.get("PROJECT_DEF_NUMBER"));
					wbsNumbers.put("WBS_IVS_NR",
							dossierDataForSap.get("WBS_IVS_NR"));

					log.info("Created sap project - PROJECT_DEF_NUMBER : "
							+ wbsNumbers.get("PROJECT_DEF_NUMBER")
							+ "WBS_IVS_NR : " + wbsNumbers.get("WBS_IVS_NR"));
				}
			}
		} else {
			throw new RuntimeException("Geen dossier gevonden met id : " + id);
		}
		return wbsNumbers;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String getOrCreateBestek(Long bestek_id) throws Exception {
		String bestek_wbs_nr = null;

		Bestek bestek = (Bestek) bestekDao.getObject(Bestek.class, bestek_id);
		if (bestek != null) {
			bestek_wbs_nr = bestek.getWbs_nr();
			if (bestek_wbs_nr != null && bestek_wbs_nr.length() > 0) {
				// bestek is al aangemaakt in SAP
				log.info("return from DB - bestek_wbs_nr : " + bestek_wbs_nr);
			} else {
				// haal gegevens op om bestek aan te maken.
				Map<String, String> wbsNumbersProj = getSpringProxy()
						.getOrCreateProject(bestek.getDossier_id());

				// maak bestek aan in SAP
				bestek_wbs_nr = createBestek(
						wbsNumbersProj.get("PROJECT_DEF_NUMBER"),
						wbsNumbersProj.get("WBS_IVS_NR"),
						bestek.getBestek_nr(), bestek.getBestek_nr());

				// bewaar bestek_wbs_nr
				bestek.setWbs_nr(bestek_wbs_nr);
				bestekDao.saveObject(bestek);
				log.info("Created sap wbs - bestek_wbs_nr : " + bestek_wbs_nr);
			}
		} else {
			throw new RuntimeException("Geen bestek gevonden met bestek_id : "
					+ bestek_id);
		}

		return bestek_wbs_nr;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String getOrCreateDeelOpdracht(Integer deelOpdracht_id)
			throws Exception {
		String deelOpdracht_wbs_nr = null;

		DeelOpdrachtDO deelOpdracht = (DeelOpdrachtDO) sqlSession.selectOne("be.ovam.art46.mappers.DeelopdrachtMapper.getDeelopdrachtById", deelOpdracht_id);
		if (deelOpdracht != null) {
			deelOpdracht_wbs_nr = deelOpdracht.getWbs_nr();

			if (deelOpdracht_wbs_nr != null && deelOpdracht_wbs_nr.length() > 0) {
				// bestek is al aangemaakt in SAP
				log.info("return from DB - deelOpdracht_wbs_nr : "
						+ deelOpdracht_wbs_nr);
			} else {
				// haal gegevens op om bestek aan te maken.
				Map<String, String> wbsNumbersProj = getSpringProxy()
						.getOrCreateProject(deelOpdracht.getDossier_id());

				// haal gegevens op om bestek aan te maken.
				String bestek_wbs_nr = getSpringProxy().getOrCreateBestek(
						deelOpdracht.getBestek_id());

                // maak deelopdracht aan in SAP
                deelOpdracht_wbs_nr = createDeelOpdracht(
                        wbsNumbersProj.get("PROJECT_DEF_NUMBER"),
                        wbsNumbersProj.get("WBS_IVS_NR"), 
                        deelOpdracht.getDeelopdracht_id().toString(),
                        deelOpdracht.getDeelopdracht_id().toString(),
                        bestek_wbs_nr);

				// bewaar bestek_wbs_nr
				deelOpdracht.setWbs_nr(deelOpdracht_wbs_nr);
				sqlSession.update("be.ovam.art46.mappers.DeelopdrachtMapper.updateDeelopdracht", deelOpdracht);
				log.info("Created sap wbs - deelOpdracht_wbs_nr : "
						+ deelOpdracht_wbs_nr);
			}
		} else {
			throw new RuntimeException(
					"Geen deelopdracht gevonden met deelOpdracht_id : "
							+ deelOpdracht_id);
		}

		return deelOpdracht_wbs_nr;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String getOrCreateVastlegging(String twaalfNr) throws Exception {
		String vastlegging_wbs_nr = null;

		Project project = (Project) sqlSession.selectOne(
				"be.ovam.art46.mappers.ProjectMapper.getProject", twaalfNr);
		if (project != null) {
			vastlegging_wbs_nr = project.getWbs_nr();
			if (vastlegging_wbs_nr != null && vastlegging_wbs_nr.length() > 0) {
				// bestek is al aangemaakt in SAP
				log.info("return from DB - vastlegging_wbs_nr : "
						+ vastlegging_wbs_nr);
			} else {
				// haal gegevens op om bestek aan te maken.
				String bestek_wbs_nr = getSpringProxy().getOrCreateBestek(
						project.getBestek_id());

				// maak bestek aan in SAP
				vastlegging_wbs_nr = createBestaandeVastlegging(bestek_wbs_nr,
						twaalfNr);

				if (vastlegging_wbs_nr == null
						|| vastlegging_wbs_nr.length() == 0) {
					throw new RuntimeException(
							"Creatie Vastlegging is mislukt,  twaalfNr : "
									+ twaalfNr + ", bestek_wbs_nr (parent) : "
									+ bestek_wbs_nr);
				}

				// bewaar bestek_wbs_nr
				project.setWbs_nr(vastlegging_wbs_nr);
				sqlSession.update(
						"be.ovam.art46.mappers.ProjectMapper.updateProject",
						project);
				log.info("Created sap wbs - vastlegging_wbs_nr : "
						+ vastlegging_wbs_nr);
			}
		} else {
			throw new RuntimeException(
					"Geen Vastlegging gevonden met twaalfNr : " + twaalfNr);
		}

		return vastlegging_wbs_nr;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String createSchuldVorderingen(Integer vorderingId, String twaalfNr,
			BigDecimal bedrag, String twaalfNr_2, BigDecimal bedrag_2)
			throws Exception {

		if (twaalfNr == null || twaalfNr.length() == 0) {
			throw new RuntimeException("geen geldig twaalfNr.");
		}

		String result = null;

		// haal gegevens schuldvordering op.
		SchuldvorderingDO schuldvordering = (SchuldvorderingDO) sqlSession
				.selectOne(
						"be.ovam.art46.mappers.SchuldvorderingMapper.getSvDO",
						vorderingId);
		List<SchuldvorderingProjectDO> projecten = sqlSession.selectList(
				"be.ovam.art46.mappers.SchuldvorderingMapper.getSvProjectDOs",
				vorderingId);

		// validatie van opgehaalde gegevens.
		if (schuldvordering == null) {
			throw new RuntimeException(
					"Geen Schuldvordering gevonden met vorderingId : "
							+ vorderingId);
		}
		if (projecten == null || projecten.size() != 0) {
			throw new RuntimeException(
					"Creatie Schuldvordering is mislukt, vorderingId : "
							+ vorderingId
							+ "Er mogen nog geen projecten aan de schuld vordering gekoppeld zijn. ");
		}

		// TODO : testen : goedkeuring_bedrag = bedrag + bedrag_2

		// deelopdracht ophalen/aanmaken indien nodig.
		String deelopdracht_wbs_nr = null;
		if (schuldvordering.getDeelopdracht_id() != null
				&& schuldvordering.getDeelopdracht_id() != 0) {
			deelopdracht_wbs_nr = getSpringProxy().getOrCreateDeelOpdracht(
					schuldvordering.getDeelopdracht_id());
		}

		String description = "";
        description = Schuldvordering.PREFIX_NUMMER +("00000" +schuldvordering.getVordering_id()).substring((""+schuldvordering.getVordering_id()).length());

		SchuldvorderingProjectDO project = new SchuldvorderingProjectDO();
		project.setVordering_id(schuldvordering.getVordering_id());
		project.setVolg_nr(0);
		project.setProject_id(twaalfNr);
		project.setBedrag(bedrag.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		result = _createSchuldvordering(project, deelopdracht_wbs_nr, description);

		if (twaalfNr_2 != null) {
			SchuldvorderingProjectDO project_2 = new SchuldvorderingProjectDO();
			BeanUtils.copyProperties(project, project_2);
			project_2.setVolg_nr(1);
			project_2.setProject_id(twaalfNr_2);
			project_2.setBedrag(bedrag_2
					.setScale(2, BigDecimal.ROUND_HALF_DOWN));
			// project_2.setGoedkeuring_bedrag(bedrag_2.doubleValue());

			result = result
					+ " "
					+ _createSchuldvordering(project_2, deelopdracht_wbs_nr,
							description);
		}
		
		
        sqlSession.update("be.ovam.art46.mappers.SchuldvorderingMapper.updateSchuldvorderingNaAanmakenSAP", vorderingId);
        
        createStatusHistory(vorderingId, SchuldvorderingStatusEnum.BEOORDEELD.getValue(), null);
        
		return result;
	}

    private void createStatusHistory(Integer vordering_id, String status, String motivatie) {
        SchuldvorderingStatusHistoryDO sshdo = new SchuldvorderingStatusHistoryDO();
        sshdo.setSchuldvordering_id(vordering_id);
        sshdo.setDossierhouder_id(Application.INSTANCE.getUser().getUser_id());
        sshdo.setDatum(new Date());
        sshdo.setMotivatie(motivatie);
        sshdo.setStatus(status);
        
        sqlSession.insertInTable("art46","schuldvordering_status_history", sshdo);
    }
	
	
	private String _createSchuldvordering(SchuldvorderingProjectDO project,
			String deelopdracht_wbs_nr, String description) throws Exception {
		String twaalfNr = project.getProject_id();

		// getOrCreateVastlegging (twaalfnr = project.project_id)
		String vastlegging_wbs_nr = getSpringProxy().getOrCreateVastlegging(
				twaalfNr);

		String parent_wbs_nr;
		if (deelopdracht_wbs_nr == null) {
			parent_wbs_nr = vastlegging_wbs_nr;
		} else {
			parent_wbs_nr = deelopdracht_wbs_nr;
		}
		String project_def_number = parent_wbs_nr.substring(0, 9);

		String refNumber = project.getVordering_id().toString() + "-"
				+ project.getVolg_nr().toString();
		// maak schuldvordering aan in SAP
		String schuldvordering_wbs_nr = __createSchuldvordering(
				project_def_number, parent_wbs_nr, refNumber, description,
				project.getBedrag(), vastlegging_wbs_nr);

		if (schuldvordering_wbs_nr == null
				|| schuldvordering_wbs_nr.length() == 0) {
			throw new RuntimeException(
					"Creatie Schuldvordering is mislukt, vorderingId : "
							+ project.getVordering_id() + ", twaalfNr : : "
							+ twaalfNr);
		}

		// bewaar bestek_wbs_nr
		project.setWbs_nr(schuldvordering_wbs_nr);

		sqlSession.insert(
				"be.ovam.art46.mappers.SchuldvorderingMapper.insertSvProject",
				project);

		log.info("Created sap wbs - schuldvordering_wbs_nr : "
				+ schuldvordering_wbs_nr + ", twaalfNr : " + twaalfNr);

		return schuldvordering_wbs_nr;
	}

	private String __createSchuldvordering(String projectDefNumber,
			String wbsNumber, String refNumber, String description,
			BigDecimal amount, String gekoppeld_wbs_nummer) {
		String sv_wbs_nr = null;
		CreateWBS port = createWbsFactory.getPort();
		CreateWbsResponse response = port.createSchuldvordering(
				projectDefNumber, wbsNumber, refNumber, description, amount,
				gekoppeld_wbs_nummer);
		if (response.getRc() == 0 || response.getRc() == 1) {
			sv_wbs_nr = response.getWbsNodes().get(0).getWbsElement();
		} else {
			String responseMsg = "";
			if (response.getReturnMessage() != null) {
				responseMsg = response.getReturnMessage().getMessage();
			}

			throw new RuntimeException("Creatie SchuldVordering is mislukt, "
					+ "[projectDefNumber : " + projectDefNumber
					+ ", wbsNumber : " + wbsNumber + ", refNumber : "
					+ refNumber + ", description : " + description
					+ ", amount : " + amount + ", gekoppeld_wbs_nummer : "
					+ gekoppeld_wbs_nummer + "] esb-message = " + responseMsg);
		}

		return sv_wbs_nr;
	}

	private String createBestaandeVastlegging(String wbsNumber, String twaalfnr) {
		String vastlegging_wbs_nr = null;
		CreateWBS port = createWbsFactory.getPort();
		CreateWbsResponse response = port.createWbsBestaandeVastlegging(
				twaalfnr, wbsNumber);
		if (response.getRc() == 0 || response.getRc() == 1) {
			vastlegging_wbs_nr = response.getWbsNodes().get(0).getWbsElement();
		} else {
			String responseMsg = "";
			if (response.getReturnMessage() != null) {
				responseMsg = response.getReturnMessage().getMessage();
			}

			throw new RuntimeException("Creatie Vastlegging is mislukt, "
					+ "[ wbsNumber : " + wbsNumber + ", twaalfnr : " + twaalfnr
					+ "] esb-message = " + responseMsg);
		}
		return vastlegging_wbs_nr;
	}

	private String createBestek(String projectDefNumber, String wbsNumber,
			String refNumber, String description) {
		String bestek_wbs_nr = null;
		if (wbsNumber == null) {
			wbsNumber = "";
		}

		CreateWBS port = createWbsFactory.getPort();
		CreateWbsResponse response = port.createBestek(projectDefNumber,
				wbsNumber, refNumber, description);
		if (response.getRc() == 0 || response.getRc() == 1) {
			bestek_wbs_nr = response.getWbsNodes().get(0).getWbsElement();
		} else {
			String responseMsg = "";
			if (response.getReturnMessage() != null) {
				responseMsg = response.getReturnMessage().getMessage();
			}

			throw new RuntimeException("Creatie Bestek is mislukt, "
					+ "[projectDefNumber : " + projectDefNumber
					+ ", wbsNumber : " + wbsNumber + ", refNumber : "
					+ refNumber + ", description : " + description
					+ "] esb-message = " + responseMsg);
		}
		return bestek_wbs_nr;
	}

	private String createDeelOpdracht(String projectDefNumber,
			String wbsNumber, String refNumber, String description,
			String wbsBestekRaamcontract) {
		String deelOpdracht_wbs_nr = null;
		CreateWBS port = createWbsFactory.getPort();
		CreateWbsResponse response = port.createDeelopdracht(projectDefNumber,
				wbsNumber, refNumber, description, null, wbsBestekRaamcontract);
		if (response.getRc() == 0 || response.getRc() == 1) {
			deelOpdracht_wbs_nr = response.getWbsNodes().get(0).getWbsElement();
		} else {
			String responseMsg = "";
			if (response.getReturnMessage() != null) {
				responseMsg = response.getReturnMessage().getMessage();
			}

			throw new RuntimeException("Creatie Bestek is mislukt, "
					+ "[projectDefNumber : " + projectDefNumber
					+ ", wbsNumber : " + wbsNumber + ", refNumber : "
					+ refNumber + ", description : " + description
					+ ", wbsBestekRaamcontract : " + wbsBestekRaamcontract
					+ "] esb-message = " + responseMsg);
		}
		return deelOpdracht_wbs_nr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void getWbs_ivs_nr(Map dossierDataForSap) {
		SearchProject port = searchProjectFactory.getPort();

		String projectDefNumber = (String) dossierDataForSap
				.get("PROJECT_DEF_NUMBER");
		ProjectResponse projectResponse = port.getProjectByProjectDefNumber(
				"budget", projectDefNumber, true);

		List<be.ovam.sap.common.WbsNode> wbsLijst = projectResponse
				.getWbsNodes();
		for (WbsNode wbsNode : wbsLijst) {
			if (wbsNode.getWbsType() == WbsType.IVS) {
				// gevonden nummer wordt in dossier meegegeven.
				System.out.println("Wbs_ivs_nr : " + wbsNode.getWbsElement());
				dossierDataForSap.put("WBS_IVS_NR", wbsNode.getWbsElement());
			}
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String createBodemProject(Map dossierDataForSap) {
		String projectDefNumber = null;
		projectDefNumber = (String) dossierDataForSap.get("PROJECT_DEF_NUMBER");
		if (projectDefNumber == null) {
			System.out.println("start creatie van project : "
					+ dossierDataForSap.get("IVS_NUMBER"));

			CreateBodemRequest req = new CreateBodemRequest();

			req.setBodemNumber((String) dossierDataForSap.get("BODEM_NUMBER"));
			req.setBodemDescription((String) dossierDataForSap
					.get("BODEM_DESCRIPTION"));
			req.setResponsibleBodem((String) dossierDataForSap
					.get("BODEM_RESPONSABLE"));

			req.setIvsNumber((String) dossierDataForSap.get("IVS_NUMBER"));
			req.setIvsDescription((String) dossierDataForSap
					.get("IVS_DESCRIPTION"));
			req.setResponsibleIvs((String) dossierDataForSap
					.get("IVS_RESPONSABLE"));

			req.setJdNumber((String) dossierDataForSap.get("JD_NUMBER"));
			req.setJdDescription((String) dossierDataForSap
					.get("JD_DESCRIPTION"));
			req.setResponsibleJd((String) dossierDataForSap
					.get("JD_RESPONSABLE"));

			req.setSystem("PAD");

			CreateProject port = createProjectFactory.getPort();
			ProjectResponse projectResponse = port.createBodem(req);
			if (projectResponse.getRc() == 0) {
				projectDefNumber = projectResponse.getProjectDefNumber();
			} else {
				System.out.println("creatie van project mislukt : "
						+ dossierDataForSap.get("IVS_NUMBER"));
				throw new RuntimeException("creatie van project mislukt"
						+ projectResponse.getReturnMessage().getMessage());
			}
			dossierDataForSap.put("PROJECT_DEF_NUMBER", projectDefNumber);
			System.out.println("einde creatie van project : "
					+ projectDefNumber);
		} else {
			System.out
					.println("creatie van project : project was al aangemaakt (geen actie) "
							+ dossierDataForSap.get("IVS_NUMBER"));
		}

		return projectDefNumber;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String createAfvalProject(Map dossierDataForSap) {
		String projectDefNumber = null;
		projectDefNumber = (String) dossierDataForSap.get("PROJECT_DEF_NUMBER");
		if (projectDefNumber == null) {
			System.out.println("start creatie van project : "
					+ dossierDataForSap.get("IVS_NUMBER"));

			CreateAfvalRequest req = new CreateAfvalRequest();

			req.setBodemNumber((String) dossierDataForSap.get("BODEM_NUMBER"));
			req.setBodemDescription((String) dossierDataForSap
					.get("BODEM_DESCRIPTION"));
			req.setResponsibleBodem((String) dossierDataForSap
					.get("BODEM_RESPONSABLE"));

			req.setIvsNumber((String) dossierDataForSap.get("IVS_NUMBER"));
			req.setIvsDescription((String) dossierDataForSap
					.get("IVS_DESCRIPTION"));
			req.setResponsibleIvs((String) dossierDataForSap
					.get("IVS_RESPONSABLE"));

			req.setJdNumber((String) dossierDataForSap.get("JD_NUMBER"));
			req.setJdDescription((String) dossierDataForSap
					.get("JD_DESCRIPTION"));
			req.setResponsibleJd((String) dossierDataForSap
					.get("JD_RESPONSABLE"));

			req.setSystem("PAD");

			CreateProject port = createProjectFactory.getPort();
			ProjectResponse projectResponse = port.createAfval(req);
			if (projectResponse.getRc() == 0) {
				projectDefNumber = projectResponse.getProjectDefNumber();
			} else {
				System.out.println("creatie van project mislukt : "
						+ dossierDataForSap.get("IVS_NUMBER"));
				throw new RuntimeException("creatie van project mislukt"
						+ projectResponse.getReturnMessage().getMessage());
			}
			dossierDataForSap.put("PROJECT_DEF_NUMBER", projectDefNumber);
			System.out.println("einde creatie van project : "
					+ projectDefNumber);
		} else {
			System.out
					.println("creatie van project : project was al aangemaakt (geen actie) "
							+ dossierDataForSap.get("IVS_NUMBER"));
		}

		return projectDefNumber;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String createRaamContract(Map dossierDataForSap) {
		String projectDefNumber = null;
		projectDefNumber = (String) dossierDataForSap.get("PROJECT_DEF_NUMBER");
		if (projectDefNumber == null) {
			System.out.println("start creatie van project : "
					+ dossierDataForSap.get("IVS_NUMBER"));

			CreateRaamcontractRequest req = new CreateRaamcontractRequest();

			// req.setBodemNumber((String)
			// dossierDataForSap.get("BODEM_NUMBER"));
			// req.setBodemDescription((String)
			// dossierDataForSap.get("BODEM_DESCRIPTION"));
			// req.setResponsibleBodem((String)
			// dossierDataForSap.get("BODEM_RESPONSABLE"));

			req.setIvsNumber((String) dossierDataForSap.get("IVS_NUMBER"));
			req.setIvsDescription((String) dossierDataForSap
					.get("IVS_DESCRIPTION"));
			req.setResponsibleIvs((String) dossierDataForSap
					.get("IVS_RESPONSABLE"));

			// req.setJdNumber((String) dossierDataForSap.get("JD_NUMBER"));
			// req.setJdDescription((String)
			// dossierDataForSap.get("JD_DESCRIPTION"));
			// req.setResponsibleJd((String)
			// dossierDataForSap.get("JD_RESPONSABLE"));

			req.setSystem("PAD");

			CreateProject port = createProjectFactory.getPort();
			ProjectResponse projectResponse = port
					.createRaamcontractDossier(req);
			if (projectResponse.getRc() == 0) {
				projectDefNumber = projectResponse.getProjectDefNumber();
			} else {
				System.out.println("creatie van project mislukt : "
						+ dossierDataForSap.get("IVS_NUMBER"));
				throw new RuntimeException("creatie van project mislukt"
						+ projectResponse.getReturnMessage().getMessage());
			}
			dossierDataForSap.put("PROJECT_DEF_NUMBER", projectDefNumber);
			System.out.println("einde creatie van project : "
					+ projectDefNumber);
		} else {
			System.out
					.println("creatie van project : project was al aangemaakt (geen actie) "
							+ dossierDataForSap.get("IVS_NUMBER"));
		}

		return projectDefNumber;
	}

	private ProjectResponse createProject(CreateBodemRequest createBodemRequest) {
		CreateProject port = createProjectFactory.getPort();
		ProjectResponse projectResponse = port.createBodem(createBodemRequest);
		return projectResponse;
	}

	public String createWbsMigratie(Integer id) throws Exception {

		Map<String, String> dossier = getOrCreateProject(id);

		String WBS_MIGR_NUMBER = null;
		CreateWBS port = createWbsFactory.getPort();

		CreateWbsResponse response = port.createAlgemeneWbsNode(
				(String) dossier.get("PROJECT_DEF_NUMBER"),
				(String) dossier.get("WBS_IVS_NR"), null, "Migratie");

		if (response.getRc() == 0) {
			WBS_MIGR_NUMBER = response.getWbsNodes().get(0).getWbsElement();
		} else {
			System.out.println("creatie van migratieWbs mislukt : "
					+ dossier.get("IVS_NUMBER"));
			throw new RuntimeException("creatie van migratieWbs mislukt"
					+ response.getReturnMessage().getMessage());
		}
		dossier.put("WBS_MIGR_NUMBER", WBS_MIGR_NUMBER);

		return WBS_MIGR_NUMBER;
	}
	

	
	public void checkForInitialData(String projectId){
		
		Session currentSession = factory.openSession();
		Transaction beginTransaction = currentSession.beginTransaction(); 
		
		try {
			
			AuditReader reader = AuditReaderFactory.get(currentSession);
			List<Number> versions = reader.getRevisions(be.ovam.art46.sap.model.Project.class, projectId);
			
			if (!(versions!=null && versions.size()>0)) {
				
				// save initial data... 
				
				be.ovam.art46.sap.model.Project project = (be.ovam.art46.sap.model.Project) currentSession
						.get(be.ovam.art46.sap.model.Project.class,
								projectId);
				
				
				List<Spreiding> spreiding = project.getSpreiding();
				
				for (Spreiding spreiding2 : spreiding) {
					
					currentSession.evict(spreiding2);
					currentSession.update(spreiding2);
					
				}
			currentSession.evict(project);
			currentSession.update(project);
				
				
			}
		
			currentSession.flush();
			beginTransaction.commit();
			
			
		} catch (Exception e) {
			
		} finally{
			
			currentSession.close();
			
		}
		
		
		
	}
	

	@Transactional(propagation = Propagation.REQUIRED)
	public ProjectDTO saveProject(ProjectDTO projectDto) {

		Session currentSession = factory.getCurrentSession();
		

		be.ovam.art46.sap.model.Project project = (be.ovam.art46.sap.model.Project) currentSession
				.get(be.ovam.art46.sap.model.Project.class,
						projectDto.getProjectId());
		
		
	//	
		

		List<Spreiding> spreidingBestaand = project.getSpreiding();
		HashMap<Integer, Spreiding> spreidingBestaandMap = new HashMap<Integer, Spreiding>();

		for (Spreiding spreiding : spreidingBestaand) {

			spreidingBestaandMap.put(spreiding.getJaar(), spreiding);

		}

		NumberFormat decimalFormattter = NumberFormat.getNumberInstance(Locale.GERMANY);

		List<SpreidingDTO> spreidingNieuw = projectDto.getSpreiding();

		BigDecimal totaalSpreiding = new BigDecimal(0);

		HashMap<Integer, SpreidingDTO> spreidingDtoMap = new HashMap<Integer, SpreidingDTO>();

		for (Iterator iterator = spreidingNieuw.iterator(); iterator.hasNext();) {
			SpreidingDTO schatting = (SpreidingDTO) iterator.next();

			totaalSpreiding = totaalSpreiding.add(schatting.getBedrag());

			if (spreidingDtoMap.get(schatting.getJaar()) != null) {

				String errorMsg = "In de spreiding betaling komt het jaar "
						+ schatting.getJaar() + "meerdere keren voor ";
				throw new RuntimeException(errorMsg);

			} else {

				spreidingDtoMap.put(schatting.getJaar(), schatting);
			}

		}

		BigDecimal controleBedrag;

		if (projectDto.getVoorgesteldAfTeBoekenBedrag() != null
				&& projectDto.isVastleggingMagAfgeboektWorden()) {

			controleBedrag = projectDto.getInitieelBedrag().subtract(
					projectDto.getVoorgesteldAfTeBoekenBedrag());

		} else {

			controleBedrag = projectDto.getInitieelBedrag();
		}

		BigDecimal verschil = projectDto.getInitieelBedrag().subtract(
				totaalSpreiding);

		if (totaalSpreiding.compareTo(controleBedrag) < 0) {

			String errorMsg = "de som van de verwachte vereffeningskredieten is kleiner ("
					+ decimalFormattter.format(verschil)
					+ ") dan het initiele vastleggingsbedrag "
					+ "gelieve dit te corrigeren of aan te vinken dat een overtollige gedeelte mag vrijgegeven worden";

			projectDto.setVoorgesteldAfTeBoekenBedrag(controleBedrag
					.subtract(totaalSpreiding));

			throw new RuntimeException(errorMsg);
		}

		if (totaalSpreiding.compareTo(controleBedrag)> 0 && !projectDto.isVastleggingMagAfgeboektWorden() ) {

			String errorMsg = "de som van de verwachte vereffeningskredieten is groter ("
					+ decimalFormattter
							.format(verschil.multiply(new BigDecimal(-1)))
					+ ") dan het initiele vastleggingsbedrag "
					+ "gelieve dit te corrigeren of aan te vinken dat dit toch klopt";

			projectDto.setVoorgesteldAfTeBoekenBedrag(controleBedrag
					.subtract(totaalSpreiding));

			throw new RuntimeException(errorMsg);
		}

		for (Iterator iterator = spreidingNieuw.iterator(); iterator.hasNext();) {
			SpreidingDTO spreidingDto = (SpreidingDTO) iterator.next();

			Spreiding spreidingItemBestaand = spreidingBestaandMap
					.get(spreidingDto.getJaar());

			if (spreidingItemBestaand != null) {

				spreidingItemBestaand.setBedrag(spreidingDto.getBedrag());
				/*if(currentSession.contains(spreidingItemBestaand)) {
					currentSession.evict(spreidingItemBestaand);
					}*/
				
				System.out.println("updating");
				currentSession.update(spreidingItemBestaand);

			} else {

				spreidingItemBestaand = new Spreiding();
				spreidingItemBestaand.setProject(project);
				spreidingItemBestaand.setJaar(spreidingDto.getJaar());
				spreidingItemBestaand.setBedrag(spreidingDto.getBedrag());

				System.out.println("updating");
				currentSession.save(spreidingItemBestaand);

			}
		}

		for (Spreiding spreiding : spreidingBestaand) {

		
			
			if(!spreidingDtoMap.containsKey(spreiding.getJaar())){
				
				currentSession.delete(spreiding);
			}

		}
		
		project.setSpreidingValidatieD(new  Date());
		project.setSpreidingValidatieUid(Application.INSTANCE.getUser_id());
		
		project.setAfTeBoekenBedrag(project.getVoorgesteldAfTeBoekenBedrag());
	
		currentSession.update(project);

		return projectDto;

	}

	private SapService getSpringProxy() {
		return applicationContext.getBean(SapService.class);
	}

}
