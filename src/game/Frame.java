package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

@SuppressWarnings( "serial" )
public class Frame extends JFrame {

//	public static JTextField expressionField = new JTextField();
//	public static JTextArea resultField = new JTextArea();
//	public static JTextField listeField = new JTextField();
//	public static JTextField conditionField = new JTextField();
	
	public Frame() {
		//Construction de l'interface graphique
		super("IPC - Interpréteur de programmes chimiques");
		Image icon = Toolkit.getDefaultToolkit().getImage("icons/exec.png");
		this.setIconImage(icon);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(700, 250);
		this.setLocationRelativeTo(null);
		

		//Insertion de la toolbar et du textArea dans le panel
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.add(createToolBar(), BorderLayout.NORTH);
		contentPane.add(createTextField(), BorderLayout.CENTER);
		contentPane.add(createTextFieldResult(), BorderLayout.SOUTH);
	}
	
	//Methode de construction de la JToolBar
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
		
		toolBar.add(actExec).setHideActionText(true);		
        toolBar.addSeparator();
        toolBar.add(actInfo).setHideActionText(true);	
		toolBar.add(actErase).setHideActionText(true);	 
		toolBar.add(actNewListe).setHideActionText(true);

		JCheckBox darkMode = new JCheckBox(new ImageIcon("icons/dark.png"));
		darkMode.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1 ) {
					expressionField.setBackground(Color.black);
					expressionField.setForeground(Color.white);
					listeField.setBackground(Color.black);
					listeField.setForeground(Color.white);
					resultField.setBackground(Color.black);
					resultField.setForeground(Color.white);
					conditionField.setBackground(Color.black);
					conditionField.setForeground(Color.white);
					//contentPane.setBackground(Color.black);
					toolBar.setBackground(Color.DARK_GRAY);
				
				}
				else {
					createTextField().setForeground(Color.black);	
					toolBar.setBackground(Color.lightGray);
				}
			}
		});
		toolBar.add(darkMode);
		return toolBar;
	}
	
	
    // Methode de construction des texfields expression / condition / liste
	private JPanel createTextField() {
		JPanel texts = new JPanel();
		JLabel expression = new JLabel( "<html><body><b />Expression :</body></html>");
		JLabel condition = new JLabel( "<html><body><b />Condition :</body></html>");
		JLabel liste = new JLabel( "<html><body><b />Liste :</body></html>");
		expressionField.setPreferredSize(new Dimension(150,40));
		listeField.setPreferredSize(new Dimension(150,40));
		conditionField.setPreferredSize(new Dimension(150,40));
		texts.add(expression);
		texts.add(expressionField);
		texts.add(condition);
		texts.add(conditionField);
		texts.add(liste);
		texts.add(listeField);
		return texts;
	}
	
	// Méthode de construction du textField contenant le résultat
	private JPanel createTextFieldResult() {
		JPanel texts = new JPanel();
		JLabel resultat = new JLabel( "<html><body><b />Résultat:</body></html>");
		JButton replay = new JButton("<html><body><b />Replay</body></html>");
		JScrollPane scrollPane = new JScrollPane(resultField);
		replay.addActionListener(actReplay);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		texts.add(resultat);
		resultField.setPreferredSize(new Dimension(350,100));
		replay.setPreferredSize(new Dimension(70,40));
		texts.add(scrollPane);
		texts.add(replay);
		return texts;
	}
	
	
	//Les méthodes associées aux boutons
    private AbstractAction actExec = new AbstractAction() {  
        {
            putValue( Action.NAME, "Play" );
            putValue( Action.SMALL_ICON, new ImageIcon( "icons/exec.png" ) );
            putValue( Action.SHORT_DESCRIPTION, "Exécuter" );
        }
        
        //Méthode d'exécution du programme qui appelle la classe Main
        @SuppressWarnings("static-access")
		@Override public void actionPerformed( ActionEvent e ) {
        	resultField.setText("");
        	System.out.println("Exec...");
        	try {
            	Main execution = new Main();
            	execution.main(null);
        	} catch (Exception e1){
        		System.out.println("Erreur : Les champs sont vides");
        	}
        }
    };
    
    //Méthode qui ouvre la fenêtre d"information
    private AbstractAction actInfo = new AbstractAction() {  
        {
            putValue( Action.NAME, "Infos" );
            putValue( Action.SMALL_ICON, new ImageIcon( "icons/info.png" ) );
            putValue( Action.SHORT_DESCRIPTION, "Information" );
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            System.out.println( "Info" );
            Information infoFrame = new Information();
            infoFrame.setVisible(true);
            
        }
    };
    
    //Méthode qui efface tous les champs
    private AbstractAction actErase = new AbstractAction() {  
        {
            putValue( Action.NAME, "Erased" );
            putValue( Action.SMALL_ICON, new ImageIcon( "icons/eraser.png" ) );
            putValue( Action.SHORT_DESCRIPTION, "Effacer" );
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            System.out.println( "Erased.." );
            expressionField.setText("");
            resultField.setText("");
            listeField.setText("");
            conditionField.setText("");
        }
    };
    
    //Méthode qui add une liste
    private AbstractAction actNewListe = new AbstractAction() {  
        {
            putValue( Action.NAME, "Erased" );
            putValue( Action.SMALL_ICON, new ImageIcon( "icons/addListe.png" ) );
            putValue( Action.SHORT_DESCRIPTION, "Ajouter une liste" );
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            JTextField newListe = new JTextField("Je suis une nouvelle Liste");
        }
    };

    //Méthode qui replay le Main
    private AbstractAction actReplay = new AbstractAction() {  
        {
            putValue( Action.NAME, "Replay" );
           // putValue( Action.SMALL_ICON, new ImageIcon( "icons/eraser.png" ) );
            putValue( Action.SHORT_DESCRIPTION, "Replay" );
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            System.out.println( "Replayed.." );
            //String newListe = String.format("%1$s", Main.valeurs);
            	//listeField.insert(Main.valeurs);
            String liste = null;
            for(int i =0; i<Main.valeurs.size(); i++) {
            	liste += Main.valeurs.get(i).toString();
            }
            listeField.setText(liste);
            System.out.println(liste);
            	String reponse = "oui";
            	Main.replay = reponse.equalsIgnoreCase("oui");
        }    
    };

    //Méthode qui ouvre la fenêtre et applique le thème
	public static void main(String[] args) throws Exception {
		
		//Apply du thème
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		
		//Start Window
		Frame window = new Frame();
		window.pack();
		window.setVisible(true);
		}
}
 