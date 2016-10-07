using System;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace TestSocket
{
	public class SocketClient
	{
		public SocketClient ()
		{
			Console.WriteLine ("client begin connect server");
			IPAddress ip = IPAddress.Parse ("192.168.1.107");
			IPEndPoint ipe = new IPEndPoint (ip,8888);
			Socket socket = new Socket (AddressFamily.InterNetwork,SocketType.Stream,ProtocolType.Tcp);
			socket.Connect (ipe);
			string msg = "hello server";
			byte[] writeBuffer = Encoding.ASCII.GetBytes (msg);
			socket.Send (writeBuffer,writeBuffer.Length,0);


			byte[] readBuffer = new byte[1024];
			int readCount = socket.Receive (readBuffer);
			string response = Encoding.ASCII.GetString (readBuffer);
			Console.WriteLine (response);
			socket.Close ();
		}

	}
}

