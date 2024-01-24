package com.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HomeGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Study Tool");
    private JLabel label = new JLabel("Let's Get to Studying! ;)");
    private JButton exitButton = new JButton("Exit");
    private JButton createButton = new JButton("Create a Study Set!");
    private JButton existingButton = new JButton("Study an Existing Set!");
    private JButton editButton = new JButton("Edit an Exisiting Study Set!");
    private ArrayList<Card> cardList = new ArrayList<>();

    public HomeGUI(){ 
        JPanel panel = new JPanel(new GridLayout(4, 1)); //use 4 buttons in one column
        JPanel bottomPanel = new JPanel(new GridLayout(1,20)); //panel for exit button

        panel.setBorder(BorderFactory.createEmptyBorder(125, 100, 100, 125));
        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(Color.PINK);

        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 325, 15, 325)); //(top, left, bottom, right)
        bottomPanel.setBackground(Color.PINK);
        
        label.setFont(new Font("Arial", Font.PLAIN, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);

        createButton.setFont(new Font("Arial", Font.PLAIN, 35)); 
        createButton.addActionListener(this);

        exitButton.setFont(new Font("Arial", Font.PLAIN, 35));  
        exitButton.addActionListener(this);
        
        existingButton.setFont(new Font("Arial", Font.PLAIN, 35));
        existingButton.addActionListener(this);
        
        editButton.setFont(new Font("Arial", Font.PLAIN, 35));
        editButton.addActionListener(this);
        
        panel.add(createButton);
        panel.add(existingButton);
        panel.add(editButton);
        
        
        bottomPanel.add(exitButton, BorderLayout.SOUTH);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setBackground(Color.PINK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 750);
        frame.setLocationRelativeTo(null); //put in middle of screen
        frame.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) { //listens for click on a specific button
        JButton clickedButton = (JButton) e.getSource();
        if(clickedButton == exitButton){ //closes frame
            frame.dispose(); 
        }

        else if(clickedButton == createButton){ //takes you to create a new set
        	SwingUtilities.invokeLater(() -> new CreatingGUI());
        	frame.dispose();
        }
        
        else if(clickedButton == editButton){ //allows you to open existing set
        	SwingUtilities.invokeLater(() -> new OpenExisting());
        	frame.dispose();
            
        }
        
        else if(clickedButton == existingButton) { //used to study an existing set
        	openSet();
        	new ChooseStudyMethod(cardList);
        	frame.dispose();
        }
    }
    
    public void openSet() {
        // Prompt the user to choose an existing study set file
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showOpenDialog(this);
        int counter = 1;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            java.io.File selectedFile = fileChooser.getSelectedFile();
            
            // Read the contents of the file and populate the list
            
            try (Scanner scanner = new Scanner(selectedFile);) {
            	scanner.useDelimiter("~");
                String line;
                counter = 1;
                while (scanner.hasNext()) {
                	line = scanner.next();
                    String[] parts = line.split(":", 2);  // Split the line into term and definition
                    
                    if (parts.length >= 2) {
                        String term = parts[0];
                        String definition = parts[1];
                        Card newCard = new Card(term, definition, counter);
                        cardList.add(newCard);

                        counter++;
                    }
                 
                }
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading study set file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }

    public static void main(String[] args){
        new HomeGUI(); //create a new HomeGUI to open it
    }

}