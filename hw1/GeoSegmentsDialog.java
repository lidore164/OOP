package homework1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

/**
 * A JDailog GUI for choosing a GeoSegemnt and adding it to the route shown
 * by RoutDirectionGUI.
 * <p>
 * A figure showing this GUI can be found in homework assignment #1.
 */
public class GeoSegmentsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	// the RouteDirectionsGUI that this JDialog was opened from
	private RouteFormatterGUI parent;
	
	// a control contained in this 
	private JList<GeoSegment> lstSegments;

	
	/**
	 * Creates a new GeoSegmentsDialog JDialog.
	 * @effects Creates a new GeoSegmentsDialog JDialog with owner-frame
	 * 			owner and parent pnlParent
	 */
	public GeoSegmentsDialog(Frame owner, RouteFormatterGUI pnlParent) {
		// create a modal JDialog with the an owner Frame (a modal window
		// in one that doesn't allow other windows to be active at the
		// same time).
		super(owner, "Please choose a GeoSegment", true);
		
		this.parent = pnlParent;

		DefaultListModel<GeoSegment> model = new DefaultListModel<>();
		for (int i =0; i< ExampleGeoSegments.segments.length; i++){
			model.addElement(ExampleGeoSegments.segments[i]);
		}
		//add the ScrollPane
		lstSegments = new JList<GeoSegment>(model);
		lstSegments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrlPane = new JScrollPane(lstSegments);
		scrlPane.setPreferredSize(new Dimension(450, 100));

		JLabel segmentLbl = new JLabel("GeoSegments:");
		segmentLbl.setLabelFor(lstSegments);

		//add the button "Add"
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.addSegment(lstSegments.getSelectedValue());
			}
		});

		//add the button "Cancel"
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JPanel panel = (JPanel) this.getContentPane();
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,0,0,0);
		gridbag.setConstraints(segmentLbl, c);
		this.add(segmentLbl);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 5;
		c.insets = new Insets(0,0,0,0);
		gridbag.setConstraints(scrlPane, c);
		this.add(scrlPane);

		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(20,0,0,0);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		gridbag.setConstraints(addBtn, c);
		this.add(addBtn);

		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(20,0,0,0);
		c.anchor = GridBagConstraints.LAST_LINE_END;
		gridbag.setConstraints(cancelBtn, c);
		this.add(cancelBtn);
	}
}
