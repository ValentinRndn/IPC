package game;

import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
	

public class Information extends JFrame{
	
	//klprivate JLabel infos = new JLabel("Wikipedia de l'IPC");
	
	//JPanel contentPane = (JPanel) this.getContentPane();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Information() {
		super("IPC - Informations");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel Title = new JLabel( "Comment fonctionne l'IPC ? \n");
		
		JLabel Info1 = new JLabel("\n Vous devez obligatoire rentrer une expression et une liste");
		JLabel Info2 = new JLabel( "<html><body><b />Comment fonctionne l'IPC :</body></html>");
		JLabel Info3 = new JLabel( "<html><body><b />Comment fonctionne l'IPC :</body></html>");
		JLabel Info4 = new JLabel( "<html><body><b />Comment fonctionne l'IPC :</body></html>");
		
		this.add(Title);
		this.add(Info1);
		
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		Information info = new Information();
		//info.setBackground(Color.BLUE);
		
		info.setVisible(true);
	}

}
