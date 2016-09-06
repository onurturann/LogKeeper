package net.logkeeper.managedController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import net.logkeeper.spring.service.ParameterService;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "imageBean")
@RequestScoped
public class GraphicImage implements Serializable {
	private static final long serialVersionUID = 1094801825228386363L;
	private Logger logger = Logger.getLogger(GraphicImage.class);
	private DefaultStreamedContent content;
	private String avatarUrl;
	public static URL url;
	
	
	public GraphicImage() {
 	}
	
 
	
	public StreamedContent getImage() throws IOException {	
		
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    BufferedImage img =null;
		    try {
				HttpSession session = SessionBean.getSession();
				avatarUrl= (String) session.getAttribute("avatar24x24");
		    	url = new URL(avatarUrl);
		    	img = ImageIO.read(url);
			} catch (IIOException e) {
				logger.warn("Avatar bulunamammdý.",e);
				url = new URL("http://icons.iconarchive.com/icons/visualpharm/must-have/24/User-icon.png");
		    	img = ImageIO.read(url);
			}

			int w = img.getWidth(null);
			int h = img.getHeight(null);
			
			// image is scaled two times at run time
			int scale = 1;
			BufferedImage bi = new BufferedImage(w * scale, h * scale,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.getGraphics();
			g.drawImage(img, 10, 10, w * scale, h * scale, null);
			ImageIO.write(bi, "png", bos);
			
			return new DefaultStreamedContent(new ByteArrayInputStream(bos.toByteArray()), "image/png");
		}
	}
	
	
}