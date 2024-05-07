#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

int main()
{
    char buff[50];
    const int fd = open("mainfifo", O_RDONLY);
    read(fd, buff, sizeof(buff));
    printf("RECIVED TEXT: %s\n", buff);

    return 0;
}