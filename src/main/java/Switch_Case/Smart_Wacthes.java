package Switch_Case;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Smart_Wacthes {

	public static void main(String[] args) throws Exception {
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBC_CRUD_Operation","root","root");
//		Statement statement=connection.createStatement();
//		boolean b=statement.execute("create table Watch_Strore(Slno int Primary Key, Watch_name varchar(20) Not Null,Watch_color varchar(15) Not Null,Watch_pic longblob Not Null )");
//		connection.close();
//		System.out.println("Table Created....");
//		System.out.println(b);
		
		Scanner scanner=new Scanner(System.in);
		
		boolean flag=true;
		while(flag)
		{
			System.out.println("1.Add\n2.Update\n3.Fetch_without_image\n4.Fetch_only_image\n5.Delete/Remove_by_Slno\n6.Exit");
			int opt=scanner.nextInt();
			switch (opt) {
			case 1:
			{
				PreparedStatement preparedStatement=connection.prepareStatement("insert into Watch_Strore values(?,?,?,?)");
				System.out.println("Enter Slno");
				preparedStatement.setInt(1, scanner.nextInt());
				System.out.println("Enter the Watch_Name");
				preparedStatement.setString(2, scanner.next());
				System.out.println("Enter the Watch_color");
				preparedStatement.setString(3, scanner.next());
				System.out.println("Enter image path");
				Scanner scanner2=new Scanner(System.in);
				FileInputStream fileInputStream=new FileInputStream(scanner2.nextLine());
				preparedStatement.setBinaryStream(4,fileInputStream,fileInputStream.available());
				int e=preparedStatement.executeUpdate();
				System.out.println("<<<<  Added  >>>>>");
			}
				break;
			case 2:
			{
				PreparedStatement preparedStatement=connection.prepareStatement("update Watch_Strore set Watch_name=? where Slno=?");
				System.out.println("Enter Slno");
				preparedStatement.setInt(1,scanner.nextInt());
				System.out.println("Enter the Watch name..");
				preparedStatement.setString(2, scanner.next());
				int e=preparedStatement.executeUpdate();
				System.out.println("<<<< Successfully Updated  >>>>");
			}
			break;
			case 3:
			{
				PreparedStatement preparedStatement=connection.prepareStatement("select * from Watch_Strore where Slno=?");
				System.out.println("Enter the Slno");
				preparedStatement.setInt(1, scanner.nextInt());
				ResultSet e=preparedStatement.executeQuery();
				System.out.println("<<<<<  Update Successfully  >>>>>");
				
				while(e.next())
				{
					System.out.println("Slno : "+e.getInt(1));
					System.out.println("Watch name : "+e.getString(2));
					System.out.println("Watch color : "+e.getString(3));
					System.out.println("Mfg date : "+e.getDate(4));
				}
				System.out.println("<<<<  Successfully Fetched  >>>>");
			}
			break;
			case 4:
			{
				PreparedStatement preparedStatement=connection.prepareStatement("select * from Watch_Strore where Slno=?");
				System.out.println("Enter Slno");
				preparedStatement.setInt(1, scanner.nextInt());
				ResultSet e=preparedStatement.executeQuery();
				
				while(e.next())
				{
					Scanner scanner2=new Scanner(System.in);
					Blob blob=e.getBlob(5);
					FileOutputStream fileOutputStream=new FileOutputStream(scanner2.nextLine());
					fileOutputStream.write(blob.getBytes(1,(int)blob.length()));
					System.out.println("<<<<  Successfully Fetched  >>>>");
				}
			}
			break;
			case 5:
			{
				PreparedStatement preparedStatement=connection.prepareStatement("delete from Watch_Strore where Slno=?");
				System.out.println("Enter Slno");
				preparedStatement.setInt(1, scanner.nextInt());
				int e=preparedStatement.executeUpdate();
				System.out.println("<<<<  Successfully deleted  >>>>");
			}
			break;
			case 6:
			{
				flag=false;
				System.out.println("Thank u TM hogu.....");
			}
			break;
			default:
			{
				System.out.println("<<<<<   Invalid Input   >>>>");
			}
			}
		}
		connection.close();
	}
}
