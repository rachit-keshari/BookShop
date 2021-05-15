import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import java.awt.Button;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BookShop {

	private JFrame frame;
	private JTextField bkname;
	private JTextField bked;
	private JTextField bkprice;
	private JTable table;
	private JTextField bkid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookShop window = new BookShop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookShop() {
		initialize();
		Connect();
		table_load();
	}
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

	public void Connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/book_shop","root","");
		}catch(ClassNotFoundException ex){
			
		}catch(SQLException ex) {
			
		}
	}
	
	public void table_load() {
		try {
			 pst = con.prepareStatement("select * from book");
			 rs = pst.executeQuery();
             table.setModel(DbUtils.resultSetToTableModel(rs));
             
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("CheckBox.background"));
		frame.setBounds(100, 100, 942, 522);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Rachit's Book Shop");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 30));
		lblNewLabel.setBounds(314, 10, 285, 57);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(SystemColor.controlHighlight);
		panel1.setBorder(new TitledBorder(null, "Register", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel1.setBounds(31, 77, 372, 229);
		frame.getContentPane().add(panel1);
		panel1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(25, 33, 110, 19);
		panel1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(25, 99, 110, 19);
		panel1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1.setBounds(25, 163, 110, 19);
		panel1.add(lblNewLabel_1_1_1);
		
		bkname = new JTextField();
		bkname.setBounds(145, 31, 204, 30);
		panel1.add(bkname);
		bkname.setColumns(10);
		
		bked = new JTextField();
		bked.setColumns(10);
		bked.setBounds(145, 97, 204, 30);
		panel1.add(bked);
		
		bkprice = new JTextField();
		bkprice.setColumns(10);
		bkprice.setBounds(145, 161, 204, 30);
		panel1.add(bkprice);
		
		JScrollPane mytable = new JScrollPane();
		mytable.setBounds(422, 75, 483, 307);
		frame.getContentPane().add(mytable);
		
		table = new JTable();
		mytable.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.controlHighlight);
		panel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(31, 392, 377, 71);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1_2 = new JLabel("Book ID");
		lblNewLabel_1_2.setBounds(23, 24, 91, 22);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(lblNewLabel_1_2);
		
		bkid = new JTextField();
		bkid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					  
					String id = bkid.getText();
					 pst = con.prepareStatement("select * from book where id=?");
					 pst.setString(1, id);
					 ResultSet rs = pst.executeQuery();
					 
					if(rs.next()==true)
					{
						String name = rs.getString(2);
						String edition = rs.getString(3);
						String price = rs.getString(4);
						
						bkname.setText(name);
						bked.setText(edition);
						bkprice.setText(price);
					}
					else {
						bkname.setText("");
						bked.setText("");
						bkprice.setText("");
					}
					
				}catch(SQLException err) {
					err.printStackTrace();
				}
			}
		});
		bkid.setBounds(124, 24, 229, 26);
		bkid.setColumns(10);
		panel.add(bkid);
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnNewButton.setBounds(160, 325, 121, 46);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String bname,edition,price;
				bname = bkname.getText();
				edition = bked.getText();
				price = bkprice.getText();
				
				try {
					pst = con.prepareStatement("insert into book set book_name= ?,book_edition= ?,book_price= ?");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					table_load();
					JOptionPane.showMessageDialog(null, "Record Added!");
					
					bkname.setText("");
					bked.setText("");
					bkprice.setText("");
					bkid.setText("");
					bkname.requestFocus();
				}catch(SQLException el) {
					el.printStackTrace();
				}

			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnSave.setBounds(31, 325, 121, 46);
		frame.getContentPane().add(btnSave);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 
				 bkname.setText("");
			     bked.setText("");
			     bkprice.setText("");
			     bkid.setText("");
                 bkid.requestFocus();	
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnClear.setBounds(291, 325, 121, 46);
		frame.getContentPane().add(btnClear);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = bkid.getText();		
				String name = bkname.getText();
				try {
					if(id.isEmpty()==true)
						JOptionPane.showMessageDialog(null,"Nothing To Delete. Select the book ID first");
					else if(id.isEmpty()==false && name.isEmpty()==true)
						JOptionPane.showMessageDialog(null,"Please Select a Valid book ID first");
					else {
					pst = con.prepareStatement("delete from book where id=?");
					pst.setString(1, id);
					pst.executeUpdate();
					table_load();
				    JOptionPane.showMessageDialog(null, "Record Deleted!");
				    bkname.setText("");
					bked.setText("");
					bkprice.setText("");
					bkid.setText("");
					bkname.requestFocus();
					}
				}catch(SQLException el) {
					el.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnDelete.setBounds(648, 405, 166, 49);
		frame.getContentPane().add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname,edition,price,id;
				bname = bkname.getText();
				edition = bked.getText();
				price = bkprice.getText();
				id = bkid.getText();
				
				try {
					
					if(id.isEmpty()==true)
					{
						JOptionPane.showMessageDialog(null, "Nothing to update!");
					}
					else if(id.isEmpty()==false && bname.isEmpty()==true && edition.isEmpty()==true && price.isEmpty()==true) {
						JOptionPane.showMessageDialog(null, "Invalid Book ID!");
					}
					else {
					pst = con.prepareStatement("update book set book_name=?,book_edition= ?,book_price= ? where id=?");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, id);
					pst.executeUpdate();
					table_load();
					JOptionPane.showMessageDialog(null, "Record Updated!");
					
					bkname.setText("");
					bked.setText("");
					bkprice.setText("");
					bkid.setText("");
					bkname.requestFocus();
					}
					
				}catch(SQLException el) {
					el.printStackTrace();
				}
			}
			  
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnUpdate.setBounds(460, 405, 166, 49);
		frame.getContentPane().add(btnUpdate);
	}
}
