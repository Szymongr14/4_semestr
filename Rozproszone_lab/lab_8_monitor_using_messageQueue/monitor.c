#include "monitor.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/msg.h>

struct monitor_t* create_monitor()
{
    struct my_msg init_msg;
    struct monitor_t* monitor = malloc(sizeof(struct monitor_t));

    // creating unique key for locking queue
    const key_t entry_key = ftok("entry_queue", 65);

    //creating unique key for condition queue
    const key_t condition_key = ftok("condition_queue", 65);

    // creating message queues
    monitor->entry_queue_id = msgget(entry_key, 0666 | IPC_CREAT);
    monitor->condition_queue_id = msgget(condition_key, 0666 | IPC_CREAT);

    // creating initial message for locking queue
    init_msg.message_type = LOCK_CONSUME_MESSAGE_TYPE;
    strcpy(init_msg.text, "Initial message");
    msgsnd(monitor->entry_queue_id, &init_msg, sizeof(init_msg.text), 0);

    init_msg.message_type = LOCK_PRODUCE_MESSAGE_TYPE;
    strcpy(init_msg.text, "Initial message");
    msgsnd(monitor->entry_queue_id, &init_msg, sizeof(init_msg.text), 0);

    init_msg.message_type = LOCK_MODIFY_MESSAGE_TYPE;
    strcpy(init_msg.text, "Initial message");
    msgsnd(monitor->entry_queue_id, &init_msg, sizeof(init_msg.text), 0);

    return monitor;
}

void enter(const struct monitor_t* monitor, const int msg_type) {
    // printf("Enter\n");

    struct my_msg msg;
    msgrcv(monitor->entry_queue_id, &msg, sizeof(msg.text), msg_type, 0);
}

void leave(const struct monitor_t* monitor, const int msg_type) {
    // printf("Leave\n");

    struct my_msg msg;
    msg.message_type = msg_type;
    strcpy(msg.text, "Leave message");
    msgsnd(monitor->entry_queue_id, &msg, sizeof(msg.text), 0);
}

void wait(struct monitor_t* monitor) {
    // printf("Wait\n");

    // sending message that process is started waiting
    struct my_msg wait_msg;
    wait_msg.message_type = PROCESS_IS_WAITING_TYPE;
    strcpy(wait_msg.text, "Wait message");
    msgsnd(monitor->condition_queue_id, &wait_msg, sizeof(wait_msg.text), 0);

    struct my_msg msg;
    msgrcv(monitor->condition_queue_id, &msg, sizeof(msg.text), CONDITION_MESSAGE_TYPE, 0);
}

void notify(const struct monitor_t* monitor) {
    // printf("Notify\n");

    // checking if any process is curently waiting
    struct msqid_ds buf;
    msgctl(monitor->condition_queue_id, IPC_STAT, &buf);
    if(buf.msg_qnum == 0) return;

    struct my_msg msg;
    msgrcv(monitor->condition_queue_id, &msg, sizeof(msg.text), PROCESS_IS_WAITING_TYPE, 0);
    strcpy(msg.text, "Notify message");
    msg.message_type = CONDITION_MESSAGE_TYPE;
    msgsnd(monitor->condition_queue_id, &msg, sizeof(msg.text), 0);
}

void clear_message_queue(const struct monitor_t* monitor)
{
    struct my_msg message;
    while(msgrcv(monitor->condition_queue_id, &message, sizeof(message.text), CONDITION_MESSAGE_TYPE, IPC_NOWAIT) != -1){}
    while(msgrcv(monitor->entry_queue_id, &message, sizeof(message.text), LOCK_CONSUME_MESSAGE_TYPE, IPC_NOWAIT) != -1){}
    while(msgrcv(monitor->entry_queue_id, &message, sizeof(message.text), LOCK_PRODUCE_MESSAGE_TYPE, IPC_NOWAIT) != -1){}
}
