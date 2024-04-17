import socket
import threading

HOST = "127.0.0.1"
PORT = 5000

connected = True


class SendThread(threading.Thread):
    def __init__(self, class_socket):
        threading.Thread.__init__(self)
        self.s_socket = class_socket

    def run(self):
        while True:
            message = input(" -> ")
            if message.lower().strip() == 'quit':
                break
            self.s_socket.send(message.encode())
        connected = [False]


def client_program():
    host = HOST
    port = PORT

    client_socket = socket.socket()
    client_socket.connect((host, port))

    send_thread = SendThread(client_socket)
    send_thread.start()

    while connected:
        data = client_socket.recv(1024).decode()
        print(data)
        print("-> ", end="")

    client_socket.close()


if __name__ == '__main__':
    client_program()
