#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>

int main()
{
    char buff[50];
    const int fd = open("mainfifo", O_RDONLY);
    read(fd, buff, sizeof(buff));
    if(strcmp(buff, "exit") == 0) return 0;
    printf("RECIVED TEXT: %s\n", buff);

    return 0;
}