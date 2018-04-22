package com.blazingbbq.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Random;

public class HoCMain {

	public static void main(String[] args) {
		
		Game game = new Game();
		
		game.run();
		
	}
	
}

class Game{
	
	// Frame
	public JFrame frame;
	public int frameHeight = 800;
	public int frameWidth = 700;
	
	// Menu components
	public JPanel menu;
	public JLabel titleLabel;
	public JButton staminaTrial;
	public JButton timeTrial;
	public JButton missedLibrary;
	public JButton help;
	public JButton exit;
	
	public JPanel records;
	public JLabel recordsLabel;
	public JLabel staminaRecord;
	public JLabel timeRecord;
	
	public final int PADDING_W = 0;
	public final int PADDING_H = 0;
	
	// Stamina Trial components
	public JPanel stamina;
	public JLabel img;
	
	public JPanel rightGroup;
	public JRadioButton b1, b2, b3, b4, b5;
	public ButtonGroup bg;
	public JLabel numCorrect;
	public JLabel numIncorrect;
	public JLabel numSkips;
	public JButton skip;
	public JButton backToMenu;
	
	public int correctCount;
	public int incorrectCount;
	public int skipCount;
	
	public final int NUM_MPS = 338;
	public int correctBtn;
	public boolean[] mpsDone;
	public int num; 				// Current MP ID
	
	public Random random = new Random();
	
	// Time trial components
	public JPanel time;
	public JLabel timeImg;
	public JTextField timeText;
	public JRadioButton timeRadio1, timeRadio2, timeRadio3, timeRadio4;
	public ButtonGroup timeBg;
	public JLabel timeNumCorrect;
	public JLabel timeNumIncorrect;
	public JLabel timeNumSkips;
	public JLabel timeStatus;
	public JButton timeSkip;
	public JButton timeBackToMenu;
	
	// Missed library components
	public JPanel libraryPanel;
	public JLabel libraryImg;
	public JLabel libraryInfoName;
	public JLabel libraryInfoParty;
	public JButton libraryPrevious;
	public JButton libraryNext;
	public JButton libraryGotIt;
	public JButton libraryBack;
	
	public int libraryCurrentID;
	
	// Back to menu screen components
	public JPanel btmPanel;
	public JLabel btmTitle;
	public JLabel btmCorrect;
	public JLabel btmIncorrect;
	public JLabel btmSkip;
	public JLabel btmMissedWarning;
	public JButton btmBackToMenu;
	public JButton btmBackToGame;
	
	// Help screen components
	public JPanel helpPanel;
	public JTextArea helpMultipleChoice;
	public JTextArea helpRealistic;
	public JTextArea helpMissedLibrary;
	public JTextArea helpOther;
	public JButton helpBackToMenu;
	
	public String helpMultipleChoiceText = 	
			  "Multiple Choice: " + "\n\t" 
			+ "- You'll be presented with the picture of an MP, as well as five names. " + "\n\t"
			+ "- You need to select the correct name to match the MP. " + "\n\t" 
			+ "- If you don't know an MP you can skip it by pressing the \"Skip\" button. " + "\n\t" 
			+ "- Any MP you skip or get incorrect with appear in your \"My Missed MPs\" screen, accessible from the main menu. " + "\n\t";
	public String helpRealisticText = 		
			  "Realistic: " + "\n\t"
			+ "- You'll be presented with the picture of an MP, a text box and four party options. " + "\n\t"
			+ "- You need to type the MP's name correctly and select the correct party. " + "\n\t"
			+ "- You will be prompted if you get the name, party or both incorrect. " + "\n\t"
			+ "- If you don't know an MP you can skip it by pressing the \"Skip\" button. " + "\n\t"
			+ "- Any MP you skip or get incorrect with appear in your \"My Missed MPs\" screen, accessible from the main menu. " + "\n\t";
	public String helpMissedLibraryText = 	
			"My Missed MPs: " + "\n\t"
			+ "- Here, you'll be able to revise any and all MPs you skipped or got incorrect in either game mode. " + "\n\t"
			+ "- Use the \"Next\" and \"Previous\" buttons to navigate the library of MPs. " + "\n\t"
			+ "- If you think you've got an MP memorized, click the \"Got it!\" button to remove them from the selection. " + "\n\t";
	public String helpOtherText = 			
			  "Other information: " + "\n\t"
			+ "- Nothing to show :)" + "\n\t";
	
	public int helpTextRows = 4;
	public int helpTextCollumns = 40;
	
	// Skipped and incorrect MPs
	public ArrayList<Integer> missed;
	
	// Fonts
	public Font titleFont = new Font(Font.SERIF, Font.BOLD, 44);
	public Font buttonFont = new Font(Font.SERIF, Font.PLAIN, 18);
	public Font recordsFont = new Font(Font.SERIF, Font.ITALIC, 24);
	
	public Font radioButtonFont = new Font(Font.SERIF, Font.PLAIN, 20);
	public Font correctIncorrectFont = new Font(Font.SERIF, Font.BOLD, 24);
	public Font gameButtonFont = new Font(Font.SERIF, Font.PLAIN, 20);
	
