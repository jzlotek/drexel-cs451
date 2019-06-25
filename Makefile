CC=g++

FLAGS=-O3

src = $(wildcard src/*.cpp)
obj = $(src:src/%.cpp=bin/%.o)

OUT=bin
EXE=checkers

.PHONY: clean test

all: ${EXE}

test:
	@echo "Running tests"

${EXE}: $(obj)
	$(CC) -o $@ $^ $(FLAGS)

$(OUT)/%.o: src/%.cpp
	$(CC) $(FLAGS) -c -o $@ $<

clean:
	rm -f $(obj) ${EXE}
