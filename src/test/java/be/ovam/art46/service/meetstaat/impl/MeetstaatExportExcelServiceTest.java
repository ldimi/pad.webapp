package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.model.OfferteForm;
import be.ovam.art46.model.OffertesExport;
import be.ovam.art46.model.OffertesExportRegel;
import be.ovam.art46.service.meetstaat.MeetstaatOfferteService;
import be.ovam.pad.model.Bestek;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.Offerte;
import be.ovam.pad.model.OfferteRegel;
import com.itextpdf.text.DocumentException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koen Corstjens on 30-8-13.
 */
public class MeetstaatExportExcelServiceTest extends MeetstaatExportServiceTest {

    MeetstaatExportExcelServiceImpl meetstaatExportExcelService;
    MeetstaatOfferteService meetstaatOfferteService;

    @Override
    protected void setupExport() throws IOException {
        meetstaatExportExcelService = new MeetstaatExportExcelServiceImpl();
        meetstaatExportService = meetstaatExportExcelService;
        meetstaatOfferteService = Mockito.mock(MeetstaatOfferteService.class);
        meetstaatExportExcelService.setMeetstaatOfferteService(meetstaatOfferteService);
        meetstaatExportExcelService.setBestekService(bestekService);
        createdFile = folder.newFile("myfile.xls");
    }

    @Test
    public void createExcel() {
        try {
            super.create();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void createExcel2() {
        try {
            super.create2();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (DocumentException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void createOfferteExportEmpty() {
        try {
            OfferteForm offerteForm = new OfferteForm();
            offerteForm.setOfferte(new Offerte());
            offerteForm.getOfferte().setBestekId(21l);
            Mockito.when(meetstaatOfferteService.getOfferte(2l)).thenReturn(offerteForm);
            Bestek bestek = new Bestek();
            bestek.setBestek_id(21l);
            bestek.setBestek_nr("21test");
            Mockito.when(bestekService.getBestek(21l)).thenReturn(bestek);
            FileOutputStream outputStream = new FileOutputStream(createdFile);
            meetstaatExportExcelService.createOfferteExport(2, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void createOfferteExport() {
        try {
            OfferteForm offerteForm = new OfferteForm();
            offerteForm.setOfferte(createOfferte(21, "inzender 1"));
            offerteForm.setOfferteRegels(new ArrayList<OfferteRegel>());
            offerteForm.getOfferteRegels().add(createOfferteRegel("1.0"));
            offerteForm.getOfferteRegels().add(createOfferteRegel("1.1"));
            offerteForm.getOfferteRegels().add(createOfferteRegel("1.2"));
            Mockito.when(meetstaatOfferteService.getOfferte(2l)).thenReturn(offerteForm);
            Bestek bestek = new Bestek();
            bestek.setBestek_id(21l);
            bestek.setBestek_nr("21test");
            Mockito.when(bestekService.getBestek(21l)).thenReturn(bestek);
            FileOutputStream outputStream = new FileOutputStream(createdFile);
            meetstaatExportExcelService.createOfferteExport(2, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void createOffertesExport() {
        try {
            OffertesExport offertesExport = new OffertesExport();
            offertesExport.setOffertes(new ArrayList<Offerte>());
            offertesExport.getOffertes().add(createOfferte(21, "inzender 1"));
            offertesExport.getOffertes().add(createOfferte(21, "inzender 2"));
            offertesExport.getOffertes().add(createOfferte(21, "inzender 3"));
            offertesExport.getOffertes().add(createOfferte(21, "inzender 4"));
            offertesExport.getOffertes().add(createOfferte(21, "inzender 5"));
            List<OffertesExportRegel> offertesExportRegelList = new ArrayList<OffertesExportRegel>();
            offertesExportRegelList.add(createOffertesExportRegelTotaal("1"));
            offertesExportRegelList.add(createOffertesExportRegel("1.1"));
            offertesExportRegelList.add(createOffertesExportRegel("1.2"));
            offertesExportRegelList.add(createOffertesExportRegel("1.3"));
            offertesExport.setOffertesExportRegels(offertesExportRegelList);
            Mockito.when(meetstaatOfferteService.getOffertesRegelsForBestek(21l)).thenReturn(offertesExport);
            Bestek bestek = new Bestek();
            bestek.setBestek_id(21l);
            bestek.setBestek_nr("21test");
            Mockito.when(bestekService.getBestek(21l)).thenReturn(bestek);
            FileOutputStream outputStream = new FileOutputStream(createdFile);
            meetstaatExportExcelService.createOffertesExport(21l, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private OffertesExportRegel createOffertesExportRegelTotaal(String s) {
        OffertesExportRegel offertesExportRegel = new OffertesExportRegel();
        offertesExportRegel.setMeetstaatRegel(new MeetstaatRegel());
        offertesExportRegel.getMeetstaatRegel().setPostnr(s);
        offertesExportRegel.setOfferteRegels(new ArrayList<OfferteRegel>());
        offertesExportRegel.getOfferteRegels().add(createOfferteTotaalRegel(s));
        offertesExportRegel.getOfferteRegels().add(createOfferteTotaalRegel(s));
        offertesExportRegel.getOfferteRegels().add(createOfferteTotaalRegel(s));
        offertesExportRegel.getOfferteRegels().add(createOfferteTotaalRegel(s));
        offertesExportRegel.getOfferteRegels().add(createOfferteTotaalRegel(s));
        return offertesExportRegel;
    }

    private OfferteRegel createOfferteTotaalRegel(String postnr) {
        OfferteRegel offerteRegel = new OfferteRegel();
        offerteRegel.setMeetstaatRegel(new MeetstaatRegel());
        offerteRegel.getMeetstaatRegel().setPostnr(postnr);
        return offerteRegel;
    }

    private Offerte createOfferte(int i, String inzender) {
        Offerte offerte = new Offerte();
        offerte.setBestekId(21l);
        offerte.setInzender(inzender);
        return offerte;
    }

    private OffertesExportRegel createOffertesExportRegel(String s) {
        OffertesExportRegel offertesExportRegel = new OffertesExportRegel();
        offertesExportRegel.setMeetstaatRegel(new MeetstaatRegel());
        offertesExportRegel.getMeetstaatRegel().setPostnr(s);
        offertesExportRegel.setAantal(new BigDecimal(2.0));
        offertesExportRegel.setEenheidsprijs(new BigDecimal(2.0));
        offertesExportRegel.setRegelTotaal(new BigDecimal(4.0));
        offertesExportRegel.setOfferteRegels(new ArrayList<OfferteRegel>());
        offertesExportRegel.getOfferteRegels().add(createOfferteRegel(s));
        offertesExportRegel.getOfferteRegels().add(createOfferteRegel(s));
        offertesExportRegel.getOfferteRegels().add(createOfferteRegel(s));
        offertesExportRegel.getOfferteRegels().add(createOfferteRegel(s));
        offertesExportRegel.getOfferteRegels().add(createOfferteRegel(s));
        return offertesExportRegel;
    }


    private OfferteRegel createOfferteRegel(String postnr) {
        OfferteRegel offerteRegel = new OfferteRegel();
        offerteRegel.setMeetstaatRegel(new MeetstaatRegel());
        offerteRegel.getMeetstaatRegel().setPostnr(postnr);
        offerteRegel.setAantal(new BigDecimal(4.0));
        offerteRegel.setEenheidsprijs(new BigDecimal(15.5));
        offerteRegel.setRegelTotaal(new BigDecimal(61.0));
        return offerteRegel;
    }

}
