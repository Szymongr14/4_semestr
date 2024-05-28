#ifndef MONITOR_H
#define MONITOR_H

#define MAX_LENGTH 128
// locking queue
#define LOCK_CONSUME_MESSAGE_TYPE 1 //used for locking consume method
#define LOCK_PRODUCE_MESSAGE_TYPE 2 // used for locking produce method
#define LOCK_MODIFY_MESSAGE_TYPE 3 // used for locking modify method

// condition queue
#define CONDITION_MESSAGE_TYPE 1 // used for condition variable - notify
#define PROCESS_IS_WAITING_TYPE 2 // used for process waiting for condition variable - wait

struct my_msg
{
    long int message_type;
    char text[MAX_LENGTH];
};

struct monitor_t
{
    int entry_queue_id;
    int condition_queue_id;
};

struct monitor_t* create_monitor(); // constructor for monitor
void enter(const struct monitor_t* monitor, int msg_type);
void leave(const struct monitor_t* monitor, int msg_type);
void wait(struct monitor_t* monitor);
void notify(const struct monitor_t* monitor);
void clear_message_queue(const struct monitor_t* monitor);
#endif //MONITOR_H
