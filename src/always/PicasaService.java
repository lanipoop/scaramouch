package always;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;


public class PicasaService {
    private static final String END_POINT = "https://picasaweb.google.com/data/feed/api/user/default";
    private static PicasaService instance = null;
    private PicasawebService picasa = null;
    private UserFeed userFeed;
    private Map<String, String> albums;
    private URL photoUploadUrl;
    
    public static PicasaService getInstance(){
        if (instance == null){
            synchronized(PicasaService.class) {
                instance = new PicasaService();
            }
        }
        
        return instance;
    }
    
    private PicasaService(){
        picasa = new PicasawebService(UIConstants.ALWAYS_TITLE);
    }
    
    public boolean setCredential(Credential credential) throws Exception {
        picasa.setOAuth2Credentials(credential);
        
        // Test credentials by initializing
        initialize();
        
        return true;
    }
    
    public Map<String, String> getAlbums(){
        return albums;
    }
    
    private void initialize() throws Exception {
        userFeed = picasa.getFeed(new URL(END_POINT), UserFeed.class);
        
        albums = new HashMap<String, String>();
        for (AlbumEntry album : userFeed.getAlbumEntries()) {
            String name = album.getTitle().getPlainText();
            String id = getAlbumId(album.getId());
            albums.put(name, id);
        }
        
        // TODO: Yucky. Put properties somewhere else?
        String album = albums.get(AlwaysClient.getInstance().getProperty("album"));
        
        // TODO: Upload to new album if doesn't exist?!?!
        StringBuilder sb = new StringBuilder();
        sb.append(END_POINT);
        sb.append("/albumid/");
        sb.append(album);
        sb.append("?kind=photo&thumbsize=800");

        photoUploadUrl = new URL(sb.toString());
    }
    
    public Map<String, String> uploadPhotos(List<String> photos) throws Exception {
        Map<String, String> photoLinks = new LinkedHashMap<String, String>();
        for (String path : photos) {
            File photo = new File(path);
            MediaFileSource myMedia = new MediaFileSource(photo, "image/jpeg");
            PhotoEntry returnedPhoto = picasa.insert(photoUploadUrl, PhotoEntry.class, myMedia);
            String imgLink = returnedPhoto.getLinks().get(2).getHref();
            String imgDirect = returnedPhoto.getMediaThumbnails().get(0).getUrl();
            photoLinks.put(imgLink, imgDirect);
        }

        return photoLinks;
    }
    
    private String getAlbumId(String url) {
        String id = url.substring(url.lastIndexOf("/") + 1, url.length());
        return id;
    }
}
