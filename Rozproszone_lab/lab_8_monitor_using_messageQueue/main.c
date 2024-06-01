#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <bits/signum-arch.h>

#include "monitor.h"
#include "magazine.h"

#define MAX_MAGAZINE_SIZE 2
#define FILE_NAME "tmp_filecreting"

int main()
{
    struct monitor_t* monitor = create_monitor();
    struct magazine_t* magazine = create_magazine(MAX_MAGAZINE_SIZE, monitor, FILE_NAME);

    srand(time(NULL));

    const pid_t consumer_pid = fork();
    if(consumer_pid == 0)
    {
        printf("Consumer started...\n");
        for(;;)
        {
            consume(magazine);
            const int sleep_seconds = rand() % 10 + 1;
            printf("Consumer will sleep for %d seconds\n", sleep_seconds);
            sleep(sleep_seconds);
        }
    }

    const pid_t producer_pid = fork();
    if(producer_pid == 0)
    {
        printf("Producer started...\n");
        for(;;)
        {
            produce(magazine);
            const int sleep_seconds = rand() % 30 + 1;
            printf("Producer will sleep for %d seconds\n", sleep_seconds);
            sleep(sleep_seconds);
        }
    }

    char buffer[128];
    do{
        printf("Type 'quit' to exit program: \n");
        fgets(buffer, 128, stdin);
    }while(strcmp(buffer, "quit\n") != 0);

    printf("Closing...\n");
    kill(producer_pid, SIGSTOP);
    kill(consumer_pid, SIGSTOP);

    clear_message_queue(monitor);
    free(monitor);
    free(magazine);
    return 0;
}
