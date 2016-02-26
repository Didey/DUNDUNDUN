package com.didey.dun.engine;

import java.io.IOException;

import com.didey.dun.gui.Game;
import com.didey.dun.objects.Player;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameClient {

	public static int mpX, mpY;
	public static Client client;
	public static String cords;
	
	public GameClient(){
		client = new Client();
		cords = Player.getPX + " " + player.getPY;
		Kryo kryo = client.getKryo();
		kryo.register(Player.class);
		kryo.register(Game.class);
		client.start();
		try {
			client.connect(5000, "192.168.1.3", 54555, 54777);
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Server is down.");
		}
	}
	
	public static void sendServerInfo(){
		client.sendTCP(cords);
		
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if(object instanceof String){
					String precor = (String)object;
					String[] cords = precor.split(" ");
					mpX = Integer.parseInt(cords[0]);
					mpY = Integer.parseInt(cords[1]);
				//	System.out.println("CLIENT RECEIVED: " + mpX + " " +  mpY);
				}
			}
		});
	}
}
