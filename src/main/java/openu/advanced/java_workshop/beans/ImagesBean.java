package openu.advanced.java_workshop.beans;

import openu.advanced.java_workshop.model.ImagesRepository;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;

@Named
@ApplicationScoped
public class ImagesBean {
    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        // Browser is rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        else {
            // The browser is requesting the image. Return a real StreamedContent with the image bytes.
            String path = context.getExternalContext().getRequestParameterMap().get("path");
            InputStream inputStream = ImagesRepository.retrieveImage(path);
            return DefaultStreamedContent.builder().stream(() -> inputStream).build();
        }
    }
}
