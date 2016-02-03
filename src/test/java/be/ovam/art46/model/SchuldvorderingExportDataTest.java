package be.ovam.art46.model;

import be.ovam.art46.service.schuldvordering.SchuldvorderingExportService;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Koen on 4/07/2014.
 */
public class SchuldvorderingExportDataTest {
    @Test
    public void getFunctie(){
        SchuldvorderingExportData schuldvorderingExportData = new SchuldvorderingExportData();
        schuldvorderingExportData.setOndertekenaarFunctie("ingenieur");
        schuldvorderingExportData.setHandtekeningNaam("acuycken");
        Assert.assertEquals("Ingenieur",schuldvorderingExportData.getOndertekenaarFunctie());
    }

    @Test
    public void getFunctieDraft(){
        SchuldvorderingExportData schuldvorderingExportData = new SchuldvorderingExportData();
        schuldvorderingExportData.setOndertekenaarFunctie("ingenieur");
        schuldvorderingExportData.setHandtekeningNaam(SchuldvorderingExportService.DRAFT);
        Assert.assertEquals("?????????",schuldvorderingExportData.getOndertekenaarFunctie());
    }
}
