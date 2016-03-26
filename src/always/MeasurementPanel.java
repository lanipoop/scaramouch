package always;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class MeasurementPanel extends CardPanel {
    private final static String LAYOUT_CONSTRAINT = "insets 4, wrap 8";
    private final static String COLUMN_CONSTRAINT = "[right, 50!]12[left, 40!, fill]push[right, 60!]12[left, 40!, fill]push[right, 60!]12[left, 40!, fill]push[right]12[left, 40!, fill]";
    private final static String ROW_CONSTRAINT = "[26!, fill]6[26!, fill]";

    public MeasurementPanel() {
        setVisible(true);
        initComponents();
        initLayout();
    }

    protected void initComponents() {
        setLayout(new MigLayout("insets 0, wrap, fill", "[grow, fill]", "[grow, fill]push"));

        // Jacket
        jacketPanel = new JPanel();
        jacketPanel.setLayout(new MigLayout(LAYOUT_CONSTRAINT, COLUMN_CONSTRAINT, ROW_CONSTRAINT));
        jacketPanel.setBorder(BorderFactory.createTitledBorder("Jacket"));
        jacketSizeLabel = new JLabel("Size:");
        jacketSizeTextField = new JTextField();
        jacketShouldersLabel = new JLabel("Shoulders:");
        jacketShouldersTextField = new JTextField();
        jacketBustLabel = new JLabel("Bust:");
        jacketBustTextField = new JTextField();
        jacketWaistLabel = new JLabel("Waist:");
        jacketWaistTextField = new JTextField();
        jacketHipsLabel = new JLabel("Hips:");
        jacketHipsTextField = new JTextField();
        jacketLengthLabel = new JLabel("Length:");
        jacketLengthTextField = new JTextField();
        jacketSleevesLabel = new JLabel("Sleeves:");
        jacketSleevesTextField = new JTextField();

        // Top
        topPanel = new JPanel();
        topPanel.setLayout(new MigLayout(LAYOUT_CONSTRAINT, COLUMN_CONSTRAINT, ROW_CONSTRAINT));
        topPanel.setBorder(BorderFactory.createTitledBorder("Top"));
        topSizeLabel = new JLabel("Size:");
        topSizeTextField = new JTextField();
        topBustLabel = new JLabel("Bust:");
        topBustTextField = new JTextField();
        topWaistLabel = new JLabel("Waist:");
        topWaistTextField = new JTextField();
        topHipsLabel = new JLabel("Hips:");
        topHipsTextField = new JTextField();
        topLengthLabel = new JLabel("Length:");
        topLengthTextField = new JTextField();

        // Skirt
        skirtPanel = new JPanel();
        skirtPanel.setLayout(new MigLayout(LAYOUT_CONSTRAINT, COLUMN_CONSTRAINT, ROW_CONSTRAINT));
        skirtPanel.setBorder(BorderFactory.createTitledBorder("Skirt"));
        skirtSizeLabel = new JLabel("Size:");
        skirtSizeTextField = new JTextField();
        skirtWaistLabel = new JLabel("Waist:");
        skirtWaistTextField = new JTextField();
        skirtHipsLabel = new JLabel("Hips:");
        skirtHipsTextField = new JTextField();
        skirtLengthLabel = new JLabel("Length:");
        skirtLengthTextField = new JTextField();
        skirtSlitLabel = new JLabel("Slit:");
        skirtSlitTextField = new JTextField();

        // Pants
        pantsPanel = new JPanel();
        pantsPanel.setLayout(new MigLayout(LAYOUT_CONSTRAINT, COLUMN_CONSTRAINT, ROW_CONSTRAINT));
        pantsPanel.setBorder(BorderFactory.createTitledBorder("Pants"));
        pantsSizeLabel = new JLabel("Size:");
        pantsSizeTextField = new JTextField();
        pantsWaistLabel = new JLabel("Waist:");
        pantsWaistTextField = new JTextField();
        pantsHipsLabel = new JLabel("Hips:");
        pantsHipsTextField = new JTextField();
        pantsLengthLabel = new JLabel("Length:");
        pantsLengthTextField = new JTextField();
        pantsInseamLabel = new JLabel("Inseam:");
        pantsInseamTextField = new JTextField();
        pantsRiseLabel = new JLabel("Rise:");
        pantsRiseTextField = new JTextField();

        // Dress
        dressPanel = new JPanel();
        dressPanel.setLayout(new MigLayout(LAYOUT_CONSTRAINT, COLUMN_CONSTRAINT, ROW_CONSTRAINT));
        dressPanel.setBorder(BorderFactory.createTitledBorder("Dress"));
        dressSizeLabel = new JLabel("Size:");
        dressSizeTextField = new JTextField();
        dressShouldersLabel = new JLabel("Shoulders:");
        dressShouldersTextField = new JTextField();
        dressBustLabel = new JLabel("Bust:");
        dressBustTextField = new JTextField();
        dressWaistLabel = new JLabel("Waist:");
        dressWaistTextField = new JTextField();
        dressHipsLabel = new JLabel("Hips:");
        dressHipsTextField = new JTextField();
        dressLengthLabel = new JLabel("Length:");
        dressLengthTextField = new JTextField();
    }

    protected void initLayout() {
        jacketPanel.add(jacketSizeLabel);
        jacketPanel.add(jacketSizeTextField);
        jacketPanel.add(jacketShouldersLabel);
        jacketPanel.add(jacketShouldersTextField);
        jacketPanel.add(jacketBustLabel);
        jacketPanel.add(jacketBustTextField);
        jacketPanel.add(jacketWaistLabel);
        jacketPanel.add(jacketWaistTextField);
        jacketPanel.add(jacketHipsLabel);
        jacketPanel.add(jacketHipsTextField);
        jacketPanel.add(jacketLengthLabel);
        jacketPanel.add(jacketLengthTextField);
        jacketPanel.add(jacketSleevesLabel);
        jacketPanel.add(jacketSleevesTextField);

        topPanel.add(topSizeLabel);
        topPanel.add(topSizeTextField);
        topPanel.add(topBustLabel);
        topPanel.add(topBustTextField);
        topPanel.add(topWaistLabel);
        topPanel.add(topWaistTextField);
        topPanel.add(topHipsLabel);
        topPanel.add(topHipsTextField);
        topPanel.add(topLengthLabel);
        topPanel.add(topLengthTextField);

        skirtPanel.add(skirtSizeLabel);
        skirtPanel.add(skirtSizeTextField);
        skirtPanel.add(skirtWaistLabel);
        skirtPanel.add(skirtWaistTextField);
        skirtPanel.add(skirtHipsLabel);
        skirtPanel.add(skirtHipsTextField);
        skirtPanel.add(skirtLengthLabel);
        skirtPanel.add(skirtLengthTextField);
        skirtPanel.add(skirtSlitLabel);
        skirtPanel.add(skirtSlitTextField);

        pantsPanel.add(pantsSizeLabel);
        pantsPanel.add(pantsSizeTextField);
        pantsPanel.add(pantsWaistLabel);
        pantsPanel.add(pantsWaistTextField);
        pantsPanel.add(pantsHipsLabel);
        pantsPanel.add(pantsHipsTextField);
        pantsPanel.add(pantsLengthLabel);
        pantsPanel.add(pantsLengthTextField);
        pantsPanel.add(pantsInseamLabel);
        pantsPanel.add(pantsInseamTextField);
        pantsPanel.add(pantsRiseLabel);
        pantsPanel.add(pantsRiseTextField);

        dressPanel.add(dressSizeLabel);
        dressPanel.add(dressSizeTextField);
        dressPanel.add(dressShouldersLabel);
        dressPanel.add(dressShouldersTextField);
        dressPanel.add(dressBustLabel);
        dressPanel.add(dressBustTextField);
        dressPanel.add(dressWaistLabel);
        dressPanel.add(dressWaistTextField);
        dressPanel.add(dressHipsLabel);
        dressPanel.add(dressHipsTextField);
        dressPanel.add(dressLengthLabel);
        dressPanel.add(dressLengthTextField);

        add(jacketPanel);
        add(topPanel);
        add(skirtPanel);
        add(pantsPanel);
        add(dressPanel);
    }
    
    @Override
    public void next() {
        List<Measurements> measurementsList = new ArrayList<Measurements>();
        
        for (Component component : getComponents()) {
            JPanel panel = (JPanel) component;
            Measurements measurements = new Measurements();
            measurements.setType(((TitledBorder) panel.getBorder()).getTitle().toUpperCase());
            measurements.setSize(((JTextField) panel.getComponent(1)).getText());
            Map<String, String> measurementMap = new HashMap<String, String>();
            
            for (int i = 2; i < panel.getComponents().length; i++) {
                Component panelComp = panel.getComponent(i);
                if (panelComp.getClass() == JTextField.class) {
                    JTextField measurementTextField = (JTextField) panelComp;
                    if (!measurementTextField.getText().isEmpty()) {
                        JLabel measurementLabel = (JLabel) panel.getComponent(i - 1);
                        measurementMap.put(measurementLabel.getText(), measurementTextField.getText());
                    }
                }
            }
            
            if (!measurementMap.isEmpty()) {
                measurements.setMeasurements(measurementMap);
                measurementsList.add(measurements);
            }
        }
        
        template.setMeasurements(measurementsList);
        super.next();
    }

    @Override
    public void clear() {
        for (Component component : getComponents()) {
            JPanel panel = (JPanel) component;
            for (Component subComponent : panel.getComponents()) {
                if (subComponent.getClass() == JTextField.class) {
                    ((JTextField) subComponent).setText("");
                }
            }
        }
    }

    public JPanel jacketPanel;
    public JLabel jacketSizeLabel;
    public JTextField jacketSizeTextField;
    public JLabel jacketShouldersLabel;
    public JTextField jacketShouldersTextField;
    public JLabel jacketBustLabel;
    public JTextField jacketBustTextField;
    public JLabel jacketWaistLabel;
    public JTextField jacketWaistTextField;
    public JLabel jacketHipsLabel;
    public JTextField jacketHipsTextField;
    public JLabel jacketLengthLabel;
    public JTextField jacketLengthTextField;
    public JLabel jacketSleevesLabel;
    public JTextField jacketSleevesTextField;

    public JPanel topPanel;
    public JLabel topSizeLabel;
    public JTextField topSizeTextField;
    public JLabel topBustLabel;
    public JTextField topBustTextField;
    public JLabel topWaistLabel;
    public JTextField topWaistTextField;
    public JLabel topHipsLabel;
    public JTextField topHipsTextField;
    public JLabel topLengthLabel;
    public JTextField topLengthTextField;

    public JPanel skirtPanel;
    public JLabel skirtSizeLabel;
    public JTextField skirtSizeTextField;
    public JLabel skirtWaistLabel;
    public JTextField skirtWaistTextField;
    public JLabel skirtHipsLabel;
    public JTextField skirtHipsTextField;
    public JLabel skirtLengthLabel;
    public JTextField skirtLengthTextField;
    public JLabel skirtSlitLabel;
    public JTextField skirtSlitTextField;

    public JPanel pantsPanel;
    public JLabel pantsSizeLabel;
    public JTextField pantsSizeTextField;
    public JLabel pantsWaistLabel;
    public JTextField pantsWaistTextField;
    public JLabel pantsHipsLabel;
    public JTextField pantsHipsTextField;
    public JLabel pantsLengthLabel;
    public JTextField pantsLengthTextField;
    public JLabel pantsInseamLabel;
    public JTextField pantsInseamTextField;
    public JLabel pantsRiseLabel;
    public JTextField pantsRiseTextField;

    public JPanel dressPanel;
    public JLabel dressSizeLabel;
    public JTextField dressSizeTextField;
    public JLabel dressShouldersLabel;
    public JTextField dressShouldersTextField;
    public JLabel dressBustLabel;
    public JTextField dressBustTextField;
    public JLabel dressWaistLabel;
    public JTextField dressWaistTextField;
    public JLabel dressHipsLabel;
    public JTextField dressHipsTextField;
    public JLabel dressLengthLabel;
    public JTextField dressLengthTextField;
}
