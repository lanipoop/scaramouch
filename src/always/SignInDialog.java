package always;
//import java.awt.Dimension;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.net.URL;
//import java.nio.file.Files;
//import java.util.Properties;
//
//import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPasswordField;
//import javax.swing.JTextField;
//import javax.swing.SwingWorker;
//
//import org.apache.commons.io.IOUtils;
//
//import com.google.gdata.client.photos.PicasawebService;
//import com.google.gdata.data.photos.AlbumEntry;
//import com.google.gdata.data.photos.UserFeed;
//import com.google.gdata.util.AuthenticationException;
//import com.google.gdata.util.ServiceException;
//
//import net.miginfocom.swing.MigLayout;
//
//public class SignInDialog extends JFrame {
//	public SignInDialog() {
//		setTitle("Always A Touch Of Class");
//		initComponents();
//		initLayout();
//		pack();
//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2
//				- this.getSize().height / 2);
//		setVisible(true);
//
//		String username = UIConstants.FRAME.getProperties().getProperty(
//				"username");
//		if (username != null) {
//			usernameTextField.setText(username);
//			passwordField.grabFocus();
//		}
//	}
//
//	private void initComponents() {
//		setLayout(new MigLayout("insets 12, fill, wrap 2, gap 6",
//				"[right]12[left]"));
//
//		usernameLabel = new JLabel("Username:");
//		usernameTextField = new JTextField();
//		passwordLabel = new JLabel("Password:");
//		passwordField = new JPasswordField();
//		passwordField.requestFocusInWindow();
//		passwordField.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				showProgressBar();
//			}
//		});
//		
//		signInButton = new JButton("Sign In");
//		signInButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				showProgressBar();
//			}
//		});
//	}
//	
//	private void showProgressBar() {
//		final ProgressStatusDialog progress = new ProgressStatusDialog("Signing in...");
//		progress.setLocationRelativeTo(this);
//		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
//			public Void doInBackground() {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException ignore) {
//				}
//				signIn();
//				return null;
//			}
//
//			@Override
//			public void done() {
//				progress.dispose();
//			}
//		};
//		
//		progress.initialize(worker);
//	}
//
//	private void signIn() {
//		try {
////			UIConstants.FRAME.getMyService().setUserCredentials(
////					usernameTextField.getText(), passwordField.getText());
//			URL feedUrl = new URL(UIConstants.END_POINT);
//
//			UserFeed myUserFeed = UIConstants.FRAME.getMyService().getFeed(
//					feedUrl, UserFeed.class);
//
//			for (AlbumEntry album : myUserFeed.getAlbumEntries()) {
//				String name = album.getTitle().getPlainText();
//				String id = getAlbumId(album.getId());
//				UIConstants.FRAME.albums.put(name, id);
//			}
//			Properties properties = UIConstants.FRAME.getProperties();
//			properties.setProperty("username", usernameTextField.getText());
//			UIConstants.FRAME.setProperties(properties);
//			String firstlogin = properties.getProperty("firstlogin");
//			if (firstlogin == null || firstlogin.equals("true")) {
//				new SettingsDialog();
//			} else {
//				UIConstants.FRAME.setVisible(true);
//			}
//
//			dispose();
//			
//			String template = properties.getProperty("template");
//			if (template == null || template.isEmpty()) {
//				UIConstants.FRAME.setTemplate(IOUtils.toString(this.getClass()
//						.getResourceAsStream("Resources/template.txt"), "UTF-8"));
//			} else {
//				FileInputStream inputStream = new FileInputStream(template);
//				UIConstants.FRAME.setTemplate(IOUtils.toString(inputStream));
//			}
//			
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(this,
//					"Unable to load default template.\nPlease upload custom template in Settings.", "File Load Fail",
//					JOptionPane.ERROR_MESSAGE); 
//		} 
//		catch (AuthenticationException e) {
//			JOptionPane.showMessageDialog(this, e.getMessage(), "Login Fail",
//					JOptionPane.ERROR_MESSAGE);
//		} catch (ServiceException e) {
//			JOptionPane.showInputDialog(this, e.getMessage(), "Login Fail",
//					JOptionPane.ERROR_MESSAGE);
//		}
//	}
//
//	// Hacky solution to extract album id from url.
//	private String getAlbumId(String url) {
//		String id = url.substring(url.lastIndexOf("/") + 1, url.length());
//		return id;
//	}
//
//	private void initLayout() {
//		add(usernameLabel);
//		add(usernameTextField, "width 200, sg textfield");
//		add(passwordLabel);
//		add(passwordField, "sg textfield");
//		add(signInButton, "alignx center, spanx");
//	}
//
//	public JLabel usernameLabel;
//	public JTextField usernameTextField;
//	public JLabel passwordLabel;
//	public JPasswordField passwordField;
//	public JButton signInButton;
//}
