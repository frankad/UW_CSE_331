package hw9;


import hw8.PathFindingModel;

/**
 * CampusPathsMain connects all the campus path finding model, view and 
 * controller using the campus data. It allows the user to interact 
 * and find the shortest path/route between two buildings on the campus.
 */
public class CampusPathsMain {
	
	/**
	 * It connects the model, view and controller components, 
	 * and start the GUI to launch.  
	 * args @param agrs
	 * @throws Exception if the file path not well formatted
	 */
	public static void main(String[] args) throws Exception { 
		// create campus path finding  model by passing the campus
		// data, based on the data from campus file
		PathFindingModel model = new PathFindingModel("src/hw8/data/campus_buildings.dat", 
							"src/hw8/data/campus_paths.dat");
		//create the GUI and pass the model object
	    new PathFindingGUI(model);
	}	
}
