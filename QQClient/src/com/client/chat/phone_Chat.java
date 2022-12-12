package com.client.chat;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.sound.sampled.AudioFormat;
import org.jsresources.apps.am.audio.AMAudioFormat;
import org.jsresources.apps.am.audio.AudioCapture;
import org.jsresources.apps.am.audio.AudioPlayStream;

public class phone_Chat {
	public AudioCapture phoneMIC;
	public AudioPlayStream phoneSPK;
	public AudioFormat format;
	public Socket cc;
	public InputStream in;
	private String address;
	private VoicePlay play ;

	public phone_Chat(String address) {
		this.address = address;
		startPhone();		
	}
	// 初始化硬件，创建接收和捕获器
	public void iniAudioHardware() throws Exception {
		
		phoneMIC = new AudioCapture(AMAudioFormat.FORMAT_CODE_GSM);//初始化MIC		
		format = AMAudioFormat.getLineAudioFormat(AMAudioFormat.FORMAT_CODE_GSM);// 初始化SPK				
		phoneSPK = new AudioPlayStream(format);
		phoneMIC.open();
		phoneSPK.open();
	}

	public void startPhone() {
		try {
			iniAudioHardware();
			cc = new Socket(address,9987); // 端口号
			in = cc.getInputStream();
			play = new VoicePlay();
			play.play();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
		phoneMIC.close();
		phoneSPK.close();
		cc.close();
		in.close();
		play.stop();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 播放接收的音频
	class VoicePlay {
		@SuppressWarnings("unused")
		private boolean complete = false;
		private byte[] gsmdata = new byte[1000];
		private int numBytesRead = 0;

		@SuppressWarnings("static-access")
		public void play() {
			try {
				phoneSPK.start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			complete = false;
			while ((!Thread.currentThread().interrupted())) {
				try {
					numBytesRead = in.read(gsmdata);
					if (numBytesRead == -1) {
						complete = true;
						break;
					}
					phoneSPK.write(gsmdata, 0, numBytesRead);
				} catch (IOException e) {
					System.exit(1);
				}
			}
		}

		public void run() {
			play();
		}
		public void stop() {
			complete = true;
		}
	}
}
