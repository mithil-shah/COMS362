package features;
import javax.swing.JPanel;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;

import configuration.Response;

import javax.swing.JButton;
import java.awt.Container;


import javax.swing.JButton;
import java.awt.Container;

/**
 * 
 * @author fadelalshammasi
 *
 */
public class Calculator implements Feature , ActionListener {
	
	
	protected int result;
	protected int op = 0;
	protected JFrame gui;
	protected JPanel button;
	protected JTextField calculator;

	 
	
	@Override
	public Response setResponse() {
		run();
		return new Response("");
	}

	@Override
	public void parseQuery(String query) {
		// TODO Auto-generated method stub
		
	}
	
	
	public Calculator(String query){
	  gui = new JFrame();
	  gui.setTitle("Calculator");
	  gui.setSize(200,200);
	  calculator = new JTextField();
	  calculator.setHorizontalAlignment(JTextField.RIGHT);
	  gui.add(calculator, BorderLayout.NORTH);
	  button = new JPanel();
	  button.setLayout(new GridLayout(4,4));   
	  gui.add(button, BorderLayout.CENTER);
	 
	  
	  int i=0;
	  while (i<10) {
	      addNumberButton(button, String.valueOf(i));
	      i++;
	  }
	 
	  addActionButton(button, 1, "+");
	  addActionButton(button, 2, "-");
	  addActionButton(button, 3, "*");
	  addActionButton(button, 4, "/");
	  addActionButton(button, 5, "^2");
	  addActionButton(button, 6, "^");
	  addActionButton(button, 7, "sqr");
	  addActionButton(button, 8, "bin");
	  addActionButton(button, 9, "hex");

	 
	  JButton equal = new JButton("=");
	  equal.setActionCommand("=");
	  equal.addActionListener(new ActionListener()
	  {
	      public void actionPerformed(ActionEvent event) {
	         
	              int number = Integer.parseInt(calculator.getText()); 
	              
	              switch (op) {
	              case 1:
	              
	                  int calculateAdd = result  + number;
	                  calculator.setText(Integer.toString(calculateAdd));
	                  break;
	              
	              case 2:
	              
	                  int calculatSub = result  - number;
	                  calculator.setText(Integer.toString(calculatSub));
	                  break;
	              
	              case 3:
	                  int calculateMul = result  * number;
	                  calculator.setText(Integer.toString(calculateMul));
	                  break;
	              
	              case 4:
	              
	                  double calculateDiv = result  / (double)number;
	                  calculator.setText(Double.toString(calculateDiv));
	                  break;
	              
	              case 5:
	              
	                  int calculatePow = (int) Math.pow(result,2);
	                  calculator.setText(Integer.toString(calculatePow));
	                  break;
	              
	              case 6:
	              
	                  int calculatePoww = (int) Math.pow(result,number);
	                  calculator.setText(Integer.toString(calculatePoww));
	                  break;
	              
	              case 7:
	              
	                 double calculateSqr =  Math.sqrt(result);
	                 calculator.setText(Double.toString(calculateSqr));
	                  break;
	              
	              case 8:
	              
	                 String calculateBinary = Integer.toBinaryString(result);
	                 calculator.setText(calculateBinary);
	                  break;
	               
	              case 9:
	            	  String calculateHex = toHex(result);
	                  calculator.setText(calculateHex);
	                  break;
	            	  
	            	  
	              default: 
	            	  calculator.setText("Exit");
	            	  break;
	              
	              
	          }
	      }});
	     
	  button.add(equal);
	  gui.setVisible(true);  
	  parseQuery(query);
	}

	private void addNumberButton(Container parent, String str)
	{
	  JButton butttom = new JButton(str);
	  butttom.setActionCommand(str);
	  butttom.addActionListener(this);
	  parent.add(butttom);
	}
	 
	private String toHex(int decimal){    
	    int reminder;  
	    String hex="";   
	    char hexchars[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};  
	   while(decimal>0)  
	    {  
	      reminder=decimal%16;   
	      hex=hexchars[reminder]+hex;   
	      decimal=decimal/16;  
	    }  
	   return hex;  
	}   


	private void addActionButton(Container parent, int action, String text)
	{
	  JButton but = new JButton(text);
	  but.setActionCommand(text);
	  OperatorAction addAction = new OperatorAction(action);
	  but.addActionListener(addAction);
	  parent.add(but);
	}
	 
	public void actionPerformed(ActionEvent event)
	{
	  String action = event.getActionCommand();
	 
	  calculator.setText(action);       
	}
	 
	private class OperatorAction implements ActionListener
	{
	  private int operator;
	 
	  public OperatorAction(int operation)
	  {
	      operator = operation;
	  }
	 
	  public void actionPerformed(ActionEvent event)
	  {
		  result = Integer.parseInt(calculator.getText()); 
	      op = operator;
	  }
	} 
	
	public void run() {
		 
       new Calculator("");         
    }
		

}
