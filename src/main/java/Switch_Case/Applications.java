package Switch_Case;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.mysql.cj.jdbc.Blob;

public class Applications {

	public static void main(String[] args) throws Exception {
		Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBC_CRUD_Operation","root","root");
//		Statement statement=c.createStatement();
//		statement.execute("create table Apps(id int Primary Key,Name varchar(25) Not Null,Application_name varchar(20) Not Null,date date Not Null,image longblob Not Null)");
//		c.close();
//		System.out.println("Done..");
		Scanner scanner=new Scanner(System.in);
		
		boolean flag=true;
		
		while(flag){
			System.out.println("1.add\n2.update Application_name\n3.fetch_value_without_image\n4.fetch_only_image\n5.delete/remove_by_id\n6.exit");
			int opt=scanner.nextInt();
			switch (opt) 
			{
			case 1:
			{
				PreparedStatement preparedStatement=c.prepareStatement("insert into Apps values(?,?,?,?,?)");
				System.out.println("Enter id");
				preparedStatement.setInt(1, scanner.nextInt());
				System.out.println("Enter the name");
				preparedStatement.setString(2, scanner.next());
				System.out.println("Enter the Application_name..");
				preparedStatement.setString(3, scanner.next());
				System.out.println("Enter the date..");
				preparedStatement.setDate(4,Date.valueOf(scanner.next()));
				System.out.println("Enter the image path...");
				Scanner scanner2=new Scanner(System.in);
				FileInputStream fileInputStream=new FileInputStream(scanner2.nextLine());
				preparedStatement.setBinaryStream(5,fileInputStream,fileInputStream.available());
				int e=preparedStatement.executeUpdate();
				System.out.println("<<<<<  Added  >>>>>");
			}
				
				break;
			case 2:
			{
				PreparedStatement preparedStatement=c.prepareStatement("update Apps set Application_name=? where id=?");
				System.out.println("Enter id..");
				preparedStatement.setInt(2, scanner.nextInt());
				System.out.println("Enter topic name");
				preparedStatement.setString(1, scanner.next());
				int e=preparedStatement.executeUpdate();
				System.out.println("<<<<<<  Updated  >>>>>");
			}
			break;
			case 3:
			{
				PreparedStatement preparedStatement=c.prepareStatement("select * from Apps where id=?");
				System.out.println("Enter id..");
				preparedStatement.setInt(1, scanner.nextInt());
				ResultSet e=preparedStatement.executeQuery();
				while(e.next())
				{
					System.out.println("ID : "+e.getInt(1));
					System.out.println("Name : "+e.getString(2));
					System.out.println("Application Name : "+e.getString(3));
					System.out.println("Date : "+e.getDate(4));
				}
				System.out.println("<<<<<  Fetched   >>>>>>");
			}
			break;
			case 4:
			{
				PreparedStatement preparedStatement=c.prepareStatement("select * from Apps where id=?");
				System.out.println("Enter id");
				preparedStatement.setInt(1, scanner.nextInt());
				ResultSet e=preparedStatement.executeQuery();
				
				while(e.next())
				{
					Scanner scanner2=new Scanner(System.in);
					Blob blob=(Blob) e.getBlob(5);
					System.out.println("Enter path...");
					FileOutputStream fileOutputStream=new FileOutputStream(scanner2.nextLine());
					fileOutputStream.write(blob.getBytes(1,(int)blob.length()));
					System.out.println("<<<<<<  Successfully fetched  >>>>>>>>");
				}
			}
			break;
			case 5:
			{
				PreparedStatement preparedStatement=c.prepareStatement("delete from Apps where id=?");
				System.out.println("Enter id..");
				preparedStatement.setInt(1, scanner.nextInt());
				int e=preparedStatement.executeUpdate();
				System.out.println("<<<<<<   Deletded   >>>>>>>>");
			}
			break;
			case 6:
			{
				flag=false;
				System.out.println("Thank You Bye");
			}
			break;
			default:
			{
				System.out.println("Invalid Option....");
			}
		   }
		}
		c.close();
	}
}

