	public Font libraryButtonFont = new Font(Font.SERIF, Font.PLAIN, 18);
	public Font libraryInfoNameFont = new Font(Font.SERIF, Font.BOLD, 36);
	public Font libraryInfoPartyFont = new Font(Font.SERIF, Font.BOLD, 24);
	public Font libraryImageFont = new Font(Font.SERIF, Font.BOLD, 56);
	
	public Font timeTextFieldFont = new Font(Font.SERIF, Font.PLAIN, 24);
	public Font timeRadioButtonFont = new Font(Font.SERIF, Font.PLAIN, 20);
	public Font timeStatusFont = new Font(Font.SERIF, Font.ITALIC, 16);
	
	public Font btmTitleFont = new Font(Font.SERIF, Font.BOLD, 64);
	public Font btmNumberFont = new Font(Font.SERIF, Font.BOLD, 24);
	public Font btmMissedWarningFont = new Font(Font.SERIF, Font.ITALIC, 16);
	public Font btmButtonFont = new Font(Font.SERIF, Font.PLAIN, 20);
	
	public Font helpTextFont = new Font(Font.SERIF, Font.PLAIN, 18);
	public Font helpButtonFont = new Font(Font.SERIF, Font.PLAIN, 20);
	
	// Resource loading
	public String regex = new String(" ");
	
	public String recordsPath = new String("./Resources/records.txt");		// TODONE: Remove '/Resources' from strings before packaging project
	public String imagePath = new String("./Resources/MPs/Images/");
	public String infoPath = new String("./Resources/MPs/Info/");
	public String missedPath = new String("./Resources/missed.txt");
	
	// Initial starting point
	public void run() {
		
		// Initialize the frame and the menu, creation of game screens is done on start of game
		initFrame();
		initMenu();
		
	}
	
	// Initialize the main frame
	public void initFrame() {
		
		frame = new JFrame("HoC - Quick Guess");
		frame.setSize(frameHeight, frameWidth);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}
	
