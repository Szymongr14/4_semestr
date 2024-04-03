#include <windows.h>
#include <stdio.h>
#include <time.h>

#define THREADS 2
#define ARRAY_SIZE 10000


typedef struct PARAMETERS PARAMETERS;
struct PARAMETERS
{
    int* array;
    int start;
    int end;
};
DWORD WINAPI ThreadRoutine(void* param)
{
    PARAMETERS* params = (PARAMETERS*)param;
    for(int i = params->start; i < params->end; i++) {
        params->array[i] = params->array[i] + 1;
    }
    return 0;
}
int main()
{
    HANDLE hHandles[10];
    DWORD ThreadId;

    int* array = (int*)malloc(ARRAY_SIZE * sizeof(int));

    for(int i = 0; i < ARRAY_SIZE; i++) {
        array[i] = 1;
    }

//    for(int i = 0; i < ARRAY_SIZE; i++) {
//        printf("%d", array[i]);
//    }


    clock_t start = clock();
    for (int i=0;i < THREADS;i++) {
        PARAMETERS params;
        params.array = array;
        params.start = ARRAY_SIZE / THREADS * i;
        params.end = ARRAY_SIZE / THREADS * (i + 1);
        hHandles[i] = CreateThread(NULL,0,ThreadRoutine,&params,0,&ThreadId);
        if (hHandles[i] == NULL) {
            fprintf(stderr,"Could not create Thread\n");
            exit(0);
        }
        else printf("Thread %d was created\n",ThreadId);
    }
    for (int i=0;i < 5;i++) {
        WaitForSingleObject(hHandles[i],INFINITE);
    }
    clock_t end = clock();
    double time_spent = (double)(end - start) / CLOCKS_PER_SEC;

//    for(int i = 0; i < ARRAY_SIZE; i++) {
//        printf("%d", array[i]);
//    }

    free(array);
    printf("Time spent: %f\n", time_spent);
    return 0;

}