
import java.awt.Dimension;
import javax.swing.JFrame;

public class TestCalendar {

	public static void main(String[] args) {

		CalendarManager m = new CalendarManager();
		UserInterface ui = new UserInterface(m);		
		ui.setSize(new Dimension(600, 500));
		ui.setTitle("Calendar Manager");
		ui.setVisible(true);
		ui.setLocationRelativeTo(null);
		ui.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);	

	}
} 