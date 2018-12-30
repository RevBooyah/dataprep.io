package io.dataprep.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import io.dataprep.app.Column;
import io.dataprep.app.DPFile;
import io.dataprep.types.DpString;
import java.awt.Font;

public class MainWindow {

	private JFrame frmDataprepio;
	private final JButton btnSelectFile = new JButton("Select File");
	public JTextField fldFile;
	public JTextField fldRowsCols;
	public JList<String> jHeadList;
	private JTextField fldSelectedCol;
	private JTextField textField;
	private DPFile dpf;
	private JTextField textUnique;
	public JPanel panel;
	JTextField[] jtf;
	JLabel[] jlbl;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmDataprepio.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDataprepio = new JFrame();
		frmDataprepio.setTitle("DataPrep.io");
		frmDataprepio.setBounds(100, 100, 894, 547);
		frmDataprepio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDataprepio.getContentPane().setLayout(null);
		
		btnSelectFile.setBounds(421, 8, 98, 21);
		frmDataprepio.getContentPane().add(btnSelectFile);
		
		fldFile = new JTextField();
		fldFile.setEditable(false);
		fldFile.setBounds(97, 8, 314, 21);
		frmDataprepio.getContentPane().add(fldFile);
		fldFile.setColumns(10);
		
		JLabel lblFile = new JLabel("Input File");
		lblFile.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFile.setBounds(10, 11, 77, 14);
		frmDataprepio.getContentPane().add(lblFile);
		
		JLabel label = new JLabel("File");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(42, 11, 77, 14);
		frmDataprepio.getContentPane().add(label);
		
		JLabel lblRowsXCols = new JLabel("Rows x Cols");
		lblRowsXCols.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRowsXCols.setBounds(10, 43, 77, 14);
		frmDataprepio.getContentPane().add(lblRowsXCols);
		
		fldRowsCols = new JTextField();
		fldRowsCols.setEditable(false);
		fldRowsCols.setBounds(97, 40, 136, 20);
		frmDataprepio.getContentPane().add(fldRowsCols);
		fldRowsCols.setColumns(10);

				
		JLabel lblDetails = new JLabel("Data Details");
		lblDetails.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDetails.setBounds(10, 72, 77, 14);
		frmDataprepio.getContentPane().add(lblDetails);
		
		ListModel<String> listModel = new DefaultListModel<String>();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(97, 71, 177, 370);
		frmDataprepio.getContentPane().add(scrollPane);
		jHeadList = new JList<String>(listModel);
		scrollPane.setViewportView(jHeadList);
		jHeadList.setValueIsAdjusting(true);
		jHeadList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel label_1 = new JLabel("Output File");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(10, 455, 77, 14);
		frmDataprepio.getContentPane().add(label_1);
		
		JButton button = new JButton("Export");
		button.setBounds(97, 452, 98, 21);
		frmDataprepio.getContentPane().add(button);
		
		panel = new JPanel();
		panel.setForeground(Color.GRAY);
		panel.setBorder(new LineBorder(Color.GRAY, 1, true));
		panel.setBounds(284, 71, 260, 370);
		frmDataprepio.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblColumn = new JLabel("Column:");
		lblColumn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblColumn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColumn.setBounds(10, 11, 81, 14);
		panel.add(lblColumn);
		
		fldSelectedCol = new JTextField();
		fldSelectedCol.setForeground(Color.BLACK);
		fldSelectedCol.setFont(new Font("Tahoma", Font.BOLD, 12));
		fldSelectedCol.setEditable(false);
		fldSelectedCol.setBounds(101, 8, 149, 20);
		fldSelectedCol.setBorder(new LineBorder(Color.LIGHT_GRAY,0));
		panel.add(fldSelectedCol);
		fldSelectedCol.setColumns(10);
		lblColumn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(101, 36, 148, 20);

		textField.setBorder(new LineBorder(Color.LIGHT_GRAY,0));
		panel.add(textField);
		
		textUnique = new JTextField();
		textUnique.setEditable(false);
		textUnique.setColumns(10);
		textUnique.setBounds(101, 64, 149, 20);
		textUnique.setBorder(new LineBorder(Color.LIGHT_GRAY,0));
		panel.add(textUnique);
		
		JLabel lblUniqueVals = new JLabel("Unique Values:");
		lblUniqueVals.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUniqueVals.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUniqueVals.setBounds(10, 67, 81, 14);
		panel.add(lblUniqueVals);
		
		JLabel lblDataType = new JLabel("Data Type:");
		lblDataType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDataType.setBounds(10, 39, 81, 14);
		panel.add(lblDataType);
		
		JMenuBar menuBar = new JMenuBar();
		frmDataprepio.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File");
		mnFile.add(mntmOpenFile);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
	    mntmExit.setMnemonic(KeyEvent.VK_Q);
	    mntmExit.setAccelerator(KeyStroke.getKeyStroke(
	             KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
	    mntmExit.setToolTipText("Exit application");
	    mntmExit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	            System.exit(0);
	        }
	    });
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				
				int returnVal = fc.showOpenDialog(frmDataprepio);
				  if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            //This is where a real application would open the file.
			            System.out.println("Opening: " + file.getAbsolutePath()  + ".\n");
			            fldFile.setText(" "+file.getAbsolutePath());
			            dpf = new DPFile(file.getAbsolutePath());
			            try {
							dpf.readBasicFileInfo();
							fldRowsCols.setText(" "+dpf.getNumLines()+" x "+dpf.getNumColumns());
							jHeadList.setListData(dpf.getHeaders());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
			        } else {
			            System.out.println("Open command cancelled by user.\n");
			        }
			}
		});
		jHeadList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Column col = new DpString();
				if(e.getButton()==1) {
					fldSelectedCol.setText(dpf.getHeaders()[jHeadList.getSelectedIndex()]);
					try {
						col = dpf.parseColumn(jHeadList.getSelectedIndex());
						textField.setText(col.getDpType().getReadable());
						textUnique.setText(""+col.getNumUnique());
						updatePanel(col);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
			
	}
	
	private void updatePanel(Column col) {
		
		if(jtf!=null && jtf.length>0) {
			for(JTextField j: jtf) {
				panel.remove(j);
			}
		}
		
		if(jlbl != null && jlbl.length>0 ) {
			for(JLabel j: jlbl) {
				panel.remove(j);
			}
		}
		panel.revalidate();
		panel.repaint();
		if(col.getDetails()==null) return;
		
		System.out.println("COLUMN: "+col.getDetails());
		HashMap<String, Object> c = col.getDetails();
		int cnt = c.size();
		
		jtf = new JTextField[cnt];
		jlbl = new JLabel[cnt];
		
		int x = 0;
		int cury = 39+28+28;
		Iterator<?> it = c.keySet().iterator();
		while(it.hasNext()) {
			String k = (String) it.next();
			jlbl[x] = new JLabel(k.toString()+":");
			jlbl[x].setHorizontalAlignment(SwingConstants.RIGHT);
			jlbl[x].setBounds(10, cury, 81, 14);
			jlbl[x].setFont(new Font("Tahoma", Font.PLAIN, 11));
			panel.add(jlbl[x]);
			
			
			jtf[x] = new JTextField();
			jtf[x].setEditable(false);
			jtf[x].setColumns(10);
			jtf[x].setBounds(101, cury-3, 149, 20);
			jtf[x].setText(c.get(k).toString());

			jtf[x].setBorder(new LineBorder(Color.LIGHT_GRAY,0));
			panel.add(jtf[x]);
			
			x++;
			cury = cury + 28;
		}
		panel.revalidate();
		panel.repaint();
	}
	
	
}
