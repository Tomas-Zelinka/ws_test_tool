package testingUnit;

import data.HttpMessageData;



public interface NewResponseListener {
	public void onNewResponseEvent(HttpMessageData[] data);
}
