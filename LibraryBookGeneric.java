package lab04;

import java.util.GregorianCalendar;



public class LibraryBook extends Book {
	GregorianCalendar dueDate;
	String holder;
	
	public LibraryBook (long isbn, String author, String title) {
		super (isbn, author, title);
	}
	
	public GregorianCalendar getdueDate(){
		return this.dueDate;
	}
	
	public String getHolder(){
		return this.holder;
	}

	public void setHolder(String holder){
		this.holder = holder;
	}

	public void setDueDate(GregorianCalendar dueDate){

		this.dueDate = dueDate;
	}

}
	
}
