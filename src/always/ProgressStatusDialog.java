package always;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import net.miginfocom.swing.MigLayout;


public class ProgressStatusDialog extends JDialog {
	private String taskName;
	public ProgressStatusDialog(String name) {
		taskName = name;
	}
	
	public void initialize(SwingWorker worker) {
        setTitle("Progress");
        setModal(true);
        initComponents();
        initLayout();
        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2
				- this.getSize().height / 2);
        
        worker.execute();
        setVisible(true);
	}
	
	public void updateTask(String task) {
	    progressLabel.setText(task);
	}
	
	private void initComponents() {
		setLayout(new MigLayout("insets 12, fill, wrap, gapy 8"));
		progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
 
        progressLabel = new JLabel(taskName);
	}
	
	private void initLayout() {
		add(progressLabel);
		add(progressBar);
	}
	
	private JProgressBar progressBar;
	private JLabel progressLabel;
}
