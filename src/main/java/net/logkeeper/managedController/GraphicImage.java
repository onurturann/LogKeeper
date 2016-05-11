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
import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.logkeeper.spring.service.ParameterService;

@ManagedBean(name = "imageBean")
@RequestScoped
public class GraphicImage implements Serializable {
	private static final long serialVersionUID = 1094801825228386363L;

	public StreamedContent getImage() throws IOException {	
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			NetClientGet ncg=new NetClientGet();
		    JSONObject obj = new JSONObject(NetClientGet.getJsonDataStr());
			URL url = new URL(obj.getJSONObject("avatarUrls").getString("avatarTest"));
			BufferedImage img = ImageIO.read(url);	
			int w = img.getWidth(null);
			int h = img.getHeight(null);
			// image is scaled two times at run time
			int scale = 1;
			BufferedImage bi = new BufferedImage(w * scale, h * scale,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.getGraphics();
			g.drawImage(img, 10, 10, w * scale, h * scale, null);
			ImageIO.write(bi, "png", bos);
			return new DefaultStreamedContent(new ByteArrayInputStream(
					bos.toByteArray()), "image/png");
		}
	}
	private void writeImage(ParameterService parameterService){
		
	}
}