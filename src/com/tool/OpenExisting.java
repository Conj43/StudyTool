package com.tool;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class OpenExisting extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Ridawg's Study Tool");
	private JTextField termField;
	private JTextArea definitionField;
	private DefaultListModel<String> listModel;
	private DefaultListModel<String> saveList;
	private int termCounter;
	private JButton saveButton = new JButton("Save Study Set!");
	private JButton homeButton = new JButton("HOME");
	private JList<String> termList;
	JButton addButton = new JButton("Add Term!");
	
	public OpenExisting() {
		

		termField = new JTextField(20);
		
		
		definitionField = new JTextArea(5, 30);
		definitionField.setLineWrap(true);
		definitionField.setWrapStyleWord(true);
		
		
		listModel = new DefaultListModel<>();
		saveList = new DefaultListModel<>();
		termList = new JList<>(listModel);
		openSet();
		
		termCounter = listModel.size();
		
		JLabel termLabel = new JLabel("Term: ");
		termLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		
		
		
		JLabel defLabel = new JLabel("Definition: ");
		defLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		
		JPanel inputPanel = new JPanel(new GridLayout(3,5));
		
		inputPanel.setBorder(BorderFactory.createEmptyBorder(25, 100, 50, 100)); //(top, left, bottom, right)
		inputPanel.setBackground(Color.PINK);
		inputPanel.add(termLabel);
		inputPanel.add(termField);
		inputPanel.add(defLabel);
		inputPanel.add(definitionField);
		inputPanel.add(saveButton);
		inputPanel.add(addButton, BorderLayout.SOUTH);
		
		
		JPanel homePanel = new JPanel(new GridLayout(1, 20));
		
		homePanel.setBackground(Color.PINK);
		homePanel.add(homeButton);
		homePanel.setBorder(BorderFactory.createEmptyBorder(15, 325, 15, 325)); //(top, left, bottom, right)
		
		JPanel listPanel = new JPanel(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane(termList);
		listPanel.add(scrollPane, BorderLayout.CENTER);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(inputPanel, BorderLayout.NORTH);
		mainPanel.add(listPanel, BorderLayout.CENTER);
		
		saveButton.addActionListener(this);
		addButton.addActionListener(this);
		homeButton.addActionListener(this);
		
		termList.addMouseListener(this);
		
		
		frame.setSize(750,750);
		frame.setVisible(true);
        frame.setLocationRelativeTo(null); //put in middle of screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.PINK);
		frame.add(homePanel, BorderLayout.NORTH);
		frame.add(mainPanel);
		
		  
		
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			deleteSelectedTerm();
		}
	}
	
	
	
	public void actionPerformed(ActionEvent e) { //listens for click on a specific button
        JButton clickedButton = (JButton) e.getSource();
        if(clickedButton == saveButton){
        	saveStudySet();
        }

        else if(clickedButton == homeButton){
        	new HomeGUI();
			frame.dispose();
        	
        }
        
        else if(clickedButton == addButton){
        	
        	addTermDefinition();
        }
    }
	

	
	
	
	private void saveStudySet() {
        
        String studySetName = JOptionPane.showInputDialog(this, "Enter the study set name:");

        if (studySetName != null && !studySetName.trim().isEmpty()) {
            
            String fileName = studySetName.trim() + ".txt";

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File(fileName));

            
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                
                java.io.File fileToSave = fileChooser.getSelectedFile();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                   
                    for (int i = 0; i < saveList.size(); i++) {
                        writer.write(saveList.getElementAt(i) + "\n");
                    }

                    JOptionPane.showMessageDialog(this, "Study set saved successfully.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error saving study set.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Study set name cannot be empty.");
        }
	
	}

	
	
    private void deleteSelectedTerm() {
    	   int selectedIndex = termList.getSelectedIndex();
    	    if (selectedIndex != -1) {
    	        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this term?", "Delete Term", JOptionPane.YES_NO_OPTION);
    	        if (option == JOptionPane.YES_OPTION) {
    	        	termCounter--;
    	            listModel.removeElementAt(selectedIndex);
    	            saveList.removeElementAt(selectedIndex);
    	            
    	            // Shift the numbers in the remaining terms
    	            for (int i = selectedIndex; i < listModel.size(); i++) {
    	                String term = listModel.getElementAt(i);
    	                String[] parts = term.split("\\. ");
    	                String smallerTerm = parts[0];
    	                String[] smallerParts = smallerTerm.split("<b>");
    	                
    	                int currentNumber = Integer.parseInt(smallerParts[1]);
    	                
    	                listModel.setElementAt("<html><font size='5'><b>" + (currentNumber-1) + ". " + parts[1], i);
    	            }
    	        }
    	    }
        
    }
	
	
	
	private void addTermDefinition() {
		String term = termField.getText();
		String definition = definitionField.getText();
		
		
		if(!term.isEmpty() && !definition.isEmpty()) {
			termCounter++;
			String item = "<html><font size='5'><b>" + termCounter + ". " + term + ":</b> " + definition;
			listModel.addElement(item);
			String saveItem = term + ":" + definition + "~";
			saveList.addElement(saveItem);
			
			termField.setText("");
			definitionField.setText("");
		}else {
			JOptionPane.showMessageDialog(this,  "Please enter both term and definition.");;
		}
	}
	
	public void openSet() {
        // open window to find .txt file
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // get the selected file
            java.io.File selectedFile = fileChooser.getSelectedFile();

            // transfer from file into the list so we can use it 
            try (Scanner scanner = new Scanner(selectedFile)) {
            	scanner.useDelimiter("~");
                String line;
                int counter = 1;
                while (scanner.hasNext()) {
                	line = scanner.next();
                    String[] parts = line.split(":", 2);  // split the line into term and definition using ':'
                    if(parts.length >= 2)
                    {
                    String term = parts[0];
                    String definition = parts.length > 1 ? parts[1] : "";  // if there is no : then it just puts a blank as the definition
                    String item = "<html><font size='5'><b>" + counter + ". " + term + ":</b> " + definition;
                    listModel.addElement(item);
                    saveList.addElement(term + ":" + definition + "~");
                    counter++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading study set file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CreatingGUI());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}