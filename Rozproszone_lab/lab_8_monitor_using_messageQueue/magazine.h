#ifndef MAGAZINE_H
#define MAGAZINE_H

#include "monitor.h"

struct magazine_t{
    int max_size;
    char* filename;
    struct monitor_t* monitor;
};

struct magazine_t* create_magazine(int max_size, struct monitor_t* monitor, char* filename);
void produce(struct magazine_t* magazine);
void consume(const struct magazine_t* magazine);
void modify_magazine(const struct magazine_t* magazine, int operation);
int read_value_from_file(const char* filename);

#endif //MAGAZINE_H
