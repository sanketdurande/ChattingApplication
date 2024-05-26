package cattingApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class Client  implements ActionListener{
	
	JTextField text;
	static JPanel a1;
	static Box vertical = Box.createVerticalBox();
	static DataOutputStream dout;
	static JFrame f= new JFrame();
	
	Client(){
		f.setLayout(null);
		
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0, 0, 499, 70);
		p1.setLayout(null);
		f.add(p1);
		
		ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2= i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3= new ImageIcon(i2);
		JLabel backimg= new JLabel(i3);
		backimg.setBounds(5, 20, 25, 25);
		p1.add(backimg);
		backimg.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		} );
		
		ImageIcon pi1= new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
		Image pi2= pi1.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon pi3= new ImageIcon(pi2);
		JLabel profile= new JLabel(pi3);
		profile.setBounds(40, 10, 50, 50);
		p1.add(profile);
		
		ImageIcon vi1= new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image vi2= vi1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon vi3= new ImageIcon(vi2);
		JLabel video= new JLabel(vi3);
		video.setBounds(350, 20, 30, 30);
		p1.add(video);
		
		ImageIcon phi1= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image phi2= phi1.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
		ImageIcon phi3= new ImageIcon(phi2);
		JLabel phone= new JLabel(phi3); 
		phone.setBounds(390, 10, 35, 50);
		p1.add(phone);
		
		ImageIcon i31= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
		Image i32= i31.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
		ImageIcon i33= new ImageIcon(i32);
		JLabel Threei= new JLabel(i33); 
		Threei.setBounds(430, 20, 10, 25);
		p1.add(Threei);
		
		JLabel name = new JLabel("Banty");
		name.setBounds(110, 15,100,20);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		p1.add(name);
		
		JLabel status = new JLabel("Active");
		status.setBounds(110, 40,100,20);
		status.setForeground(Color.GREEN);
		status.setFont(new Font("SAN_SERIF", Font.BOLD, 10));
		p1.add(status);
		
		
		a1= new JPanel();
		a1.setBounds(5, 75, 490, 570);
		f.add(a1);
		
		text= new JTextField();
		text.setBounds(5, 650, 380, 40);
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f.add(text);
		
		JButton send= new JButton("Send");
		send.setBounds(390, 650, 100, 40);
		send.setBackground(new Color(7, 94,84));
		send.setForeground(Color.WHITE);
		send.addActionListener(this);
		send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f.add(send);
		
		f.setSize(499, 699);
		f.setLocation(800, 50);
		f.setUndecorated(true);
		f.getContentPane().setBackground(Color.WHITE);
		
		
		
		f.setVisible(true);
		
	}
	public void actionPerformed(ActionEvent ae) {
		try {
	String out= text.getText();
	JLabel output = new JLabel(out);
	
	JPanel p2 = formatLabel(out);
	
	
	a1.setLayout(new BorderLayout());
	
	JPanel right= new JPanel(new BorderLayout());
	right.add(p2, BorderLayout.LINE_END);
	vertical.add(right);
	vertical.add(Box.createVerticalStrut(15));
	a1.add(vertical, BorderLayout.PAGE_START);
	
	dout.writeUTF(out);
	
	text.setText("");
	f.repaint();
	f.invalidate();
	f.validate();
		} catch(Exception e){
			e.printStackTrace();
		
		}
	
		 
		
	}
	public static JPanel formatLabel (String out) {
		JPanel panel= new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
		output.setFont(new Font("Tahoma", Font.PLAIN, 16));
		output.setBackground(new Color(37, 211,102));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15,15,15,50));
		panel.add(output);
		
		Calendar cal= Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		JLabel time= new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);
		
		
		return panel;
	}
	public static void main(String[] args) {
		new Client();
		
		try {
			Socket s= new Socket("127.0.0.1",6001);
			DataInputStream din= new DataInputStream(s.getInputStream());
			dout= new DataOutputStream(s.getOutputStream());
			
			while(true) {
				a1.setLayout(new BorderLayout());
				String msg = din.readUTF();
				JPanel panel = formatLabel(msg);
				JPanel left = new JPanel(new BorderLayout());
				left.add(panel,BorderLayout.LINE_START);
				vertical.add(left);
				a1.add(vertical, BorderLayout.PAGE_START);
				
				f.validate();
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
