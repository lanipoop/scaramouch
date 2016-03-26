package always;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class AlwaysClient {
    private static AlwaysClient instance;
    private Properties properties;
    private Frame frame;
    private GoogleAuth googleAuth = GoogleAuth.getInstance();
    private Template template = new Template();
    
    public static AlwaysClient getInstance() {
        synchronized(AlwaysClient.class) {
            if (instance == null) {
                instance = new AlwaysClient();
            }
            
            return instance;
        }
    }
    
    public void init(Properties properties) throws Exception {
        frame = new Frame();
        this.properties = properties;
        
        // Load the template
        String templatePath = getProperty("template");
        String baseTemplate;
        if (templatePath == null || templatePath.isEmpty()) { // If no template set, use default template
            baseTemplate = IOUtils.toString(this.getClass().getResourceAsStream("/Resources/template.txt"), "UTF-8");
        } else { // Otherwise use custom template
            FileInputStream inputStream = new FileInputStream(templatePath);
            baseTemplate = IOUtils.toString(inputStream);
            // TODO: if path invalid, use custom? open settings dialog? give options?!?!?
        }
        
        template.setBaseTemplate(baseTemplate);
        
        // Get the access token. If it's invalid,
        // open the authorization dialog.
        if (!googleAuth.isTokenValid(properties.getProperty("accessToken"))) {
            googleAuth.showDialog();
        }

        // If this is the first time logging in, open settings dialog.
        // Otherwise, display Frame.
        if (Boolean.getBoolean(properties.getProperty("firstlogin"))) {
            new SettingsDialog();
        } else {
            frame.setVisible(true);
        }
    }
    
    public Frame getFrame() {
        return frame;
    }
    
    public Template getTemplate() {
        return template;
    }
//    
//    public String generateTemplate() {
//        Map<String, String> replacementMap = new HashMap<String, String>();
//        StringBuilder sb = new StringBuilder();
//
//        DescriptionPanel descriptionPanel = parent.descriptionPanel;
//        String brand = descriptionPanel.brandTextField.getText();
//        String title = descriptionPanel.titleTextField.getText();
//        String size = descriptionPanel.sizeTextField.getText();
//        sb.append(brand);
//        sb.append(" ");
//        sb.append(title);
//        sb.append(" Size ");
//        sb.append(size);
//        String fullTitle = sb.toString();
//        replacementMap.put(UIConstants.REPLACE_TITLE, fullTitle);
//
//        String description = descriptionPanel.descriptionTextArea.getText().replaceAll("(\r|\n)", "<br>");
//        replacementMap.put(UIConstants.REPLACE_DESCRIPTION, description);
//
//        sb = new StringBuilder();
//        for (Component component : UIConstants.FRAME.measurementPanel.getComponents()) {
//            JPanel panel = (JPanel) component;
//            boolean typeSet = false;
//            for (int i = 2; i < panel.getComponents().length; i++) {
//                String subType = ((TitledBorder) panel.getBorder()).getTitle();
//                Component panelComp = panel.getComponent(i);
//                if (panelComp.getClass() == JTextField.class) {
//                    JTextField measurementTextField = (JTextField) panelComp;
//                    if (!measurementTextField.getText().isEmpty()) {
//                        if (!typeSet) {
//                            JTextField subSize = (JTextField) panel.getComponent(1);
//                            sb.append("<BR>");
//                            sb.append(pearlIcon);
//                            sb.append(brand);
//                            sb.append(" ");
//                            sb.append(subType.toUpperCase());
//                            sb.append(": SIZE ");
//                            sb.append(subSize.getText());
//                            sb.append("<BR>");
//                            typeSet = true;
//                        }
//                        JLabel measurementLabel = (JLabel) panel.getComponent(i - 1);
//                        sb.append(measurementLabel.getText());
//                        sb.append(" ");
//                        sb.append(measurementTextField.getText());
//                        sb.append("\" ");
//                    }
//                }
//            }
//        }
//
//        replacementMap.put(UIConstants.REPLACE_MEASUREMENTS, sb.toString());
//
//        sb = new StringBuilder();
//        String type = descriptionPanel.typeTextField.getText();
//        String typeLower = type.toLowerCase();
//        sb.append(typeLower.equals("pants") || typeLower.equals("shoes") ? "These " + typeLower + " are" : "This " + typeLower + " is");
//        switch ((UIConstants.CONDITIONS) descriptionPanel.conditionComboBox.getSelectedItem()) {
//            case NWT:
//                sb.append(" new with tags.");
//                break;
//            case NWOT:
//                sb.append(" new without tags.");
//                break;
//            case Preowned:
//                sb.append(" preowned.");
//                break;
//            case Great:
//                sb.append(" in great condition.");
//                break;
//            case Good:
//                sb.append(" in good condition.");
//                break;
//        }
//
//        String defects = descriptionPanel.defectTextField.getText();
//        if (!defects.isEmpty()) {
//            sb.append("<BR>");
//            sb.append(defects);
//        }
//
//        replacementMap.put(UIConstants.REPLACE_CONDITION, sb.toString());
//
//        // PHOTOS HERE
//        sb = new StringBuilder();
//
//        for (Map.Entry<String, String> link : photoLinks.entrySet()) {
//            sb.append("<DIV>");
//            sb.append("<a href=\"");
//            sb.append(link.getKey());
//            sb.append("\" target=\"_blank\">");
//            sb.append("<img src=\"");
//            sb.append(link.getValue());
//            sb.append("\"></a>");
//            sb.append("<p>");
//            sb.append("</DIV>");
//        }
//
//        replacementMap.put(UIConstants.REPLACE_PHOTOS, sb.toString());
//
//        try {
//            eBayTemplate = UIConstants.FRAME.getItem();
//            eBayTemplate = eBayTemplate.replaceAll("STR_TITLE", fullTitle);
//            for (Map.Entry<String, String> entry : replacementMap.entrySet()) {
//                eBayTemplate = eBayTemplate.replaceFirst(entry.getKey(), entry.getValue());
//            }
//
//            String bigCommerceStart = "<!--START BIGCOMMERCE-->";
//            String bigCommerceEnd = "<!--END BIGCOMMERCE-->";
//            bigCommerceTemplate = eBayTemplate.substring(eBayTemplate.indexOf(bigCommerceStart) + bigCommerceStart.length(), eBayTemplate.indexOf(bigCommerceEnd));
//
//            // Get rid of the eBay stuff
//            String bigCommerceRemoveStart = "<!--START BC REMOVE-->";
//            String bigCommerceRemoveEnd = "<!--END BC REMOVE-->";
//            String bcRemove = bigCommerceTemplate.substring(bigCommerceTemplate.indexOf(bigCommerceRemoveStart) + bigCommerceRemoveStart.length(), bigCommerceTemplate.indexOf(bigCommerceRemoveEnd));
//            System.out.println(bcRemove);
//            bigCommerceTemplate = bigCommerceTemplate.replace(bcRemove, "");
//
//            String sku = descriptionPanel.skuTextField.getText();
//            String format = ".txt";
//
//            Properties properties = UIConstants.FRAME.getProperties();
//            String writeDir = properties.getProperty("writeDirectory");
//
//            File directory = new File(writeDir + "/eBay");
//            directory.mkdir();
//            File file = new File(directory.getAbsolutePath() + "/" + sku + format);
//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write(eBayTemplate);
//            fileWriter.close();
//
//            directory = new File(writeDir + "/BC");
//            directory.mkdir();
//            file = new File(directory.getAbsolutePath() + "/" + sku + format);
//            fileWriter = new FileWriter(file);
//            fileWriter.write(bigCommerceTemplate);
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        setProperties(properties);
    }

    public void setProperties(Properties properties) {
        try {
            // There's a bug here somewhere...
            OutputStream output = new FileOutputStream("always.properties");
            properties.store(output, null);
            output.close();
            this.properties = properties;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
