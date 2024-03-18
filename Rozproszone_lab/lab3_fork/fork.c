#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

void tree(int n){

    if (n==0){
//       execlp("ps", "ps", "-u", "szymongrrr", "--forest", NULL);
       return;
    }

    int right_child=fork();
    if (right_child!=0){
        int left_child=fork();
    }
    else tree(n-1);

}

int main(int argc, char *argv[]){
    int firstArgInt = atoi(argv[1]);
    printf("%d\n", firstArgInt);

    if (fork()==0) tree(firstArgInt);
    else{
        execlp("ps", "ps","--forest", NULL);
    }
    sleep(1);
}

