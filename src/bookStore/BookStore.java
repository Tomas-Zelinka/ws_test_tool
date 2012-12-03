package bookStore;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService()
public class BookStore {

	
	//private List<List<String>> books;
	//private final String dataStore = "data/data.txt";
	
	/*public BookStore()
	{
		try{
			books =  CSVIO.readTXTFile(dataStore);
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		showAllBooks();
		
	}*/
	
	@WebMethod
	public String addBook()
	{
		return "addBook()";
	}
	/*public void addBook(String authorName, String publishYear, String bookName)
	{
		ArrayList<String> book = new ArrayList<String>();
		
		book.add(bookName);
		book.add(authorName);
		book.add(publishYear);
		
		this.books.add(book);
		
	}*/
	
	public List<String> getBookInfo(String book)
	{
		List<String> infos;
		infos = new ArrayList<String>();
				
		return infos;
		
	}
	
	@WebMethod
	public String removeBook()
	{
		return "removeBook";
	}
	
	@WebMethod
	public String showAllBooks()
	{
			
		/*for ( List<String> book : books){
			for (String value : book){
				System.out.print(value + ",");
			}
		}*/
		
		return "showAllBooks";
	}
	
	
	
}
