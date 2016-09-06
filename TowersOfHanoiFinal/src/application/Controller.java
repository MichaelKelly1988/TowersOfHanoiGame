package application;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
//import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller implements Initializable {
	
	@FXML
	private VBox vbox1;
	
	@FXML
	private VBox vbox2;
	
	@FXML
	private VBox vbox3;
	
	@FXML
	private ComboBox <Integer> Amount;
	
	@FXML
	private VBox HiddenVBox;
	
	@FXML
	private Pane splash;
	


	
	@FXML
	private Label movesNum;
	
	private int counter = 0; //count the number of moves
	
	private Rectangle selectedRectangle;	//This is going to keep track of the currently selected rectangle
	
	private Stack<Integer> fromMove = new Stack<Integer>();//creating 2 stacks a from and a to
	private Stack<Integer> toMove = new Stack<Integer>();
	
	public void original(ActionEvent event){
		splash.setVisible(false);
	}
	
	public void alternative(ActionEvent event){
		splash.setVisible(false);
	}
			
			
	@Override
	public void initialize(URL location, ResourceBundle resources){
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);											//code from stack overflow
		list.add(7);
		list.add(8);
		ObservableList<Integer> oblist = FXCollections.observableList(list);
		Amount.setItems(oblist);
		Amount.setValue(8);
		
	}
	
		public void change(ActionEvent event){
		System.out.println("You changed the number of rectangles");
		System.out.println(Amount.getValue());
		restartGame(null);
		
		
		int chose=Amount.getValue();		//the number of shapes we want
		int current=vbox1.getChildren().size();	//the number of shapes we have
		if(chose < current){
			for(int i = current - 1; i >=chose; i --){
				Rectangle r = (Rectangle) vbox1.getChildren().remove(i);
				HiddenVBox.getChildren().add(0, r);
				
			}
		}else if(chose > current){		//moves chapes back to the hidden vbox
			for(int i = 0; i < chose - current;i++){
				Rectangle r = (Rectangle) HiddenVBox.getChildren().remove(0);
				vbox1.getChildren().add(r);
			}
		}
	}
		private void moveCount(){
			counter++;
			movesNum.setText(String.valueOf(counter));   //update the display
		
	}
		public void save(ActionEvent event){
			System.out.println("You saved the game");
			
			try{    
			       
				   
			     //  create a text file object to write the array list to
			      File objectfile= new File("tower.txt");
			      double size=objectfile.length();
			      
			       
			       //Here we are adding an object if the file is empty
			       if(size==0.0)
			       {
			                
			       FileOutputStream saveStream= new FileOutputStream(objectfile);
			       ObjectOutputStream changeObject=new ObjectOutputStream(saveStream);
			      
			      changeObject.writeObject(fromMove);
			      changeObject.writeObject(toMove);

			      changeObject.close();

			}

			}

			catch(Exception e)
			{
			// JOptionPane.showMessageDialog(null,"An unknown exception has occurred"); 
			  e.printStackTrace(); 				//trace back up the stack to find where exceptions have occurred
			}
		}
		
		public void load(ActionEvent event){
			
			try{
		          File objectFile= new File("tower.txt");
		       FileInputStream saveStream= new FileInputStream(objectFile);

		      ObjectInputStream createdfiles=new ObjectInputStream(saveStream);
		      
		      Stack<Integer> fromMove=(Stack<Integer>)createdfiles.readObject();
		      								//ArrayList is populated from the file
		      Stack<Integer> toMove=(Stack<Integer>)createdfiles.readObject();
		createdfiles.close();


		
		System.out.println("You have the game loaded");
		replay(fromMove, toMove);
		}

										//First catch block deals with exception of type input / output
		catch (IOException e) 
		  {
		  
		 // JOptionPane.showMessageDialog(null," Gym Details not backed up due to An error within the file handling has caused the program to crash ");  
		   e.printStackTrace();
		  } 
		  
		catch(Exception e)			//Second one is if it get through the first one
		{
		// JOptionPane.showMessageDialog(null,"An error has occurred or an file is not yet been created for gymStorage"); 
		  e.printStackTrace();
		}
		}
		
		public void replay(Stack<Integer> fromMove, Stack<Integer> toMove)
		{
			//restartGame(null);
			
			Iterator<Integer> it1= fromMove.iterator(); //passing in new stacks to hold the data for the saved game temporarily
			Iterator<Integer> it2= toMove.iterator();
			
			while(it1.hasNext()){
				int from = it1.next();
				int to = it2.next();
			
				switch(from){
				case 1: onButtonTower1(null);
				break;
				case 2: buttonTower2(null);
				break;
				case 3: buttonTower3(null);
				break;
				}
				
				switch(to){
				case 1: onButtonTower1(null);
				break;
				case 2: buttonTower2(null);
				break;
				case 3: buttonTower3(null);
				break;
				}
				
				
			}
			
			splash.setVisible(false);
			
		}
		private void recordMove(int from, int to){
			System.out.println(from + " -> " + to);		//this is printing at the bottom of the screen which tower is moving to which
			fromMove.push(from);
			toMove.push(to);
		}
		
		

	public void onButtonTower1(ActionEvent event) {
		System.out.println("YOU MOVED TO TOWER 1");
		System.out.println(vbox1.getChildren().size());
		Rectangle topRectangle = null;
		if(vbox1.getChildren().size()>0){
		topRectangle =(Rectangle)vbox1.getChildren().get(0);		//Getting a reffrence to the top rectangle in the stack
		}
		if(selectedRectangle == null){
			
			topRectangle.setFill(Color.RED);		//changing the fill color
			selectedRectangle = topRectangle; 		//stores a reference to the selected rectangle
		
		}else{
			if(selectedRectangle == topRectangle){	//if the button is clicked but the rectangle is on the same tower
				topRectangle.setFill(Color.ORANGE);		//changing the fill color
				selectedRectangle =null;
			
			}else{
				VBox p = (VBox) selectedRectangle.getParent();
				if(vbox1.getChildren().size()==0 || selectedRectangle.getWidth() <topRectangle.getWidth()){		//if there are no rectangles in vbox1 then we can definetly make the move
					
				
				p.getChildren().remove(selectedRectangle);
				vbox1.getChildren().add(0, selectedRectangle);	//Because ya added the 0 it always adds it to the top
				moveCount();
				if(p== vbox2){
				recordMove( 2,1);	//this is recording a move on the stack the reason why its 2 first because thats the destintion
				}else{
					recordMove(3,1);
				}
				selectedRectangle.setFill(Color.ORANGE);		//changing the fill color
				selectedRectangle =null;
			}else{
				System.out.println("MOVE NOT ALLOWED");
			}
			}
			
		}
		
		
	}
	
	public void buttonTower2(ActionEvent event) {
		System.out.println("YOU MOVED TO TOWER 2");
		System.out.println(vbox2.getChildren().size());
		Rectangle topRectangle = null;
		if(vbox2.getChildren().size()>0){
			topRectangle =(Rectangle)vbox2.getChildren().get(0);		//Getting a reffrence to the top rectangle in the stack
			}	

		
		if(selectedRectangle == null){
			
			topRectangle.setFill(Color.RED);		//changing the fill color
			selectedRectangle = topRectangle; 		//stores a reference to the selected rectangle
		
		}else{
			if(selectedRectangle == topRectangle){	//if the button is clicked but the rectangle is on the same tower
				topRectangle.setFill(Color.ORANGE);		//changing the fill color
				selectedRectangle =null;
			
			}else{
				VBox p = (VBox) selectedRectangle.getParent();
				if(vbox2.getChildren().size()==0 || selectedRectangle.getWidth() <topRectangle.getWidth()){		//if there are no rectangles in vbox1 then we can definetly make the move
							
				p.getChildren().remove(selectedRectangle);
				vbox2.getChildren().add(0, selectedRectangle);	//Because ya added the 0 it always adds it to the top
				moveCount();
				if(p== vbox1){
					recordMove( 1,2);	//this is recording a move on the stack the reason why its 2 first because thats the destintion
					}else{
						recordMove(3,2);
					}
				selectedRectangle.setFill(Color.ORANGE);		//changing the fill color
				selectedRectangle =null;
			
			
			}else{
				System.out.println("MOVE NOT ALLOWED");
			}
			
		}
		}
	}
			
	
	
	public void buttonTower3(ActionEvent event){
		System.out.println("YOU MOVED TO TOWER 3");
		System.out.println(vbox3.getChildren().size());
		Rectangle topRectangle = null;
		if(vbox3.getChildren().size()>0){
			topRectangle =(Rectangle)vbox3.getChildren().get(0);		//Getting a reffrence to the top rectangle in the stack
			}	

		
		if(selectedRectangle == null){
			
			topRectangle.setFill(Color.RED);		//changing the fill color
			selectedRectangle = topRectangle; 		//stores a reference to the selected rectangle
		
		}else{
			if(selectedRectangle == topRectangle){	//if the button is clicked but the rectangle is on the same tower
				topRectangle.setFill(Color.ORANGE);		//changing the fill color
				selectedRectangle =null;
			
			}else{
				VBox p = (VBox) selectedRectangle.getParent();
				if(vbox3.getChildren().size()==0 || selectedRectangle.getWidth() <topRectangle.getWidth()){		//if there are no rectangles in vbox1 then we can definetly make the move
					
				p.getChildren().remove(selectedRectangle);
				vbox3.getChildren().add(0, selectedRectangle);	//Because ya added the 0 it always adds it to the top
				moveCount();
				if(p== vbox1){
					recordMove( 1,3);	//this is recording a move on the stack the reason why its 2 first because thats the destintion
					}else{
						recordMove(2,3);
					}
				selectedRectangle.setFill(Color.ORANGE);		//changing the fill color
				selectedRectangle =null;
			}else{
				System.out.println("MOVE NOT ALLOWED");		//Making the user aware it was invalid move
			}
			}
		}
			
		}
	
	public void column1(MouseEvent event){
		onButtonTower1(null);
	}
	
	public void column2(MouseEvent event){
		buttonTower2(null);
		
	}
	
	public void column3 (MouseEvent event){
		buttonTower3(null);
	}
		
	
	
	public void restartGame(ActionEvent event){
		System.out.println("YOU HAVED RESTARTED GAME");
		
		//moves all the rectangles back to the first tower
		while(toMove.size()>0) {
			undoMove(null);
		}
		
		//clear undo stacks
		//toMove.clear();
		//fromMove.clear();
		
		//reset moves counter
		counter = 0;
		movesNum.setText("0");
		
		//make sure nothing is selected
		//selectedRectangle = null;
		
		
		
		
	
	}
	
	public void undoMove(ActionEvent event){
		if(toMove.size()>0){		//make sure there is something to undo
			int from = toMove.pop();		//the pop means that it takes the top and brings it back
			int to = fromMove.pop();
			
		
		System.out.println("YOU UNDID MOVE");
		
		System.out.println("Undo: " + from + " -> " + to);
		if(selectedRectangle != null){
			selectedRectangle.setFill(Color.ORANGE);
			selectedRectangle = null;		//make sure nothing is selected
		}
		switch(from){		//fake a click on the from column
			case 1:onButtonTower1(null);
			break;
			case 2:buttonTower2(null);
			break;
			case 3:buttonTower3(null);
			break;
		}
		switch(to){			//fake a click on the to column
		case 1:onButtonTower1(null);
		break;
		case 2:buttonTower2(null);
		break;
		case 3:buttonTower3(null);
		break;
		}
		toMove.pop();
		fromMove.pop();	//We don't want to undo an undo 
		}
	}
}
	
	
	


	
	


