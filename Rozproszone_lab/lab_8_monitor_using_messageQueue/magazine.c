#include "magazine.h"
#include <stdio.h>
#include "monitor.h"
#include <stdlib.h>
#include <unistd.h>

struct magazine_t* create_magazine(const int max_size, struct monitor_t* monitor)
{
    struct magazine_t* magazine = malloc(sizeof(struct magazine_t));
    magazine->space = malloc(max_size * sizeof(int));
    magazine->max_size = max_size;
    magazine->monitor = monitor;
    return magazine;
}


void produce(struct magazine_t* magazine)
{
    enter(magazine->monitor, LOCK_PRODUCE_MESSAGE_TYPE);
    while(return_size(magazine) == magazine->max_size)
    {
        printf("PRODUCTS: %d\n", magazine->current_size);
        printf("Waiting for space\n");
        wait(magazine->monitor);
    }
    modify_magazine(magazine, 1);
    printf("Produced product\n");
    notify(magazine->monitor);
    leave(magazine->monitor, LOCK_PRODUCE_MESSAGE_TYPE);
}

void consume(const struct magazine_t* magazine)
{
    enter(magazine->monitor, LOCK_CONSUME_MESSAGE_TYPE);
    while(return_size(magazine) == 0)
    {
        printf("PRODUCTS: %d\n", magazine->current_size);
        printf("Waiting for product\n");
        wait(magazine->monitor);
    }
    modify_magazine(magazine, 2);
    printf("Consumed product\n");
    notify(magazine->monitor);
    leave(magazine->monitor, LOCK_CONSUME_MESSAGE_TYPE);
}

void modify_magazine(const struct magazine_t* magazine, const int operation)
{
    enter(magazine->monitor, LOCK_MODIFY_MESSAGE_TYPE);
    printf("modify");
    switch (operation)
    {
    case 1:
        for(int i = 0; i < magazine->max_size; i++)
        {
            if(magazine->space[i] == 0)
            {
                magazine->space[i] = 1;
                break;
            }
        }
        break;

    case 2:
        for(int i = 0; i < magazine->max_size; i++)
        {
            if(magazine->space[i] == 1)
            {
                magazine->space[i] = 0;
                break;
            }
        }
        break;
    }
    leave(magazine->monitor, LOCK_MODIFY_MESSAGE_TYPE);
}

int return_size(const struct magazine_t* magazine)
{
    int counter = 0;
    for (int i = 0; i < magazine->max_size; i++)
    {
        if(magazine->space[i] == 1)
        {
            counter++;
        }
    }
    return counter;
}
