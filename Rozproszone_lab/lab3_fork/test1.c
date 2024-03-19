#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>
#include <stdlib.h>

int main(int argc, char *argv[]){
    if (argc != 2){
        printf("Provide height of a tree!");
        return 1;
    }
    int lvl = atoi(argv[1]);
    for (int i = 0; i < lvl; i++)
    {
        int pid = fork();
        if (pid == 0){
            if (i == lvl - 1)
            {
                execlp("ps", "-u root", "-l", "--forest", NULL);
            }
        }
        else{
            if(i % 2 == 0)
                fork();
            waitpid(pid, NULL, 0);
            return 0;
        }
    }
    return 0;
}