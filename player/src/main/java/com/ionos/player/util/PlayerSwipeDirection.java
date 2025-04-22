package com.ionos.player.util;

/**
 * Created by Alex Kucherenko on 30.09.2016.
 */

public enum PlayerSwipeDirection {
	START,
	NEXT,
	PREVIOUS;

	public String toString(){
		String str = "";
		switch (this){
			case START:
				str = "start";
				break;
			case NEXT:
				str = "next";
				break;
			case PREVIOUS:
				str = "previous";
		}
		return str;
	}
}
