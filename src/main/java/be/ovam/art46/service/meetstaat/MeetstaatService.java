package be.ovam.art46.service.meetstaat;

import be.ovam.art46.service.meetstaat.response.ResponseReadMeetstaatCSV;
import be.ovam.pad.model.Bestek;
import be.ovam.pad.model.MeetstaatRegel;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Koen Corstjens on 29-7-13.
 */
public interface MeetstaatService extends MeetstaatBaseService<MeetstaatRegel> {

    ResponseReadMeetstaatCSV readMeetstaatCSV(Reader reader, Bestek bestekId) throws IOException;

    List<MeetstaatRegel> save(List<MeetstaatRegel> meetstaatRegelList, Long bestekId);

    List<MeetstaatRegel> getAll(Long bestekId);

    MeetstaatRegel get(Long bestekId, String postnr);

    List<MeetstaatRegel> herbereken(List<MeetstaatRegel> meetstaatRegels, BigDecimal btw);

    List<MeetstaatRegel> addNewMeetstaat(MeetstaatRegel newMeetstaatRegel, List<MeetstaatRegel> meetstaatRegels, BigDecimal btw);

    List<MeetstaatRegel> replace(List<MeetstaatRegel> meetstaatRegelList, String tevervangenPostnr, String nieuwPostnr, BigDecimal btw);

    List<String> definitiefmaken(Long bestekId) throws DocumentException, InvocationTargetException, IllegalAccessException;

    void ontgrendelDefinitieveMeetstaat(Long bestekId) throws Exception;

    void save(MeetstaatRegel oldMeetstaatRegel);
}
