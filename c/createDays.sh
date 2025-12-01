#!/bin/bash

for i in {1..12}; do
    dir="day$i"
    
    # Create directory if it doesn't exist
    mkdir -p "$dir"

    cfile="$dir/day$i.c"
    hfile="$dir/day$i.h"

    # Generate header file
    cat > "$hfile" <<EOF
#ifndef DAY${i}_H
#define DAY${i}_H

#include <stdlib.h>
#include <stdio.h>

void solve_day${i}_part1(FILE *input);
void solve_day${i}_part2(FILE *input);

#endif // DAY${i}_H
EOF

    # Generate C file
    cat > "$cfile" <<EOF
#include "day$i.h"

void solve_day${i}_part1(FILE *input) {
    // TODO: implement solution for Day $i Part 1
}

void solve_day${i}_part2(FILE *input) {
    // TODO: implement solution for Day $i Part 2
}
EOF

    echo "Generated: $dir/"
done

echo "All files created!"

