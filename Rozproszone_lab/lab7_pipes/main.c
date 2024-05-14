#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>

#define READ 0
#define WRITE 1

void send_string(const int* file_descriptor, const char* text)
{
    write(file_descriptor[WRITE], text, strlen(text));
    // printf("p1: %s\n", text);
    if(strcmp(text, "exit\n") == 0)
    {
        close(file_descriptor[WRITE]);
        exit(0);
    }
}

void invert_letters(const int* fd_1, const int* fd_2)
{
    char text[50]={};
    read(fd_1[READ], text, sizeof(text));

    // printf("before p2: %s\n", text);
    if(strcmp(text, "exit\n") != 0)
    {
        for(int i=0; i<strlen(text);i++)
        {
            if((int)text[i] >= 65 && (int)text[i] <= 90)
            {
                text[i] = (char)((int)text[i] + 32);
            }
            else if((int)text[i] >= 97 && (int)text[i] <= 122)
            {
                text[i] = (char)((int)text[i] - 32);
            }
        }
    }
    write(fd_2[WRITE], text, strlen(text));
    // printf("p2: %s\n", text);
    if(strcmp(text, "exit\n") == 0)
    {
        close(fd_2[WRITE]);
        close(fd_1[READ]);
        exit(0);
    }
}

void multiply_digits(const int* fd_2)
{
    char text[50]={};
    read(fd_2[READ], text, sizeof(text));
    // printf("p3: %s", text);

    for(int i = 0;i<strlen(text); i++)
    {
        if(48 <= (int) text[i] && (int) text[i] <= 57)
        {
            int value = (int) text[i] - '0';
            value *= 2;
            if(value >= 10)
            {
                text[i] = 'X';
                continue;
            }
            text[i] = (char) value + '0';
        }
    }

    const int fd = open("mainfifo", O_WRONLY);
    write(fd, text, strlen(text)+1);
    // printf("p3 after: %s\n", text);
    if (strcmp(text, "exit\n") == 0)
    {
        printf("Detected 'exit'\nClosing...");
        close(fd_2[READ]);
        close(fd);
        exit(0);
    }
}


int main() {
    int fd_1[2], fd_2[2];
    pipe(fd_1);
    pipe(fd_2);

    // creating named pipe
    if(mkfifo("mainfifo", 0777) == -1)
    {
        if(errno != EEXIST)
        {
            fprintf(stderr, "Error while creating named pipe");
        }
    }

    pid_t pid = fork();
    if(pid == 0)
    {
        close(fd_1[READ]);
        do
        {
            char buffer[50]= {};
            printf("Type string to chagne : ");
            fgets(buffer, sizeof(buffer),stdin);
            send_string(fd_1, buffer);
        }while(1);
    }


    pid = fork();
    if(pid == 0 )
    {
        close(fd_1[WRITE]);
        close(fd_2[READ]);
        while(1)
        {
            invert_letters(fd_1, fd_2);
        }
    }

    close(fd_2[WRITE]);
    close(fd_1[READ]);
    close(fd_1[WRITE]);
    while(1)
    {
        multiply_digits(fd_2);
    }
}
