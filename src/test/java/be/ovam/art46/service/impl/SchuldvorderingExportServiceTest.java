package be.ovam.art46.service.impl;

import be.ovam.art46.model.*;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.service.GebruikerService;
import be.ovam.art46.service.schuldvordering.AanvraagSchuldvorderingExportService;
import be.ovam.art46.service.schuldvordering.SchuldvorderingExportServiceImpl;
import be.ovam.art46.service.schuldvordering.SchuldvorderingService;
import be.ovam.pad.model.AanvraagSchuldvordering;
import be.ovam.pad.model.Adres;
import be.ovam.pad.model.Ambtenaar;
import be.ovam.pad.model.Bestek;
import be.ovam.pad.model.Brief;
import be.ovam.pad.model.BriefCategorie;
import be.ovam.pad.model.Dossier;
import be.ovam.pad.model.Offerte;
import be.ovam.pad.model.OvamGemeente;
import be.ovam.pad.model.SapProject;
import be.ovam.pad.model.SchuldVorderingSapProject;
import be.ovam.pad.model.Schuldvordering;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Koen on 19/03/14.
 */
public class SchuldvorderingExportServiceTest {
    private SchuldvorderingExportServiceImpl schuldvorderingExportService;
    private BriefService briefService;
    private SchuldvorderingService schuldvorderingService;
    private AanvraagSchuldvorderingExportService aanvraagSchuldvorderingExportService;
    private GebruikerService gebruikerService;
    private EsbServiceImpl esbService;

    @Before
    public void setup() throws Exception {

        SchuldvorderingExportServiceImpl exportSchuldvorderingServiceImpl = new SchuldvorderingExportServiceImpl();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        FormHttpMessageConverter converter = new FormHttpMessageConverter();
        esbService = new EsbServiceImpl();
        esbService.setRestTemplate(restTemplate);
        esbService.setDocumentUrl("http://10.1.11.155:8001/rest/cmis/convertToPdf");
        exportSchuldvorderingServiceImpl.setEsbService(esbService);
        briefService = Mockito.mock(BriefService.class);
        schuldvorderingService = Mockito.mock(SchuldvorderingService.class);
        exportSchuldvorderingServiceImpl.setBriefService(briefService);
        schuldvorderingExportService = exportSchuldvorderingServiceImpl;
        aanvraagSchuldvorderingExportService = Mockito.mock(AanvraagSchuldvorderingExportService.class);
        gebruikerService = Mockito.mock(GebruikerService.class);
        exportSchuldvorderingServiceImpl.setGebruikerService(gebruikerService);
        exportSchuldvorderingServiceImpl.setAanvraagSchuldvorderingExportService(aanvraagSchuldvorderingExportService);
    }



    @Test
    public void exportToPdfGoedegekeurd() throws Exception {
        Schuldvordering schuldvordering = mockSchuldvordering("test1Goedegekeurd1");
        Brief brief = new Brief();
        brief.setBrief_nr("IVS-SI/96010-6/KJA-11/0010");
        Mockito.when(brief.getAuteur()).thenReturn(createAuteur());
        Mockito.when( briefService.makeDocument(schuldvordering.getBestek().getDossier_id(), 12, "kcorstje", "Antwoord schuldvordering test1Goedegekeurd1", schuldvordering.getBestek_id())).thenReturn(brief);
        FileInputStream fis = new FileInputStream(FileUtils.toFile(this.getClass().getResource("/Aanvraag_schuldvordering.pdf")));
        Mockito.when(briefService.getFileBrief(Mockito.any(Brief.class))).thenReturn(fis);
        Mockito.when(aanvraagSchuldvorderingExportService.createMeetstaatExportFile(Mockito.any(AanvraagSchuldvordering.class))).thenReturn(null);
        Ambtenaar ambtenaar = new Ambtenaar();
        ambtenaar.setAmbtenaar_b("Koen Corstjens");
        Mockito.when(gebruikerService.getHuidigAmbtenaar()).thenReturn(ambtenaar);
        schuldvordering.setAfgekeurd_jn("N");



        //schuldvorderingExportService.generatePdf(schuldvordering, "kcorstje");
    }
    @Test
    public void exportToPdfAfgekeurd() throws Exception {
        Schuldvordering schuldvordering = mockSchuldvordering("testAfegekeurd1");
        Brief brief = new Brief();
        brief.setBrief_nr("IVS-SI/96010-6/KJA-11/0010");
        Mockito.when(brief.getAuteur()).thenReturn(createAuteur());
        Mockito.when( briefService.makeDocument(schuldvordering.getBestek().getDossier_id(), 12, "kcorstje", "Antwoord schuldvordering testAfegekeurd1", schuldvordering.getBestek_id())).thenReturn(brief);
        FileInputStream fis = new FileInputStream(FileUtils.toFile(this.getClass().getResource("/Aanvraag_schuldvordering.pdf")));
        Mockito.when(briefService.getFileBrief(Mockito.any(Brief.class))).thenReturn(fis);
        Mockito.when(aanvraagSchuldvorderingExportService.createMeetstaatExportFile(Mockito.any(AanvraagSchuldvordering.class))).thenReturn(null);
        Ambtenaar ambtenaar = new Ambtenaar();
        ambtenaar.setAmbtenaar_b("Koen Corstjens");
        Mockito.when(gebruikerService.getHuidigAmbtenaar()).thenReturn(ambtenaar);
        schuldvordering.setAfgekeurd_jn("J");
        //schuldvorderingExportService.generatePdf(schuldvordering, "kcorstje");
    }

