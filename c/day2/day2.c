#include "day2.h"

#include <string.h>

int count_digits(unsigned long num) {
	if (num < 0) {
		printf("count_digits needs positive num\n");
		exit(EXIT_FAILURE);
	}

	if (num < 10) return 1;
	else return 1 + count_digits(num / 10);
}

unsigned long ten_pow(int exp) {
	if (exp < 0) {
		printf("ten_pow needs positive exp\n");
		exit(EXIT_FAILURE);
	}

	if (exp == 0)
		return 1;
	else return 10 * ten_pow(exp - 1);
}

int repeats_n_times(unsigned long num, short n) {
	short digits = count_digits(num);

	if (digits % n != 0)
		return 0;

	short chunk_size = digits / n;
	unsigned long chunk = num % ten_pow(chunk_size);
	int div = 0;
	for (int i = 1; i < n; i++) {
		div += chunk_size;
		unsigned long next_chunk = num;
		next_chunk /= ten_pow(div);
		next_chunk %= ten_pow(chunk_size);

		if (next_chunk != chunk)
			return 0;	

		chunk = next_chunk;
	}

	return 1;
}

int repeats(unsigned long num) {
	short digits = count_digits(num);	
	for (int i = 2; i <= digits; i++) {
		if (repeats_n_times(num, i))		
			return 1;
	}
	return 0;
}

void solve(FILE *input, short part) {
	char *line = NULL;
	size_t size = 0;
	ssize_t len;

	len = getline(&line, &size, input);
	if (len == -1) {
		free(line);
		perror("getline");
		exit(EXIT_FAILURE);
	}

	char* range = strtok(line, ",");
	unsigned long sum = 0;
	
	while (range != NULL) {
		unsigned long min = strtoul(range, NULL, 10);
		char *maxPtr = strstr(range, "-") + 1;
		unsigned long max = strtoul(maxPtr, NULL, 10);

		for (unsigned long num = min; num <= max; num++) {
			if ((part == 1 && repeats_n_times(num, 2)) || (part == 2 && repeats(num)))
				sum += num;
		}
		
		range = strtok(NULL, ",");
	}
	
	free(line);
	printf("%lu\n", sum);
}

void solve_day2_part1(FILE *input) {
	solve(input, 1);
}

void solve_day2_part2(FILE *input) {
	solve(input, 2);
}

