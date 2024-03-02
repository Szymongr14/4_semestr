#ifndef ROZPROSZONE_LAB1_HASH_TABLE_H
#define ROZPROSZONE_LAB1_HASH_TABLE_H

typedef struct {
    struct ValueNode* next;
    void*  value;
}ValueNode;

typedef struct {
    struct HashNode* next;
    struct HashNode* prev;
    ValueNode* value_head;
}HashNode;

typedef struct Manager{
    void* (*f_hash)(void*);
    int (*f_comp)(void*, void*);
    void (*print)(struct Manager*);
    HashNode* head;
} Manager;

void insert(Manager* manager, void* value);
void delete(Manager* manager, void* hash_value);
void* get(Manager* manager, void* hash_value);
void clear(Manager* manager);
#endif //ROZPROSZONE_LAB1_HASH_TABLE_H
