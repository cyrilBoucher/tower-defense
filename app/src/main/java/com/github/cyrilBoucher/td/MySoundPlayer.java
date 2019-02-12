package com.github.cyrilBoucher.td;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;

public class MySoundPlayer 
{
	private Context context;
	private SoundPool soundPool;
	private MediaPlayer mediaPlayer;
	private HashMap<String,Integer> soundMap;
	private HashMap<String,Integer> playing;
	private int numberOfSounds = 0;
	public static final Semaphore semaphore = new Semaphore(0);
	public boolean loaded = false;
	
	private float volume;
	
	public MySoundPlayer(Context c)
	{
		context = c;
		soundMap = new HashMap<String,Integer>();
		playing = new HashMap<String,Integer>();
		soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
		mediaPlayer = new MediaPlayer();
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener()
		{
		   @Override
		   public void onLoadComplete(SoundPool soundPool, int sampleId, int status) 
		   {
			   if(numberOfSounds > 0)
			   {
				   numberOfSounds--;
			   }
			   else
			   {
				   loaded = true;
				   Log.d("SoundPlayer", "Every sounds loaded !");
			   }
		   }
		});
	}
	
	public void LoadingSound()
	{
		numberOfSounds = 3;
		loaded = false;
		soundMap.put("MG_Tower_Shot", soundPool.load(context, R.raw.shoot, 0));
		soundMap.put("MG_Enemy_Damage", soundPool.load(context, R.raw.hurting, 0));
		soundMap.put("MG_Tower_Spawn", soundPool.load(context, R.raw.buying, 0));
		//soundMap.put("PG_Victory", soundPool.load(context, R.raw.victory, 0));
		soundMap.put("MG_Base_Damage", soundPool.load(context, R.raw.damage, 0));
		
		//soundId = soundPool.load(context, R.raw.menu, 0);
		//soundMap.put("MM_MainMenuLoop", soundId);
		/*
		// Main game sound effects  
		soundMap.put("MG_Tower_Spawn", soundId);
		soundMap.put("MG_Enemy_Damage", soundId);
		soundMap.put("MG_Enemy_Death", soundId);
		soundMap.put("MG_Enemy_Sacrifice", soundId);
		soundMap.put("MG_Base_Destroyed", soundId);
		soundMap.put("MG_Base_Damage", soundId);
		soundMap.put("MG_MainBackgroundLoop", soundId);
		
		// Main Menu sound effects
		soundMap.put("MM_PlayButton", soundId);
		soundMap.put("MM_QuitButton", soundId);
		soundMap.put("MM_MainMenuLoop", soundId);
		
		// Post game sound effects
		soundMap.put("PG_Victory", soundId);
		soundMap.put("PG_Defeat", soundId);
		soundMap.put("PG_ReplayButton", soundId);
		soundMap.put("PG_TitleButton", soundId);*/
		
	}
	public void LoadingIntro()
	{
		soundMap.put("MM_MainMenuIntro", soundPool.load(context, R.raw.intro, 0));
	}
	
	public void PlaySound(String s, int loop, float... reduc)
	{
		if(loaded == true)
		{
			if(soundMap.containsKey(s) == true)
			{
				setVolume();
				if(reduc.length < 1)
				{
					playing.put(s, soundPool.play(soundMap.get(s), volume, volume, 0, loop, 1));
				}
				else
				{
					playing.put(s, soundPool.play(soundMap.get(s), volume-reduc[0], volume-reduc[0], 0, loop, 1));
				}
			}
		}
	}
	
	public void setVolume()
	{
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume / maxVolume;
	}
	
	public void PauseSound()
	{
		soundPool.autoPause();
	}
	
	public void StopSound()
	{
		for(String s : playing.keySet())
		{
			soundPool.stop(playing.get(s));
		}
	}
	
	public void PlayInGame()
	{
		if(mediaPlayer.isPlaying() == true)
		{
			mediaPlayer.stop();
		}
		setVolume();
		mediaPlayer = MediaPlayer.create(context, R.raw.ingameloop);
		mediaPlayer.setLooping(true);
		mediaPlayer.setVolume(volume - 0.5f, volume - 0.5f);
		mediaPlayer.start();
	}
	
	public void PlayVictory() 
	{
		if(mediaPlayer.isPlaying() == true)
		{
			mediaPlayer.stop();
		}
		setVolume();
		mediaPlayer = MediaPlayer.create(context, R.raw.victory);
		//mediaPlayer.setLooping(true);
		mediaPlayer.setVolume(volume - 0.2f, volume - 0.2f);
		mediaPlayer.start();
		
	}
	
	public void PlayDefeat()
	{
		if(mediaPlayer.isPlaying() == true)
		{
			mediaPlayer.stop();
		}
		setVolume();
		mediaPlayer = MediaPlayer.create(context, R.raw.defeat);
		//mediaPlayer.setLooping(true);
		mediaPlayer.setVolume(volume - 0.2f, volume - 0.2f);
		mediaPlayer.start();
	}
	
	public void PauseInGame()
	{
		mediaPlayer.pause();
	}
	
	public void ResumeInGame()
	{
		mediaPlayer.start();
	}
	
	public void StopInGame()
	{
		mediaPlayer.stop();
		if(mediaPlayer.isPlaying() == false)
		{
			mediaPlayer.stop();
			try {
				mediaPlayer.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isMediaPlaying()
	{
		boolean ret = false;
		if(mediaPlayer.isPlaying() == true)
		{
			ret = true;
		}
		return ret;
	}
}
