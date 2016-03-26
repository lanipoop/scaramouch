package always;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template {
    private final static String TITLE = "STR_TITLE";
    private final static String DESCRIPTION = "STR_DESCRIPTION";
    private final static String MEASUREMENTS = "STR_MEASUREMENTS";
    private final static String CONDITION = "STR_CONDITION";
    private final static String PHOTOS = "STR_PHOTOS";
    
    private final static String BIG_COMMERCE_START = "<!--START BIGCOMMERCE-->";
    private final static String BIG_COMMERCE_END = "<!--END BIGCOMMERCE-->";
    private final static String REMOVE_START = "<!--START BC REMOVE-->";
    private final static String REMOVE_END = "<!--END BC REMOVE-->";

    private final static String FORMAT = ".txt";
    private final static String BR = "<BR>";
    private final static String PEARL_ICON = "<IMG src=\"http://i151.photobucket.com/albums/s123/getframednow/misc/littleblackpearl.png\">";
    
    private String sku;
    private String brand;
    private String title;
    private String size;
    private String description;
    private Condition condition;
    private List<Measurements> measurements;
    private String type;
    private String defects;
    private Map<String, String> photos;
    
    private String baseTemplate;
    private String ebayTemplate;
    private String bigCommerceTemplate;
    
    public void setBaseTemplate(String baseTemplate) {
        this.baseTemplate = baseTemplate;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setMeasurements(List<Measurements> measurements) {
        this.measurements = measurements;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDefects(String defects) {
        this.defects = defects;
    }

    public void setPhotos(List<String> photoUrls) throws Exception {
        Collections.sort(photoUrls);
        photos = PicasaService.getInstance().uploadPhotos(photoUrls);
    }

    public void generate() {
        Map<String, String> replacements = new HashMap<String, String>();

        String replaceTitle = getTitle();
        replacements.put(DESCRIPTION, description);
        replacements.put(MEASUREMENTS, getMeasurements());
        replacements.put(CONDITION, getCondition());
        replacements.put(PHOTOS, getPhotos());
        
        ebayTemplate = baseTemplate.replaceAll(TITLE, replaceTitle);
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            ebayTemplate = ebayTemplate.replaceFirst(entry.getKey(), entry.getValue());
        }
        
        // Copy over the relevant stuff to the big commerce template
        int start = ebayTemplate.indexOf(BIG_COMMERCE_START) + BIG_COMMERCE_START.length();
        int end = ebayTemplate.indexOf(BIG_COMMERCE_END);
        bigCommerceTemplate = ebayTemplate.substring(start, end);
        
        // Get rid of the ebay stuff
        start = bigCommerceTemplate.indexOf(REMOVE_START) + REMOVE_START.length();
        end = bigCommerceTemplate.indexOf(REMOVE_END);
        String removedText = bigCommerceTemplate.substring(start, end);
        
        bigCommerceTemplate = bigCommerceTemplate.replace(removedText, "");
        
    }
    
    public void save() throws Exception {
        writeTo("eBay", ebayTemplate);
        writeTo("BC", bigCommerceTemplate);
    }
    
    private void writeTo(String dirName, String template) throws Exception {
        String writeDirectory = AlwaysClient.getInstance().getProperty("imageDirectory");
        File directory = new File(writeDirectory + "/" + dirName);
        directory.mkdir();
        File file = new File(directory.getAbsolutePath() + "/" + sku + FORMAT);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(template);
        fileWriter.close();
    }

    private String getTitle() {
        StringBuilder sb = new StringBuilder();

        sb.append(brand);
        sb.append(" ");
        sb.append(title);
        sb.append(" Size ");
        sb.append(size);

        return sb.toString();
    }

    private String getMeasurements() {
        StringBuilder sb = new StringBuilder();
        for (Measurements measurement : measurements) {
            sb.append(BR);
            sb.append(PEARL_ICON);
            sb.append(brand);
            sb.append(" ");
            sb.append(measurement.getType());
            sb.append(": SIZE ");
            sb.append(measurement.getSize());
            sb.append(BR);

            for (Map.Entry<String, String> entry : measurement.getMeasurements().entrySet()) {
                sb.append(entry.getKey());
                sb.append(" ");
                sb.append(entry.getValue());
                sb.append("\" ");
            }
        }
        return sb.toString();
    }

    private String getCondition() {
        StringBuilder sb = new StringBuilder();
        if (type.equals("pants") || type.equals("shoes")) {
            sb.append("These ");
            sb.append(type);
            sb.append(" are ");
        } else {
            sb.append("This ");
            sb.append(type);
            sb.append(" is ");
        }

        sb.append(condition.getDescription());
        sb.append(".");

        if (!defects.isEmpty()) {
            sb.append(BR);
            sb.append(defects);
        }

        return sb.toString();
    }

    private String getPhotos() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> photo : photos.entrySet()) {
            sb.append("<DIV>");
            sb.append("<a href=\"");
            sb.append(photo.getKey());
            sb.append("\" target=\"_blank\">");
            sb.append("<img src=\"");
            sb.append(photo.getValue());
            sb.append("\"></a>");
            sb.append("<p>");
            sb.append("</DIV>");
        }
        return sb.toString();
    }
}
