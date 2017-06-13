#include <stdio.h>

int main() {
    freopen("in.txt", "r", stdin);
    freopen("out.txt", "w", stdout);
    char c;
    c = getchar();
    while (c != EOF) {
        if (c == '(')
            printf("\t");
        else if (c == ')')
            ;
        else
            printf("%c", c);
        c = getchar();
    }
    return 0;12547
}
