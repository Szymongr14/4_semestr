#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <bits/signum-arch.h>

#include "monitor.h"
#include "magazine.h"

#define MAX_MAGAZINE_SIZE 3

int main()
{
    struct monitor_t* monitor = create_monitor();
    struct magazine_t* magazine = create_magazine(MAX_MAGAZINE_SIZE, monitor);

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

    sleep(1);
    const pid_t producer_pid = fork();
    if(producer_pid == 0)
    {
        printf("Producer started...\n");
        for(;;)
        {
            produce(magazine);
            const int sleep_seconds = rand() % 5 + 1;
            printf("Producer will sleep for %d seconds\n", sleep_seconds);
            sleep(sleep_seconds);
            printf("PR: %d\n", return_size(magazine));
        }
    }

    char buffer[128];
    do{
        printf("Type 'quit' to exit program: \n");
        printf("PR: %d\n", return_size(magazine));
        fgets(buffer, 128, stdin);
    }while(strcmp(buffer, "quit\n") != 0);

    printf("Closing...\n");
    kill(producer_pid, SIGSTOP);
    kill(consumer_pid, SIGSTOP);

    clear_message_queue(monitor);
    free(monitor);
    free(magazine->space);
    free(magazine);
    return 0;
}