	// Initialize the menu with all functionalities 
	public void initMenu() {
		
		// Menu initialization
		menu = new JPanel(new GridLayout(7,1, PADDING_W, PADDING_H));
		menu.setSize(frameHeight, frameWidth);
		
		// Title label
		titleLabel = new JLabel("House of Commons MP Practice Quiz", JLabel.CENTER);
		titleLabel.setFont(titleFont);
		menu.add(titleLabel);
		
		// Records
		records = new JPanel(new GridLayout(1, 3));
		
		recordsLabel = new JLabel("Previous Records - ", JLabel.RIGHT);
		recordsLabel.setFont(recordsFont);
		records.add(recordsLabel);
		
		staminaRecord = new JLabel("Multiple Choice: " + getStaminaRecord(), JLabel.CENTER);
		staminaRecord.setFont(recordsFont);
		records.add(staminaRecord);
		
		timeRecord = new JLabel("Realistic: " + getTimeRecord(), JLabel.LEFT);
		timeRecord.setFont(recordsFont);
		records.add(timeRecord);
		
		menu.add(records);
		
		// Buttons - Stamina Trial
		staminaTrial = new JButton("Start Multiple Choice Challenge");
		staminaTrial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initStaminaTrial();
			}
		});
		staminaTrial.setFont(buttonFont);
		menu.add(staminaTrial);
		
		// Button - Time Trial
		timeTrial = new JButton("Start Realistic Challenge");	
		timeTrial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initTimeTrial();
			}
		});
		timeTrial.setFont(buttonFont);
		menu.add(timeTrial);
		
		// Button - Missed Library
		missedLibrary = new JButton("My Missed MPs");	
		missedLibrary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initMissedLibrary();
			}
		});
		missedLibrary.setFont(buttonFont);
		menu.add(missedLibrary);
		
		// Button - Help
		help = new JButton("Help");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initHelp();
			}
		});
		help.setFont(buttonFont);
		menu.add(help);
		
		// Button - Exit
		exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		exit.setFont(buttonFont);
		menu.add(exit);
		
		// Pack the frame and set its content pane
		frame.setContentPane(menu);
		frame.pack();
		
	}
	
	// Update menu display
	public void updateMenu() {
		
		staminaRecord.setText("Multiple Choice " + getStaminaRecord());
		timeRecord.setText("Realistic: " + getTimeRecord());
		
		frame.update(frame.getGraphics());
		
	}
	
	// Initialize stamina trial game screen and set the frame
	public void initStaminaTrial() {
		
		// TODONE: Setup stamina trial
		// Initialize game screen JPanel
		stamina = new JPanel(new GridLayout(1, 2));
		
		// Image Label
		img = new JLabel("", JLabel.CENTER);
		stamina.add(img);
		
		// Initialize button group
		rightGroup = new JPanel(new GridLayout(7, 1));
		
		bg = new ButtonGroup();
		ActionListener radioButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				onRadioButtonAction(e.getActionCommand());
				
			}
		};
		
		// Radio buttons
		// b1
		b1 = new JRadioButton("");
		b1.setActionCommand("0");
		b1.addActionListener(radioButtonListener);
		b1.setFont(radioButtonFont);
		bg.add(b1);
		rightGroup.add(b1);
		// b2
		b2 = new JRadioButton("");
		b2.setActionCommand("1");
		b2.addActionListener(radioButtonListener);
		b2.setFont(radioButtonFont);
		bg.add(b2);
		rightGroup.add(b2);
		// b3
		b3 = new JRadioButton("");
		b3.setActionCommand("2");
		b3.addActionListener(radioButtonListener);
		b3.setFont(radioButtonFont);
		bg.add(b3);
		rightGroup.add(b3);
		// b4
		b4 = new JRadioButton("");
		b4.setActionCommand("3");
		b4.addActionListener(radioButtonListener);
		b4.setFont(radioButtonFont);
		bg.add(b4);
		rightGroup.add(b4);
		// b5
		b5 = new JRadioButton("");
		b5.setActionCommand("4");
		b5.addActionListener(radioButtonListener);
		b5.setFont(radioButtonFont);
		bg.add(b5);
		rightGroup.add(b5);
		
		// Initialize correct, incorrect and skip counts
		correctCount = 0;
		incorrectCount = 0;
		skipCount = 0;
		
		// Create labels for the correct, incorrect and skip counts
		numCorrect = new JLabel("Correct: " + correctCount);
		numCorrect.setFont(correctIncorrectFont);
		
		numIncorrect = new JLabel("Incorrect: " + incorrectCount);
		numIncorrect.setFont(correctIncorrectFont);
		
		numSkips = new JLabel("Skips: " + skipCount);
		numSkips.setFont(correctIncorrectFont);
		
		JPanel numsPanel = new JPanel(new GridLayout(1, 2));
		numsPanel.add(numCorrect);
		numsPanel.add(numIncorrect);
		numsPanel.add(numSkips);
		rightGroup.add(numsPanel);
		
		// Setup skip and back to menu buttons, add them to a group and add to main button group
		skip = new JButton("Skip");
		skip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skip();
			}
		});
		skip.setFont(gameButtonFont);
		
		backToMenu = new JButton("Back to menu");
		backToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToMenu();
			}
		});
		backToMenu.setFont(gameButtonFont);
		
		JPanel skipBack = new JPanel(new GridLayout(1, 2));
		skipBack.add(skip);
		skipBack.add(backToMenu);
		
		rightGroup.add(skipBack);
		
		// Add right button group to game screen
		stamina.add(rightGroup);
		
		// Set content pane and update graphics to show game screen
		frame.setContentPane(stamina);
		updateDisplay();
		
		
		// Initialize game
		mpsDone = new boolean[NUM_MPS];
		for (int i = 0; i < NUM_MPS; i++) {
			mpsDone[i] = false;
		}
		
		missed = new ArrayList<Integer>();
		generateNew(false, false, false);
		
	}
	
	// Update the stamina display to have current correct and skip values 
	public void updateStamina() {
		
		numCorrect.setText("Correct: " + correctCount);
		numIncorrect.setText("Incorrect: " + incorrectCount);
		numSkips.setText("Skips: " + skipCount);
		
		updateDisplay();
		
	}
	
	// Redraw display
	public void updateDisplay() {
		
		frame.update(frame.getGraphics());
		frame.revalidate();
		
	}
	
	// On radio button action
	public void onRadioButtonAction(String actionCommand) {
		
		if (correctBtn == (new Integer(actionCommand))) {
			generateNew(true, false, false);
		} else {
			generateNew(false, true, false);
		}
		
	}
	
	

