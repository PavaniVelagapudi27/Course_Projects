import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.Scanner;
import java.io.*;
import javax.swing.*;

public class GraphicsManager3_nvelag2 extends JFrame implements ActionListener,MouseListener{
	
	JPanel base=new JPanel();
	JPanel buttons=new JPanel();
	JPanel mix=new JPanel();
	private int click;
	private boolean check=false;
	private int[] x=new int[40];
	private int[] y=new int[40];
	private boolean enabled=false,clear=false,preview=false;
	int index;
	JPanel draw=new JPanel(){
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if(preview)
			{
				int n;
				Dimension[] de=shapes[index].getDimensions();
				for(n=0;n<(shapes[index].getSides()-1);n++)
				{
					g.drawLine((int)de[n].getWidth(), (int)de[n].getHeight(), (int)de[n+1].getWidth(), (int)de[n+1].getHeight());	
				}
				g.drawLine((int)de[n].getWidth(), (int)de[n].getHeight(), (int)de[0].getWidth(), (int)de[0].getHeight());
					
			}
			else if(clear)
			{
				
			}
			else if(enabled && click>=2)
			{
				for(int n=0;n<(click-1);n++)
			g.drawLine(x[n],y[n],x[n+1],y[n+1]);
				g.setColor(Color.RED);
				g.drawLine(x[0],y[0], x[click-1], y[click-1]);
			}
		}
	};
	JPanel fb=new JPanel();
	JButton load=new JButton("Load File");
	JButton save=new JButton("Save File");
	JButton create=new JButton("Create");
	JButton scrap=new JButton("Scrap");
	JButton keep=new JButton("Keep");
	JButton prev=new JButton("<");
	JButton next=new JButton(">");
	
	private Polygon3[] shapes;
	private static Polygon3[] p;
	private int size;
	
	//private int check=0;
	//Scanner s=new Scanner(System.in);
	private File file;
	private FileReader fin;
	//private FileWriter fw;
	PrintWriter pw;
	BufferedReader br;
	int indicator;
	
	public static void main(String[] args)
	{
		GraphicsManager3_nvelag2 gm3=new GraphicsManager3_nvelag2();
		//gm3.load_shapes();
		gm3.gui();
		//gm3.pw.close();
		
	}
	
	public  GraphicsManager3_nvelag2()
	{
			
			shapes=new Polygon3[0];
			
			
			
	}
	void gui()
	{
		
			try
			{
				
		file=new File("/Users/pavanivelagapudi/Documents/workspace/GraphicsManager3_nvelag2/src/shapes.data");
		fin=new FileReader(file);
		br=new BufferedReader(fin);
		
		load_shapes();
		//fout=new FileOutputStream(file);
		pw=new PrintWriter(new FileWriter(file,true));
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setTitle("GraphicsManager3_nvelag2");
		this.add(base);
		base.setLayout(new GridLayout(1,2));
		fb.add(prev);
		fb.add(next);
		mix.setLayout(new GridLayout(2,1));
		mix.add(draw);
		draw.setBorder(BorderFactory.createLineBorder(Color.black));
		mix.add(fb);
		buttons.setLayout(new GridLayout(5,1));
		buttons.add(load);
		buttons.add(save);
		buttons.add(create);
		buttons.add(scrap);
		buttons.add(keep);
		base.add(buttons);
		base.add(mix);
		load.addActionListener(this);
		save.addActionListener(this);
		create.addActionListener(this);
		scrap.addActionListener(this);
		keep.addActionListener(this);
		prev.addActionListener(this);
		next.addActionListener(this);
		draw.addMouseListener(this);
		this.setSize(800, 600);
		this.setResizable(false);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
		    public void WindowClosing(WindowEvent e) {
		        pw.close();
		        try{
		        br.close();
		        fin.close();
		        }catch(Exception x){}
		    }
		});
		
		
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,e.toString(), "No Polygons to load", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	Polygon3 create()
	{
		Polygon3 poly=new Polygon3();
		poly=new Polygon3();
		poly.setSides(click);
		poly.setDimensions(x,y);
		return poly;		
	}
	
	void add(Polygon3 p)
	{
		int i;
		int alsize=shapes.length;
		Polygon3[] temp=new Polygon3[alsize];
		for(i=0;i<alsize;i++)
		{
			temp[i]=shapes[i];
		}
		shapes=new Polygon3[alsize+1];
		for(i=0;i<alsize;i++)
		{
			shapes[i]=temp[i];
		}
		shapes[i]=p;
		//JOptionPane.showMessageDialog(null,shapes.length, "No Polygons to load-add", JOptionPane.INFORMATION_MESSAGE);	
		
	}
	public void load_shapes()
	{
		try{
			int[] x1=new int[40];
			int[] y1=new int[40];
			int j,sides1;
				
				if(file.length()==0)
					return;
			
			while(br.readLine()!=null)
			{
				//JOptionPane.showMessageDialog(null, "e", "InfoBox-loads", JOptionPane.INFORMATION_MESSAGE);
				Polygon3 p3=new Polygon3();
				sides1=Integer.parseInt(br.readLine());
				p3.setSides(sides1);
				for(j=0;j<sides1;j++)
				{
					double d1=Double.parseDouble(br.readLine());
					double d2=Double.parseDouble(br.readLine());
					x1[j]=(int)d1;
					y1[j]=(int)d2;
				}
				p3.setDimensions(x1, y1);
				add(p3);
			//JOptionPane.showMessageDialog(null, "last", "InfoBox-loads", JOptionPane.INFORMATION_MESSAGE);
				
			}
			indicator=shapes.length;
			br.close();
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, e.toString(), "InfoBox-loads", JOptionPane.INFORMATION_MESSAGE);
			}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
		JButton b= (JButton) e.getSource();
		String s=b.getText();
		
		//JOptionPane.showMessageDialog(null, s, "InfoBox: " + s, JOptionPane.INFORMATION_MESSAGE);
		if(s.equals("Load File"))
		{
			
			if(shapes.length==0)
				JOptionPane.showMessageDialog(null,"No Polygons to Load!!", "Info", JOptionPane.INFORMATION_MESSAGE);	
			else
			{
				draw.setBackground(Color.WHITE);
				preview=true;
				index=0;
				repaint();
			}
			
		}
		if(s.equals("Save File"))
		{
			
			for(int k=indicator;k<shapes.length;k++)
        	{
        	pw.println(k);
			pw.println(shapes[k].getSides());
			for(int i=0;i<shapes[k].getSides();i++)
			{
				Dimension[] di=shapes[k].getDimensions();
					pw.println(di[i].getWidth());
					
					pw.println(di[i].getHeight());
				
				
			}
        	}
			indicator=shapes.length;
			pw.flush();
			pw.close();
			JOptionPane.showMessageDialog(null,"File Saved!!", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		if(s.equals("Create"))
		{
			preview=false;
			draw.setBackground(Color.WHITE);
			enabled=true;
			repaint();
		}
		if(s.equals("Scrap"))
		{
			click=0;
			draw.setBackground(Color.GRAY);
			enabled=false;
			preview=false;
			repaint();
		}
		if(s.equals("Keep"))
		{
			if(check)
			{
			int id=shapes.length;
			//JOptionPane.showMessageDialog(null,id, "No Polygons to load", JOptionPane.INFORMATION_MESSAGE);
			Polygon3 temp=create();
			add(temp);
			
			//JOptionPane.showMessageDialog(null, "info", "InfoBox:saved", JOptionPane.INFORMATION_MESSAGE);
			click=0;
			clear=true;
			repaint();
			}
			check=false;
		}
		if(s.equals("<"))
		{
			index--;
			if(index<0)
				index=(shapes.length-1);
			repaint();
		}
		if(s.equals(">"))	
		{
			index++;
			if(index==shapes.length)
				index=0;
			repaint();
		}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.toString(), "InfoBox", JOptionPane.INFORMATION_MESSAGE);
		}

		
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		clear=false;
		//JOptionPane.showMessageDialog(null, e.getX(), "InfoBox: " + e.getY(), JOptionPane.INFORMATION_MESSAGE);
		if(enabled)
		{
		x[click]=e.getX();
		y[click]=e.getY();
		click++;
		if(click>=2)
		{
			check=true;
			draw.repaint();
		}
		
        
		//JOptionPane.showMessageDialog(null, "Fired!!!!", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
		}
		
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
		// TODO Auto-generated method stub
		
	}

}

class Polygon3
{
	Scanner s=new Scanner(System.in);
	private int id,sides;
	private Dimension d[];
	
	void setDimensions(int[] a,int[] b)
	{
		d=new Dimension[sides];
		for(int i=0;i<sides;i++)
		{
			d[i]=new Dimension();
			
			d[i].width=a[i];
			d[i].height=b[i];
		}
	
		
	}
	
	Dimension[] getDimensions()
	{
		return d;
	}
	
	void setSides(int s)
	{
		this.sides=s;
	}
	
	int getSides()
	{
		return sides;
	}
	
	void clear()
	{
		sides=0;
		d=new Dimension[0];
	}
	void extend(Dimension dim)
	{
		int i;
		sides=sides+1;
		Dimension[] temp=new Dimension[sides];
		for(i=0;i<sides;i++)
			temp[i]=d[i];
		temp[i]=dim;
		d=temp;
	}
	void retract()
	{
		int i;
		sides=sides-1;
		Dimension[] temp=new Dimension[sides];
		for(i=0;i<sides;i++)
			temp[i]=d[i];
		d=temp;
	}
}
