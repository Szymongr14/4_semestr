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
    close(file_descriptor[READ]);
    write(file_descriptor[WRITE], text, sizeof(text));
    close(file_descriptor[WRITE]);
}

void invert_letters(const int* file_descriptor)
{
    char text[50];
    read(file_descriptor[READ], text, sizeof(text));
    close(file_descriptor[READ]);

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
    write(file_descriptor[WRITE], text, sizeof(text));
    close(file_descriptor[WRITE]);
}

void multiply_digits(const int* file_descriptor)
{
    char text[50];
    close(file_descriptor[WRITE]);
    read(file_descriptor[READ], text, sizeof(text));
    close(file_descriptor[READ]);

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
    close(fd);
}


int main() {
    int fd[2];
    pipe(fd);
    pid_t pid = fork();

    if(mkfifo("mainfifo", 0777) == -1)
    {
        if(errno != EEXIST)
        {
            fprintf(stderr, "Error while creating named pipe");
        }
    }

    if(pid == 0)
    {
        char buffer[50];
        scanf("%s", buffer);
        send_string(fd, buffer);
        exit(0);
    }

    pid = fork();
    if(pid == 0)
    {
        invert_letters(fd);
        exit(0);
    }

    pid = fork();
    if(pid == 0)
    {
        multiply_digits(fd);
        exit(0);
    }

    return 0;
}
