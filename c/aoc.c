#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
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
void print_run_time(clock_t start, clock_t end);

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
	clock_t start1 = clock();
	switch(day) {
		case 1: solve_day1_part1(input); break;
		case 2: solve_day2_part1(input); break;
		case 3: solve_day3_part1(input); break;
		case 4: solve_day4_part1(input); break;
		case 5: solve_day5_part1(input); break;
		case 6: solve_day6_part1(input); break;
		case 7: solve_day7_part1(input); break;
		case 8: solve_day8_part1(input); break;
		case 9: solve_day9_part1(input); break;
		case 10: solve_day10_part1(input); break;
		case 11: solve_day11_part1(input); break;
		case 12: solve_day12_part1(input); break;
	}

	clock_t end1 = clock();
	print_run_time(start1, end1);

	fclose(input);
	FILE *input_part2 = open_input_file();

	printf("\nSolution Part 2:\n");
	clock_t start2 = clock();
	switch(day) {
		case 1: solve_day1_part2(input_part2); break;
		case 2: solve_day2_part2(input_part2); break;
		case 3: solve_day3_part2(input_part2); break;
		case 4: solve_day4_part2(input_part2); break;
		case 5: solve_day5_part2(input_part2); break;
		case 6: solve_day6_part2(input_part2); break;
		case 7: solve_day7_part2(input_part2); break;
		case 8: solve_day8_part2(input_part2); break;
		case 9: solve_day9_part2(input_part2); break;
		case 10: solve_day10_part2(input_part2); break;
		case 11: solve_day11_part2(input_part2); break;
		case 12: solve_day12_part2(input_part2); break;
	}

	clock_t end2 = clock();
	print_run_time(start2, end2);
	
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

void print_run_time(clock_t start, clock_t end) {
	double time = (double) (end - start) / CLOCKS_PER_SEC;
	if (time >= 1)
		printf("in %f s\n", time);
	else
		printf("in %f ms\n", time * 1000);
}

