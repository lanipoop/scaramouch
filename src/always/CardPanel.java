package always;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import always.Frame.Direction;

public abstract class CardPanel extends JPanel {
    private AlwaysClient alwaysClient = AlwaysClient.getInstance();
    private static Frame parent;
    protected Template template = alwaysClient.getTemplate();
    
    public CardPanel() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent evt) {
                stop();
            }

            @Override
            public void componentShown(ComponentEvent evt) {
                start();
            }
        });
    }
    
    public static void setFrame(Frame frame) {
        CardPanel.parent = frame;
    }

    protected abstract void initComponents();

    protected abstract void initLayout();

    public void start() {}

    public void stop() {}

    public abstract void clear();
    
    public void back() {
        parent.navigate(Direction.BACK);
    }
    
    public void next() {
        parent.navigate(Direction.FORWARD);
    }
}
