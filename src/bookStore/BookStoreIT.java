package bookStore;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


@WebService
public interface BookStoreIT {

	@WebMethod(operationName = "addBook")
	@WebResult(name = "book")
	public String addBook();
	
	@WebMethod(operationName = "removeBook")
	
	public String removeBook();
	
	@WebMethod(operationName = "showAllBooks")
	public String showAllBooks();
	
	

}
