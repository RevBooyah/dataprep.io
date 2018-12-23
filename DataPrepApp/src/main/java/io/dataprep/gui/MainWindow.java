package io.dataprep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JTextField;

import io.dataprep.app.DPFile;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frmDataprepio;
	private final JButton btnSelectFile = new JButton("Select File");
	private JTextField fldFile;

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
		frmDataprepio.setBounds(100, 100, 706, 512);
		frmDataprepio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDataprepio.getContentPane().setLayout(null);
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				
				int returnVal = fc.showOpenDialog(frmDataprepio);
				  if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            //This is where a real application would open the file.
			            System.out.println("Opening: " + file.getAbsolutePath()  + ".\n");
			            fldFile.setText(file.getAbsolutePath());
			            DPFile dpf = new DPFile(file.getAbsolutePath());
			            try {
							dpf.readFullFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        } else {
			            System.out.println("Open command cancelled by user.\n");
			        }
			}
		});
		btnSelectFile.setBounds(24, 30, 98, 21);
		frmDataprepio.getContentPane().add(btnSelectFile);
		
		fldFile = new JTextField();
		fldFile.setBounds(135, 30, 297, 21);
		frmDataprepio.getContentPane().add(fldFile);
		fldFile.setColumns(10);
	}
}
