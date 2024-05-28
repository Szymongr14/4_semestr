#ifndef MONITOR_H
#define MONITOR_H

#define MAX_LENGTH 128
#define LOCK_CONSUME_MESSAGE_TYPE 1
#define LOCK_PRODUCE_MESSAGE_TYPE 2
#define LOCK_MODIFY_MESSAGE_TYPE 3
#define CONDITION_MESSAGE_TYPE 1

struct my_msg
{
    long int message_type;
    char text[MAX_LENGTH];
};

struct monitor_t
{
    int entry_queue_id;
    int condition_queue_id;
    int condition_counter;
};

struct monitor_t* create_monitor(); // constructor for monitor
void enter(const struct monitor_t* monitor, int msg_type);
void leave(const struct monitor_t* monitor, int msg_type);
void wait(struct monitor_t* monitor);
void notify(const struct monitor_t* monitor);
void clear_message_queue(const struct monitor_t* monitor);
#endif //MONITOR_H
