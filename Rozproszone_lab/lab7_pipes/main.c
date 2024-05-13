#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include<sys/wait.h>

#define READ 0
#define WRITE 1

void send_string(const int* file_descriptor, const char* text)
{
    close(file_descriptor[READ]);
    write(file_descriptor[WRITE], text, sizeof(text));
    printf("p1: %s\n", text);
    close(file_descriptor[WRITE]);
}

void invert_letters(const int* fd_1, const int* fd_2)
{
    close(fd_1[WRITE]);
    close(fd_2[READ]);

    char text[50];
    read(fd_1[READ], text, sizeof(text));
    close(fd_1[READ]);
    printf("before p2: %s\n", text);

    if(strcmp(text, "exit") != 0)
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
    write(fd_2[WRITE], text, sizeof(text));
    printf("p2: %s\n", text);
    close(fd_2[WRITE]);
    if(strcmp(text, "exit") == 0)
    {
        exit(0);
    }
}

void multiply_digits(const int* fd_2)
{
    char text[50];
    close(fd_2[WRITE]);
    read(fd_2[READ], text, sizeof(text));
    close(fd_2[READ]);

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
    write(fd, text, sizeof(text));
    printf("p3: %s\n", text);
    close(fd);
    if (strcmp(text, "exit") == 0)
    {
        exit(0);
    }
}


int main() {
    int fd_1[2];
    pipe(fd_1);
    printf("fd_1: %d %d\n", fd_1[0], fd_1[1]);
    int fd_2[2];
    pipe(fd_2);
    printf("fd_2: %d %d\n", fd_2[0], fd_2[1]);

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
        do
        {
            char buffer[50];
            scanf("%s", buffer);
            send_string(fd_1, buffer);
            // printf("process1 - sends data\n");
            if(strcmp(buffer,"exit") == 0) exit(0);
        }while(1);
    }

    pid = fork();
    if(pid == 0 )
    {
        while(1)
        {
            invert_letters(fd_1, fd_2);
        }
    }

    while(1)
    {
        multiply_digits(fd_2);
    }

}
