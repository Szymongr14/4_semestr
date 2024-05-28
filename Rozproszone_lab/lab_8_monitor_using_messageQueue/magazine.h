#ifndef MAGAZINE_H
#define MAGAZINE_H
#include "monitor.h"

struct magazine_t{
    int max_size;
    int* space;
    int current_size;
    struct monitor_t* monitor;
};

struct magazine_t* create_magazine(int max_size, struct monitor_t* monitor);
void produce(struct magazine_t* magazine);
void consume(const struct magazine_t* magazine);
void modify_magazine(const struct magazine_t* magazine, int operation);
int return_size(const struct magazine_t* magazine);

#endif //MAGAZINE_H
