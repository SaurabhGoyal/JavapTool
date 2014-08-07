import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class MyJavapGUI {

	JFrame f;
	JTextField textField;
	JPanel north,center;
	JButton show;
	JTextArea code;
	String classNames[];
	
	public MyJavapGUI()
	{
		
		f=new JFrame("MyJAVAP TOOL");
		north=new JPanel(new BorderLayout());
		center=new JPanel(new BorderLayout());
		code=new JTextArea();
		code.setEditable(false);
		JScrollPane sp=new JScrollPane(code,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		textField=new JTextField("");
//		textField.requestFocus();
		textField.addKeyListener(new MyKeyListener());
		show=new JButton("Show code");
		show.addActionListener(new ShowListener());
		
		north.add(textField,BorderLayout.CENTER);
		north.add(show,BorderLayout.EAST);
		
		center.add(sp,BorderLayout.CENTER);
		code.addMouseListener(new MyMouseListener());
		
		f.setLayout(new BorderLayout());
		f.add(north,BorderLayout.NORTH);
		f.add(center,BorderLayout.CENTER);
		
		f.setSize(new Dimension(500,500));
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
	
	public void compile(){
		String s=textField.getText();
		classNames=s.split(",|;");
		code.setText(null);
		for(String name:classNames)
			code.append(new MyJavapTool(name.trim(),"d:\\").classSource);			

	}

	
	class ShowListener implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			compile();
		}
	}
		
	class MyKeyListener implements KeyListener
	{

		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar()==KeyEvent.VK_ENTER)
			{	compile();
			}
		}

		public void keyReleased(KeyEvent e) {
			
		}	
	}
	
	class MyMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			JOptionPane.showMessageDialog(code, "Class details are here");
		
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyJavapGUI();
	}

}
