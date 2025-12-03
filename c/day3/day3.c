#include "day3.h"

#include <string.h>

unsigned long pow(int exp) {
	if (exp < 0) {
		printf("error: ten_pow requires positive exp\n");
		return 1;
	}

	if (exp == 0)
		return 1;
	return 10 * pow(exp - 1);
}

int find_max(char *line, int start, int space) {
	int max = 0;
	int index = 0;
	int end = strlen(line) - space - 1; // -1 for \n
	for (int i = start + 1; i < end; i++) {
		int digit = line[i] - '0';
		if (digit > max) {
			max = digit;
			index = i;
		}
	}
	return index;
}

int char_to_int(char c) {
	return c - '0';
}

void solve_day3(FILE *input, int num_length) {
	char *line = NULL;
	size_t size = 0;
	ssize_t len;	

	unsigned long sum = 0;
	while ( (len = getline(&line, &size, input)) != -1) {
		int last_index = -1;
		unsigned long num = 0;
		for (int i = 0; i < num_length; i++) {
			int room = num_length - i - 1;
			int idx = find_max(line, last_index, room);
			int digit = char_to_int(line[idx]);
			num += digit * pow(room);	
			last_index = idx;
		}
		sum += num;
	}
	
	free(line);
	printf("%lu\n", sum);
}

void solve_day3_part1(FILE *input) {
	solve_day3(input, 2);
}

void solve_day3_part2(FILE *input) {
	solve_day3(input, 12);
}
