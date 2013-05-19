package cz.vutbr.fit.dp.xzelin15.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.vutbr.fit.dp.xzelin15.proxyUnit.HttpMessage;


/**
 * 
 * Class to apply header condition
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class HttpHeaderCondition extends Condition {

	/**
	 * Name of condition 
	 */
	private String name;
	
	/**
	 * Header parameter name
	 */
	private String parametrName;
	
	/**
	 * Header parameter value
	 */
	private String value;
	
	public HttpHeaderCondition(String name, String value){
		this.name = "Header condition";
		this.parametrName = name;
		this.value = value;
	}
	
	/**
	 * Prints description of the condition
	 */
	@Override
	public String getDescription() {
		
		String desription = "Apply if defined header parameter \""+this.parametrName+"\" is present";
		return desription;
	}

	/**
	 * This method will test the conditions
	 */
	@Override
	public boolean isFulfilled(HttpMessage message) {
		
			
		Pattern pattern= Pattern.compile("^"+this.parametrName+": "+this.value+"", Pattern.MULTILINE);
		Matcher matcher= pattern.matcher(message.getHttpHeader());
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * Prints the name of the condition
	 */
	@Override
	public String toString() {
		
		return this.name;
	}

}
