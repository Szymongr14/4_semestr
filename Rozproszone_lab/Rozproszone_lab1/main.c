#include <stdio.h>
#include <malloc.h>
#include "hash_table.h"

void print(Manager* manager){

    HashNode* temp_node = manager->head;
    while(temp_node != NULL){
        void* value_hash_ptr = manager->f_hash(temp_node->value_head->value);
        printf("HashNode[%d]: ", *(int*)value_hash_ptr);
        free(value_hash_ptr);
        ValueNode* temp_value_node = temp_node->value_head;
        while(temp_value_node->next != NULL){
            printf("%d -> ", *((int*)temp_value_node->value));
            temp_value_node = temp_value_node->next;
        }
        printf("%d\n", *((int*)temp_value_node->value));
        temp_node = temp_node->next;
    }

}

int f_comp(void* hash1, void* hash2){

    int* int_hash1 = (int*)hash1;
    int* int_hash2 = (int*)hash2;
    if(*int_hash1 == *int_hash2) return 0;
    if(*int_hash1 < *int_hash2) return 1;
    return -1;
}


void* f_hash(void* value) {
    int* int_value = (int*)value;
    int* hash_value = malloc(sizeof(int));
    *hash_value = *int_value % 7;
    return hash_value;
}


int main() {
    printf("Hello, World!\n");

    int (*f_comp_ptr)(void*, void*) = &f_comp;
    void (*print_ptr)(Manager*) = &print;
    void* (*f_hash_pointer)(void*) = &f_hash;

    Manager manager = {f_hash_pointer, f_comp_ptr, print_ptr, NULL};


    int x = 4;
    int y = 5;
    int z = 6;
    int x1 = 11;
    insert(&manager, (void *) &x);
    insert(&manager, (void *) &y);
    insert(&manager, (void *) &z);
    insert(&manager, (void *) &x1);
    print(&manager);
    clear(&manager);
    return 0;
}
