#include "hash_table.h"
#include <stdio.h>
#include <malloc.h>


void add_to_value_node(void* value, HashNode* temp_node){
    ValueNode* temp_value_node = temp_node->value_head;
    while(temp_value_node->next != NULL){
        temp_value_node = temp_value_node->next;
    }
    ValueNode* new_value_node = malloc((sizeof(ValueNode)));
    temp_value_node->next = new_value_node;
    new_value_node->value = value;
    new_value_node->next = NULL;
}


void add_new_hash_node_in_the_middle(HashNode* temp_node, void* value) {
    //if there's only one HashNode
    if(temp_node->next == NULL){
        HashNode* new_node = malloc(sizeof(HashNode));
        temp_node->prev = new_node;
        new_node->prev = NULL;
        new_node->next = temp_node;
        new_node->value_head = malloc(sizeof(ValueNode));
        new_node->value_head->next = NULL;
        new_node->value_head->value = value;

    }
    else{
        HashNode* new_node = malloc(sizeof(HashNode));
        HashNode* prev_node = temp_node->prev;
        prev_node->next = new_node;
        new_node->prev = prev_node;
        new_node->next = temp_node;
        temp_node->prev = new_node;
        new_node->value_head = malloc(sizeof(ValueNode));
        new_node->value_head->next = NULL;
        new_node->value_head->value = value;
    }
}

void add_new_hash_node_at_the_end(HashNode* temp_node, void* value){
    HashNode* new_node = malloc(sizeof(HashNode));
    temp_node->next = new_node;
    new_node->prev = temp_node;
    new_node->next = NULL;
    new_node->value_head = malloc(sizeof(ValueNode));
    new_node->value_head->next = NULL;
    new_node->value_head->value = value;
}


int check_hash_order(Manager* manager, void* value, HashNode* temp_node){
    void* value_hash_ptr = manager->f_hash(value);
    void* temp_node_value_hash_ptr = manager->f_hash(temp_node->value_head->value);
    int f_comp_result = manager->f_comp(value_hash_ptr,temp_node_value_hash_ptr);
    free(value_hash_ptr);
    free(temp_node_value_hash_ptr);
    return f_comp_result;
}


void* get(Manager* manager, void* hash_value){

    return NULL;
}


void insert(Manager* manager , void* value){
    //if there's no HashNodes
   if(manager->head == NULL){
         manager->head = malloc(sizeof(HashNode));
         manager->head->next = NULL;
         manager->head->prev = NULL;
         manager->head->value_head = malloc(sizeof(ValueNode));
         manager->head->value_head->next = NULL;
         manager->head->value_head->value = value;
         return;
   }
   else{
       HashNode* temp_node = manager->head;
       //iterating through the list of hash nodes
       while(temp_node->next != NULL){
           int f_comp_result = check_hash_order(manager, value, temp_node);
           //if hash value is the same
           if(f_comp_result == 0){
               add_to_value_node(value, temp_node);
               return;
           }
           //if current hash value is greater
           else if(f_comp_result == 1){
               add_new_hash_node_in_the_middle(temp_node, value);
               return;
           }
           temp_node = temp_node->next;
       }
       //if there's only one hash node
       if(temp_node->next == NULL && temp_node->prev == NULL){
          int f_comp_result = check_hash_order(manager, value, temp_node);
          if(f_comp_result == 0){
            add_to_value_node(value, temp_node);
            return;
          }
          else if(f_comp_result == 1){
            add_new_hash_node_in_the_middle(temp_node, value);
            return;
          }
          else if(f_comp_result == -1){
              add_new_hash_node_at_the_end(temp_node, value);
              return;
          }
       }
       // check last Hash Node
       int f_comp_result = check_hash_order(manager, value, temp_node);
       if(f_comp_result == 0){
           add_to_value_node(value, temp_node);
           return;
       }
       else if(f_comp_result == 1){
           add_new_hash_node_in_the_middle(temp_node, value);
           return;
       }
       add_new_hash_node_at_the_end(temp_node, value);
       return;
   }
}


void delete(Manager* manager, void* hash_value){

}

void clear(Manager* manager){
    HashNode* temp_node = manager->head;
    while(temp_node != NULL){
        ValueNode* temp_value_node = temp_node->value_head;
        while(temp_value_node->next != NULL){
            ValueNode* next_value_node = temp_value_node->next;
            free(temp_value_node);
            temp_value_node = next_value_node;
        }
        HashNode* next_hash_node = temp_node->next;
        free(temp_node);
        temp_node = next_hash_node;
    }

}
