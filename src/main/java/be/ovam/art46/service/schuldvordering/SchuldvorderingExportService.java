package be.ovam.art46.service.schuldvordering;

import be.ovam.pad.model.Schuldvordering;

import javax.servlet.ServletOutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by Koen on 19/03/14.
 */

public interface SchuldvorderingExportService {

    public final static String DRAFT = "draft";

    public byte[] makeSchuldvorderingPdf(Schuldvordering schuldvordering, String handtekeningNaam) throws Exception;

    void createDraftExport(Integer schuldvorderingId, ServletOutputStream op) throws Exception;


    String handtekenningBeschikbaar() throws ExecutionException;

}
