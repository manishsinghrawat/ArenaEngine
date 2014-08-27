package com.me.LanNetworkUtils;

import java.net.DatagramPacket;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryonet.ClientDiscoveryHandler;

public class ClientDiscovery implements ClientDiscoveryHandler{
		private Input input = null;
		int i;
		private Array<DiscoveryPacket> hostlist;
		
		public ClientDiscovery(Array<DiscoveryPacket> hostlist) {
			super();
			this.hostlist = hostlist;
		}

		@Override
		public DatagramPacket onRequestNewDatagramPacket() {
			byte[] buffer = new byte[1024];
			input = new Input(buffer);
			return new DatagramPacket(buffer, buffer.length);
		}

		@Override
		public void onDiscoveredHost(DatagramPacket datagramPacket, Kryo kryo) {
			if (input != null) {
				DiscoveryPacket packet;
				packet = (DiscoveryPacket) kryo.readClassAndObject(input);
				packet.ipaddress=datagramPacket.getAddress().toString();
				synchronized(hostlist){
					for(i=0;i<hostlist.size;i++)
						if(hostlist.get(i).ipaddress.equals(packet.ipaddress)){
							hostlist.get(i).update(packet);
							break;
						}
					if(i==hostlist.size)
						hostlist.add(packet);
				}
			}
		}
		
		@Override
		public void onFinally() {
			if (input != null) {
				input.close();
			}
		}

}
