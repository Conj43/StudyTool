package com.tool;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class FlashcardStudy extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Flashcards Review Mode!");
	private JPanel cardPanel = new JPanel();
	private JLabel cardLabel = new JLabel("", SwingConstants.CENTER);
	private JLabel cardNumber = new JLabel();
	private JButton rightButton = new JButton("->");
	private JButton leftButton = new JButton("<-");
	private JButton flipButton = new JButton("Flip");
	private JButton shuffleButton = new JButton("Shuffle");
	private JButton optionsButton = new JButton("Options");
	private JButton restartButton = new JButton("Restart");
	private JButton learnReviewButton = new JButton("Switch to Learn!");
	private JPanel buttonPanel = new JPanel();
	private ArrayList<Card> cardList;
	private int currentCardIndex = 0;
	private int totalCardCount;
	private int textSize = 75;
	Learn learnFrame;
	

	public FlashcardStudy(ArrayList<Card> passedList) {
		// TODO Auto-generated constructor stub
		cardList = new ArrayList<>();
		addCardFromList(cardList, passedList);
		totalCardCount = cardList.size();
		//printCards();
		
		cardPanel.setLayout(new BorderLayout());
		cardPanel.add(cardLabel, BorderLayout.CENTER);
		cardPanel.setBackground(Color.PINK);
		//cardPanel.setBorder(BorderFactory.createEmptyBorder(275, 150, 25, 150));
		cardPanel.setBorder(new LineBorder(Color.BLACK, 5));
		
		cardLabel.setBackground(Color.PINK);
		
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(Color.PINK);
		buttonPanel.add(learnReviewButton, 0);
        buttonPanel.add(shuffleButton, 1);
        buttonPanel.add(cardNumber, 2);
        buttonPanel.add(optionsButton, 3);
        buttonPanel.add(restartButton, 4);
        
		
		cardNumber.setBackground(Color.PINK);
		
		cardLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
		flipButton.setFont(new Font("Arial", Font.PLAIN,55));
		cardNumber.setFont(new Font("Arial", Font.PLAIN, 25));
		rightButton.setFont(new Font("Arial", Font.PLAIN, 40));
		leftButton.setFont(new Font("Arial", Font.PLAIN, 40));
		
		learnReviewButton.setFont(new Font("Arial", Font.PLAIN, 20));
		learnReviewButton.setBackground(Color.PINK);
		learnReviewButton.setOpaque(true);
		learnReviewButton.setContentAreaFilled(true);
		learnReviewButton.setForeground(Color.PINK);
		learnReviewButton.addActionListener(this);
		
		optionsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        optionsButton.setBackground(Color.PINK);
        optionsButton.setOpaque(true);
        optionsButton.setContentAreaFilled(true);
        optionsButton.setForeground(Color.PINK);
        optionsButton.addActionListener(this);
		
		shuffleButton.setFont(new Font("Arial", Font.PLAIN, 20));
        shuffleButton.setBackground(Color.PINK);
        shuffleButton.setOpaque(true);
        shuffleButton.setContentAreaFilled(true);
        shuffleButton.setForeground(Color.PINK);
        
        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.setBackground(Color.PINK);
        restartButton.setOpaque(true);
        restartButton.setContentAreaFilled(true);
        restartButton.setForeground(Color.PINK);
        restartButton.addActionListener(this);
		
		rightButton.setBackground(Color.PINK);
		rightButton.setOpaque(true);
		rightButton.setContentAreaFilled(true);
		rightButton.setForeground(Color.PINK);
		
		leftButton.setBackground(Color.PINK);
		leftButton.setOpaque(true);
		leftButton.setContentAreaFilled(true);
		leftButton.setForeground(Color.PINK);
		
		flipButton.setBackground(Color.PINK);
		flipButton.setOpaque(true);
		flipButton.setContentAreaFilled(true);
		flipButton.setForeground(Color.PINK);
		
		showCurrentCard();
		
		frame.setLayout(new BorderLayout());
		frame.addKeyListener(this);
		frame.add(leftButton, BorderLayout.WEST);
		frame.add(rightButton, BorderLayout.EAST);
		frame.add(flipButton, BorderLayout.SOUTH);
		frame.add(cardPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.NORTH);
		frame.setBackground(Color.PINK);
		frame.setFocusable(true);
		
		
		rightButton.addActionListener(this);
		leftButton.addActionListener(this);
		flipButton.addActionListener(this);
		shuffleButton.addActionListener(this);
		
		frame.setSize(1100, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
	}
	
	public void optionsFrame() {
		
		JFrame optionFrame = new JFrame("Options");
		JButton flipButton = new JButton("Flip Term & Definition");
		JButton studyButton = new JButton("Choose a Different Study Method");
		JButton homeButton = new JButton("Go Home");
		JButton exitButton = new JButton("Exit");
		
		optionFrame.setLayout(new GridLayout(4,1));
		
		optionFrame.add(flipButton);
		optionFrame.add(studyButton);
		optionFrame.add(homeButton);
		optionFrame.add(exitButton);
		
		flipButton.setBackground(Color.PINK);
		flipButton.setOpaque(true);
		flipButton.setContentAreaFilled(true);
		
		studyButton.setBackground(Color.PINK);
		studyButton.setOpaque(true);
		studyButton.setContentAreaFilled(true);
		
		homeButton.setBackground(Color.PINK);
		homeButton.setOpaque(true);
		homeButton.setContentAreaFilled(true);
		
		
		exitButton.setBackground(Color.PINK);
		exitButton.setOpaque(true);
		exitButton.setContentAreaFilled(true);
		
		
		flipButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	optionFrame.dispose();
	            reverseSet();
	            
	        }
	    });
		
		studyButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	optionFrame.dispose();
	        	frame.dispose();
	            new ChooseStudyMethod(cardList);
	            
	        }
	    });
		

		
		homeButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose();
	            optionFrame.dispose();
	            new HomeGUI();
	        }
	    });
		
		exitButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            optionFrame.dispose();
	        }
	    });
		
		
		
		
		
		optionFrame.setBackground(Color.PINK);
		optionFrame.setSize(350, 350);
		optionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		optionFrame.setVisible(true);
		optionFrame.setLocationRelativeTo(null);
		
	}
	
	
	

	public void actionPerformed(ActionEvent e) { //listens for click on a specific button
        JButton clickedButton = (JButton) e.getSource();
        if(clickedButton == rightButton){
        	
        	showNextCard();
        	
        }

        else if(clickedButton == leftButton){
        	showPreviousCard();
        	
        	
        }
        
        else if(clickedButton == flipButton) {
        	flipCard();
        	
        }
        else if(clickedButton == shuffleButton) {
        	shuffleCards();
        	
        }
        else if(clickedButton == optionsButton) {
        	optionsFrame();
        	
        }
        else if(clickedButton == restartButton) {
        	restartSet();
        	
        }
        else if(clickedButton == learnReviewButton) {
        	
        	new Learn(cardList);
        	frame.dispose();
        	 
        	}
        frame.requestFocus();
        
    }
	
	
    @Override
    public void keyPressed(KeyEvent e) {
        // Handle key presses
        int keyCode = e.getKeyCode();

       
            if(keyCode == KeyEvent.VK_SPACE) {
                // Flip the card
                flipCard();
            }
            else if(keyCode == KeyEvent.VK_RIGHT) {
            	//System.out.println(currentLearnCount);
                // Move to the next card
            	
            		showNextCard();
            	}
            else if(keyCode == KeyEvent.VK_LEFT) {
                // Move to the previous card
                showPreviousCard();
            }
            // Add more cases if needed
        
    }
    
    
    private void shuffleCards() {
        // Implement the logic to shuffle the cards
        java.util.Collections.shuffle(cardList);
        currentCardIndex = 0;
        showCurrentCard();
    }
	
	public void flipCard() {
		
        if (!cardList.isEmpty() && currentCardIndex >= 0 && currentCardIndex < cardList.size()) {
            Card currentCard = cardList.get(currentCardIndex);
            textSize = (textSize == 75) ? 45 : 75;
            
            cardLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
            if (cardLabel.getText().equals("<html>"+currentCard.getTerm()+"</html>")) {
                cardLabel.setText("<html>"+currentCard.getDef().replace("\n", "<br>")+"</html>");
                
            } else {
                cardLabel.setText("<html>"+currentCard.getTerm()+"</html>");
            }
        }
		}
       
        
        
    
	
	
	public void restartSet() {
		currentCardIndex = 0;
		showCurrentCard();
	}
	
	
	
	public void showCurrentCard() {
		
	    if (!cardList.isEmpty() && currentCardIndex >= 0 && currentCardIndex < cardList.size()) {
	    	cardLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
	        Card currentCard = cardList.get(currentCardIndex);
	        String htmlText = "<html>" + currentCard.getTerm() + "</html>";
	        
	        cardLabel.setText(htmlText);
	        cardNumber.setText((currentCardIndex + 1) + "/" + totalCardCount);
	    } else {
	        cardLabel.setText("No cards available.");
	    }
		}
	
		
	
	public void showNextCard() {
		if(textSize == 45) {
			textSize = 75;
		}
		if (currentCardIndex < totalCardCount - 1) {
	            currentCardIndex++;
	            showCurrentCard();
	        }
		}

	
	
	
	
	

    public void showPreviousCard() {
        if (currentCardIndex > 0) {
            currentCardIndex--;
            showCurrentCard();
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
            totalCardCount = counter-1;
        }
    }
	
	public void reverseSet() {
        for (Card card: cardList) {
        	String originalTerm = card.getTerm();
            String originalDefinition = card.getDef();

            // Swap term and definition
            card.setTerm(originalDefinition);
            card.setDef(originalTerm);
            
        }
        currentCardIndex = 0;
        showCurrentCard();
    }
	
	

	
	

		
	
		
		
	
	

	
	public void printCards() {
	    for (Card card : cardList) {
	        System.out.println("Term: " + card.getTerm() + "\n Definition: " + card.getDef() + "\n Number: " + card.getNum()+"\n\n");
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


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



}
