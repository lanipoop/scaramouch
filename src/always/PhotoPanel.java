package always;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import net.tomahawk.XFileDialog;

public class PhotoPanel extends CardPanel {
    /** All preview icons will be this width and height */
    private static final int ICON_SIZE = 100;

    /** This blank icon will be used while previews are loading */
    private static final Image LOADING_IMAGE = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);

    private static final int IMAGE_PADDING = 7;
    private static final int COLUMNS = 3;

    private List<String> photos = new ArrayList<String>();
    private AlwaysClient alwaysClient = AlwaysClient.getInstance();
    private int cellSize;

    public PhotoPanel() {
        initComponents();
        initLayout();
    }

    @Override
    public void next() {
        Integer input = null;
        if (photos.isEmpty()) {
            input = JOptionPane.showOptionDialog(this, "You haven't selected any photos.\nAre you sure you want to continue?", "Yo, Mila!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
        }

        if (!photos.isEmpty() || input == JOptionPane.OK_OPTION) {
            final ProgressStatusDialog progress = new ProgressStatusDialog("Uploading photos...");
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                public Void doInBackground() {
                    try {
                        template.setPhotos(photos);
                        progress.updateTask("Generating template...");
                        template.generate();
                        progress.updateTask("Saving templates...");
                        template.save();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void done() {
                    PhotoPanel.super.next();
                    progress.dispose();
                }
            };

            progress.initialize(worker);
        }
    }

    @Override
    public void clear() {
        photoListModel.clear();
    }

    protected void initComponents() {
        setLayout(new MigLayout("insets 0, fill"));

        photoListModel = new PhotoListModel();
        photoList = new JList(photoListModel);
        photoList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        photoList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        photoList.setVisibleRowCount(-1);
        photoList.setCellRenderer(new PhotoListRenderer());
        photoList.setBackground(getBackground());
        photoList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    if (photoList.isSelectionEmpty()) {
                        deleteButton.setEnabled(false);
                    } else {
                        deleteButton.setEnabled(true);
                    }
                }
            }
        });

        photoScrollPane = new JScrollPane(photoList);
        photoScrollPane.setBorder(BorderFactory.createTitledBorder("Photos"));

        browseButton = new JButton("Browse...");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XFileDialog fileDialog = new XFileDialog(alwaysClient.getFrame());
                fileDialog.setTitle("please");
                fileDialog.setThumbnail(true);
                String[] files = fileDialog.getFiles();
                String directory = fileDialog.getDirectory();

                // Save the last used directory
                // TODO: Move someof thise to alwyasclient?
                Properties properties = alwaysClient.getProperties();
                properties.setProperty("imageDirectory", directory);
                alwaysClient.setProperties(properties);

                if (files != null) {
                    for (String file : files) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(directory);
                        sb.append(file);

                        String photo = sb.toString();
                        photos.add(photo);

                        if (!photoListModel.contains(photo)) {
                            photoListModel.addElement(photo);
                        }
                    }
                }
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int[] indices = photoList.getSelectedIndices();
                for (int i = indices.length - 1; i >= 0; i--) {
                    photoListModel.removeElementAt(indices[i]);
                }
            }
        });
    }

    protected void initLayout() {
        add(photoScrollPane, "grow, push, spany");
        add(browseButton, "wrap, width 100");
        add(deleteButton, "wrap push, width 100, gaptop 20");

    }

    private ImageIcon resizePhoto(String file, int size) {
        ImageIcon icon = new ImageIcon(file);
        if (icon != null) {
            if (icon.getIconWidth() > size) {
                return new ImageIcon(icon.getImage().getScaledInstance(size, -1, Image.SCALE_FAST));
            }
        }
        return icon;
    }

    private class PhotoListRenderer extends DefaultListCellRenderer {
        private Map cache = new WeakHashMap();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, final int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            final String file = (String) value;

            synchronized (cache) {
                ImageIcon icon = (ImageIcon) cache.get(file);

                if (icon == null) {
                    icon = new ImageIcon(LOADING_IMAGE);

                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        public Void doInBackground() {
                            ImageIcon newIcon = new ImageIcon(file);
                            cellSize = photoScrollPane.getViewport().getViewRect().width / COLUMNS - IMAGE_PADDING * 2;
                            double aspect = (double) newIcon.getIconWidth() / newIcon.getIconHeight();
                            double newAspect = (double) cellSize / cellSize;

                            int newWidth, newHeight;

                            if (newAspect > aspect) {
                                newHeight = cellSize;
                                newWidth = newIcon.getIconWidth() * cellSize / newIcon.getIconHeight();
                            } else {
                                newWidth = cellSize;
                                newHeight = newIcon.getIconHeight() * cellSize / newIcon.getIconWidth();
                            }

                            Image image = newIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
                            newIcon.setImage(image);
                            cache.put(file, newIcon);

                            return null;
                        }

                        @Override
                        public void done() {
                            photoListModel.fireContentsChanged(index, index);
                        }
                    };

                    worker.execute();
                }

                label.setIcon(icon);
                label.setBorder(BorderFactory.createEmptyBorder(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING));
                label.setText(null);
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
            
            return label;
        }
    }

    private class PhotoListModel extends DefaultListModel {
        public void fireContentsChanged(int start, int end) {
            super.fireContentsChanged(this, start, end);
        }
    }

    public PhotoListModel photoListModel;
    public JList photoList;
    public JScrollPane photoScrollPane;
    public JButton browseButton;
    public JButton deleteButton;
}
