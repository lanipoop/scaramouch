package always;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class SettingsDialog extends JDialog {
	private Properties properties;
	private File templateFile = null;
	private PicasaService picasa;

	public SettingsDialog() {
	    picasa = PicasaService.getInstance();
		properties = AlwaysClient.getInstance().getProperties();
		setModal(true);
		setTitle("Settings");
		initComponents();
		initLayout();
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2
				- this.getSize().height / 2);
		setVisible(true);
	}

	private void initComponents() {
		setLayout(new MigLayout("insets 12, fill, wrap 3", "[right]12[]"));
		picasaAlbumLabel = new JLabel("Picasa Album:");
		picasaAlbumComboBox = new JComboBox(picasa.getAlbums().keySet().toArray());
		if (properties.getProperty("album") != null) {
			picasaAlbumComboBox.setSelectedItem(properties.getProperty("album"));
		}
		writeDirectoryLabel = new JLabel("Write Directory:");
		writeDirectoryTextField = new JTextField();
		writeDirectoryTextField.setText(properties.getProperty("writeDirectory"));
		writeDirectoryBrowseButton = new JButton("Browse...");
		writeDirectoryBrowseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showDialog(SettingsDialog.this,
						"Select");	

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					writeDirectoryTextField.setText(file.getAbsolutePath());
				}
			}
		});
		photobucketStatusLabel = new JLabel("Photobucket Status:");
		photobucketStatusTextLabel = new JLabel();
		photobucketStatusTextLabel.setFont(photobucketStatusTextLabel.getFont()
				.deriveFont(Font.BOLD));
		photobucketSignInButton = new JButton("Sign In");
		photobucketSignInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				new SignInDialog();
			}
		});
		fontSizeLabel = new JLabel("Font Size:");
		String[] sizes = { "Small", "Medium", "Large" };
		fontSizeComboBox = new JComboBox(sizes);
		if (properties.getProperty("fontSize") != null) {
			fontSizeComboBox.setSelectedItem(properties.getProperty("fontSize"));
		}
		htmlTemplateLabel = new JLabel("HTML Template:");
		htmlTemplateTextField = new JTextField();
		htmlTemplateTextField.setText(properties.getProperty("template"));
		htmlTemplateBrowseButton = new JButton("Browse...");
		htmlTemplateBrowseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showDialog(SettingsDialog.this,
						"Select");

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					templateFile = fileChooser.getSelectedFile();
					htmlTemplateTextField.setText(templateFile.getName());
				}
			}
		});
		
		defaultRadioButton = new JRadioButton("Default");
		defaultRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				radioButtonSelected();
			}
		});
		customRadioButton = new JRadioButton("Custom");
		customRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				radioButtonSelected();
			}
		});
		buttonGroup = new ButtonGroup();
		buttonGroup.add(defaultRadioButton);
		buttonGroup.add(customRadioButton);

		String template = properties.getProperty("template");
		if (template != null && !template.isEmpty()) {
			templateFile = new File(template);
			customRadioButton.setSelected(true);
			htmlTemplateTextField.setText(templateFile.getName());
		} else {
			defaultRadioButton.setSelected(true);
		}
		
		radioButtonSelected();
		
		separator = new JSeparator();
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (writeDirectoryTextField.getText().isEmpty() || (customRadioButton.isSelected() && htmlTemplateTextField.getText().isEmpty())) {
					JOptionPane.showMessageDialog(SettingsDialog.this, "Please fill in the necessary fields.", "Alert", JOptionPane.WARNING_MESSAGE);
					if (writeDirectoryTextField.getText().isEmpty()) {
						writeDirectoryTextField.setBackground(Color.PINK);
					}
					if (customRadioButton.isSelected() && htmlTemplateTextField.getText().isEmpty()) {
						htmlTemplateTextField.setBackground(Color.PINK);
					}
				} else {
					Properties newProperties = new Properties();
					
					if (defaultRadioButton.isSelected()) {
						newProperties.setProperty("template", "");
					} else {
						newProperties.setProperty("template", templateFile.getAbsolutePath());
					}
					newProperties.setProperty("album", picasaAlbumComboBox.getSelectedItem().toString());
					newProperties.setProperty("firstlogin", "false");
					newProperties.setProperty("writeDirectory", writeDirectoryTextField.getText());
					
					AlwaysClient.getInstance().setProperties(newProperties);
					setVisible(false);
				}
			}
		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
	
	private void radioButtonSelected() {
		if (defaultRadioButton.isSelected()) {
			htmlTemplateTextField.setEnabled(false);
			htmlTemplateBrowseButton.setEnabled(false);
			htmlTemplateTextField.setBackground(Color.WHITE);
			htmlTemplateTextField.setText("");
		} else {
			htmlTemplateTextField.setEnabled(true);
			htmlTemplateBrowseButton.setEnabled(true);
		}
	}

	private void initLayout() {
		add(picasaAlbumLabel);
		add(picasaAlbumComboBox);
		add(writeDirectoryLabel, "skip");
		add(writeDirectoryTextField, "width 250");
		add(writeDirectoryBrowseButton, "sg button");
		add(htmlTemplateLabel);
		add(defaultRadioButton, "split 2");
		add(customRadioButton, "wrap");
		add(htmlTemplateTextField, "skip, width 150, split 2");
		add(htmlTemplateBrowseButton, "sg button, wrap");
		add(separator, "span, grow, wrap, gaptop 6");
		add(saveButton, "span, split 2, alignx right");
		add(cancelButton);
	}

	public JLabel statusLabel;
	public JLabel statusTextLabel;
	public JButton authorizeButton;
	public JLabel picasaAlbumLabel;
	public JComboBox picasaAlbumComboBox;
	public JLabel writeDirectoryLabel;
	public JTextField writeDirectoryTextField;
	public JButton writeDirectoryBrowseButton;
	public JLabel photobucketStatusLabel;
	public JLabel photobucketStatusTextLabel;
	public JButton photobucketSignInButton;
	public JLabel fontSizeLabel;
	public JComboBox fontSizeComboBox;
	public JLabel htmlTemplateLabel;
	public JTextField htmlTemplateTextField;
	public JButton htmlTemplateBrowseButton;
	public JRadioButton defaultRadioButton;
	public JRadioButton customRadioButton;
	public ButtonGroup buttonGroup;
	public JSeparator separator;
	public JButton saveButton;
	public JButton cancelButton;
}
