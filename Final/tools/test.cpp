#include <stdio.h>

int main() {
    freopen("in.txt", "r", stdin);
    while (1) {
        printf("[%c]\n", getchar());
    }

    return 0;
}
