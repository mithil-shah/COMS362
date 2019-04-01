package configuration;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Response
{	
	public Response(String inputVariable)
	{
		System.out.println(inputVariable);
	}
	
	public Response(ArrayList<BufferedImage> images)
	{
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new GridLayout(images.size()/2, images.size()/2));
		
		for(BufferedImage image: images)
		{
			frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		}
		
		frame.pack();
		frame.setVisible(true);
	}
}
