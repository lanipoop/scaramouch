package always;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

public class DescriptionPanel extends CardPanel {
    public DescriptionPanel() {
        initComponents();
        initLayout();
    }

    protected void initComponents() {
        setLayout(new MigLayout("insets 0,  gap 12", "[30!, left][80::, grow, fill][right][80::, grow, fill][right][left]"));

        titleLabel = new JLabel("Title:");
        titleTextField = new JTextField();
        titleTextField.setToolTipText("Do not include brand and size in title!");

        skuLabel = new JLabel("SKU:");
        skuTextField = new JTextField();

        brandLabel = new JLabel("Brand:");
        brandTextField = new JTextField();
        brandTextField.setText("St. John Knit");
        brandTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        brandTextField.selectAll();
                    }
                });
            }
        });

        conditionLabel = new JLabel("Condition:");
        conditionComboBox = new JComboBox(Condition.values());
        conditionComboBox.setBackground(Color.WHITE);

        typeLabel = new JLabel("Type:");
        typeTextField = new JTextField();

        sizeLabel = new JLabel("Size:");
        sizeTextField = new JTextField();

        descriptionLabel = new JLabel("Description:");
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setBorder(typeTextField.getBorder());
        descriptionTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    if (e.getModifiers() > 0) {
                        descriptionTextArea.transferFocusBackward();
                    } else {
                        descriptionTextArea.transferFocus();
                    }
                    e.consume();
                }
            }
        });
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);

        defectLabel = new JLabel("Defects:");
        defectTextField = new JTextField();
    }

    protected void initLayout() {
        add(titleLabel);
        add(titleTextField, "grow, spanx, wrap, height 26");
        add(skuLabel);
        add(skuTextField, "height 26");
        add(brandLabel);
        add(brandTextField, "height 26");
        add(conditionLabel);
        add(conditionComboBox, "wrap");
        add(typeLabel);
        add(typeTextField, "height 26");
        add(sizeLabel);
        add(sizeTextField, "wrap, height 26");
        add(descriptionLabel, "wrap, alignx left, spanx");
        add(descriptionTextArea, "grow, spanx, push");
        add(defectLabel, "spanx, wrap, alignx left");
        add(defectTextField, "spanx, grow, height 26");
    }
    
    @Override
    public void next() {
        template.setBrand(brandTextField.getText());
        template.setTitle(titleTextField.getText());
        template.setSize(sizeTextField.getText());
        template.setDescription(descriptionTextArea.getText());
        template.setType(typeTextField.getText().toLowerCase());
        template.setCondition((Condition) conditionComboBox.getSelectedItem());
        template.setDefects(defectTextField.getText());
        
        super.next();
    }

    @Override
    public void clear() {
        for (Component component : getComponents()) {
            if (component.getClass() == JTextField.class) {
                ((JTextField) component).setText("");
            }
        }

        descriptionTextArea.setText("");
        brandTextField.setText("St. John Knit");
        conditionComboBox.setSelectedIndex(0);
    }

    public JLabel titleLabel;
    public JTextField titleTextField;
    public JLabel skuLabel;
    public JTextField skuTextField;
    public JLabel brandLabel;
    public JTextField brandTextField;
    public JLabel conditionLabel;
    public JComboBox conditionComboBox;
    public JLabel typeLabel;
    public JTextField typeTextField;
    public JLabel sizeLabel;
    public JTextField sizeTextField;
    public JLabel descriptionLabel;
    public JTextArea descriptionTextArea;
    public JLabel defectLabel;
    public JTextField defectTextField;
}
