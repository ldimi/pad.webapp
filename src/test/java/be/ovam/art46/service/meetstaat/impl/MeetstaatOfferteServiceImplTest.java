package be.ovam.art46.service.meetstaat.impl;

import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.Offerte;
import be.ovam.pad.model.OfferteRegel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by koencorstjens on 29-7-13.
 */
public class MeetstaatOfferteServiceImplTest {
    MeetstaatOfferteServiceImpl meetstaatOfferteService;

    @Before
    public void setUp(){
        meetstaatOfferteService = new MeetstaatOfferteServiceImpl();
    }

    @Test
    public void bereken(){

        List<OfferteRegel> offerteRegelList = new ArrayList<OfferteRegel>();
        addToList(offerteRegelList, "10.1", null);
        addToList(offerteRegelList, "4", null);
        addToList(offerteRegelList, "4.1", null);
        addToList(offerteRegelList, "4.1.1",null, new BigDecimal(1), new BigDecimal(1));
        addToList(offerteRegelList, "4.1.2",null, new BigDecimal(1), new BigDecimal(1));
        addToList(offerteRegelList, "4.1.3",null, null, null);
        addToList(offerteRegelList, "4.1.t",null, null, null);
        addToList(offerteRegelList, "2.2", null);
        addToList(offerteRegelList, "2.1", null);
        addToList(offerteRegelList, "1.1", null, new BigDecimal(1) ,new BigDecimal(1));
        addToList(offerteRegelList, "3.1.1", null);
        addToList(offerteRegelList, "10", null);
        addToList(offerteRegelList, ".t",null, null, null);
        addToList(offerteRegelList, ".tbtw",null, null, null);

        offerteRegelList = meetstaatOfferteService.bereken(offerteRegelList);

        Assert.assertEquals("1.1",offerteRegelList.get(0).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("1.t",offerteRegelList.get(1).getMeetstaatRegel().getPostnr());        
        Assert.assertEquals("2.1",offerteRegelList.get(2).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("2.2",offerteRegelList.get(3).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("3.1.1", offerteRegelList.get(4).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("4",offerteRegelList.get(5).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("4.1",offerteRegelList.get(6).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("4.1.1",offerteRegelList.get(7).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("4.1.2",offerteRegelList.get(8).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("4.1.3",offerteRegelList.get(9).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("4.1.t",offerteRegelList.get(10).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("4.t",offerteRegelList.get(11).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("10",offerteRegelList.get(12).getMeetstaatRegel().getPostnr());
        Assert.assertEquals("10.1",offerteRegelList.get(13).getMeetstaatRegel().getPostnr());
        Assert.assertEquals(".t",offerteRegelList.get(14).getMeetstaatRegel().getPostnr());        
        Assert.assertEquals(".tbtw",offerteRegelList.get(15).getMeetstaatRegel().getPostnr());
        Assert.assertEquals(16, offerteRegelList.size());
        Assert.assertEquals(new BigDecimal(3) ,offerteRegelList.get(14).getPostTotaal());
        Assert.assertEquals(new BigDecimal(3.63).round(new MathContext(2, RoundingMode.HALF_UP)) ,offerteRegelList.get(15).getPostTotaal().round(new MathContext(2, RoundingMode.HALF_UP)));
    }

    private void addToList(List<OfferteRegel> offerteRegels, String postnr, String taak) {
        addToList(offerteRegels, postnr, taak, null, null);
    }

    private void addToList(List<OfferteRegel> offerteRegels, String postnr, String taak, BigDecimal eenheidsprijs, BigDecimal aantal) {
        Offerte offerte = new Offerte();
        offerte.setBtwTarief(21);
        OfferteRegel offerteRegel = new OfferteRegel();
        offerteRegel.setOfferte(offerte);
        offerteRegel.setMeetstaatRegel(new MeetstaatRegel());
        offerteRegel.getMeetstaatRegel().setPostnr(postnr);
        offerteRegel.getMeetstaatRegel().setTaak(taak);
        offerteRegel.getMeetstaatRegel().setType("VH");
        offerteRegel.setEenheidsprijs(eenheidsprijs);
        offerteRegel.setAantal(aantal);
        offerteRegels.add(offerteRegel);
    }

}