    private Ambtenaar createAuteur() {
        Ambtenaar auteur= new Ambtenaar();
        auteur.setAmbtenaar_b("Dirk Damman");
        auteur.setFunctie("ingenieur");
        auteur.setTel1("015/284379");
        auteur.setEmail("ddamman@ovam.be");
        return auteur;
    }

    private Schuldvordering mockSchuldvordering(String naam) {
        Schuldvordering schuldvordering = new Schuldvordering();
        schuldvordering.setVordering_nr(naam);
        schuldvordering.setVordering_d(new Date());
        schuldvordering.setUiterste_d(new Date());
        Bestek bestek = new Bestek();
        bestek.setBestek_id(1l);
        bestek.setDossier_id(2);
        bestek.setType_id(1);
        bestek.setBestek_nr("SI061110");
        bestek.setOmschrijving("omschrijving dosier");

        Dossier dossier = new Dossier();
        dossier.setDossier_b("Test Dossier beschrijving");
        OvamGemeente gemeente = new OvamGemeente();
        gemeente.setNaam("Maaseik");
        Mockito.when(dossier.getGemeente()).thenReturn(gemeente);
        Mockito.when(bestek.getDossier()).thenReturn(dossier);
        Mockito.when(schuldvordering.getBestek()).thenReturn(bestek);
        AanvraagSchuldvordering aanvraagSchuldvordering = new AanvraagSchuldvordering();
        Offerte offerte = new Offerte();
        offerte.setInzender("Inzender 1");
        Brief brief = new Brief();
        Mockito.when(brief.getAdres()).thenReturn(createAdres());
        Mockito.when(brief.getAuteur()).thenReturn(createAuteur());
        offerte.setBrief(brief);
        aanvraagSchuldvordering.setOfferte(offerte);
        schuldvordering.setAanvraagSchuldvordering(aanvraagSchuldvordering);
        aanvraagSchuldvordering.setBeschrijving("23456789");
        schuldvordering.setAfgekeurd_jn("N");
        BriefCategorie briefCategorie = new BriefCategorie();
        briefCategorie.setBeschrijving("ABSW-ANZ");
        briefCategorie.setId(3);
        Mockito.when(schuldvordering.getBriefCategorie()).thenReturn(briefCategorie);
        schuldvordering.setGoedkeuring_d(new Date());
        schuldvordering.setSchuldVorderingSapProjects(createProjecten());
        schuldvordering.setCommentaar("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,");
        schuldvordering.setVan_d(new Date());
        schuldvordering.setTot_d(new Date());
        schuldvordering.setBoete_bedrag(100.0);
        schuldvordering.setGoedkeuring_bedrag(2100.0);
        schuldvordering.setVordering_bedrag(250.0);
        return schuldvordering;
    }

    private List<SchuldVorderingSapProject> createProjecten() {
        List<SchuldVorderingSapProject> schuldVorderingSapProjects = new ArrayList<SchuldVorderingSapProject>();
        schuldVorderingSapProjects.add(createSchuldVorderingSapProject(new BigDecimal(100),"wbs nummer1"));
        schuldVorderingSapProjects.add(createSchuldVorderingSapProject(new BigDecimal(101),"wbs nummer2"));
        return schuldVorderingSapProjects;
    }

    private SchuldVorderingSapProject createSchuldVorderingSapProject(BigDecimal bedrag, String wbs ) {
        SchuldVorderingSapProject schuldVorderingSapProject = new SchuldVorderingSapProject();
        schuldVorderingSapProject.setBedrag(bedrag);
        schuldVorderingSapProject.setWbsNr(wbs);
        SapProject project = new SapProject();
        project.setBudgetairArtikel("7341200");
        project.setBeschrijvingBudgetairArtikel("Verwijderingen en saneringen (EFOS)");
        project.setInitieelAchtNummer("0800011556");
        project.setBoekJaar("2014");
        project.setProjectId("1200002756");
        Mockito.when(schuldVorderingSapProject.getSapProject()).thenReturn(project);
        schuldVorderingSapProject.setSchuldVorderingSapProjectId(null);
        return schuldVorderingSapProject;
    }

    private Adres createAdres() {
        Adres adres = new Adres();
        adres.setNaam("Mourik nv");
        adres.setEmail("info@Mourik.be");
        adres.setGemeente("Kinrooi");
        adres.setPostcode("3500");
        adres.setStraat("Groenendaallaan 399");
        return adres;
    }


}
