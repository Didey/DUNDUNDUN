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
	public static String coords;
	
	public GameClient(){
		client = new Client();
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if(object instanceof String){
					System.out.println("CLIENT RECEIVED:" + (String)object);
					String precor = (String)object;
					String[] coordinates = precor.split(" ");
					mpX = Integer.parseInt(coordinates[0]);
					mpY = Integer.parseInt(coordinates[1]);
					coords = (String) object;
				}
			}
		});
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
		coords = Player.getPX + " " + Player.getPY;
		client.sendTCP(coords);
	}
}