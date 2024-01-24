package com.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Scanner;



public class MultipleChoice extends JFrame implements ActionListener {
    @Serial
	private static final long serialVersionUID = 1L;
    private JFrame frame = new JFrame("Multiple Choice");
	private JLabel termLabel = new JLabel();
    private ButtonGroup answerGroup = new ButtonGroup();
    private JRadioButton[] answerOptions = new JRadioButton[4];;
    private JButton shuffleButton = new JButton("Shuffle");
    private JButton skipButton = new JButton("Skip");
    private JButton optionsButton = new JButton("Options");
    private ArrayList<Card> cardList;
    private int currentCardIndex = 0;
	private int totalCardCount;
	private int correctAnswers = 0;
	private int questionsAnswered = 0;
	private boolean isCorrect;
	private String currentAnswer;

    public MultipleChoice(ArrayList<Card> passedList) {
    	
    	cardList = new ArrayList<>();
    	addCardFromList(cardList, passedList);
    	totalCardCount = cardList.size();
        

        
        termLabel.setFont(new Font("Arial", Font.PLAIN, 75));
        termLabel.setBackground(Color.PINK);
        termLabel.setOpaque(true);
        termLabel.setForeground(Color.BLACK);
        
        
        
        for (int i = 0; i < 4; i++) {
            answerOptions[i] = new JRadioButton();
            answerOptions[i].setFont(new Font("Arial", Font.PLAIN, 25));
            answerOptions[i].setBackground(Color.PINK);
            answerOptions[i].setOpaque(true);
            answerOptions[i].setContentAreaFilled(true);
            answerOptions[i].setForeground(Color.BLACK);
            answerOptions[i].addActionListener(this);
            answerGroup.add(answerOptions[i]);
        }
        
        JPanel buttonPanel = new JPanel(new GridLayout(1,3));
        

        shuffleButton.setFont(new Font("Arial", Font.PLAIN, 30));
        shuffleButton.setBackground(Color.PINK);
        shuffleButton.setOpaque(true);
        shuffleButton.setContentAreaFilled(true);
        shuffleButton.setForeground(Color.PINK);
        shuffleButton.addActionListener(this);
        
        
        skipButton.setFont(new Font("Arial", Font.PLAIN, 30));
        skipButton.setBackground(Color.PINK);
        skipButton.setOpaque(true);
        skipButton.setContentAreaFilled(true);
        skipButton.setForeground(Color.PINK);
        skipButton.addActionListener(this);
        
       
        optionsButton.setFont(new Font("Arial", Font.PLAIN, 30));
        optionsButton.setBackground(Color.PINK);
        optionsButton.setOpaque(true);
        optionsButton.setContentAreaFilled(true);
        optionsButton.setForeground(Color.PINK);
        optionsButton.addActionListener(this);
        
        
        buttonPanel.add(shuffleButton);
        buttonPanel.add(optionsButton);
        buttonPanel.add(skipButton);
        
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            optionsPanel.add(answerOptions[i]);
        }
        
