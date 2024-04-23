#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>

#define MAX_CLIENTS 10
int connected_sockets[MAX_CLIENTS];
int server_is_running;

void broadcast(int sender, char* message, char* username){
    for(int i = 0; i< MAX_CLIENTS; i++){
        if(connected_sockets[i] !=-1 && connected_sockets[i] != sender){
            char new_message[1024];
            sprintf(new_message, "%s: %s", username, message);
            write(connected_sockets[i] , new_message , strlen(new_message));
        }
    }
}


void *connection_handler(void *socket_desc) {
    int sock = * (int *)socket_desc;
    int read_size;
    char *message , client_message[2000];
    char username[20];
    connected_sockets[sock] = sock;

    message = "Type your username: ";
    write(sock , message , strlen(message));


    read_size = recv(sock , username , 20 , 0);
    username[read_size] = '\0';
    fprintf(stderr, "%s\n" ,username);

    message = "SERVER INFO: successfully connected";
    write(sock , message , strlen(message));


    for(;;){
        read_size = recv(sock, client_message, 2000, 0);
        client_message[read_size] = '\0';
        fprintf(stderr, "%s sends: %s\n", username, client_message);

        if (strcmp(client_message, "quit") == 0) {
            message = "quit";
            write(sock, message, strlen(message));
            break;
        }

        broadcast(sock, client_message, username);
        memset(client_message, 0, 2000);
    }
    connected_sockets[sock] = -1;
    fprintf(stderr, "%s disconnected\n", username);
    sprintf(client_message, "%s disconnected", username);
    broadcast(sock, client_message, "SERVER INFO");
    close(sock);
    pthread_exit(NULL);
}

void* admin_thread(void* arg){
    char command[256];
    while(server_is_running){
        fgets(command, 256, stdin);
        if(strcmp(command, "close\n") == 0){
            server_is_running = 0;
        }
    }

    for(int i = 0; i< MAX_CLIENTS; i++){
        if(connected_sockets[i] !=-1){
            close(connected_sockets[i]);
        }
    }

    pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
    int listenfd = 0, connfd = 0;
    struct sockaddr_in serv_addr;

    pthread_t client_thread_id, server_admin_thread_id;
    for (int i = 0; i < MAX_CLIENTS; i++) {
        connected_sockets[i] = -1;
    }

    fprintf(stderr, "Server started...\n");
    server_is_running = 1;
    listenfd = socket(AF_INET, SOCK_STREAM, 0);
    memset(&serv_addr, '0', sizeof(serv_addr));

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    serv_addr.sin_port = htons(5000);

    bind(listenfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr));

    listen(listenfd, 10);
    pthread_create(&server_admin_thread_id, NULL, admin_thread, NULL);

    for(;;) {
        connfd = accept(listenfd, (struct sockaddr*)NULL, NULL);
        fprintf(stderr, "Connection accepted\n");
        pthread_create(&client_thread_id, NULL, connection_handler , (void *) &connfd);
    }
}
