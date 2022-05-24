package application4;

import java.util.*;

public class BusBooking 
{
	private static String encrypt(String passwd) 
	{
		String res="";
		
		for(int i=0;i<passwd.length();i++)
		{
			res += (char)((int)(passwd.charAt(i))+2);
		}
		return res;
	}

	public static void main(String[] args) 
	{
		//
		User u1 = new User(1,"AAA",20,'M',encrypt("333"));
		User u2 = new User(2,"BBB",25,'F',encrypt("555"));
		int usrid = 2;
		List<User> usr= new ArrayList<User>();
		List<Bus> bus= new ArrayList<Bus>();
		List<Passenger> psg = new ArrayList<Passenger>();
		Scanner sc = new Scanner(System.in);
		usr.add(u1);
		usr.add(u2);
		
		bus.add(new Bus("AcSleeper"));
		bus.add(new Bus("AcSeater"));
		bus.add(new Bus("NonAcSleeper"));
		bus.add(new Bus("NonAcSeater"));
		
		
		
		int choice;
		do
		{
			System.out.println("1.SignUp");
			System.out.println("2.LogIn");
			System.out.println("0.Exit");
			System.out.print("Enter your choice(1/2/0):");
			choice = sc.nextInt();
			switch(choice)
			{
			case 1:
				System.out.println("=============Sign Up=============");
				System.out.print("Enter name:");
				String name = sc.next();
				System.out.print("Enter Age:");
				int age = sc.nextInt();
				System.out.print("Enter Gender(M/F):");
				char gender = sc.next().charAt(0);
				System.out.print("Enter Password:");
				String password = sc.next();
				usr.add(new User(++usrid,name,age,gender,encrypt(password)));
				System.out.println("Account Created Successfully");
				System.out.println("-----------------------------------");
				break;
				
			case 2:
				System.out.println("=============LogIn=============");
				System.out.print("Enter user name:");
				String uName = sc.next();
				System.out.print("Enter Password:");
				String uPassword = sc.next();
				boolean userFound = false;
				for(User u:usr)
				{
					if(u.usrName.equals(uName))
					{
						userFound =true;
						if(u.usrPassword.equals(encrypt(uPassword)))
						{
							
							System.out.println(">>>>>>>>Logged In Successfully<<<<<<<<");
							int ch;
							
							do
							{
								System.out.println("1.Book Ticket");
								System.out.println("0.LogOut");
								System.out.print("Enter your choice(0/1):");
								ch = sc.nextInt();
								switch(ch)
								{
								case 0:
									System.out.println("Logging out>>>");
									break;
									
								case 1:
									for(Bus b:bus)
									{
										System.out.println(b.bId+" - "+b.busType+":"+b.noOfFreeSeats+" Seats");
									}
									/*System.out.println("1.AC Sleeper");
									System.out.println("2.AC Seater");
									System.out.println("3.Non AC Sleeper");
									System.out.println("4.Non AC Seater");*/
									System.out.print("Enter Bus Choice(1/2/3/4):");
									int busChoice = sc.nextInt();
									String type="";
									switch(busChoice)
									{
									case 1:
										type = "AcSleeper";
										break;
									case 2:
										type = "AcSeater";
										break;
									case 3:
										type = "NonAcSleeper";
										break;
									case 4:
										type = "NonAcSeater";
										break;
									}
									
									for(Bus b:bus)
									{
										if(b.busType.equalsIgnoreCase(type))
										{
											b.displaySeat();
										}
									}
									
									int k=0;
									loop:
					
									for(;k<bus.size();k++)
									{
										if(bus.get(k).busType.equalsIgnoreCase(type))
										{	
											System.out.println("Enter no of tickets:");
											int noOfTicket = sc.nextInt();
											if(bus.get(k).isAvaliable(noOfTicket))
											{
												
												for(int i = 0;i < noOfTicket;i++)
												{
													System.out.print("Enter name:");
													String tname = sc.next();
													System.out.print("Enter gender:");
													char gen = sc.next().charAt(0);
													bus.get(k).book();
													psg.add(new Passenger(tname,gen,bus.get(k).b));
													System.out.println("-----------------------------------");
													
												}
												int totalFare = bus.get(k).fair * noOfTicket;
												System.out.println("Booked " + noOfTicket + " Seats Successfully.");
												System.out.println("Total fare is " + totalFare);
												System.out.println("-----------------------------------");
											}
											else
											{
												System.out.println("Avaliable seats in the bus is "+bus.get(k).noOfFreeSeats+".Enter seats accordingly.");
												k=-1;
												continue loop;
											}
												
										}
										
									}
									
									break;
			
								default:
									System.out.println("Ivalid Option!!!");
									break;
								}
							}while(ch!=0);
							System.out.println("<<<<<<<<LoggedOut Successfully>>>>>>>>");	
						}
						else
						{
							System.out.println("Invalid Password!!!");
						}
					}
					
				}
				if(!userFound)
				{
					System.out.println("Invalid UserName!!!");
				}
				break;
			case 0:
				System.out.println(">>>Exiting>>>");
				break;
				
			default:
				System.out.println("Invalid Option!!!");
				break;
			}
			
		}while(choice != 0);
		System.out.println("__________Thankyou for using__________");
		
		
	}

}

class User
{
	int usrId;
	String usrName;
	int age;
	char usrGender;
	String usrPassword;
	public User(int id,String name,int age,char gender,String passwd)
	{
		usrId = id;
		usrName = name;
		this.age = age;
		usrGender = gender;
		//String password = encrypt(passwd);
		usrPassword = passwd;
	}
}

class Passenger
{
	String name;
	char gender;
	int seatNo;
	public Passenger(String name,char gender,int seatNo)
	{
		this.name = name;
		this.gender = gender;
		this.seatNo = seatNo+1;
	}
	
	void display()
	{
		System.out.println("Name: " + name);
		System.out.println("Gender: " + gender);
		System.out.println("SeatNo: " + seatNo);
	}
}

class Bus
{
	int bId;
	static int cnt=0;
	String busType;
	int b = 0;
	int noOfPassengers;
	int noOfFreeSeats;
	int[] seat = new int[12];
	int fair;
	public Bus(String type)
	{
		bId = ++cnt;
		busType = type;
		noOfPassengers = 0;
		noOfFreeSeats = 12;
		if(type.equalsIgnoreCase("AcSleeper"))
		{
			fair = 700;
		}
		else if(type.equalsIgnoreCase("AcSeater"))
		{
			fair = 550;
		}
		else if(type.equalsIgnoreCase("NonAcSleeper"))
		{
			fair = 600;
		}
		else if(type.equalsIgnoreCase("NonAcSeater"))
		{
			fair = 450;
		}
		else
		{
			fair = 0;
		}
	}
	
	boolean isAvaliable(int n)
	{
		if(n <= noOfFreeSeats)
		{
			return true;
		}
		return false;
	}
	
	void book()
	{
		noOfFreeSeats--;
		noOfPassengers++;
		seat[b++]=1;
		
	}
	
	void displaySeat()
	{
		int cnt = 1;
		System.out.println("-------------------------------------------------------------------------");
		for(int i = 0;i < 12;i++)
		{
			if(seat[i]==1)
			{
				System.out.print("\tOccupied\t|");
			}
			else if(seat[i]==0)
			{
				System.out.print("\tAvaliable\t|");
				
			}
			if(cnt%3==0)
			{
				System.out.println("\n-------------------------------------------------------------------------");
			}
			cnt++;
		}
	}
}
