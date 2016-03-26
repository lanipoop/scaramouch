package always;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import net.miginfocom.swing.MigLayout;

public class TemplatePanel extends CardPanel {
	public TemplatePanel() {
		initComponents();
		initLayout();
	}
	
	protected void initComponents() {
		setLayout(new MigLayout("insets 0, fill, wrap"));
		
		statusLabel = new JLabel("Success! Template generated.");
		
		eBayLabel = new JLabel("eBay:");
		eBayTextArea = new JTextArea();
		eBayTextArea.setEditable(false);
		eBayTextArea.setBorder((new JTextField()).getBorder());
		eBayTextArea.setLineWrap(true);
		eBayTextArea.setWrapStyleWord(true);
		eBayScrollPane = new JScrollPane(eBayTextArea);
		DefaultCaret eBayCaret = (DefaultCaret) eBayTextArea.getCaret();
		eBayCaret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		eBayCopyButton = new JButton("Copy to Clipboard");
		eBayCopyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				copyToClipboard(eBayTextArea);
			}
		});
		
		bigCommerceLabel = new JLabel("Big Commerce:");
		bigCommerceTextArea = new JTextArea();
		bigCommerceTextArea.setEditable(false);
		bigCommerceTextArea.setBorder((new JTextField()).getBorder());
		bigCommerceTextArea.setLineWrap(true);
		bigCommerceTextArea.setWrapStyleWord(true);
		bigCommerceScrollPane = new JScrollPane(bigCommerceTextArea);
		DefaultCaret bigCommerceCaret = (DefaultCaret) bigCommerceTextArea.getCaret();
		bigCommerceCaret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		bigCommerceCopyButton = new JButton("Copy to Clipboard");
		bigCommerceCopyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				copyToClipboard(bigCommerceTextArea);
			}
		});
	}
	
	private void copyToClipboard(JTextArea textArea) {
		textArea.requestFocusInWindow();
		textArea.selectAll();
		StringSelection stringSelection = new StringSelection(textArea.getText());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard ();
		clipboard.setContents(stringSelection, null);
	}
	
	protected void initLayout() {
		add(statusLabel, "alignx center");
		add(eBayLabel);
		add(eBayScrollPane, "grow, push, height 150, sg sp");
		add(eBayCopyButton, "gaptop 6, alignx right");
		add(bigCommerceLabel);
		add(bigCommerceScrollPane, "grow, push, sg sp");
		add(bigCommerceCopyButton, "gaptop 6, alignx right");
	}
	
	@Override
    public void clear() {}
	
    public JLabel statusLabel;
    public JLabel eBayLabel;
    public JTextArea eBayTextArea;
    public JScrollPane eBayScrollPane;
    public JButton eBayCopyButton;
    public JLabel bigCommerceLabel;
    public JTextArea bigCommerceTextArea;
    public JScrollPane bigCommerceScrollPane;
    public JButton bigCommerceCopyButton;
}
