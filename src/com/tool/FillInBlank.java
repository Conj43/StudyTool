package com.tool;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class FillInBlank extends JFrame implements ActionListener, KeyListener{
	
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Fill In The Blank");
	private JTextArea textLabel = new JTextArea("Welcome!");
	private JTextArea blank;
	private JPanel buttonPanel = new JPanel();
	private JLabel cardNumber = new JLabel();
	private JButton shuffleButton = new JButton("Shuffle");
	private JButton optionsButton = new JButton("Options");
	private JButton skipButton = new JButton("Skip");
	private JButton submitButton = new JButton("Submit");
	private JPanel bottomPanel = new JPanel();
	private JPanel textPanel = new JPanel();
	private ArrayList<Card> cardList;
	private int currentCardIndex = 0;
	private int totalCardCount;
	private int correctAnswers = 0;
	private int questionsAnswered = 0;

	public FillInBlank(ArrayList<Card> passedList) {
		
		cardList = new ArrayList<>();
		
		addCardFromList(cardList, passedList);
		totalCardCount = cardList.size();
		blank = new JTextArea(1, 15);
		blank.setLineWrap(true);
		blank.setWrapStyleWord(true);
		blank.setFont(new Font("Arial", Font.PLAIN, 45));
		
		//textLabel.setEditable(false);
		textLabel.setWrapStyleWord(true);
		textLabel.setLineWrap(true);
		textLabel.setOpaque(false);
		textLabel.setFocusable(false);
		textLabel.setBackground(new Color(0, 0, 0, 0));
		textLabel.setFont(new Font("Arial", Font.PLAIN, 45));
		
		textPanel.setBorder(BorderFactory.createEmptyBorder(25, 225, 60, 225)); //(top, left, bottom, right)
		textPanel.setLayout(new BorderLayout());
		textPanel.add(textLabel, BorderLayout.CENTER);
		textPanel.add(blank, BorderLayout.SOUTH);
		
		
		
		textLabel.setBackground(Color.PINK);
		textLabel.setPreferredSize(new Dimension(250, 500));
		
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(Color.PINK);
        buttonPanel.add(shuffleButton, 0);
        buttonPanel.add(cardNumber, 1);
        buttonPanel.add(optionsButton, 2);
        
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.PINK);
        bottomPanel.add(skipButton, 0);
        bottomPanel.add(submitButton, 1);
        
        
        blank.setPreferredSize(new Dimension(125, 50));

       
        
        shuffleButton.setPreferredSize(new Dimension(175, 75));
        optionsButton.setPreferredSize(new Dimension(175, 75));
        skipButton.setPreferredSize(new Dimension(175, 75));
        submitButton.setPreferredSize(new Dimension(175, 75));
        

		
		cardNumber.setBackground(Color.PINK);
		textPanel.setBackground(Color.PINK);
		
		textLabel.setFont(new Font("Arial", Font.PLAIN, 45));
		shuffleButton.setFont(new Font("Arial", Font.PLAIN,55));
		cardNumber.setFont(new Font("Arial", Font.PLAIN, 25));
		skipButton.setFont(new Font("Arial", Font.PLAIN, 40));
		submitButton.setFont(new Font("Arial", Font.PLAIN, 40));
		optionsButton.setFont(new Font("Arial", Font.PLAIN, 40));
		
		optionsButton.setFont(new Font("Arial", Font.PLAIN, 45));
        optionsButton.setBackground(Color.PINK);
        optionsButton.setOpaque(true);
        optionsButton.setContentAreaFilled(true);
        optionsButton.setForeground(Color.PINK);
        optionsButton.addActionListener(this);
		
		shuffleButton.setFont(new Font("Arial", Font.PLAIN, 45));
        shuffleButton.setBackground(Color.PINK);
        shuffleButton.setOpaque(true);
        shuffleButton.setContentAreaFilled(true);
        shuffleButton.setForeground(Color.PINK);
        shuffleButton.addActionListener(this);
        
        skipButton.setFont(new Font("Arial", Font.PLAIN, 45));
        skipButton.setBackground(Color.PINK);
        skipButton.setOpaque(true);
        skipButton.setContentAreaFilled(true);
        skipButton.setForeground(Color.PINK);
        skipButton.addActionListener(this);
		
        submitButton.setFont(new Font("Arial", Font.PLAIN, 45));
        submitButton.setBackground(Color.PINK);
        submitButton.setOpaque(true);
        submitButton.setContentAreaFilled(true);
        submitButton.setForeground(Color.PINK);
        submitButton.addActionListener(this);
        

        
		frame.setLayout(new BorderLayout());
		frame.addKeyListener(this);
		frame.add(buttonPanel, BorderLayout.NORTH);
		frame.add(textPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.setBackground(Color.PINK);
		frame.setSize(1100, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        
        showCurrentCard();
        
        blank.requestFocus();
        
        
        blank.addKeyListener(new KeyAdapter() {
    	    @Override
    	    public void keyPressed(KeyEvent e) {
    	        int keyCode = e.getKeyCode();
    	        if (keyCode == KeyEvent.VK_ENTER) {
    	            questionsAnswered++;
    	            submitFrame();  
    	        }
    	    }  	    
    	});
	}
	
	
	

	
	
	public void actionPerformed(ActionEvent e) { //listens for click on a specific button
        JButton clickedButton = (JButton) e.getSource();
        if(clickedButton == optionsButton){
        	optionsFrame();
        	
        }

        else if(clickedButton == shuffleButton){
        	shuffleCards();
        	
        	
        }
        
        else if(clickedButton == skipButton){
        	questionsAnswered++;
        	if(questionsAnswered >= totalCardCount) {
    			recapGUI();
    		}
        	else submitFrame();
        	
        	
        }
        
        else if(clickedButton == submitButton){
        	questionsAnswered++;
        	if(questionsAnswered >= totalCardCount) {
    			recapGUI();
    		}
        	else submitFrame();
        	
        	
        }
        
        blank.requestFocus();
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

	public void shuffleCards() {
    // Implement the logic to shuffle the cards
    java.util.Collections.shuffle(cardList);
    currentCardIndex = 0;
    questionsAnswered = 0;
    correctAnswers = 0;
    showCurrentCard();
    blank.setText("");
    
}
	

	
	public void submitFrame()
	{
		
			
			
		String term = blank.getText().trim().toLowerCase();
		Card currentCard = cardList.get(currentCardIndex);
		
		JFrame submitFrame = new JFrame();
		JButton nextButton = new JButton("Next Term!");
		JLabel label = new JLabel("", SwingConstants.CENTER);
		JLabel yourAnswer = new JLabel("", SwingConstants.CENTER);
		JLabel answerLabel = new JLabel("", SwingConstants.CENTER);
		
		label.setFont(new Font("Arial", Font.PLAIN, 35));
		label.setBackground(Color.PINK);
		label.setOpaque(true);
		
		yourAnswer.setFont(new Font("Arial", Font.PLAIN, 20));
		yourAnswer.setBackground(Color.PINK);
		yourAnswer.setOpaque(true);
		
		answerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		answerLabel.setBackground(Color.PINK);
		answerLabel.setOpaque(true);
		
		nextButton.setFont(new Font("Arial", Font.PLAIN, 30));
		nextButton.setBackground(Color.PINK);
		nextButton.setOpaque(true);
		nextButton.setContentAreaFilled(true);
		
		
		
		String checkTerm = currentCard.getTerm().trim().toLowerCase();
		if(term.equals(checkTerm))
		{
			label.setText("Correct!");
			correctAnswers++;
		}
		else {
			label.setText("Incorrect!");
			
		}
		yourAnswer.setText("<html><b><center>You answered: </center></b><center>"+term+"</center></html>");
		answerLabel.setText("<html><b><center>The answer was: </center></b><center>"+currentCard.getTerm()+"</center></html>");
		
		
		submitFrame.setLayout(new GridLayout(4,1));
		submitFrame.add(label);
		submitFrame.add(yourAnswer);
		submitFrame.add(answerLabel);
		submitFrame.add(nextButton);
		
		
		
		
		
		submitFrame.setBackground(Color.PINK);
		submitFrame.setSize(500, 600);
		submitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		submitFrame.setVisible(true);
		submitFrame.setLocationRelativeTo(null);
		
		
		nextButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (questionsAnswered >= totalCardCount) {
	        		submitFrame.dispose();
	                recapGUI();
	            }
	        	else {
	            submitFrame.dispose();
	            showNextCard();
	            blank.setText("");
	            blank.requestFocusInWindow();
	        	}
	            
	        }
	    });
		
		nextButton.addKeyListener(new KeyAdapter() {
    	    @Override
    	    public void keyPressed(KeyEvent e) {
    	        int keyCode = e.getKeyCode();
    	        if (keyCode == KeyEvent.VK_ENTER) {
    	        	if (questionsAnswered >= totalCardCount) {
    	        		submitFrame.dispose();
    	                recapGUI();
    	            }
    	        	else {
    	        	submitFrame.dispose();
    	        	SwingUtilities.invokeLater(() -> showNextCard());
    	        	blank.setText("");
                    blank.requestFocusInWindow();
    	        	}
    	        }
    	        
    	    }
    	});
		
		
		
	}
	
	
	public void recapGUI() {
		JFrame recapFrame = new JFrame("Great Job!");
		JLabel stats = new JLabel("", SwingConstants.CENTER);
		JButton restartButton = new JButton("Restart");
		JButton studyButton = new JButton("Change Study Method");
		
		
		double correct = correctAnswers;
		double total = totalCardCount;
		double percent = (correct/total)*100;
		String formattedDouble = String.format("%.2f", percent);
		
		stats.setText("You got " + correctAnswers + "/"+totalCardCount+" correct ("+formattedDouble+"%)");
		//System.out.println("correct: "+correctAnswers+" total cards: "+totalCardCount);
		stats.setFont(new Font("Arial", Font.PLAIN, 15));
		stats.setBackground(Color.PINK);
		stats.setOpaque(true);
		
		restartButton.setFont(new Font("Arial", Font.PLAIN, 30));
		restartButton.setBackground(Color.PINK);
		restartButton.setOpaque(true);
		restartButton.setContentAreaFilled(true);
		
		studyButton.setFont(new Font("Arial", Font.PLAIN, 30));
		studyButton.setBackground(Color.PINK);
		studyButton.setOpaque(true);
		studyButton.setContentAreaFilled(true);
		
		
		restartButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            recapFrame.dispose();
	            currentCardIndex = 0;
	            correctAnswers = 0;
	            questionsAnswered = 0;
	            SwingUtilities.invokeLater(() -> showCurrentCard());
	            blank.setText("");
                blank.requestFocus();
	        }
	    });
		
		studyButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            recapFrame.dispose();
	            frame.dispose();
	            new ChooseStudyMethod(cardList);
	        }
	    });
		recapFrame.setLayout(new GridLayout(3,1));
		recapFrame.add(stats);
		recapFrame.add(restartButton);
		recapFrame.add(studyButton);
		
		
		recapFrame.setBackground(Color.PINK);
		recapFrame.setSize(450, 450);
		recapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		recapFrame.setVisible(true);
		recapFrame.setLocationRelativeTo(null);
		
		
		blank.setText("");
	}


	public void reverseSet() {
    for (Card card: cardList) {
    	String originalTerm = card.getTerm();
        String originalDefinition = card.getDef();

        // Swap term and definition
        card.setTerm(originalDefinition);
        card.setDef(originalTerm);
        
    }
    correctAnswers = 0;
    questionsAnswered = 0;
    currentCardIndex = 0;
    showCurrentCard();
}
	
	public void showNextCard() {
        if (currentCardIndex < cardList.size() - 1) {
            currentCardIndex++;
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
	
	
	public void showCurrentCard() {
		if (!cardList.isEmpty() && currentCardIndex >= 0 && currentCardIndex < cardList.size()) {
            Card currentCard = cardList.get(currentCardIndex);
            textLabel.setText(currentCard.getDef());
            cardNumber.setText((currentCardIndex+1)+"/"+totalCardCount);
            blank.requestFocus();
            
        } else {
            textLabel.setText("No cards available.");
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




	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}





}
