using System.Net;
using System.Net.Sockets;
using System.Text;
using MessageProtocolLib;

namespace server;

internal static class Server
{
    public static void Main()
    {
        var cts = new CancellationTokenSource();
        CancellationToken token = cts.Token;

        _ = Task.Run(() => StartServer(token), token);

        Console.WriteLine("Press any key to stop serer...");
        Console.Read();
        
        cts.Cancel();
        cts.Dispose();
    }


    private static void StartServer(CancellationToken token)
    {
        using (Socket server = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp))
        {
            server.Bind(new IPEndPoint(IPAddress.Any, 12345));
            server.Listen(10);
            Console.WriteLine("Server started...");

            while (!token.IsCancellationRequested)
            {
                Console.WriteLine("Waiting for connections...");
                Socket client = server.Accept();
                _ = Task.Run(() => HandleClient(client, token), token);
            }
        }
    }

    private static void HandleClient(Socket clientSocket, CancellationToken token)
    {
        using (clientSocket)
        {
            var buffer = new byte[1024];
            while (!token.IsCancellationRequested)
            {
                int received = clientSocket.Receive(buffer);
                if (received == 0)
                {
                    break;
                }

                var serializedMessage = Encoding.UTF8.GetString(buffer, 0, received);
                var message = MessageProtocol.Deserialize(serializedMessage);
                Console.WriteLine($"Client sent: {message}");

                message.X += 1.0f;
                message.Y += 1.0f;
                message.Z += 1.0f;

                Console.WriteLine($"Modified message: {message}");
                var modifiedMessage = message.Serialize();
                buffer = Encoding.UTF8.GetBytes(modifiedMessage);
                clientSocket.Send(buffer);
            }
        }
    }
}