//	public JTextField timeText;
//	public JRadioButton timeRadio1, timeRadio2, timeRadio3, timeRadio4;
//	public JLabel timeNumCorrect;
//	public JLabel timeNumIncorrect;
//	public JLabel timeNumSkips;
//	public JButton timeSkip;
//	public JButton timeBackToMenu;
	
	
	// Initialize time trial game screen and set the frame
	public void initTimeTrial() {
		
		// TODONE: Setup time trial
		// Initialize game screen JPanel
		time = new JPanel(new GridLayout(2, 1));
		
		// Image Label
		timeImg = new JLabel("", JLabel.CENTER);
		time.add(timeImg);
		
		// Initialize bottom group panel
		JPanel bottomGroup = new JPanel(new GridLayout(5, 1));

		// Initialize text field, with spacers on all sides to make it the right size
		JPanel textPanel = new JPanel(new GridLayout(3, 3));
		
		textPanel.add(new JLabel());			// Spacers for text field
		textPanel.add(new JLabel());
		textPanel.add(new JLabel());
		textPanel.add(new JLabel());
		
		timeText = new JTextField(60);
		timeText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				timeOnVerify();
				
			}
		});
		timeText.setFont(timeTextFieldFont);
		textPanel.add(timeText);
		
		textPanel.add(new JLabel());			// Spacers for text field
		textPanel.add(new JLabel());
		textPanel.add(new JLabel());
		textPanel.add(new JLabel());
		
		bottomGroup.add(textPanel);
		
		// Initialize button group
		timeBg = new ButtonGroup();
		ActionListener timeRadioButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				timeOnVerify();
				
			}
		};
		
		// Radio buttons
		// Radio button panel
		JPanel radioButtonPanel = new JPanel(new GridLayout(1, 8));
		
		// Adding spacers on right side of radio buttons
		radioButtonPanel.add(new JLabel());
		radioButtonPanel.add(new JLabel());
		
		// b1
		timeRadio1 = new JRadioButton("CPC");
		timeRadio1.setActionCommand("CPC");
		timeRadio1.setHorizontalAlignment(JRadioButton.CENTER);
		timeRadio1.addActionListener(timeRadioButtonListener);
		timeRadio1.setFont(timeRadioButtonFont);
		timeBg.add(timeRadio1);
		radioButtonPanel.add(timeRadio1);
		
		// b2
		timeRadio2 = new JRadioButton("LIB");
		timeRadio2.setActionCommand("LIB");
		timeRadio2.setHorizontalAlignment(JRadioButton.CENTER);
		timeRadio2.addActionListener(timeRadioButtonListener);
		timeRadio2.setFont(timeRadioButtonFont);
		timeBg.add(timeRadio2);
		radioButtonPanel.add(timeRadio2);
		
		// b3
		timeRadio3 = new JRadioButton("NDP");
		timeRadio3.setActionCommand("NDP");
		timeRadio3.setHorizontalAlignment(JRadioButton.CENTER);
		timeRadio3.addActionListener(timeRadioButtonListener);
		timeRadio3.setFont(timeRadioButtonFont);
		timeBg.add(timeRadio3);
		radioButtonPanel.add(timeRadio3);
		
		// b4
		timeRadio4 = new JRadioButton("IND");
		timeRadio4.setActionCommand("IND");
		timeRadio4.setHorizontalAlignment(JRadioButton.CENTER);
		timeRadio4.addActionListener(timeRadioButtonListener);
		timeRadio4.setFont(timeRadioButtonFont);
		timeBg.add(timeRadio4);
		radioButtonPanel.add(timeRadio4);
		
		// Adding spacers on left side of radio buttons
		radioButtonPanel.add(new JLabel());
		radioButtonPanel.add(new JLabel());
		
		// Add radio button panel to the bottom panel
		bottomGroup.add(radioButtonPanel);		
		
		// Initialize correct, incorrect and skip counts
		correctCount = 0;
		incorrectCount = 0;
		skipCount = 0;
		
		// Create labels for the correct, incorrect and skip counts
		timeNumCorrect = new JLabel("Correct: " + correctCount, JLabel.RIGHT);
		timeNumCorrect.setFont(correctIncorrectFont);
		
		timeNumIncorrect = new JLabel("Incorrect: " + incorrectCount, JLabel.CENTER);
		timeNumIncorrect.setFont(correctIncorrectFont);
		
		timeNumSkips = new JLabel("Skips: " + skipCount, JLabel.LEFT);
		timeNumSkips.setFont(correctIncorrectFont);
		
		JPanel timeNumsPanel = new JPanel(new GridLayout(1, 2));
		timeNumsPanel.add(timeNumCorrect);
		timeNumsPanel.add(timeNumIncorrect);
		timeNumsPanel.add(timeNumSkips);
		bottomGroup.add(timeNumsPanel);

		// Initialize status label
		timeStatus = new JLabel("", JLabel.CENTER);
		timeStatus.setFont(timeStatusFont);
		bottomGroup.add(timeStatus);
		
		// Setup skip and back to menu buttons, add them to a group and add to main button group
		timeSkip = new JButton("Skip");
		timeSkip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeSkip();
			}
		});
		timeSkip.setFont(gameButtonFont);
		
		timeBackToMenu = new JButton("Back to menu");
		timeBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeBackToMenu();
			}
		});
		timeBackToMenu.setFont(gameButtonFont);
		
		JPanel timeSkipBack = new JPanel(new GridLayout(1, 2));
		timeSkipBack.add(timeSkip);
		timeSkipBack.add(timeBackToMenu);
		
		bottomGroup.add(timeSkipBack);
		
		// Add right button group to game screen
		time.add(bottomGroup);
		
		// Set content pane and update graphics to show game screen
		frame.setContentPane(time);
		updateDisplay();
		
		
		// Initialize game
		mpsDone = new boolean[NUM_MPS];
		for (int i = 0; i < NUM_MPS; i++) {
			mpsDone[i] = false;
		}
		
		missed = new ArrayList<Integer>();
		timeGenerateNew(false, false, false);

	}
	
	// Update time trial display
	public void updateTime() {
		
		timeNumCorrect.setText("Correct: " + correctCount);
		timeNumIncorrect.setText("Incorrect: " + incorrectCount);
		timeNumSkips.setText("Skips: " + skipCount);
		
		updateDisplay();
		
	}
	
	// Verify actions for time trial (from both text field and radio buttons)
	public void timeOnVerify() {
		
		String nameGuess = timeText.getText().toLowerCase().trim();
		String partyGuess = timeBg.getSelection().getActionCommand().trim();
		
		String nameCorrect = getButtonText(num).split("   -  ")[0].toLowerCase().trim();
		String partyCorrect = getButtonText(num).split("   -  ")[1].trim();
		
		// TODONE: Verify if there's text in box and if a button is selected, check if answer is correct, generate new
		if (timeBg.getSelection() != null && !timeText.getText().equals("")) {
			
			if (nameGuess.equals(nameCorrect) && partyGuess.equals(partyCorrect)) {
				
				timeGenerateNew(true, false, false);
				timeStatus.setText("Correct!");
				
			} else if (!nameGuess.equals(nameCorrect) && partyGuess.equals(partyCorrect)) {
				
				timeGenerateNew(false, true, false);
				timeStatus.setText("Incorrect name or spelling!");
				
			} else if (nameGuess.equals(nameCorrect) && !partyGuess.equals(partyCorrect)) {
				
				timeGenerateNew(false, true, false);
				timeStatus.setText("Incorrect party!");
				
			} else if (!nameGuess.equals(nameCorrect) && !partyGuess.equals(partyCorrect)) {
				
				timeGenerateNew(false, true, false);
				timeStatus.setText("Incorrect name/spelling and party!");
				
			}
			
		}
		
	}
	
	// Skip for the time trial screen
	public void timeSkip() {
		
		// TODONE: Setup method to skip current MP and call generation of new MP
		timeGenerateNew(false, false, true);
		
	}
	
	// Back to menu from time game screen
	public void timeBackToMenu() {

		// Update record and missed
		if (correctCount > getTimeRecord()) {
			setTimeRecord(correctCount);
		}
		addMissed();
		
		// Build intermediate screen and show
		btmPanel = new JPanel(new GridLayout(2, 1));
		
		// Title label
		JPanel topPanel = new JPanel(new GridLayout(1, 1));
		
		btmTitle = new JLabel("Your score so far:", JLabel.CENTER);
		btmTitle.setFont(btmTitleFont);
		topPanel.add(btmTitle);
		
		btmPanel.add(topPanel);

		// Bottom panel
		JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
		JPanel numsPanel = new JPanel(new GridLayout(1, 3));
		
		btmCorrect = new JLabel("Correct: " + correctCount, JLabel.RIGHT);
		btmCorrect.setFont(btmNumberFont);
		numsPanel.add(btmCorrect);
		
		btmIncorrect = new JLabel("Incorrect: " + incorrectCount, JLabel.CENTER);
		btmIncorrect.setFont(btmNumberFont);
		numsPanel.add(btmIncorrect);
		
		btmSkip = new JLabel("Skip: " + skipCount, JLabel.LEFT);
		btmSkip.setFont(btmNumberFont);
		numsPanel.add(btmSkip);
		
		bottomPanel.add(numsPanel);
		
		btmMissedWarning = new JLabel("PS: You can view all the MPs you missed by clicking on \"My Missed MPs\" in the main menu", JLabel.CENTER);
		btmMissedWarning.setFont(btmMissedWarningFont);
		bottomPanel.add(btmMissedWarning);
		
		// Buttons
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		
		btmBackToMenu = new JButton("Back to menu");
		btmBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(menu);
				updateDisplay();
			}
		});
		btmBackToMenu.setFont(btmButtonFont);
		buttonPanel.add(btmBackToMenu);
		
		btmBackToGame = new JButton("Back to game");
		btmBackToGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(time);
				updateDisplay();
			}
		});
		btmBackToGame.setFont(btmButtonFont);
		buttonPanel.add(btmBackToGame);
		
		bottomPanel.add(buttonPanel);
		btmPanel.add(bottomPanel);
		
		frame.setContentPane(btmPanel);
		updateDisplay();
		
	}
	
	// Generate new MP for time trial game mode
	public void timeGenerateNew(boolean correct, boolean incorrect, boolean skip) {
		
		// TODONE: Generate new MP for time trial game mode
		
		// Increase correct count if player got correct answer
		if (correct) {
			correctCount++;
		}
		// Increase incorrect count if the player selected the wrong answer
		if (incorrect) {
			incorrectCount++;
			
			// Add the ID of the missed MP to the list
			missed.add(num);
		}
		// Increase skip count if the player skipped the current MP
		if (skip) {
			skipCount++;
			
			// Add the ID of the missed MP to the list
			missed.add(num);
		}
		
		// Check to see if all the MPs have been done
		if ((correctCount + incorrectCount + skipCount) >= NUM_MPS) {
			timeBackToMenu();
		} else {
			
			// Generate new number
			do {
				num = random.nextInt(NUM_MPS);
			} while (mpsDone[num]);
			
			mpsDone[num] = true;
			
			// Set the image for the MP to guess
			ImageIcon icon = new ImageIcon(imagePath + num + ".jpg");
			timeImg.setIcon(icon);
			
			// Reset button selections
			timeText.setText("");
			timeText.selectAll();
			timeBg.clearSelection();
			
			// Update panel
			updateTime();
			updateDisplay();
			
		}
		
	}
	
	// Initialize missed library screen and set the frame
	public void initMissedLibrary() {
		
		// TODONE: Setup missed library
		libraryPanel = new JPanel(new GridLayout(2, 1));
		
		// Image label to host MP image
		libraryImg = new JLabel("", JLabel.CENTER);
		libraryImg.setFont(libraryImageFont);
		libraryPanel.add(libraryImg);
		
		// Bottom half panel
		JPanel bottomHalf = new JPanel(new GridLayout(5, 1));
		
		// Info labels
		libraryInfoName = new JLabel("", JLabel.CENTER);
		libraryInfoName.setFont(libraryInfoNameFont);
		bottomHalf.add(libraryInfoName);
		
		libraryInfoParty = new JLabel("", JLabel.CENTER);
		libraryInfoParty.setFont(libraryInfoPartyFont);
		bottomHalf.add(libraryInfoParty);
		
		// Previous and next buttons
		libraryPrevious = new JButton("Previous");
		libraryPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				libraryPrevious();
			}
		});
		libraryPrevious.setFont(libraryButtonFont);
		
		libraryNext = new JButton("Next");
		libraryNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				libraryNext();
			}
		});
		libraryNext.setFont(libraryButtonFont);
		
		JPanel previousNextPanel = new JPanel(new GridLayout(1, 2));
		previousNextPanel.add(libraryPrevious);
		previousNextPanel.add(libraryNext);
		bottomHalf.add(previousNextPanel);
		
		// Got it button to remove MPs from the library
		libraryGotIt = new JButton("Got it!");
		libraryGotIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				libraryGotIt();
			}
		});
		libraryGotIt.setFont(libraryButtonFont);
		bottomHalf.add(libraryGotIt);
		
		// Back to menu button for the library screen
		libraryBack = new JButton("Back to menu");
		libraryBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				libraryBack();
			}
		});
		libraryBack.setFont(libraryButtonFont);
		bottomHalf.add(libraryBack);
		
		libraryPanel.add(bottomHalf);
		
		// Set the frame
		frame.setContentPane(libraryPanel);
		updateDisplay();
		
		libraryGenerateNew(0);
		
	}
	
	// Library methods: 
	// Generate new library MP
	public void libraryGenerateNew(int ID) {
		
		ArrayList<Integer> library = getMissed();
		
		if (library.isEmpty()){
			
			// TODONE: Handle empty library
			libraryImg.setText("Oops!");
			libraryImg.setIcon(new ImageIcon());
			libraryInfoName.setText("Looks like you know them all so far!");
			libraryInfoParty.setText("");
			
		} else {
			
			// Get and set image for MP
			if (ID < library.size() && ID >= 0) {
				
				// Image
				ImageIcon icon = new ImageIcon(imagePath + library.get(ID) + ".jpg");
				libraryImg.setIcon(icon);
				
				// Name
				libraryInfoName.setText(getButtonText(library.get(ID)).split("  -  ")[0]);
				
				// Party
				libraryInfoParty.setText(getButtonText(library.get(ID)).split("  -  ")[1]);
				
				// Set current library ID 
				libraryCurrentID = ID;
				
			} else if (ID >= library.size()){
				
				libraryGenerateNew(ID - 1);
				
			} else if (ID < 0) {
				
				libraryGenerateNew(ID + 1);
				
			}
			
		}
		
	}
	
	// Previous MP in library
	public void libraryPrevious() {
		
		libraryGenerateNew(libraryCurrentID - 1);
		
	}
	
	// Next MP in library
	public void libraryNext() {
		
		libraryGenerateNew(libraryCurrentID + 1);
		
	}
	
	// Library Got It: Remove current MP  from library and call new MP
	public void libraryGotIt() {
		
		// TODONE: Remove current MP ID from list , move all IDs 1 down and generate new with currentID
		
		// Remove MP from list
		ArrayList<Integer> library = getMissed();
		
		if (!library.isEmpty()) {
			library.remove(libraryCurrentID);
			
			// Rewrite missed file
			try {
				
				// Add MP ID to the list if its not already there
				Files.write(Paths.get(missedPath), new String("").getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
				for (Integer ID : library) {
					Files.write(Paths.get(missedPath), new String(ID + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Regenerate screen
		libraryGenerateNew(libraryCurrentID);
		
	}
	
	// Return to menu from library screen
	public void libraryBack() {
		
		// TODONE: Return to main menu
		frame.setContentPane(menu);
		updateDisplay();
		
	}
	
	// Setup help screen and set the frame
	public void initHelp() {
		
		// TODONE: Setup help screen
		helpPanel = new JPanel(new GridLayout(6, 1));

		// Spacer
		JTextArea spacer = new JTextArea();
		spacer.setEditable(false);
		helpPanel.add(spacer);
		
		// Initialization of Text Areas
		// Multiple Choice
		helpMultipleChoice = new JTextArea(helpMultipleChoiceText, helpTextRows, helpTextCollumns);
		helpMultipleChoice.setFont(helpTextFont);
		helpMultipleChoice.setEditable(false);
		helpMultipleChoice.setLineWrap(true);
		helpMultipleChoice.setWrapStyleWord(true);
		helpPanel.add(helpMultipleChoice);

		// Realistic
		helpRealistic = new JTextArea(helpRealisticText, helpTextRows, helpTextCollumns);
		helpRealistic.setFont(helpTextFont);
		helpRealistic.setEditable(false);
		helpRealistic.setLineWrap(true);
		helpRealistic.setWrapStyleWord(true);
		helpPanel.add(helpRealistic);

		// Missed Library
		helpMissedLibrary = new JTextArea(helpMissedLibraryText, helpTextRows, helpTextCollumns);
		helpMissedLibrary.setFont(helpTextFont);
		helpMissedLibrary.setEditable(false);
		helpMissedLibrary.setLineWrap(true);
		helpMissedLibrary.setWrapStyleWord(true);
		helpPanel.add(helpMissedLibrary);

		// Other
		helpOther = new JTextArea(helpOtherText, helpTextRows, helpTextCollumns);
		helpOther.setFont(helpTextFont);
		helpOther.setEditable(false);
		helpOther.setLineWrap(true);
		helpOther.setWrapStyleWord(true);
		helpPanel.add(helpOther);
		
		// Back to menu button
		helpBackToMenu = new JButton("Back To Menu");
		helpBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(menu);
				updateDisplay();
			}
		});
		helpBackToMenu.setFont(helpButtonFont);
		helpPanel.add(helpBackToMenu);
		
		// Set content pane and display help screen
		frame.setContentPane(helpPanel);
		updateDisplay();
		
	}
	
	// Exit the program
	public void exit() {
		
		System.exit(0);
		
	}
	
	// Retrieve stamina record from file
	public int getStaminaRecord() {
		
		// DONE: Retrieve info from file
		try {
			BufferedReader br = new BufferedReader(new FileReader(recordsPath));
			
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while(line != null) {
				sb.append(line);
				sb.append(regex);
				line = br.readLine();
			}
			
			br.close();
			
			return new Integer(sb.toString().split(regex)[1]);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
		
	}
	
	// Retrieve time record from file
	public int getTimeRecord() {
		
		// DONE: Retrieve info from file
		try {
			BufferedReader br = new BufferedReader(new FileReader(recordsPath));
			
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while(line != null) {
				sb.append(line);
				sb.append(regex);
				line = br.readLine();
			}
			
			br.close();
			
			return new Integer(sb.toString().split(regex)[4]);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
		
	}
	
	// Set records
	// Set stamina record
	public void setStaminaRecord(int stamina) {
		
		setBothRecords(stamina, getTimeRecord());
		
	}
	
	// Set time record
	public void setTimeRecord(int time) {
		
		setBothRecords(getStaminaRecord(), time);
		
	}
	
	// Set both records
	public void setBothRecords(int stamina, int time) {
		
		try {
			Files.write(Paths.get(recordsPath), new String("stamina= " + stamina + " \n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			Files.write(Paths.get(recordsPath), new String("time= " + time).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		updateMenu();
		
	}
	
	// Store missed MPs in the missed file
	public void addMissed() {
		
		try {
			
			// Get a list of all the MPs currently in the list 
			ArrayList<Integer> compare = getMissed();
			
			// Add MP ID to the list if its not already there
			for (Integer ID : missed) {
				if (!compare.contains(ID)) {
					Files.write(Paths.get(missedPath), new String(ID + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Integer> getMissed() {
		
		ArrayList<Integer> miss = new ArrayList<Integer>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(missedPath));
			
			String line = br.readLine();
			
			while(line != null) {
				miss.add(new Integer(line));
				
				line = br.readLine();
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return miss;
	}
	
//	public JButton btmBackToMenu;
//	public JButton btmBackToGame;
	
	// Back to menu 
	public void backToMenu() {
		
		// Update record and missed
		if (correctCount > getStaminaRecord()) {
			setStaminaRecord(correctCount);
		}
		addMissed();
		
		// Build intermediate screen and show
		btmPanel = new JPanel(new GridLayout(2, 1));
		
		// Title label
		JPanel topPanel = new JPanel(new GridLayout(1, 1));
		
		btmTitle = new JLabel("Your score so far:", JLabel.CENTER);
		btmTitle.setFont(btmTitleFont);
		topPanel.add(btmTitle);
		
		btmPanel.add(topPanel);

		// Bottom panel
		JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
		JPanel numsPanel = new JPanel(new GridLayout(1, 3));
		
		btmCorrect = new JLabel("Correct: " + correctCount, JLabel.RIGHT);
		btmCorrect.setFont(btmNumberFont);
		numsPanel.add(btmCorrect);
		
		btmIncorrect = new JLabel("Incorrect: " + incorrectCount, JLabel.CENTER);
		btmIncorrect.setFont(btmNumberFont);
		numsPanel.add(btmIncorrect);
		
		btmSkip = new JLabel("Skip: " + skipCount, JLabel.LEFT);
		btmSkip.setFont(btmNumberFont);
		numsPanel.add(btmSkip);
		
		bottomPanel.add(numsPanel);
		
		btmMissedWarning = new JLabel("PS: You can view all the MPs you missed by clicking on \"My Missed MPs\" in the main menu", JLabel.CENTER);
		btmMissedWarning.setFont(btmMissedWarningFont);
		bottomPanel.add(btmMissedWarning);
		
		// Buttons
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		
		btmBackToMenu = new JButton("Back to menu");
		btmBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(menu);
				updateDisplay();
			}
		});
		btmBackToMenu.setFont(btmButtonFont);
		buttonPanel.add(btmBackToMenu);
		
		btmBackToGame = new JButton("Back to game");
		btmBackToGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(stamina);
				updateDisplay();
			}
		});
		btmBackToGame.setFont(btmButtonFont);
		buttonPanel.add(btmBackToGame);
		
		bottomPanel.add(buttonPanel);
		btmPanel.add(bottomPanel);
		
		frame.setContentPane(btmPanel);
		updateDisplay();
		
	}
	
	// Game methods ---
	public void generateNew(boolean correct, boolean incorrect, boolean skip) {
		
		// Increase correct count if player got correct answer
		if (correct) {
			correctCount++;
		}
		// Increase incorrect count if the player selected the wrong answer
		if (incorrect) {
			incorrectCount++;
			
			// Add the ID of the missed MP to the list
			missed.add(num);
		}
		// Increase skip count if the player skipped the current MP
		if (skip) {
			skipCount++;
			
			// Add the ID of the missed MP to the list
			missed.add(num);
		}
		
		// Check to see if all the MPs have been done
		if ((correctCount + incorrectCount + skipCount) >= NUM_MPS) {
			backToMenu();
		} else {
			
			// Generate new number
			do {
				num = random.nextInt(NUM_MPS);
			} while (mpsDone[num]);
			
			mpsDone[num] = true;
			
			// Generate 4 other MP numbers that aren't the same as each other for the other guess options
			int other1, other2, other3, other4;
			do {
				other1 = random.nextInt(NUM_MPS);														// Other 1
			} while (other1 == num);
			
			do {
				other2 = random.nextInt(NUM_MPS);														// Other 2
			} while (other2 == num || other2 == other1);
			
			do {
				other3 = random.nextInt(NUM_MPS);														// Other 3
			} while (other3 == num || other3 == other1 || other3 == other2);
			
			do {
				other4 = random.nextInt(NUM_MPS);														// Other 4
			} while (other4 == num || other4 == other1 || other4 == other2 || other4 == other3);
			
			// Set the image for the MP to guess
			ImageIcon icon = new ImageIcon(imagePath + num + ".jpg");
			img.setIcon(icon);
			
			// Reset button selections
			bg.clearSelection();
			
			// Set up the radio button texts
			correctBtn = random.nextInt(5);
			
			if (correctBtn == 0) {
				
				b1.setText(getButtonText(num));
				b2.setText(getButtonText(other1));
				b3.setText(getButtonText(other2));
				b4.setText(getButtonText(other3));
				b5.setText(getButtonText(other4));
				
			} else if (correctBtn == 1) {

				b1.setText(getButtonText(other1));
				b2.setText(getButtonText(num));
				b3.setText(getButtonText(other2));
				b4.setText(getButtonText(other3));
				b5.setText(getButtonText(other4));
				
			} else if (correctBtn == 2) {

				b1.setText(getButtonText(other1));
				b2.setText(getButtonText(other2));
				b3.setText(getButtonText(num));
				b4.setText(getButtonText(other3));
				b5.setText(getButtonText(other4));
				
			} else if (correctBtn == 3) {

				b1.setText(getButtonText(other1));
				b2.setText(getButtonText(other2));
				b3.setText(getButtonText(other3));
				b4.setText(getButtonText(num));
				b5.setText(getButtonText(other4));
				
			} else if (correctBtn == 4) {

				b1.setText(getButtonText(other1));
				b2.setText(getButtonText(other2));
				b3.setText(getButtonText(other3));
				b4.setText(getButtonText(other4));
				b5.setText(getButtonText(num));
				
			} else { 
				System.out.println("Error assigning correct button");
			}
			
			// Update panel
			updateStamina();
			updateDisplay();
		}
		
	}
	
	public void skip() {
		
		// TODONE: Skip current and increase skip count by one
		generateNew(false, false, true);
		
	}
	
	public String getButtonText(int mpID) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(infoPath + mpID + ".txt"));
			
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while(line != null) {
				sb.append(line);
				sb.append(regex);
				line = br.readLine();
			}
			
			br.close();
			
			return new String(sb.toString().split("=")[2].split("party")[0] + "  -  " + sb.toString().split("=")[3]);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Unknown MP";
		
	}
	
}
