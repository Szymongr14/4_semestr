#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>

int main()
{
    const int fd = open("mainfifo", O_RDONLY);
    while(1){
        char buff[50];
        read(fd, buff, sizeof(buff));
        printf("RECIVED TEXT: %s\n", buff);
        if(strcmp(buff, "exit\n") == 0)
        {
            printf("Detected 'exit'\nClosing...");
            close(fd);
            return 0;
        }
    }
}