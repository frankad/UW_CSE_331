package hw9;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hw8.PathFindingModel;

/**
 * PathFindingController is the controller of the GUI for the campus
 * path/roue finding tool. It translates the user to make interaction 
 * with the view into actions that the model will perform. The user 
 * interactions could be button clicks or menu selections data, and 
 * provide and display appropriate message/actions.
 */
public class PathFindingController extends JPanel { 
	// to verify that the sender and receiver of a serialized 
	// object
	private static final long serialVersionUID = 1L;
	
	// view for the campus path/route finding view and model
	private PathFindingView view;
	private PathFindingModel model;
	// labels for the staring and ending position/building 
	// for the route
	private JLabel start; 
	private JLabel destination; 
	
	// menu to choose the starting and ending building to
	// find route/path.
	private JComboBox <String> listOfStartBuilding;
	private JComboBox <String> listOfEndBuilding;
	
	// label for value and text of the distance field  
	private JLabel textDistance; 
	private JTextField valueDistance;
	
	// label for value and text of the time field
	private JLabel  labelTime;
	private JTextField valueTime; 
	
	/**
	 * Construct a GUI controller for the campus path/route
	 * finding.
	 * 
	 * @param md model of campus path/route finding 
	 * @param vw view of the campus path/route finding
	 * @requires md != null and vw != null
	 * @effects create a JFrame container to hold the view 
	 *        and the labels created.
	 */
	public PathFindingController (PathFindingModel md, PathFindingView vw) { 
		 // instantiate variables
		view = vw; 
		model = md;  
		
		// get both short and full name of the buildings in the campus   
		Map<String,String> nameShortToFull = model.getBuildingShortToFullName();
		
		// use tree set to get alphabetical order the building name  
		Set <String> nameBuilding = new TreeSet <String>(); 
		
		// loops through and add each building to the set
		for(String shortName : nameShortToFull.keySet()){
			String name = shortName +": "+nameShortToFull.get(shortName); 
			nameBuilding.add(name); 
		}
		
		//create a panel container to hold Start and Destination labels
		JPanel panelStartToEnd = new JPanel(new GridLayout(2, 1));
		start = new JLabel("Start building: Red Dot"); 
	    destination = new JLabel("Destination building: Blue Dot");		
		//add From and To labels to related JPanel
	    panelStartToEnd.add(start);
	    panelStartToEnd.add(destination); 
		
		// panal container to hold choice/menu JCombobox. 
		JPanel menuPanel = new JPanel(new GridLayout(2, 1));
		
		// add list of building in the start and destination menu set of 
		listOfStartBuilding = new JComboBox<String>(nameBuilding.toArray(new String[0]));
		listOfEndBuilding = new JComboBox<String>(nameBuilding.toArray(new String[0])); 
		
		//add the Combo box component to the menu panel
		menuPanel.add(listOfStartBuilding);  
		menuPanel.add(listOfEndBuilding); 
		
		// panel to hold buttons for finding route and reset buttons 
		JPanel panelButtons = new JPanel(new GridLayout(2, 1));
		
		// create find route and reset button and add to actionListener 
		// to update the view when the buttons clicked and to reset.
		JButton findRoute = new JButton("Find Route");
		findRoute.addActionListener(new PathFindingActionListener());   
		
		// Allow the user to reset back to its starting state.
		JButton reset = new JButton("Reset"); 
		reset.addActionListener(new PathFindingActionListener()); 
		
		//add buttons to the button panel container.
		panelButtons.add(findRoute);
		panelButtons.add(reset); 
		
		// JPanel container for the distanxce label and text field compennet.
		JPanel panelDistance = new JPanel(new GridLayout(2, 1));
		textDistance = new JLabel("Total Distance");
		valueDistance =new JTextField("");
		
		//add distance label and text field to distance panel
		panelDistance.add(textDistance); 
		panelDistance.add(valueDistance); 
		
		// JPanel container for time labe; and text field compennet
	    JPanel panelTime = new JPanel(new GridLayout(2, 1));
	    // label text field for time measurement 
	    labelTime = new JLabel("Time Taken: walking speed = 3.1 mph");
	    valueTime = new JTextField("");  
	    
		// add to the time JLabel and JTextField in to panelTime container 
	    panelTime.add(labelTime);
	    panelTime.add(valueTime); 
	    
	    //add all JPanel components to the JFrame container
	    this.add(panelStartToEnd);
	    this.add(menuPanel);
	    this.add(panelButtons);		
	  	this.add(panelDistance);		
	  	this.add(panelTime);
		 	
	}
	
	/**
	 * This allows the user to reset the GUI back to the starting  
	 * state. It clear all markings on the map, the building selectors, 
	 * the distance value and the value of the estimated time. 
	 */
	public void resetButtonValue() { 
		listOfStartBuilding.setSelectedIndex(0);
		listOfEndBuilding.setSelectedIndex(0);
		valueDistance.setText("");
		valueTime.setText("");		
	}
	
	/**
	 * The path finding ActionListener, it handles the event what should be
	 * done when an user performs certain operation. It respond to finding 
	 * path and reset the button action listener. Thus, it updates the  
	 * shortest path/route between two buildings currently selected, or it
	 * reset the view to its starting/initial state.
	 */
	private class PathFindingActionListener implements ActionListener { 
		
		/**
		 * It invoked when an action occurs, the button pressed.
		 * 
		 * @param e an event when the user performs certain operation.
		 */
		@Override
		public void actionPerformed(ActionEvent e) { 
			
		    //Set up a string variable to get action for the user
			String action = e.getActionCommand();
			// the selected starting and end/destination building
			// to find path/route
			String start;
			String end;
			
			// if there is route 
			if(action.equals("Find Route")){
				
				// get the selected start and end/destination building 
				// from the JComboBox by the user
				start = listOfStartBuilding.getSelectedItem().toString();
            	end = listOfEndBuilding.getSelectedItem().toString();

            	// split the selected start and end building name to  
            	// get the short/abbreviated name of each building.  
            	String[] startToken = start.split(":");
            	String[] endToken = end.split(":");
            	
            	// pass the short name of the start and end building and 
            	// find the distance between them. 
            	double dis = view.routeDistnace(startToken[0], endToken[0]);
 
            	//format and set the distance value and discription.
            	String routeDistance = String.format(" %.0f feet", dis); 
            	valueDistance.setText(routeDistance);  
            	
            	// format and set the time value and text discription, the 
            	// the average human walking speed is about 3.1 miles/hour
            	// or 0.0516667 miles/min == 272.8 foot/min, time =distance/speed.
            	// source: https://en.wikipedia.org/wiki/Walking
            	String routeTime = String.format("  %.0f minute", (dis/272.8));
            	valueTime.setText(routeTime);    
			} else { 
				// reset the the controller and view; distance value and  
				// discription; time value and description to starting/initial
				// state
				view.reset(); 
				resetButtonValue();
			}
		}
	}
}
