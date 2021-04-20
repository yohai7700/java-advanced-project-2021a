package openu.advanced.java_workshop.beans;

import openu.advanced.java_workshop.ImagesRepository;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;

/**
 * A bean that is used to stream images by given request parameters.
 * Used for handling graphicImage tags request behavior.
 */
@Named
@ApplicationScoped
public class ImagesBean {
    /**
     * Receives a path parameter via request param and returns a streamed content
     * if the browser requests the image value, else returns a stub
     *
     * @return streamed content of the image, returns a stub id the HTML is only rendering.
     * @throws IOException if the image isn't found in repository.
     */
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
