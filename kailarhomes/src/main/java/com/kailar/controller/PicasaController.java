package com.kailar.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;





import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.media.mediarss.MediaContent;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

@Controller
public class PicasaController {
	private PicasawebService service;


	public PicasaController() {
		this.service = new PicasawebService("kailarhomes");
		try {
			this.service.setUserCredentials("admin@haalkerehomes.com", "Bhagavan@22416");
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// This method returns the list of photos from the given URL 

	public List<PhotoEntry> getPhotoEntries(String baseURL)  {
		baseURL = "https://picasaweb.google.com/data/feed/api/user/admin@haalkerehomes.com";
		StringBuilder builder = new StringBuilder(baseURL);
		builder.append("?kind=photo");
		System.out.println("Final URL: "+builder.toString());
		AlbumFeed albumFeed = null;
		try {
			albumFeed =	service.getFeed(new URL(builder.toString()), AlbumFeed.class);
		} catch (IOException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (PhotoEntry photo : albumFeed.getPhotoEntries()) {

			MediaContent mediaContent =  photo.getMediaContents().get(0);
			mediaContent.getUrl();
		}

		return albumFeed.getPhotoEntries();

	}


	
	@RequestMapping("/photos")
	public ModelAndView getPhotos(){

		List<PhotoEntry>  photoEntries = null;

		photoEntries = this.getPhotoEntries("");

		JSONArray urls = new JSONArray();
		JSONObject titles = new JSONObject();
		for (int i = 0; i < photoEntries.size(); i++) {
			PhotoEntry photo = photoEntries.get(i);
			if(photo != null){
				List<MediaContent> mediaContents = photo.getMediaContents();
				for (int j = 0; j < mediaContents.size(); j++) {
					MediaContent mediaContent = mediaContents.get(j);
					if (mediaContent!=null && mediaContent.getUrl()!=null) {
						String url = mediaContent.getUrl();
						if(url.endsWith("jpg")||url.endsWith("JPG")){
							urls.add(url);
							String caption = photo.getDescription().getPlainText();
							if(caption!=null){
								titles.put(url,caption);
							}
						}
					}
				}
			}
		}

		JSONObject output = new JSONObject();
		output.put("URL", urls);
		output.put("CAPTION", titles);

		return new ModelAndView("index", "photos", output);
	}

}
