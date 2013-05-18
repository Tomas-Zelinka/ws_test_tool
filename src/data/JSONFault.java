package data;

import org.json.JSONException;
import org.json.JSONObject;

import proxyUnit.HttpMessage;

public class JSONFault extends Fault{

	private String variableName;
	private String value;
	
	public JSONFault(String name, String value){
		this.variableName = name;
		this.value = value;
	}
	
	@Override
	public String getDescription() {
		
		return "Replace variable \""+this.variableName+"\" value \""+this.value+"\" inside JSON format";
	}

	@Override
	public void inject(HttpMessage message) {
		
		String content = message.getContent();
		JSONObject model =null;
		try {
			model = new JSONObject(content);
			if(model.has(variableName)){
				model.remove(variableName);
				model.put(variableName,value);
			}
		} catch (JSONException e) {
			
			
		}
		
		message.setChangedContent(model.toString());
		message.setChanged(true);
	}

	@Override
	public String toString() {
		
		return "JSON fault";
	}
	

}
