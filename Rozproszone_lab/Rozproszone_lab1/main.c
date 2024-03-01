#include <stdio.h>
typedef enum {
    NEGATIVE_ONE = -1,
    ZERO = 0,
    ONE = 1
} IntRestricted;

typedef struct Node{
    int value;
    struct Node* next;
    struct Node* prev;
}Node;

typedef struct HashTable{
    int (*f_hash) (int);
    IntRestricted (*f_comp) (int);

}HashTable;


int main() {
    printf("Hello, World!\n");
    return 0;
}
