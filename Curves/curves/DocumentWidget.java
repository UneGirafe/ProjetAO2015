package curves;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import function.Function;
import function.Functions;
import function.SyntaxErrorException;


public class DocumentWidget extends Observable {
	private JTextField functionField;
	private JPanel widget;
	private JButton loader;
	private JButton saver;
	private JButton addButton;
	private JFileChooser fileChooser;
	private JComboBox<String> cbFunctions;
	private JList availableFunctionsList;
	private JList drawnFunctionsList;
	private Map<String,Function> functionsList;


	public DocumentWidget(){
		widget = new JPanel();
		DefaultListModel availableFun = new DefaultListModel();
		DefaultListModel drawnFun = new DefaultListModel();
		availableFunctionsList = new JList(availableFun);
		drawnFunctionsList = new JList(drawnFun);
		cbFunctions = new JComboBox<>();
		fileChooser = new JFileChooser();
		functionField = new JTextField(3);
		widget.add(functionField);
		//functionsList = new HashMap<String, Function>();
		
		
		//Available function's list
		dlm.addElement("un");
		availableFunctionsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
			    if (e.getValueIsAdjusting() == false) {

			        if (availableFunctionsList.getSelectedIndex() == -1) {
			        //No selection, disable add button.
			            addButton.setEnabled(false);
			        } else {
			        //Selection, enable the add button.
			            addButton.setEnabled(true);
			        }
			    }
				
			}
		});
		widget.add(availableFunctionsList);
		
/*		//Combo box
		cbFunctions = new JComboBox<String>();
		cbFunctions.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					String item = e.getItem().toString();
					applyFunction(item);
				}
			}
		});

		widget.add(cbFunctions);*/

		//Saver
		saver = new JButton("Sauvegarder");
		saver.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent e){
				fileChooser.showSaveDialog(saver);
				save(fileChooser.getSelectedFile());
			}
		});

		widget.add(saver);

		//Loader
		loader = new JButton("Importer à partir d'un fichier");
		loader.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fileChooser.showOpenDialog(loader);
				System.out.println("fichier choisi : " + fileChooser.getSelectedFile()); //trace
				load(fileChooser.getSelectedFile());
			}
		});

		widget.add(loader);


		//Button to add manually a function to the comboBox
		addButton = new JButton("ajouter");
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addFunction(functionField.getText());
			}
		});

		widget.add(addButton);
	}


	public void applyFunction(String function){
		if ( cbFunctions.getSelectedItem() != function){
			notifyObservers(this);
			System.out.println("notification ");
		}else {
			System.out.println("Can't apply this function");
		}
	}

	public boolean functionFound(String txt){
		boolean isFound = false;
		for ( int i=0 ; i<cbFunctions.getItemCount() ; i++ ){
			if ( cbFunctions.getItemAt(i) == txt ){
				isFound = true;
			}
		}
		return isFound;
	}

	public void addFunction(String txt) {
		try {
			System.out.println("ligne lue : " + txt);
			Function fun;
			fun = Functions.parse(txt); //convert String to Function

			if ( functionFound(txt) == true ){
				functionsList.put(txt , fun);
				cbFunctions.addItem(txt);
				cbFunctions.repaint();
			}else {
				System.out.println("Cette fonction existe déjà, ajout impossible.");
			}
		} catch (SyntaxErrorException | IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void load(File f){
		try (BufferedReader reader = new BufferedReader( new FileReader(f))) {//try-with-ressources
								    											//libère la ressource dès que la vaiable n'est plus utilisée
			String line;
			while ((line = reader.readLine()) != null ){ //second test ne fonctionne pas
				//applyFunction(line);
				if (functionsList.containsValue(line)){
					System.out.println("ligne rejetée car déjà importée" + line );
				} else {
					addFunction(line);
				}
			}
			//Print the map
			for (String i : this.functionsList.keySet() ){
				String key = i.toString();
				String value = this.functionsList.get(key).toString();
				System.out.println("clé : " + key + " || valeur : " + value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void save(File f){
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(f,true))){
			//Functions.parse(functionField.getText()); //récupère la fonction dans le champ texte et vérifie la syntaxe
			f.createNewFile();

			for (String i : this.functionsList.keySet()){
				String key = i.toString();
				//String value = this.functionsList.get(key).toString();
				writer.write(key /* + ';' + value*/);
				writer.newLine();
			}


		} catch (IOException /* | SyntaxErrorException */ e) {
			System.out.println("You shall not parse !");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


	public JPanel getWidget() {
		return widget;
	}	
}
