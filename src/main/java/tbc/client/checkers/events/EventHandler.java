package tbc.client.checkers.events;

import java.util.ArrayList;

public class EventHandler
{
	private ArrayList<EventListener> listeners = new ArrayList<EventListener>();
	
	public void InvokeEvent(Object[] _args)
	{
		for(int i = 0; i < listeners.size(); ++i)
		{
			listeners.get(i).ExecuteAction(_args);
		}
	}
	
	public void addListener(EventListener _listener)
	{
		if(!listeners.contains(_listener))
		{
			listeners.add(_listener);
		}
	}
	
	public void removeListener(EventListener _listener)
	{
		if(listeners.contains(_listener))
		{
			listeners.remove(_listener);
		}
	}
}