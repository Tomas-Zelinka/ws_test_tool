package bookStore;


@javax.jws.WebService
public class BookStore {

	private int books;
	
	public BookStore(){
		this.books = 0;
	}
	
	public int addBook(){
		this.books++;
		return this.books;
	}
	
	
	public int removeBook(){
		if(this.books > 0){
			this.books--;
		}
		return this.books;
	}
	
	
	public int showAllBooks(){
		return this.books;
	}
	
	
	
}
