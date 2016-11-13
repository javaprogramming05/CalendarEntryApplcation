/**
 * Description: Calendar application which allows the user
 * 		to select a date and retrieve previously stored calendar entry
 * 		 and also to save a new calendar entry.
 * 

 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UserInterface extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	CalendarManager manager;

	JPanel panel;
	JLabel monthLabel, dayLabel ,yearLabel, calendarentryLabel, instructionsLabel;
	JTextField monthText, dayText, yearText;
	JButton saveButton, retrieveButton;
	JTextArea calendarEntry, errorDisplay;

	//constructor
	public UserInterface(CalendarManager m) {

		this.manager = m;

		monthLabel = new JLabel("Month(mm):");
		dayLabel = new JLabel("Day(dd):");
		yearLabel = new JLabel("Year(yyyy):");
		calendarentryLabel = new JLabel("Calendar Entry:");
		instructionsLabel = new JLabel("NOTE: clear the data to make a new entry");

		monthText = new JTextField(20);
		dayText = new JTextField(20);
		yearText = new JTextField(20);

		saveButton = new JButton("Save");
		retrieveButton = new JButton("Retrieve");

		calendarEntry = new JTextArea(17, 30);
		calendarEntry.setBorder(BorderFactory.createLineBorder(Color.black));
		calendarEntry.setLineWrap(true);

		errorDisplay = new JTextArea(2,10);
		errorDisplay.setEditable(false);
		errorDisplay.setForeground(Color.RED);

		panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

		//setting up grid alignment for panel content
		GridBagConstraints left = new GridBagConstraints();
		left.weighty = 10.0;
		left.anchor = GridBagConstraints.WEST;
		GridBagConstraints right = new GridBagConstraints();
		right.weightx = 12.0;
		right.fill = GridBagConstraints.HORIZONTAL;
		right.gridwidth = GridBagConstraints.REMAINDER;
		GridBagConstraints center = new GridBagConstraints();
		center.gridwidth = GridBagConstraints.EAST;

		//adding contents to panel
		panel.add(monthLabel,left);
		panel.add(monthText,right);

		panel.add(dayLabel,left);
		panel.add(dayText,right);

		panel.add(yearLabel,left);
		panel.add(yearText,right);

		panel.add(errorDisplay,right);

		panel.add(calendarentryLabel,left);
		panel.add(calendarEntry,right);

		panel.add(Box.createHorizontalStrut(10)); 
		panel.add(instructionsLabel,right);

		panel.add(saveButton,left);
		panel.add(retrieveButton,left);

		saveButton.addActionListener(this);
		retrieveButton.addActionListener(this);

		Container container = getContentPane();

		//adding the panel to the container
		container.add(panel, BorderLayout.NORTH);


	}


	public void actionPerformed(ActionEvent e) {

		String month = monthText.getText();
		String day = dayText.getText();
		String year = yearText.getText();

		//if save button is pressed
		if(e.getActionCommand().equals("Save")) {

			if(!manager.isValidDate(year, month, day)){

				errorDisplay.setText("Invalid calendar date");
			}
			else if("".equals(calendarEntry.getText())) {

				errorDisplay.setText("Calendar entry is empty");
			}
			else{

				manager.save(month, day, year, calendarEntry.getText());
				errorDisplay.setText("Data written successfully to file "+ year+"-"+month+".txt");
			}

			//if retrieve button is pressed
		} else if(e.getActionCommand().equals("Retrieve")){


			if(!manager.isValidDate(year, month, day)){
				calendarEntry.setText(null);
				errorDisplay.setText("Invalid calendar date");

			}
			else{
				String lines;
				try {
					errorDisplay.setText(null);
					calendarEntry.setText(null);

					lines = manager.retrieve(month, day, year);

					//if there is no entry for the given day
					if(lines.isEmpty()){

						errorDisplay.setText("Entry not found");

					}else if (lines.equalsIgnoreCase("File not found")){

						errorDisplay.setText("File not found");

					}else{

						String input = lines;
						String[] entries = input.split("\\s*,\\s*");

						for(String s : entries){

							calendarEntry.append("\n");
							calendarEntry.append(s);
						}
					}
				}catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					errorDisplay.setText("File not found");
				}

			}


		}
	}


}

