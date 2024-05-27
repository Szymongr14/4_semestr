#include <stdio.h>
#include <sys/types.h>
#include <sys/msg.h>
#include <sys/ipc.h>
#define MAX_LENGTH 100

struct my_msg
{
    long int message_type;
    char text[MAX_LENGTH];
};

struct monitor
{
    int entry_queue_id;
    int condition_queue_id;
    int condition_counter;
};

int main()
{
    printf("elo\n");
    return 0;
}
