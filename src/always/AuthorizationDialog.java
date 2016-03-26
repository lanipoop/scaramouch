package always;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import net.miginfocom.swing.MigLayout;

public class AuthorizationDialog extends JDialog {
    private String url;
    private GoogleAuth googleAuth;

    // private final Credential credential;

    public AuthorizationDialog(GoogleAuth googleAuth, String url) {
        this.googleAuth = googleAuth;
        this.url = url;
        setModal(true);
        setTitle(UIConstants.ALWAYS_TITLE);
        initComponents();
        initLayout();
        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new MigLayout("insets 12, fill, wrap, gap 6"));

        codeLabel = new JLabel("Please enter code from authorization page:");
        codeTextField = new JTextField();
        codeTextField.requestFocusInWindow();
        codeTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                getAccessToken();
            }
        });

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                getAccessToken();
            }
        });

        separator = new JSeparator();

        copyLinkLabel = new JLabel("Page didn't open? ");
        Font footerFont = copyLinkLabel.getFont().deriveFont(10f);
        copyLinkLabel.setFont(footerFont);

        copyLinkButton = new JLabel("<html><u>Copy link.</u></html>");
        copyLinkButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyLinkButton.setForeground(Color.BLUE);
        copyLinkButton.setFont(footerFont);
        copyLinkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                // Copy link to clipboard
                StringSelection stringSelection = new StringSelection(url);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard ();
                clipboard.setContents(stringSelection, null);
            }
        });
    }

    private void initLayout() {
        add(codeLabel);
        add(codeTextField, "width 200, sg textfield, split 2");
        add(submitButton);
        add(separator, "grow");
        add(copyLinkLabel, "alignx left, split 2");
        add(copyLinkButton, "alignx left");
    }

    private void getAccessToken() {
        final ProgressStatusDialog progress = new ProgressStatusDialog("Signing in...");
        progress.setLocationRelativeTo(this);

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            boolean isSuccess = false;

            public Void doInBackground() {
                try {
                    isSuccess= googleAuth.getAccessToken(codeTextField.getText());
                    Thread.sleep(1000);
                } catch (Exception e) {
                    // TODO: Failed to verify access token
                }

                return null;
            }

            @Override
            public void done() {
                progress.dispose();

                if (isSuccess) {
                    dispose();
                } else {
                    int input = JOptionPane.showOptionDialog(AuthorizationDialog.this, "Invalid code. Get new code?", "Error", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
                    if (input == JOptionPane.YES_OPTION) {
                        // Open new link
                        url = googleAuth.refreshUrl();
                    }
                }
            }
        };

        progress.initialize(worker);
    }

    public JLabel codeLabel;
    public JTextField codeTextField;
    public JSeparator separator;
    public JButton submitButton;
    public JLabel copyLinkLabel;
    public JLabel copyLinkButton;
}