        frame.setLayout(new BorderLayout());
        frame.add(termLabel, BorderLayout.NORTH);
        frame.add(optionsPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setTitle("Multiple Choice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 800);
        frame.setBackground(Color.PINK);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        showCard(0);
        
    }
        
    

    

    private void showCard(int index) {
        if (index >= 0 && index < cardList.size()) {
        	frame.setTitle("Multiple Choice (" + (currentCardIndex+1) + "/"+ totalCardCount + ")");
        	answerGroup.clearSelection();
            Card currentCard = cardList.get(index);
            termLabel.setText("<html>"+(currentCardIndex+1)+") " + currentCard.getTerm()+"</html>");
            String[] options = generateRandomOptions(currentCard.getDef());
            for (int i = 0; i < 4; i++) {
                answerOptions[i].setText("<html>"+options[i].replace("\n", "<br>")+"</html>");
                answerOptions[i].setSelected(false);
            }
            currentCardIndex = index;
        } else {
            recapGUI();
        }
    }
    public void showNextCard() {
    	currentCardIndex += 1;
    	showCard(currentCardIndex);
    }
    
    private String[] generateRandom(String correctOption) {
        String[] options = new String[4];
        options[0] = correctOption;

        
        ArrayList<Card> incorrectOptions = new ArrayList<>(cardList);
        incorrectOptions.remove(currentCardIndex); 
        java.util.Collections.shuffle(incorrectOptions);

        for (int i = 1; i < 4; i++) {
            options[i] = incorrectOptions.get(i - 1).getDef();
        }

        java.util.Collections.shuffle(java.util.Arrays.asList(options));
        return options;
    }
    
    

    public String[] generateRandomOptions(String correctOption) {
        if(totalCardCount >= 4)
        {
        	String[] bigOptions = generateRandom(correctOption);
        	java.util.Collections.shuffle(java.util.Arrays.asList(bigOptions));
        	return bigOptions;
        }
        else {
        	String[] smallOptions = {correctOption, "Option A", "Option B", "Option C"};
        	java.util.Collections.shuffle(java.util.Arrays.asList(smallOptions));
        	return smallOptions;
        }
        
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getSource() instanceof JRadioButton) {
            // Radio button selected
            for (int i = 0; i < 4; i++) {
                if (answerOptions[i].isSelected()) {
                    // Handle the selected answer (e.g., check if correct)
                	currentAnswer = answerOptions[i].getText();
                    handleSelectedAnswer(answerOptions[i].getText());
                    submitFrame();
                    break;
                }
            }
        }
    	else {
    	JButton clickedButton = (JButton) e.getSource();
    	if (clickedButton == shuffleButton) {
            shuffleCards();
        } 
        else if(clickedButton == skipButton) {
        	submitFrame();
        }
        else if(clickedButton == optionsButton) {
        	optionsFrame();
        }
    	}
        
    }

    private void handleSelectedAnswer(String selectedAnswer) {
        // Add your logic to handle the selected answer (e.g., check if correct)
        // For simplicity, let's display a message indicating if the answer is correct
        Card currentCard = cardList.get(currentCardIndex);
        String correctAnswer = currentCard.getDef().replace("\n", "<br>");
        isCorrect = selectedAnswer.equals("<html>"+correctAnswer+"</html>");
        
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
        showCard(0);
    }
    
    public void shuffleCards() {
        // Implement the logic to shuffle the cards
        java.util.Collections.shuffle(cardList);
        currentCardIndex = 0;
        questionsAnswered = 0;
        correctAnswers = 0;
        showCard(currentCardIndex);
        
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
                        String temp = newCard.getDef();
                        temp.replace("\n", "<br>");
                        newCard.setDef(temp);
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
    //SUBMUT FRAME
    public void submitFrame()
	{
		
			
			
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
		
		String term = currentAnswer;
		if(isCorrect)
		{
			label.setText("Correct!");
			correctAnswers++;
		}
		else {
			label.setText("Incorrect!");
			
		}
		yourAnswer.setText("<html><b><center>You answered: </center></b><center>"+term+"</center></html>");
		answerLabel.setText("<html><b><center>The answer was: </center></b><center>"+currentCard.getDef()+"</center></html>");
		
		
		submitFrame.setLayout(new GridLayout(4,1));
		submitFrame.add(label);
		submitFrame.add(yourAnswer);
		submitFrame.add(answerLabel);
		submitFrame.add(nextButton);
		
		
		
		
		
		submitFrame.setBackground(Color.PINK);
		submitFrame.setSize(500, 650);
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
	            isCorrect = false;
	            yourAnswer.setText("");
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
    	        	showNextCard();
    	        	isCorrect = false;
    	            currentAnswer = "";
    	        	
    	        	}
    	        }
    	        
    	    }
    	});
		
		
		
	}
    
    //RECAP FRAME
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
	            showCard(currentCardIndex);
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
		
		
	}
    
    
    //OPTIONS FRAME
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

public void addCardFromList(ArrayList<Card> newList, ArrayList<Card> original) {
	for(Card currentCard : original) {
		newList.add(currentCard);
	}
}

    public static void main(String[] args) {
        
    }
}