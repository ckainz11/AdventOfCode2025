#include <stdlib.h>
#include <stdio.h>

#include "util.h"

long ten_pow(int exp) {
    if (exp < 0) {
        fprintf(stderr, "Error: ten_pow requires positive exp, given: %d\n", exp);
        return 1;
    }
    if (exp == 0)
        return 1;

    return 10 * ten_pow(exp - 1);
}

int digit_to_int(char c) {
    return c - '0';
}
