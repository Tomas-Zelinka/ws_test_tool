package bookStore;

//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

//import javax.jws.WebMethod;
//import javax.jws.WebResult;



@javax.jws.WebService
public class BookStore {

	
	//private List<List<String>> books;
	//private final String dataStore = "data/data.txt";
	
	private int books;
	public BookStore()
	{
		this.books = 0;
		//try{
		//	books =  CSVIO.readTXTFile(dataStore);
		//}catch(IOException ex){
		//	ex.printStackTrace();
		//}
		
		///showAllBooks();
		
	}
	
	public int addBook()
	{
		this.books++;
		return this.books;
	}
	/*public void addBook(String authorName, String publishYear, String bookName)
	{
		ArrayList<String> book = new ArrayList<String>();
		
		book.add(bookName);
		book.add(authorName);
		book.add(publishYear);
		
		this.books.add(book);
		
	}*/
	
	/*public List<String> getBookInfo(String book)
	{
		List<String> infos;
		infos = new ArrayList<String>();
				
		return infos;
		
	}*/
	
	
	public String removeBook()
	{
		return "removeBook";
	}
	
	
	public int showAllBooks()
	{
			
		/*for ( List<String> book : books){
			for (String value : book){
				System.out.print(value + ",");
			}
		}*/
		
		return this.books;
	}
	
	
	
}
