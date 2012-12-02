package bookStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookStore {

	
	private List<List<String>> books;
	private final String dataStore = "data/data.txt";
	
	public BookStore()
	{
		try{
			books =  CSVIO.readTXTFile(dataStore);
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		showAllBooks();
		
	}
	
	
	public void addBook(String authorName, String publishYear, String bookName)
	{
		ArrayList<String> book = new ArrayList<String>();
		
		book.add(bookName);
		book.add(authorName);
		book.add(publishYear);
		
		this.books.add(book);
		
	}
	
	public List<String> getBookInfo(String book)
	{
		List<String> infos;
		infos = new ArrayList<String>();
				
		return infos;
		
	}
	
	public void removeBook()
	{
		
	}
	
	
	public void showAllBooks()
	{
			
		for ( List<String> book : books){
			for (String value : book){
				System.out.print(value + ",");
			}
		}
	}
	
	
	
}
