package com.github.cyrilBoucher.td;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.github.cyrilBoucher.td.GameObjects.TouchEvent;

public class TouchEventHandler {

	private ConcurrentLinkedQueue<TouchEvent> mEventQueue;
	
	public TouchEventHandler()
	{
		mEventQueue = new ConcurrentLinkedQueue<TouchEvent>();
	}
	
	public void push(TouchEvent te)
	{
		mEventQueue.add(te);
	}
	
	public TouchEvent pull()
	{
		return mEventQueue.remove();
	}
	
	public boolean queueIsEmpty()
	{
		return mEventQueue.isEmpty();
	}
}
