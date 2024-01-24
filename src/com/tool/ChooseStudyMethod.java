package com.tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class ChooseStudyMethod implements ActionListener {
	private JFrame frame = new JFrame();
	private JButton homeButton = new JButton("Home");
	private JButton flashcardButton = new JButton("Study Using Flashcards");
	private JButton fillInButton = new JButton("Study Using Fill in the Blank");
	private JButton testButton = new JButton("Study Using a Multiple Choice Test");
	private JLabel label = new JLabel("Choose a Study Method!", SwingConstants.CENTER);
	private JPanel topPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private ArrayList<Card> selectedList = new ArrayList<>();
	
	

	public ChooseStudyMethod(ArrayList<Card> cardList) {

		addCardFromList(selectedList, cardList);
		
		topPanel.setLayout(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(5, 425, 50, 425));
		topPanel.add(homeButton);
		topPanel.setBackground(Color.PINK);
		
		centerPanel.setLayout(new GridLayout(4,1));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 100, 125));
		centerPanel.add(label);
		centerPanel.add(flashcardButton);
		centerPanel.add(fillInButton);
		centerPanel.add(testButton);
		centerPanel.setBackground(Color.PINK);
		
		homeButton.addActionListener(this);
		flashcardButton.addActionListener(this);
		fillInButton.addActionListener(this);
		testButton.addActionListener(this);
		
		label.setFont(new Font("Arial", Font.PLAIN, 50));
		homeButton.setFont(new Font("Arial", Font.PLAIN, 35));
		flashcardButton.setFont(new Font("Arial", Font.PLAIN,35));
		fillInButton.setFont(new Font("Arial", Font.PLAIN, 35));
		testButton.setFont(new Font("Arial", Font.PLAIN, 35));
		
		frame.setLayout(new BorderLayout());
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.setBackground(Color.PINK);
		frame.setSize(1100, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
	}
	
	
	public void actionPerformed(ActionEvent e) { //listens for click on a specific button
        JButton clickedButton = (JButton) e.getSource();
        if(clickedButton == homeButton){
        	new HomeGUI();
        	frame.dispose();
        }

        else if(clickedButton == flashcardButton){
        	new FlashcardStudy(selectedList);
        	frame.dispose();
        	
        }
        else if(clickedButton == fillInButton) {
        	SwingUtilities.invokeLater(() -> new FillInBlank(selectedList));
        	frame.dispose();
        }
        else if(clickedButton == testButton) {
        	SwingUtilities.invokeLater(() -> {
                new MultipleChoice(selectedList);
            });
        	frame.dispose();
        }
        
        
        
        
    }
	
	
	public void addCardFromList(ArrayList<Card> newList, ArrayList<Card> original) {
		for(Card currentCard : original) {
			newList.add(currentCard);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
