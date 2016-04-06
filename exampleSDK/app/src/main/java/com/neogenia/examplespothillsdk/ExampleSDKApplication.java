package com.neogenia.examplespothillsdk;

import android.app.Application;

import cz.neogenia.spothill_library.SpothillLibrary;

/**
 * Created by juraj on 06/04/16.
 */
public class ExampleSDKApplication extends Application
{
	public SpothillLibrary spothillLibrary;

	@Override
	public void onCreate()
	{
		super.onCreate();

		spothillLibrary = SpothillLibrary.getInstanceForApplication(this, "hash", true);
	}

	public SpothillLibrary getSpothillLibrary()
	{
		return this.spothillLibrary;
	}
}
