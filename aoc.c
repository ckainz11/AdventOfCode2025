#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "day1/day1.h"
#include "day2/day2.h"
#include "day3/day3.h"
#include "day4/day4.h"
#include "day5/day5.h"
#include "day6/day6.h"
#include "day7/day7.h"
#include "day8/day8.h"
#include "day9/day9.h"
#include "day10/day10.h"
#include "day11/day11.h"
#include "day12/day12.h"

void run(void);
FILE *open_input_file(void);
void usage(int retcode) {
	printf("Usage: aoc -d day [-x]\n");
	exit(retcode);
}

int day = 0;
int runExample = -1;


int main(int argc, char *argv[]) {
	int c;
	while ( (c = getopt(argc, argv, "d:x")) != -1) {
		switch(c) {	
			case 'd': {
				if (day != 0)
					usage(EXIT_FAILURE);
				else {
					day = atoi(optarg);
					if (day == 0) {
						perror("atoi");
						usage(EXIT_FAILURE);
					}
				}
				break;
			}
			case 'x': {
				if (runExample == -1)
					runExample = 1;
				else usage(EXIT_FAILURE);
				break;
			}
			default: {
				usage(EXIT_FAILURE);
				break;
			}
		}
	}

	if (runExample == -1)
		runExample = 0;
	
	if (day <= 0 || day > 12)
		usage(EXIT_FAILURE);

	run();

	return 0;
}

void run(void) {
	FILE *input = open_input_file();

	if (!input)
		exit(EXIT_FAILURE);
	
	printf("Solution Part 1:\n");
	switch(day) {
		case 1: solveDay1PartOne(input); break;
		case 2: solveDay2PartOne(input); break;
		case 3: solveDay3PartOne(input); break;
		case 4: solveDay4PartOne(input); break;
		case 5: solveDay5PartOne(input); break;
		case 6: solveDay6PartOne(input); break;
		case 7: solveDay7PartOne(input); break;
		case 8: solveDay8PartOne(input); break;
		case 9: solveDay9PartOne(input); break;
		case 10: solveDay10PartOne(input); break;
		case 11: solveDay11PartOne(input); break;
		case 12: solveDay12PartOne(input); break;
	}


	fclose(input);
	FILE *input_part2 = open_input_file();

	printf("Solution Part 2:\n");
	switch(day) {
		case 1: solveDay1PartTwo(input_part2); break;
		case 2: solveDay2PartTwo(input_part2); break;
		case 3: solveDay3PartTwo(input_part2); break;
		case 4: solveDay4PartTwo(input_part2); break;
		case 5: solveDay5PartTwo(input_part2); break;
		case 6: solveDay6PartTwo(input_part2); break;
		case 7: solveDay7PartTwo(input_part2); break;
		case 8: solveDay8PartTwo(input_part2); break;
		case 9: solveDay9PartTwo(input_part2); break;
		case 10: solveDay10PartTwo(input_part2); break;
		case 11: solveDay11PartTwo(input_part2); break;
		case 12: solveDay12PartTwo(input_part2); break;
	}
	
	fclose(input);
}

FILE *open_input_file(void) {
    char filename[64]; // large enough

    if (runExample) {
        snprintf(filename, sizeof(filename), "input/day%d_example.in", day);
    } else {
        snprintf(filename, sizeof(filename), "input/day%d.in", day);
    }

    FILE *fp = fopen(filename, "r");
    if (!fp) {
        perror(filename);
        return NULL;
    }

    return fp;
}

