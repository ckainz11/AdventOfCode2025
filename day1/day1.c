#include "day1.h"

int adjust_dial(int dial) {
	if (dial > 99) return dial % 100;
	else if (dial < 0) return 100 + dial;
	else return dial;
	
}

void solveDay1PartOne(FILE *input) {
	char *line = NULL;
	size_t size = 0;
	size_t len;

	short dial = 50;
	int count = 0;

	while( (len = getline(&line, &size, input)) != -1) {
		char* distance = &line[1];

		if (line[0] == 'R') dial += atoi(distance);
		else dial -= atoi(distance);

		dial = adjust_dial(dial);
		if (dial == 0)
			count++;
	}

	free(line);
	printf("%d\n", count);
}


void solveDay1PartTwo(FILE *input) {
	char *line = NULL;
	size_t size = 0;
	size_t len;

	short dial = 50;
	int count = 0;

	while( (len = getline(&line, &size, input)) != -1) {
		int distance = atoi(&line[1]);
		int old_dial = dial;

		int rotations = distance / 100;
		distance = distance % 100;

		if (line[0] == 'R') dial += distance;
		else dial -= distance;

		if (dial > 100 || dial < 0) {
			if (old_dial != 0)
				count++;		
		}

		dial = adjust_dial(dial);
		if (dial == 0)
			count++;



		count += rotations;
	}

	free(line);
	printf("%d\n", count);
}
