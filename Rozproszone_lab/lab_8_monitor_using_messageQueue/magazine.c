#include "magazine.h"
#include <stdio.h>
#include "monitor.h"
#include <stdlib.h>
#include <unistd.h>

struct magazine_t* create_magazine(const int max_size, struct monitor_t* monitor, char* filename)
{
    struct magazine_t* magazine = malloc(sizeof(struct magazine_t));
    magazine->filename = filename;
    magazine->max_size = max_size;
    magazine->monitor = monitor;

    FILE* file_ptr = fopen(filename, "w");
    fprintf(file_ptr, "%d", 0);
    fclose(file_ptr);

    return magazine;
}


void produce(struct magazine_t* magazine)
{
    enter(magazine->monitor, LOCK_PRODUCE_MESSAGE_TYPE);
    while(read_value_from_file(magazine->filename) == magazine->max_size)
    {
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
    while(read_value_from_file(magazine->filename) == 0)
    {
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
    const int current_value = read_value_from_file(magazine->filename);
    FILE* file = fopen(magazine->filename, "w");
    switch (operation)
    {
        case 1:
            fprintf(file, "%d", current_value + 1);
            break;
        case 2:
            fprintf(file, "%d", current_value - 1);
            break;
    }
    // fflush(file);
    fclose(file);
    leave(magazine->monitor, LOCK_MODIFY_MESSAGE_TYPE);
}

int read_value_from_file(const char* filename) {
    FILE* file = fopen(filename, "r");
    if (file == NULL) {
        printf("Error opening file!\n");
        exit(1);
    }
    int value;
    fscanf(file, "%d", &value);
    fclose(file);
    printf("READ_VALUE: %d\n", value);
    return value;
}
