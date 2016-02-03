package be.ovam.art46.controller.lijsten;

import be.ovam.web.Response;
import be.ovam.art46.model.VastleggingOrdonanceringLijstDO;
import be.ovam.art46.model.VastleggingOrdonanceringLijstResponse;
import be.ovam.art46.sap.model.Project;
import be.ovam.art46.sap.model.ProjectDTO;
import be.ovam.art46.sap.model.SpreidingDTO;
import be.ovam.art46.service.ExportVekService;
import be.ovam.art46.service.sap.SapService;

import com.fasterxml.jackson.core.JsonProcessingException;

import be.ovam.util.mybatis.SqlSession;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@Controller
public class VEKoverzichtslijstController {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private SapService service;
	
	@Autowired
	private ExportVekService exportVekService;
	
	  private Logger log = Logger.getLogger(VEKoverzichtslijstController.class);

	@RequestMapping(value = "/VEKoverzichtslijst/", method = RequestMethod.GET)
	public String toonOverzicht(Model model, HttpServletRequest request)
			throws JsonProcessingException {

		List<Object> dossierhouders = sqlSession
				.selectList("be.ovam.art46.mappers.DropDownMapper.dossierhouders");
		List<Object> budgettairArtikels = sqlSession
				.selectList("be.ovam.art46.mappers.BudgetairArtikelMapper.budgetairArtikels");
		
		List<Object> programmaTypes  =  sqlSession.selectList("be.ovam.art46.mappers.ProgrammaMapper.getProgrammaList", null);

		model.addAttribute("dossierhouders", dossierhouders);
		model.addAttribute("budgettairArtikels", budgettairArtikels);
		model.addAttribute("programmaTypes", programmaTypes);

		return "lijsten.VEK";

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/VEKoverzichtslijst/getAll/", method = RequestMethod.GET)
	public @ResponseBody
	VastleggingOrdonanceringLijstResponse get(
			@RequestParam("jaar") String jaar,
			@RequestParam("budgettairArtikel") String budgettairArtikel,
			@RequestParam(required = false, value = "doss_hdr_id") String dossierhouder,
			@RequestParam(required = false, value = "programma") String programma,
			@RequestParam(required = false, value = "startValidatie") Date startValidatie,
			@RequestParam(required = false, value = "eindValidatie") Date eindValidatie,
			Model model) throws Exception {

		VastleggingOrdonanceringLijstResponse response = new VastleggingOrdonanceringLijstResponse();
		
		String datumString = jaar + "-01-01";
		String einddatumString = jaar + "-12-31";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDatum", datumString);
		params.put("eindDatum", einddatumString);
    	params.put("jaar", jaar);   

		if (dossierhouder != null) {
			params.put("doss_hdr_id", dossierhouder);
           
			
		}

		if (budgettairArtikel != null) {
			params.put("budgettair_artikel", budgettairArtikel);

		}
		
		if (programma != null) {
			params.put("programma", programma);

		}
		
		if (eindValidatie != null) {
			params.put("eindValidatie", eindValidatie);

		}
		
		if (startValidatie != null) {
			params.put("startValidatie", startValidatie);

		}
		
		response.setGefactureerdTotaal(new BigDecimal(0));
		response.setVekVoorzienTotaal (new BigDecimal(0));
		response.setVekBegrotingTotaal(new BigDecimal(0));
		
		Map<String, Integer> map2 = new HashMap<String, Integer>();
		map2.put("jaar", Integer.valueOf(jaar));
		 List<Map> selectList2 = sqlSession.selectList("be.ovam.art46.mappers.JaarbudgetMapper.getJaarbudgetList", map2);
		
		 for (Map mapJaarbudget : selectList2) {
			 
			String art =  (String) mapJaarbudget.get("ARTIKEL_B") ; 
			 
			 if (art!=null && art.equals(budgettairArtikel) ) {
				
				Integer vektotaal=  (Integer) mapJaarbudget.get("VEK_BUDGET");
				response.setVekBegrotingTotaal(new BigDecimal(vektotaal));
				 
			}
			
		}
		 
		

		List<VastleggingOrdonanceringLijstDO> selectList = sqlSession
				.selectList(
						"be.ovam.art46.mappers.ProjectMapper.getOrdonannceringLijst",
						params);
		
		

		for (VastleggingOrdonanceringLijstDO vastleggingOrdonanceringLijstDO : selectList) {
			BigDecimal gefactureerd = vastleggingOrdonanceringLijstDO.getGefactureerd()!=null?vastleggingOrdonanceringLijstDO.getGefactureerd():new BigDecimal(0);
			BigDecimal vekvoorzien = vastleggingOrdonanceringLijstDO.getVekVoorzien()!=null?vastleggingOrdonanceringLijstDO.getVekVoorzien():new BigDecimal(0);
			
			response.setGefactureerdTotaal(response.getGefactureerdTotaal().add(gefactureerd));
			response.setVekVoorzienTotaal(response.getVekVoorzienTotaal().add(vekvoorzien));
		}
		
		
		
		response.setVastleggingOrdonanceringLijst(selectList);
		
		return response;

	}
	
	@RequestMapping(value = "/VEKoverzichtslijst/getExport/", method = RequestMethod.GET)
    public void exportExcel(@RequestParam("jaar") String jaar,
			@RequestParam("budgettairArtikel") String budgettairArtikel,
			@RequestParam(required = false, value = "doss_hdr_id") String dossierhouder,
			@RequestParam(required = false, value = "programma") String programma,
			@RequestParam(required = false, value = "startValidatie") Date startValidatie,
			@RequestParam(required = false, value = "eindValidatie") Date eindValidatie,
            @RequestParam(required = false, value = "ivs") String ivs,
			 HttpServletResponse response) throws IOException {
		
		String datumString = jaar + "-01-01";
		String einddatumString = jaar + "-12-31";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDatum", datumString);
		params.put("eindDatum", einddatumString);
    	params.put("jaar", jaar);   


		if (dossierhouder != null) {
			params.put("doss_hdr_id", dossierhouder);
           
			
		}

		if (budgettairArtikel != null) {
			params.put("budgettair_artikel", budgettairArtikel);

		}
		
		if (programma != null) {
			params.put("programma", programma);

		}
		
		if (eindValidatie != null) {
			params.put("eindValidatie", eindValidatie);

		}
		
		if (startValidatie != null) {
			params.put("startValidatie", startValidatie);

		}
		
		int startJaar = Integer.valueOf(jaar) -5 ; 
		int eindJaar = Integer.valueOf(jaar) + 5 ; 
		
		
        ServletOutputStream op = null;
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=export-VEK-" + jaar +"-" + budgettairArtikel +  ".xls");
            op = response.getOutputStream();

            if(ivs==null){

              exportVekService.createVekExport("export", op, params, startJaar, eindJaar,null); }

            else {

                exportVekService.createVekExport("export", op, params, startJaar, eindJaar,ivs); }

        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if(op!=null){
                op.flush();
                op.close();
            }
        }
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/VEKoverzichtslijst/getSpreiding/", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody
	ProjectDTO getSpreiding(@RequestParam("twaalfnr") String twaalfnr, Model model)
			throws Exception {

		org.hibernate.Session currentSession = sessionFactory
				.getCurrentSession();

		Criteria project = currentSession.createCriteria(Project.class, "p");
		Criteria spreidingCrit = project.createCriteria("p.spreiding", "s",
				JoinType.LEFT_OUTER_JOIN);

		project.add(Restrictions.eq("p.id", twaalfnr));

		Project uniqueResult = (Project) project.uniqueResult();
		
		ProjectDTO projectDto = new ProjectDTO(uniqueResult);


        //voeg bestek en dossier info toe:



        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("projectId", twaalfnr);

        List<Map> selectList2 = sqlSession.selectList("be.ovam.art46.mappers.ProjectMapper.getBestekDossier", map2);


        Map resultMapBestek = selectList2.get(0);


        projectDto.setBestekNr((String)  resultMapBestek.get("BESTEK_NR") + " (" + (String)  resultMapBestek.get("OMSCHRIJVING") +")");
        projectDto.setDossierNr((String)  resultMapBestek.get("DOSSIER_ID") +  " (" + (String)  resultMapBestek.get("DOSSIER_B") +")");
      //  projectDto.setDossier_B((String) resultMapBestek.get("DOSSIER_B"));

		Collections.sort(projectDto.getSpreiding(),
				new Comparator<SpreidingDTO>() {

					public int compare(SpreidingDTO o1, SpreidingDTO o2) {
						return (-1) * o1.getJaar().compareTo(o2.getJaar());
					}
				});

		List<SpreidingDTO> spreidingResult = projectDto.getSpreiding();

		Map<Integer, SpreidingDTO> spreidingMap = new HashMap<Integer, SpreidingDTO>();

		for (SpreidingDTO spreiding : spreidingResult) {

			spreidingMap.put(spreiding.getJaar(), spreiding);
			spreiding.setGefactureerd(new BigDecimal(0));
			spreiding.setVorigBedrag(spreiding.getBedrag());

		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", twaalfnr);

		List<SpreidingDTO> factuurTotalenList = sqlSession
				.selectList(
						"be.ovam.art46.mappers.ProjectMapper.getFactuurTotalen",
						params);

		int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
		

		for (SpreidingDTO gefactureerd : factuurTotalenList) {

			gefactureerd.setBedrag(new BigDecimal(0));
            gefactureerd.setVorigBedrag(new BigDecimal(0));
			SpreidingDTO spreiding = spreidingMap.get(gefactureerd.getJaar());

			if (spreiding == null) {



				spreidingMap.put(gefactureerd.getJaar(), gefactureerd);

			} else {

				spreiding.setGefactureerd(gefactureerd.getGefactureerd());

			}

		}
		
		Collection<SpreidingDTO> defValues = spreidingMap.values();
		
		BigDecimal totaalGeschatteWaarde = new BigDecimal(0);
		
		for (SpreidingDTO spreiding : defValues) {
			
			if (spreiding.getJaar().intValue() < huidigJaar) {

				spreiding.setBedrag(spreiding.getGefactureerd());

			}


            totaalGeschatteWaarde= totaalGeschatteWaarde.add(spreiding.getBedrag());

           /* if (spreiding.getJaar().equals(huidigJaar)) {


				//spreiding.setBedrag(spreiding.getVorigBedrag().add(overteDragenNaarHuidigJaar));

			}
			*/
		}


        SpreidingDTO spreidingHuidigJaar = spreidingMap.get(huidigJaar);

        if (spreidingHuidigJaar==null){

            spreidingHuidigJaar = new SpreidingDTO(huidigJaar,new BigDecimal(0));
            spreidingMap.put(huidigJaar,spreidingHuidigJaar);
        }


        BigDecimal nieuweWaarde = spreidingHuidigJaar.getBedrag().add(projectDto.getInitieelBedrag().subtract(totaalGeschatteWaarde));

        if (nieuweWaarde.compareTo(new BigDecimal(0)) < 0){

            nieuweWaarde = new BigDecimal(0);

        }

        spreidingHuidigJaar.setBedrag(nieuweWaarde);

        projectDto.setSpreiding(new ArrayList(spreidingMap.values()));

		// currentSession.close();

		return projectDto;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/VEKoverzichtslijst/updateSpreiding/", method = RequestMethod.POST)
	public @ResponseBody
	Response updateSpreiding(@RequestBody ProjectDTO project, Model model, HttpServletRequest request)
			throws Exception {
		
		
		
		service.checkForInitialData(project.getProjectId());
		

		try {
			ProjectDTO saveProject = service.saveProject(project);
			return new Response(saveProject, true, null);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			return new Response(project, false, e.getMessage());
			
		}

	

	}

}
