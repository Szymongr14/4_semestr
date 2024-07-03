using System.Net;
using System.Net.Sockets;
using System.Text;
using MessageProtocolLib;

internal static class Client
{
    private static readonly IPAddress Address = IPAddress.Loopback;
    private const int Port = 12345;
    public static void Main()
    {
        using (var sender = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp))
        {
            sender.Connect(new IPEndPoint(Address, Port));
            Console.WriteLine("Connected to server...");
            Task.Run(() => ServerListener(sender));
            while (true)
            {
                Console.WriteLine("want to quit ? yes/no");
                string? quit = Console.ReadLine();
                if (quit == "yes")
                {
                    break;
                }
                
                var message = new MessageProtocol(2.0f,3.1f , 0);
                Console.WriteLine($"Sent message to server: {message}");
                string? serializedMessage = message.Serialize();
                byte[] buffer = Encoding.UTF8.GetBytes(serializedMessage);
                sender.Send(buffer);
            }
        }
    }
    
    private static void ServerListener(Socket connection)
    {
        var buffer = new byte[1024];
        while (true)
        {
            int received = connection.Receive(buffer);
            if (received == 0)
            {
                break;
            }
            string message = Encoding.UTF8.GetString(buffer, 0, received);
            MessageProtocol? deserializedMessage = MessageProtocol.Deserialize(message);
            Console.WriteLine($"Received message after modification: {deserializedMessage}");
        }
    }
}