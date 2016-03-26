package always;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;

public class Frame extends JFrame {
    public enum Direction {
        FORWARD, BACK
    }

    public CardLayout cardLayout;
    private int cardIndex = 0;

    public Frame() {
        setSize(580, 600);
        initComponents();
        initLayout();

        setTitle(UIConstants.ALWAYS_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    private void initComponents() {
        setLayout(new MigLayout("insets 12, fill"));
        
        CardPanel.setFrame(this);

        descriptionPanel = new DescriptionPanel();
        measurementPanel = new MeasurementPanel();
        photoPanel = new PhotoPanel();
        templatePanel = new TemplatePanel();

        cardPanels.add(descriptionPanel);
        cardPanels.add(measurementPanel);
        cardPanels.add(photoPanel);
        cardPanels.add(templatePanel);

        cardPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) (cardPanel.getLayout());
        
        separator = new JSeparator();

        clearButton = new JButton("Clear");
        clearButton.setFocusable(false);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.get(cardIndex).clear();
            }
        });

        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsDialog();
            }
        });

        backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setVisible(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.get(cardIndex).back();
            }
        });

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.get(cardIndex).next();
            }
        });
    }

    public void navigate(Direction direction) {
        if (direction == Direction.BACK) {
            cardIndex--;
            cardLayout.previous(cardPanel);
        } else {
            cardIndex++;
            cardLayout.next(cardPanel);
        }
        
        if (cardIndex >= cardPanels.size()) {
            cardIndex = 0;
        }

        switch (cardIndex) {
            case 0:
                backButton.setFocusable(false);
                backButton.setVisible(false);
                nextButton.setText("Next");
                
                for (CardPanel cardPanel : cardPanels) {
                    cardPanel.clear();
                }
                break;
            case 2:
                nextButton.setText("Generate");
                break;
            case 3:
                nextButton.setText("Start Over");
                clearButton.setVisible(false);
                break;
        }
    }

    private void initLayout() {
        cardPanel.add(descriptionPanel, UIConstants.DESCRIPTION);
        cardPanel.add(measurementPanel, UIConstants.MEASUREMENT);
        cardPanel.add(photoPanel, UIConstants.PHOTO);
        cardPanel.add(templatePanel, UIConstants.TEMPLATE);

        add(cardPanel, "spanx, grow, push, wrap");
        add(separator, "spanx, growx, wrap, gaptop 6");
        add(settingsButton, "split 2, width 90");
        add(clearButton, "gapafter push, width 90");
        add(backButton, "split 2, alignx right, width 90");
        add(nextButton, "width 90");
    }

    private List<CardPanel> cardPanels = new ArrayList<CardPanel>();
    public JPanel cardPanel;
    public DescriptionPanel descriptionPanel;
    public MeasurementPanel measurementPanel;
    public PhotoPanel photoPanel;
    public TemplatePanel templatePanel;
    public JSeparator separator;
    public JButton clearButton;
    public JButton settingsButton;
    public JButton backButton;
    public JButton nextButton;
}
