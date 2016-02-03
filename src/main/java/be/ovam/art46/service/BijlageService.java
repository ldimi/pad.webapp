package be.ovam.art46.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Koen on 15/05/2014.
 */
public interface BijlageService {
    String saveBijlageForVoorstelDeelopdracht(Long voorstelDeelopdrachtId, MultipartFile multipartFile) throws IOException;

    void deleteForVoorstelDeelopdracht(Long voorstelDeelopdrachtId, Long bijlageId);
}
