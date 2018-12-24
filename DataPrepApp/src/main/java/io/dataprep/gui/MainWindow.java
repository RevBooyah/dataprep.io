package io.dataprep.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ListModel;

import io.dataprep.app.DPFile;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class MainWindow {

	private JFrame frmDataprepio;
	private final JButton btnSelectFile = new JButton("Select File");
	private JTextField fldFile;
	private JTextField fldRowsCols;
	private JTable table;
	
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
		frmDataprepio.setBounds(100, 100, 894, 512);
		frmDataprepio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDataprepio.getContentPane().setLayout(null);
		
		btnSelectFile.setBounds(421, 8, 98, 21);
		frmDataprepio.getContentPane().add(btnSelectFile);
		
		fldFile = new JTextField();
		fldFile.setEditable(false);
		fldFile.setBounds(97, 8, 314, 21);
		frmDataprepio.getContentPane().add(fldFile);
		fldFile.setColumns(10);
		
		JLabel lblFile = new JLabel("File");
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
		
		ListModel listModel = new DefaultListModel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(97, 71, 177, 370);
		frmDataprepio.getContentPane().add(scrollPane);
		final JList jHeadList = new JList(listModel);
		scrollPane.setViewportView(jHeadList);
		jHeadList.setValueIsAdjusting(true);
		jHeadList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JMenuBar menuBar = new JMenuBar();
		frmDataprepio.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File");
		mnFile.add(mntmOpenFile);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
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
			            DPFile dpf = new DPFile(file.getAbsolutePath());
			            try {
							dpf.readFullFile();
							fldRowsCols.setText(" "+dpf.getNumLines()+" x "+dpf.getNumColumns());
							jHeadList.setListData(dpf.getHeaders());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        } else {
			            System.out.println("Open command cancelled by user.\n");
			        }
			}
		});
	}
}
