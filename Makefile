CC = gcc
CFLAGS = -std=c99 -pedantic -Wall -D_DEFAULT_SOURCE -D_BSD_SOURCE -D_SVID_SOURCE -D_POSIX_C_SOURCE=200809L -g -pthread

DAYS := $(shell seq 1 12)
OBJS := $(foreach d,$(DAYS),day$(d)/day$(d).o)

.PHONY: all clean

all: aoc

aoc: aoc.o $(OBJS)
	$(CC) $(CFLAGS) -o $@ $^

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

clean:
	rm -rf *.o aoc $(OBJS)
