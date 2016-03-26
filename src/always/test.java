package always;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.tomahawk.XFileDialog;


public class test extends JFrame implements ActionListener {
	public test() {
		JPanel main = new JPanel(new BorderLayout());
	    JButton button1 = new JButton("ow");
	    button1.addActionListener(this);
	    pack();
	    main.add(button1);
	    getContentPane().add(main);
	}
	
	public void actionPerformed(ActionEvent e) {
		XFileDialog dialog = new XFileDialog(test.this);
		dialog.setTitle("ow");
		String yeah = dialog.getFile();
	}
	
	public static void main(String[] args) {
		new test().setVisible(true);
	}
}
