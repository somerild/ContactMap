import java.util.*;
import java.io.*;

class Contact{

	//vars
	private String lastName;
	private String firstName;
	private String phoneNumber;
	private String emailAddress;

	//constructor
	Contact(String newLastName, String newFirstName, String newPhoneNumber, String newEmailAddress){
	
		setLastName(newLastName);
		setFirstName(newFirstName);
		setPhoneNumber(newPhoneNumber);
		setEmailAddress(newEmailAddress);
	}
	//set methods
	private void setLastName(String newLastName){
	
		lastName = newLastName;
	}
	private void setFirstName(String newFirstName){
	
		firstName = newFirstName;
	}
	private void setPhoneNumber(String newPhoneNumber){
	
		phoneNumber = newPhoneNumber;
	}
	private void setEmailAddress(String newEmailAddress){
	
		emailAddress = newEmailAddress;
	}
	//get methods
	public String getLastName(){
	
		return lastName;
	}
	private String getFirstName(){
	
		return firstName;
	}
	private String getPhoneNumber(){
	
		return phoneNumber;
	}
	private String getEmailAddress(){
	
		return emailAddress;
	}
	public String toString(){
	
		return (getLastName() + "," + getFirstName() + "," + getPhoneNumber() + "," + getEmailAddress() + "\n");
	}

}
class ContactList{

	//vars
	private String fileName;
	private File f;
	private Scanner in;
	private PrintWriter out;
	private static TreeMap<String, Contact> contactMap = new TreeMap<String, Contact>();

	//constructor
	ContactList(Scanner input){
	
		load(input);
	}
	//methods
	private void load(Scanner input){

		try{
		
			fileName = input.nextLine();
			f = new File(fileName);

			if(!f.exists()) throw new FileNotFoundException("File does not exist.");
			if(!f.canRead()) throw new IOException("Cannot read file.");

			in = new Scanner(
					new BufferedReader(
					new FileReader(f)));
			
			in.useDelimiter(",");

			while(in.hasNext()){
			
				String last = in.next();
				String first = in.next();
				String phone = in.next();
				String email = in.nextLine().replaceAll(",", " ").trim();
				Contact temp = new Contact(last, first, phone, email);
				contactMap.put(temp.getLastName(), temp);
			}
			in.close();
		}
		catch(Exception e){
		
			e.printStackTrace();
			System.exit(1);
		}
	}
	public void save(){
	
		try{
			if(!f.canWrite()) throw new IOException("Cannot write to file.");

			out = new PrintWriter(
					new BufferedWriter(
					new FileWriter(f)));
			
			for(Map.Entry individual : contactMap.entrySet()){
			
				Contact c = contactMap.get(individual.getKey());
				out.print(c);
			}
			System.out.println("Contact list saved.");
			out.close();
			System.out.println("File closed.");
		}
		catch(Exception e1){
		
			e1.printStackTrace();
		}
	}
	public void display(){
	
		for(Map.Entry individual : contactMap.entrySet()){
		
			Contact c = contactMap.get(individual.getKey());
			System.out.print(c);
		}
	}
	public void addContact(String newLastName, String newFirstName, String newPhoneNumber, String newEmailAddress){
	
		Contact individual = new Contact(newLastName, newFirstName, newPhoneNumber, newEmailAddress);
		contactMap.put(individual.getLastName(), individual);
	}
	public void removeContact(String removeLastName){
	
		if (contactMap.containsKey(removeLastName)){
			contactMap.remove(removeLastName);
			System.out.println("Contact removed.");
		}
		else System.out.println("Contact not found.");
	}
}
public class Problem1{

	public static void main(String[] args){
	
		//vars
		Scanner input = new Scanner(System.in);
		String selection = null;
		String selectionMenu = """
			Contact List Options:
			[1] Display list
			[2] Add a contact
			[3] Remove a contact
			[4] Save list and exit
			Please enter your selection: """;	

		System.out.println("Please enter the contact list file name: ");
		ContactList contacts = new ContactList(input);

		do{
		
			System.out.println();
			System.out.println(selectionMenu);
			selection = input.nextLine();
			System.out.println();

			switch(Integer.valueOf(selection)){
				case 1: contacts.display();
					break;
				case 2: System.out.println("Enter last name: ");
					String newLastName = input.nextLine().trim().toLowerCase();
					System.out.println("Enter first name: ");
					String newFirstName = input.nextLine().trim().toLowerCase();
					System.out.println("Enter phone number: ");
					String newPhoneNumber = input.nextLine().trim().toLowerCase();
					System.out.println("Enter email address: ");
					String newEmailAddress = input.nextLine().trim().toLowerCase();
					contacts.addContact(newLastName, newFirstName, newPhoneNumber, newEmailAddress);
					break;
				case 3:	System.out.println("Enter last name of contact to remove: ");
					String removeLastName = input.nextLine().trim().toLowerCase();
					contacts.removeContact(removeLastName);
					break;
				case 4: contacts.save();
					break;	
				default: break;
			}

		}while(!selection.equals("4"));
	}
}
