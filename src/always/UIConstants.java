package always;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class UIConstants {
	public final static String ALWAYS_TITLE = "Always a Touch of Class";
	
	public final static String DESCRIPTION = "Description";
	public final static String MEASUREMENT = "Measurement";
	public final static String PHOTO = "Photo";
	public final static String TEMPLATE = "Template";
	

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";

    /*
     * Get the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
