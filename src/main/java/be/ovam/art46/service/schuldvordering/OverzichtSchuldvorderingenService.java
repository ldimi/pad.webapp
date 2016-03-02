package be.ovam.art46.service.schuldvordering;



import be.ovam.pad.dto.ReportViewRegelDto;

import javax.servlet.ServletOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

/**
 * Created by Koen on 12/01/2015.
 */
public interface OverzichtSchuldvorderingenService {
    LinkedHashMap<String, ReportViewRegelDto> getReportViewDtoForOfferte(Long offerteId) throws Exception;

    LinkedHashMap<String, ReportViewRegelDto> getReportViewDtoForDeelopdracht(Integer deelOpdrachtId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void createExportOfferte(OutputStream outputStream, Long offerteId, Integer deelOpdrachtId) throws Exception;

    void createExportDeelOpdracht(ServletOutputStream op, String title, Integer deelopdrachtId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;
}
